package org.example.springproject.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "students")
public class Student extends User{
	@Column(name = "studyyear")
	private Integer studyYear;

	@Column(name = "grade")
	private Float grade;

	@Column(name = "facultySection")
	private String facultySection;

	// Empty constructor
	public Student() {};

	// Constructoru
	public Student(Integer studyYear, Float grade, String facultySection) {
		this.studyYear = studyYear;
		this.grade = grade;
		this.facultySection = facultySection;
	}

	// Getters
	public Integer getStudyYear() {
		return studyYear;
	}

	public Float getGrade() {
		return grade;
	}

	public String getFacultySection() {
		return facultySection;
	}

	// Setters
	public void setStudyYear(Integer newStudyYear) {
		this.studyYear = newStudyYear;
	}

	public void setGrade(Float newGrade) {
		this.grade = newGrade;
	}

	public void setFacultySection(String newFacultySection) {
		this.facultySection = newFacultySection;
	}
}
