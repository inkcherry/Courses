package com.se.courses.dao;

import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import com.se.courses.entity.ExperimentDetail;

@Repository
public class ExperimentDetailDao extends GenericDao<ExperimentDetail> {
	public ExperimentDetail find(long userId, long expId) {
		String HQL = "FROM ExperimentDetail e WHERE e.student.id=:userId AND e.experiment.id=:expId";
		Query<?> query = getSessionFactory().getCurrentSession().createQuery(HQL)
				.setParameter("userId", userId)
				.setParameter("expId", expId);
		return (ExperimentDetail) query.uniqueResult();
	}
}
