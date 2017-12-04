package com.se.courses.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.se.courses.dao.TeacherDao;
import com.se.courses.dao.UserDao;
import com.se.courses.entity.Teacher;
import com.se.courses.entity.User;
import com.se.courses.util.MD5;

@Service
@Transactional
public class UserService {
	@Autowired
	private UserDao userDao;
	@Autowired
	private TeacherDao teacherDao;
	
	public User getUser(String number, String password) {
		return userDao.find(number, MD5.generateMD5(password));
	}
	
	public User getUser(long id) {
		// TODO Auto-generated method stub
		return userDao.find(id);
	}
	public void updatePassword(long id, String pwd) {
		// TODO Auto-generated method stub
		User user = userDao.find(id);
		user.setPassword(MD5.generateMD5(pwd));
	}
	
	public void initTeacher() {
		if (teacherDao.list().size() == 0) {
			Teacher teacher = new Teacher();
			teacher.setName("王波");
			teacher.setNumber("1007");
			teacher.setLevel(10);
			teacher.setPassword(MD5.generateMD5("1007"));
			teacherDao.persist(teacher);
		}
	}
}
