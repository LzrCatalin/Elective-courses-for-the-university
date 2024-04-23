package org.example.springproject.services;

import org.example.springproject.entity.Application;
import org.example.springproject.enums.Status;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ApplicationService {
	List<Application> getAllApplications();
	ResponseEntity<String> addApplication(Long studentId, Long courseId, Integer priority, Status status);
	ResponseEntity<String> updateApplication(Long id, Integer priority, Status status);
	ResponseEntity<String> deleteApplication(Long id);
}
