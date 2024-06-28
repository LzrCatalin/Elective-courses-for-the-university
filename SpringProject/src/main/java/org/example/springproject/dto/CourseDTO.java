package org.example.springproject.dto;


import org.example.springproject.entity.Course;

import java.util.ArrayList;
import java.util.List;

public class CourseDTO {
	private Long id;
	private String name;
	private String category;
	private Integer studyYear;
	private String teacher;
	private Integer maxCapacity;
	private String facultySection;
	private Integer applicationsCount;

	// Empty Constructor
	public CourseDTO() {}

	// Constuctor with params
	public CourseDTO(Long id, String name, String category, Integer studyYear,
					 String teacher, Integer maxCapacity, String facultySection, Integer applicationsCount) {
		this.id = id;
		this.name = name;
		this.category = category;
		this.studyYear = studyYear;
		this.teacher = teacher;
		this.maxCapacity = maxCapacity;
		this.facultySection = facultySection;
		this.applicationsCount = applicationsCount;
	}

	// Getters & Setters
	public Long getId() {
		return id;
	}
	public String getName() {
		return name;
	}
	public String getCategory() {
		return category;
	}
	public Integer getStudyYear() {
		return studyYear;
	}
	public String getTeacher() {
		return teacher;
	}
	public Integer getMaxCapacity() {
		return maxCapacity;
	}
	public String getFacultySection() {
		return facultySection;
	}
	public Integer getApplicationsCount() {
		return applicationsCount;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public void setStudyYear(Integer studyYear) {
		this.studyYear = studyYear;
	}
	public void setTeacher(String teacher) {
		this.teacher = teacher;
	}
	public void setFacultySection(String facultySection) {
		this.facultySection = facultySection;
	}
	public void setApplicationsCount(Integer applicationsCount) {
		this.applicationsCount = applicationsCount;
	}
	public void setMaxCapacity(Integer maxCapacity) {
		this.maxCapacity = maxCapacity;
	}

	// Convert to DTO
	public static List<CourseDTO> convertToDTO(List<Course> courses) {
		List<CourseDTO> coursesDTO = new ArrayList<>();
		courses.forEach(course ->
				coursesDTO.add(
						new CourseDTO(course.getId(), course.getName(), course.getCategory(),
								course.getStudyYear(), course.getTeacher(), course.getMaxCapacity(),
								course.getFacultySection().toString(), course.getApplicationsCount())
				));

		return coursesDTO;
	}
}
