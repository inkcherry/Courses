package com.se.courses.entity;

import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
@Entity
public class Student extends User{
	// 班级
	private String clazz;
	@OneToMany(mappedBy = "student")
	@OrderBy(value ="id ASC")
	private Set<CourseDetail> courseDetails;
	@OneToMany(mappedBy = "student")
	@OrderBy(value ="id ASC")
	private Set<ExperimentDetail> experimentDetails;

	public String getClazz() {
		return clazz;
	}

	public void setClazz(String clazz) {
		this.clazz = clazz;
	}

	public Set<CourseDetail> getCourseDetails() {
		return courseDetails;
	}

	public void setCourseDetails(Set<CourseDetail> courseDetails) {
		this.courseDetails = courseDetails;
	}

	public Set<ExperimentDetail> getExperimentDetails() {
		return experimentDetails;
	}

	public void setExperimentDetails(Set<ExperimentDetail> experimentDetails) {
		this.experimentDetails = experimentDetails;
	}

	public Student() {
		// TODO Auto-generated constructor stub
	}

}
