package org.example.springproject.controller;

import org.example.springproject.entity.Application;
import org.example.springproject.entity.Course;
import org.example.springproject.entity.Student;
import org.example.springproject.enums.Status;
import org.example.springproject.services.ApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/applications")
public class ApplicationApi {

	@Autowired
	public ApplicationService applicationService;

	@GetMapping("/")
	public List<Application> getAllApplications() {
		return applicationService.getAllApplications();
	}
	@PostMapping("/")
	public ResponseEntity<String> addApplication(Long studentId, Long courseId, Integer priority, Status status) {
		return applicationService.addApplication(studentId, courseId, priority, status);
	}

	@PutMapping("/{id}")
	public ResponseEntity<String> updateApplication(@PathVariable("id") Long id, Integer priority, Status status) {
		return applicationService.updateApplication(id, priority, status);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteApplication(@PathVariable("id") Long id) {
		return applicationService.deleteApplication(id);
	}
}
