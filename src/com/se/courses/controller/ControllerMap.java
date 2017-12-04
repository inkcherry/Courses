package com.se.courses.controller;

public interface ControllerMap {
	String USER = "user";
	String REDIRECT = "redirect:";
	String STUDENT = "/student";
	String ADMIN = "/admin";

	interface UserRequestMap {
		String LOGIN = "/login";
		String LOGOUT = "/logout";
		String MAIN = "/main";
		String UPDATEPASSWORD  =  "/updatepassword";
		String UPDATEUSERSETTING = "/updateusersetting";
		String COURSE = "/course/{courseid}";
		String LISTEXPS = "/listexps";
		String LISTCOURSES = "/listcourses";
		String ADDEXP = "/addexp";
		String DOWNLOAD_EXP = "/download/{expId}";
	}

	interface UserViewMap {
		String UPDATEUSERSETTING = STUDENT + UserRequestMap.UPDATEUSERSETTING;
		String MAIN = STUDENT + "/main";
		String COURSE = STUDENT + "/course";
		String LISTEXPS = STUDENT + UserRequestMap.LISTEXPS;
		String LISTCOURSES = STUDENT + UserRequestMap.LISTCOURSES;
	}

	interface StudentRequestMap {

	}

	interface StudentResqonseMap {

	}

	interface AdminRequestMap {
		String ADD_COURSE = ADMIN + "/addcourse";
		String LIST_COURSES = ADMIN + "/listcourses";
		String LIST_EXPS = ADMIN + "/course/{courseid}/listexps";
		String ADD_EXP = ADMIN + "/addexp";
		String IMPORT_EXCEL = ADMIN + "/importstudentexcel";
		String DOWNLOAD_ZIP = ADMIN+ "/downloadzip/{directory}/{exp}";
		String UNSUBMITED = ADMIN + "/unsubmited/{expid}";
	}

	interface AdminViewMap {
		String LIST_COURSE = ADMIN + "/listcourses";
		String LIST_EXPS = ADMIN + "/listexps";
		String IMPORT_EXCEL = ADMIN + "/importstudentexcel";
		String UNSUBMITED = ADMIN + "/unsubmited";
	}
}
