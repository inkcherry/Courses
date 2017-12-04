package test.com.se.courses.service.courseservice;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.se.courses.entity.Student;
import com.se.courses.service.CourseService;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:WebContent/WEB-INF/applicationContext.xml")
@WebAppConfiguration("WebContent") // web项目的根目录，默认为 file:src/main/webapp
@Transactional
public class GetStudentsForUnsubmitedTest {

	@Autowired
	private CourseService courseService;
	/**
	 * 测试未提交实验学生
	 */
	@Test
	public void test() {
		List<Student> students = courseService.getStudentsForUnsubmited(1);
		System.out.println(students.size());
		students.stream().forEach(e -> System.out.println(e.getName()));
		
	}

}
