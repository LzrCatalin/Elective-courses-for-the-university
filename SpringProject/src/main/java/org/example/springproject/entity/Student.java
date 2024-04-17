package org.example.springproject.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "students")
public class Student {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long userId;

	@MapsId
	@OneToOne
	@JoinColumn(name = "userId")
	private User user;

	@Column(name = "grade")
	private Float grade;

	@Column(name = "studyYear")
	private Integer studyYear;

	@Column(name = "facultySection")
	private String facultySection;

	// Empty Constructor
	public Student() {}

	// Constructor
	public Student(Long id, User user, Float grade, Integer studyYear, String facultySection) {
		this.userId = id;
		this.user = user;
		this.grade = grade;
		this.studyYear = studyYear;
		this.facultySection = facultySection;
	}

	// Getters
	public Float getGrade() {
		return grade;
	}

	public Integer getStudyYear() {
		return studyYear;
	}

	public String getFacultySection() {
		return facultySection;
	}

	public User getUser() {
		return user;
	}

	// Setters
	public void setGrade(Float newGrade) {
		this.grade = newGrade;
	}

	public void setStudyYear(Integer newStudyYear) {
		this.studyYear = newStudyYear;
	}

	public void setFacultySection(String newFacultySection) {
		this.facultySection = newFacultySection;
	}
}
