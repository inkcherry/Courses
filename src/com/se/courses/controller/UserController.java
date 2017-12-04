package com.se.courses.controller;

import static com.se.courses.controller.ControllerMap.REDIRECT;
import static com.se.courses.controller.ControllerMap.USER;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.se.courses.controller.ControllerMap.UserRequestMap;
import com.se.courses.controller.ControllerMap.UserViewMap;
import com.se.courses.entity.Course;
import com.se.courses.entity.CourseDetail;
import com.se.courses.entity.Experiment;
import com.se.courses.entity.ExperimentDetail;
import com.se.courses.entity.Student;
import com.se.courses.entity.User;
import com.se.courses.service.CourseService;
import com.se.courses.service.UserService;

@Controller
public class UserController {
	private String cookieName = "courses";
	@Autowired
	private CourseService courseService;
	
	@Autowired
	private UserService userService;
	
	/**
	 * 加载login页面，清空session，加载cookie
	 * 
	 * @return
	 */
	@RequestMapping(UserRequestMap.LOGIN)
	public String login(HttpSession session, HttpServletRequest request, HttpServletResponse response, Model m) {
		session.invalidate();
		String remember = null;
		String msg = null;
		Cookie cookie = getCookie(request);
		if (cookie != null) {
			remember = "true";
			String result = new String(Base64.getDecoder().decode(cookie.getValue()));
			String result2 = new String(Base64.getDecoder().decode(result));
			String regex = "(.*)/(.*)/(.*)/(.*)/";
			Pattern pattern = Pattern.compile(regex);
			Matcher matcher = pattern.matcher(result2);
			while (matcher.find()) {
				msg = matcher.group(4);
			}
		} else {
			cleanCookie(request, response);
		}
		m.addAttribute("remember", remember);
		m.addAttribute("msg", msg);
		return UserRequestMap.LOGIN;
	}

	/**
	 * 使用redirect重定向时参数会暴露在地址栏，使用RedirectAttributes接口隐藏参数
	 * 重定向回login时传递登录错误信息参数
	 * @param employeeNumber
	 * @param password
	 * @param checked
	 * @param session
	 * @param response
	 * @param errorMap
	 * @return
	 */
	@RequestMapping(value = UserRequestMap.LOGIN, method = RequestMethod.POST)
	public String login(String number, String password, String checked, HttpSession session, HttpServletResponse response, RedirectAttributes errorMap) {
		User user = userService.getUser(number, password);
		if (user != null) {
			session.setAttribute(ControllerMap.USER, user);
			String msg = user.getName();
			if (checked != null) {
				if (user.getLevel() >= 10) {
					msg = msg + "老师";
				} else {
					msg = msg + "同学";
				}
				createCookie(response, number, password, msg);
			}
			
			return REDIRECT + UserRequestMap.MAIN;
		}
		errorMap.addFlashAttribute("exception", "用户名或密码错误！");
		return REDIRECT + UserRequestMap.LOGIN;
	}
	
	/**
	 * 基于Cookie登录
	 * @param session
	 * @param request
	 * @param errorMap
	 * @return
	 */
	@RequestMapping(value = "/ilogin", method = RequestMethod.POST)
	public String iLogin(HttpSession session, HttpServletRequest request, HttpServletResponse response,
			RedirectAttributes errorMap) {
		Cookie cookie = getCookie(request);
		if (cookie != null) {
			String result = new String(Base64.getDecoder().decode(cookie.getValue()));
			String result2 = new String(Base64.getDecoder().decode(result));
			String regex = "(.*)/(.*)/(.*)/(.*)/";
			Pattern pattern = Pattern.compile(regex);
			Matcher matcher = pattern.matcher(result2);
			String number = null;
			String password = null;
			while (matcher.find()) {
				number = matcher.group(1);
				password = matcher.group(3);
			}
			if (number != null && password != null) {
				User user = userService.getUser(number, password);
				if (user != null) {
					session.setAttribute(USER, user);
					return REDIRECT + UserRequestMap.MAIN;
				}
			}
		}
		// 登录错误，清空Cookie
		cleanCookie(request, response);
		errorMap.addFlashAttribute("exception", "用户名或密码错误！");
		return REDIRECT + UserRequestMap.LOGIN;
	}

	/**
	 * 加载用于基本信息
	 * 
	 * @param vMap
	 * @param session
	 * @return
	 */
	@RequestMapping(UserRequestMap.UPDATEUSERSETTING)
	public String updateUserSetting(@SessionAttribute(USER) User user, Model m, HttpSession session) {
		m.addAttribute(USER, userService.getUser(user.getId()));
		return UserViewMap.UPDATEUSERSETTING;
	}
	
	@RequestMapping(UserRequestMap.MAIN)
	public String getMain(@SessionAttribute(USER) User user,Model m) {
		//List<Course> courses = courseService.findCourses(user.getId());
		//m.addAttribute("courses", courses);
		return UserViewMap.MAIN;
	}
	
	
	@RequestMapping(UserRequestMap.LISTEXPS)
	public String listExps(@SessionAttribute(USER) User user, Model m, HttpSession session) {
		Student student = (Student) userService.getUser(user.getId()); 
		List<Experiment> exps = new ArrayList<>();
		for (CourseDetail d : student.getCourseDetails()) {
			exps.addAll(d.getCourse().getExperiments());
		}
		List<ExperimentDetail> details = new ArrayList<>(student.getExperimentDetails());	
		
		m.addAttribute("exps", exps);
		m.addAttribute("details", details);
		return UserViewMap.LISTEXPS;
	}
	
	
	@RequestMapping(path = UserRequestMap.ADDEXP, method = RequestMethod.POST)
	public String addExpFile(@SessionAttribute(USER) User user, long expid, MultipartFile uploadFile, HttpSession session) {
		
		if (expid != 0 && !uploadFile.isEmpty()) {
			
			courseService.addExpFile(expid, uploadFile, user.getId());
		}
		return REDIRECT + UserRequestMap.LISTEXPS;
	}
 	
	@RequestMapping(UserRequestMap.LISTCOURSES)
	public String listCourses(@SessionAttribute(USER) User user, HttpSession session, Model m) {
		Student student = (Student) userService.getUser(user.getId()); 
		Set<CourseDetail> details = student.getCourseDetails();
		m.addAttribute("details", details);
		return UserViewMap.LISTCOURSES;
	}
	
	
	/**
	 * 更新密码
	 * 
	 * @param pwd
	 * @param session
	 * @return
	 */
	@RequestMapping(path = UserRequestMap.UPDATEPASSWORD, method = RequestMethod.POST)
	public String updatePassword(@SessionAttribute(USER) User user, String pwd, HttpSession session) {
		userService.updatePassword(user.getId(), pwd);
		return REDIRECT + UserRequestMap.UPDATEUSERSETTING;
	}

	@RequestMapping(path = UserRequestMap.LOGOUT)
	public String logout(HttpSession session, SessionStatus status) {
		session.removeAttribute(USER);
		session.invalidate();
		status.setComplete();
		return REDIRECT + UserRequestMap.LOGIN;
	}

	/**
	 * 下载文件
	 * 
	 * @param taskid
	 * @return
	 * @throws SEWMException
	 */
	@RequestMapping(path = UserRequestMap.DOWNLOAD_EXP, method = RequestMethod.GET)
	public ResponseEntity<byte[]> dowload(@SessionAttribute(USER) User user, @PathVariable long expId, HttpSession session) {
		
		return courseService.dowlnoadExpFile(expId, user.getId());
	}
	
	/**
	 * 创建Cookie，时效1年，算法： 
	 * @param response
	 * @param employeeNumber
	 * @param password
	 */
	private void createCookie(HttpServletResponse response, String employeeNumber, String password, String msg) {
		String base42 =  employeeNumber + "/" + "R28H22ZVTAL" + "/" + password + "/" + msg + "/";
		String result = Base64.getEncoder().encodeToString(base42.getBytes());
		Cookie cookie = new Cookie(cookieName, Base64.getEncoder().encodeToString(result.getBytes()));
		int expiry = 60 * 60 * 24 * 365;
		cookie.setMaxAge(expiry);
		response.addCookie(cookie);
	}
	
	
	/**
	 * 获取Cookie
	 * @param request
	 * @return
	 */
	private Cookie getCookie(HttpServletRequest request) {
		Cookie[] cookies = request.getCookies();
		Cookie cookie = null;
		if (cookies != null) {
			for (Cookie c : cookies) {
				if (c.getName().equals(cookieName)) {
					cookie = c;
				}
			}
		}
		return cookie;
	}
	
	/**
	 * 清空Cookie
	 * @param request
	 * @param response
	 */
	private void cleanCookie(HttpServletRequest request, HttpServletResponse response) {
		Cookie cookie = getCookie(request);
		if (cookie != null) {
			cookie.setMaxAge(0);
			response.addCookie(cookie);
		}
	}
	
}
