package org.example.springproject.services;

import org.example.springproject.entity.Application;
import org.example.springproject.entity.Student;
import org.example.springproject.enums.Status;
import org.springframework.data.jpa.repository.Query;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ApplicationService {
	List<Application> getAllApplications();
	List<Application> getStudentApplications(Long id);
	List<Student> getStudentsOfCourse(Long courseId, Status status);
	List<Student> getStudentClassmatesOnCourse(Long courseId, Long studentId);
	Application addApplication(Long studentId, Long courseId);
	Application updateApplication(Long id, Long studentId, Long courseId, Integer priority, Status status);
	Application updateApplicationAsAdmin(Long studentId, String courseName, String newCourseName);
	List<Application> updateApplicationAsStudent(Long id, Integer priority);
	void deleteApplication(Long id);
}
