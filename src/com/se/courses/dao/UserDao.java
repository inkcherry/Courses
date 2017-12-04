package com.se.courses.dao;

import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import com.se.courses.entity.User;
@Repository
public class UserDao extends GenericDao<User> {

	public User find(String number, String password) {
		String HQL = "FROM User u WHERE u.number=:number AND u.password=:password";
		Query<?> query = getSessionFactory().getCurrentSession().createQuery(HQL)
				.setParameter("number", number)
				.setParameter("password", password);
		return  (User) query.uniqueResult();
	}
	
	public UserDao() {
		// TODO Auto-generated constructor stub
	}

}
