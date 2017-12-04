package com.se.courses.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.se.courses.dao.CourseDao;
import com.se.courses.dao.CourseDetailDao;
import com.se.courses.dao.ExperimentDao;
import com.se.courses.dao.ExperimentDetailDao;
import com.se.courses.dao.StudentDao;
import com.se.courses.dao.TeacherDao;
import com.se.courses.entity.Course;
import com.se.courses.entity.CourseDetail;
import com.se.courses.entity.Experiment;
import com.se.courses.entity.ExperimentDetail;
import com.se.courses.entity.Student;
import com.se.courses.entity.Teacher;
import com.se.courses.exception.CourseException;
import com.se.courses.util.ExcelUtils;
import com.se.courses.util.FileUtils;
import com.se.courses.util.MD5;
import com.se.courses.util.StringUtils;

@Service
@Transactional
public class CourseService {
	@Autowired
	private TeacherDao teacherDao;
	@Autowired
	private CourseDao courseDao;
	@Autowired
	private ExperimentDao experimentDao;
	@Autowired
	private StudentDao studentDao;
	@Autowired
	private CourseDetailDao courseDetailDao;
	@Autowired
	private ExperimentDetailDao experimentDetailDao;
	
	
	public void addCourse(Course course) {
		courseDao.persist(course);
	}
	
	public Course getCourse(long courseId) {
		return courseDao.find(courseId);
	}
	
	public Experiment getExperiment(long expId) {
		return experimentDao.find(expId);
	}
	
	public List<Experiment> geExperiments(long courseId) {
		Course course = courseDao.find(courseId);
		return new ArrayList<>(course.getExperiments());
	}
	
	
	public void addExp(long courseId, Experiment experiment) {
		Course course = new Course(courseId);
		experiment.setCourse(course);
		experimentDao.persist(experiment);
	}
	
	public List<Course> getCourses(long userId) {
		Teacher teacher = teacherDao.find(userId);
		return new ArrayList<>(teacher.getCourses());
	}
	
	public List<Student> batchingStudentByExcelFile(long courseId, MultipartFile file) {
		List<Student> students = null;
		try (InputStream is = file.getInputStream()) {
			students = ExcelUtils.getExcel(is);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			throw new CourseException("批量导入错误！", e);
		}
		
		if (students == null || students.size() == 0) {
			throw new CourseException("导入的学生人数为零");
		}
		
		Course course = courseDao.find(courseId);
		// 清空course原detail
		if (course.getCourseDetails().size() > 0) {
			for (CourseDetail d : course.getCourseDetails()) {
				courseDetailDao.delete(d);
			}
		}
		
		for (int i = 0; i < students.size(); i++) {
			Student s = students.get(i);
			Student oldStu = studentDao.find(s.getNumber());
			// not exist
			if (oldStu == null) {
				students.get(i).setPassword(MD5.generateMD5(students.get(i).getNumber()));
				studentDao.persist(students.get(i));
			} else {
				students.set(i, oldStu);
			}
			CourseDetail detail = new CourseDetail();
			detail.setCourse(course);
			detail.setStudent(students.get(i));
			courseDetailDao.persist(detail);
		}
		return students;
	}
	
	public void addExpFile(long expId, MultipartFile file, long userId) {
		
		Experiment experiment = experimentDao.find(expId);
		Student student = studentDao.find(userId);
		String extension = StringUtils.getFileExtension(file.getOriginalFilename());
		String courseDir = FileUtils.getCourseDir(experiment.getCourse().getId(), experiment.getCourse().getName());
		String expDir = FileUtils.getExpDir(expId, experiment.getName());
		String fileName = FileUtils.getFileName(experiment.getName(), student.getName(), student.getNumber(), extension);
		FileUtils.createExpFile(courseDir, expDir, fileName, file);
		
		
		ExperimentDetail detail = experimentDetailDao.find(userId, expId);
		if (detail == null) {
			detail = new ExperimentDetail();
		}
		detail.setExperiment(experiment);
		detail.setStudent(student);
		detail.setFile(fileName);
		detail.setCompleteTime(new Date());
		experimentDetailDao.persist(detail);
	}
	/**
	 * 指定实验的，未提交学生
	 * @param expId
	 * @return
	 */
	public List<Student> getStudentsForUnsubmited(long expId) {
		return courseDetailDao.getStudents(expId);
	}
	
	public ResponseEntity<byte[]> dowlnoadExpFile(long expId, long userId) {
		Experiment experiment = experimentDao.find(expId);
		
		String courseDir = FileUtils.getCourseDir(experiment.getCourse().getId(), experiment.getCourse().getName());
		String expDir = FileUtils.getExpDir(expId, experiment.getName());
		ExperimentDetail detail = experimentDetailDao.find(userId, expId);
		
		return FileUtils.getFile(courseDir, expDir, detail.getFile());
	}
	
}
