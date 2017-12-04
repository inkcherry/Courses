package com.se.courses.dao;

import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import com.se.courses.entity.Student;
@Repository
public class StudentDao extends GenericDao<Student> {
	
	public Student find(String number) {
		String HQL = "FROM Student s WHERE s.number=:number";
		Query<?> query = getSessionFactory().getCurrentSession().createQuery(HQL);
		query.setParameter("number", number);
		return (Student) query.uniqueResult();
	}
	
}
