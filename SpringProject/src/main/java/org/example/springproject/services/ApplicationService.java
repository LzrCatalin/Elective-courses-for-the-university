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
	Application addApplication(Long studentId, Long courseId, Integer priority);
	Application addApplicationAsStudent(Long studentId, String courseName, Integer priority);
	Application updateApplication(Long id, Integer priority, Status status);
	Application updateApplicationAsStudent(Long id, Integer priority);
	void deleteApplication(Long id);
}
