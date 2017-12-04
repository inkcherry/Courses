package com.se.courses.service;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class InitSys implements InitializingBean {
	@Autowired
	private UserService userService;

	@Override
	public void afterPropertiesSet() throws Exception {
		// TODO Auto-generated method stub
		userService.initTeacher();
	}
}
