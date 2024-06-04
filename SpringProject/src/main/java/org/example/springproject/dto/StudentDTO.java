package org.example.springproject.dto;

import org.example.springproject.entity.Student;
import org.example.springproject.enums.FacultySection;

import java.util.ArrayList;
import java.util.List;

public class StudentDTO {
	private Long id;
	private String name;
	private String email;
	private String role;
	private Integer studyYear;
	private Float grade;
	private String facultySection;

	// Empty constructor
	public StudentDTO() {}

	// Constructor with param
	public StudentDTO(Long id, String name, String email, String role,
					  Integer studyYear, Float grade, String facultySection)
	{
		this.id = id;
		this.name = name;
		this.email = email;
		this.role = role;
		this.studyYear = studyYear;
		this.grade = grade;
		this.facultySection = facultySection;
	}

	// Getters & Setters
	public Long getId() {
		return id;
	}
	public String getName() {
		return name;
	}
	public String getEmail() {
		return email;
	}
	public String getRole() {
		return role;
	}
	public Integer getStudyYear() {
		return studyYear;
	}
	public Float getGrade() {
		return grade;
	}
	public String getFacultySection() {
		return facultySection;
	}

	public void setId(Long id) {
		this.id = id;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public void setStudyYear(int studyYear) {
		this.studyYear = studyYear;
	}
	public void setGrade(Float grade) {
		this.grade = grade;
	}
	public void setFacultySection(String facultySection) {
		this.facultySection = facultySection;
	}


	// Convert to DTO
	public static List<StudentDTO> convertToDTO(List<Student> students) {
		List<StudentDTO> studentDTO = new ArrayList<>();
		students.forEach(student ->
				studentDTO.add(
						new StudentDTO(student.getId(), student.getName(), student.getEmail(),
								student.getRole(), student.getStudyYear(), student.getGrade(), student.getFacultySection().toString())
				));

		return studentDTO;
	}
}
