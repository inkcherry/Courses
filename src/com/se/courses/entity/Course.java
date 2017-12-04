package com.se.courses.entity;

import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
@Entity
public class Course {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private String name;
	@ManyToOne
	private Teacher teacher;
	@OneToMany(mappedBy = "course")
	@OrderBy(value ="id ASC")
	private Set<CourseDetail> courseDetails;
	@OrderBy(value ="id ASC")
	@OneToMany(mappedBy = "course")
	private Set<Experiment> experiments;
	
	@Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	private Date insertTime;
	
	
	public long getId() {
		return id;
	}


	public void setId(long id) {
		this.id = id;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public Teacher getTeacher() {
		return teacher;
	}


	public void setTeacher(Teacher teacher) {
		this.teacher = teacher;
	}


	public Set<CourseDetail> getCourseDetails() {
		return courseDetails;
	}


	public void setCourseDetails(Set<CourseDetail> courseDetails) {
		this.courseDetails = courseDetails;
	}


	public Set<Experiment> getExperiments() {
		return experiments;
	}


	public void setExperiments(Set<Experiment> experiments) {
		this.experiments = experiments;
	}


	public Date getInsertTime() {
		return insertTime;
	}


	public void setInsertTime(Date insertTime) {
		this.insertTime = insertTime;
	}


	public Course(long id) {
		super();
		this.id = id;
	}


	public Course() {
		// TODO Auto-generated constructor stub
	}

}
