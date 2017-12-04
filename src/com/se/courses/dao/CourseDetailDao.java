package com.se.courses.dao;

import java.util.List;

import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import com.se.courses.entity.CourseDetail;
import com.se.courses.entity.Student;
@Repository
public class CourseDetailDao extends GenericDao<CourseDetail> {

	/**
	 * 指定实验，查询所有未提交学生
	 * @param experimentId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Student> getStudents(long experimentId) {
		String HQL = "SELECT cd.student FROM CourseDetail cd LEFT JOIN ExperimentDetail ed "
				+ "ON cd.student.id=ed.student.id AND ed.experiment.id=:expid "
				+ "WHERE ed.student.id IS NULL";
		Query<?> query = getSessionFactory().getCurrentSession().createQuery(HQL)
				.setParameter("expid", experimentId);
		return (List<Student>) query.list();
	}
}
