package org.example.springproject.entity;

import jakarta.persistence.*;
import org.example.springproject.enums.FacultySection;

@Entity
@Table(name = "students")
public class Student extends User{
	@Column(name = "studyYear")
	private Integer studyYear;

	@Column(name = "grade")
	private Float grade;

	@Column(name = "facultySection")
	private FacultySection facultySection;

	// Empty constructor
	public Student() {};

	// Constructor
	public Student(Integer studyYear, Float grade, FacultySection facultySection) {
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

	public FacultySection getFacultySection() {
		return facultySection;
	}

	// Setters
	public void setStudyYear(Integer newStudyYear) {
		this.studyYear = newStudyYear;
	}

	public void setGrade(Float newGrade) {
		this.grade = newGrade;
	}

	public void setFacultySection(FacultySection newFacultySection) {
		this.facultySection = newFacultySection;
	}
}
