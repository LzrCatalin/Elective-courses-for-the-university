package org.example.springproject.dto;

import org.example.springproject.entity.Application;

import java.util.ArrayList;
import java.util.List;

public class ApplicationDTO {
	private Long id;
	private Long studentId;
	private Long courseId;
	private Integer priority;
	private String status;

	// Empty Constructor
	public ApplicationDTO() {}

	// Constructor with params
	public ApplicationDTO(Long id, Long studentId, Long courseId, Integer priority, String status) {
		this.id = id;
		this.studentId = studentId;
		this.courseId = courseId;
		this.priority = priority;
		this.status = status;
	}

	public ApplicationDTO(Long id, Long studentId, Long courseId, Integer priority) {
		this.id = id;
		this.studentId = studentId;
		this.courseId = courseId;
		this.priority = priority;
	}

	// Getters & Setters
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getStudentId() {
		return studentId;
	}
	public void setStudentId(Long studentId) {
		this.studentId = studentId;
	}
	public Long getCourseId() {
		return courseId;
	}
	public void setCourseId(Long courseId) {
		this.courseId = courseId;
	}
	public Integer getPriority() {
		return priority;
	}
	public void setPriority(Integer priority) {
		this.priority = priority;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}

	// Converto to DTO
	public static List<ApplicationDTO> convertToDTO(List<Application> applications) {
		List<ApplicationDTO> applicationDTOs = new ArrayList<>();
		applications.forEach(application ->
				applicationDTOs.add(
						new ApplicationDTO(application.getId(), application.getStudent().getId(), application.getCourse().getId(),
								application.getPriority(), application.getStatus().toString())
				));

		return applicationDTOs;
	}
}
