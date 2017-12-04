package com.se.courses.controller;

import static com.se.courses.controller.ControllerMap.REDIRECT;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.se.courses.controller.ControllerMap.AdminRequestMap;
import com.se.courses.controller.ControllerMap.AdminViewMap;
import com.se.courses.entity.Course;
import com.se.courses.entity.Experiment;
import com.se.courses.entity.Teacher;
import com.se.courses.entity.User;
import com.se.courses.service.CourseService;
import com.se.courses.util.FileUtils;


@Controller
public class AdminController {
	@Autowired
	private CourseService courseService;
	
	
	@RequestMapping(AdminRequestMap.LIST_COURSES)
	public String listCourses(@SessionAttribute(ControllerMap.USER) User user, Model model) {
		List<Course> courses = courseService.getCourses(user.getId());
		model.addAttribute("courses", courses);
		return AdminViewMap.LIST_COURSE;
	}
	
	@RequestMapping(path = AdminRequestMap.ADD_COURSE, method = RequestMethod.POST)
	public String addCourse(@SessionAttribute(ControllerMap.USER) User user, Course course, HttpSession session) {
		long userId = user.getId();
		course.setTeacher(new Teacher(userId));
		courseService.addCourse(course);
		return REDIRECT + AdminViewMap.LIST_COURSE;
	}
	
	@RequestMapping(AdminRequestMap.LIST_EXPS)
	public String listExps(@PathVariable long courseid, Model model) {
		List<Experiment> experiments = courseService.geExperiments(courseid);
		model.addAttribute("course", courseService.getCourse(courseid));
		model.addAttribute("exps", experiments);
		model.addAttribute("courseid", courseid);
		return AdminViewMap.LIST_EXPS;
	}
	
	@RequestMapping(path = AdminRequestMap.ADD_EXP, method = RequestMethod.POST)
	public String addExp(long courseid, Experiment experiment) {
		
		courseService.addExp(courseid, experiment);
		return REDIRECT+ AdminViewMap.LIST_EXPS;
	}
	
	@RequestMapping(AdminRequestMap.IMPORT_EXCEL)
	public String importStudentExcel(@SessionAttribute(ControllerMap.USER) User user , Model model, HttpSession session) {
		long userId = ((User)session.getAttribute(ControllerMap.USER)).getId();
		List<Course> courses = courseService.getCourses(userId);
		model.addAttribute("courses", courses);
		return AdminViewMap.IMPORT_EXCEL;
	}
	@RequestMapping(path = AdminRequestMap.IMPORT_EXCEL, method = RequestMethod.POST)
	public String importStudentExcel(long courseid, MultipartFile file, RedirectAttributes vMap) {
		int count = 0;
		if (!file.isEmpty()) {
			count = courseService.batchingStudentByExcelFile(courseid, file).size();
		}
		vMap.addFlashAttribute("count", count);
		return REDIRECT+ AdminRequestMap.IMPORT_EXCEL;
	}
	
	@GetMapping(path = AdminRequestMap.UNSUBMITED)
	public String listUnsubmited(@PathVariable long expid, Model model) {
		
		model.addAttribute("students", courseService.getStudentsForUnsubmited(expid));
		model.addAttribute("exp", courseService.getExperiment(expid));
		return AdminViewMap.UNSUBMITED;
	}
	
	/**
	 * 下载ZIP压缩包
	 * 
	 * @param directory
	 * @return
	 */
	@RequestMapping(path = AdminRequestMap.DOWNLOAD_ZIP, method = RequestMethod.GET)
	public ResponseEntity<byte[]> getFileZip(@PathVariable String directory, @PathVariable String exp) {
		String d = directory + "/" + exp;
		ResponseEntity<byte[]> entity = FileUtils.toResponseEntity(exp + ".zip", FileUtils.zipDirectory(d));
	
		return entity;
	}
}
