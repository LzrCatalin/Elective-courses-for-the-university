package org.example.springproject.controller;

import org.example.springproject.entity.Application;
import org.example.springproject.enums.Status;
import org.example.springproject.exceptions.DuplicatePriorityException;
import org.example.springproject.exceptions.MismatchedFacultySectionException;
import org.example.springproject.exceptions.MismatchedIdTypeException;
import org.example.springproject.exceptions.NoSuchObjectExistsException;
import org.example.springproject.services.ApplicationService;
import org.example.springproject.services.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.google.gson.Gson;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/applications")
@CrossOrigin
public class ApplicationApi {
	/*
	Added gson import helping on sending JSON data to Angular part
	 */
	private static final Gson gson = new Gson();
	@Autowired
	public ApplicationService applicationService;
	@Autowired
	public EmailService emailService;
	@GetMapping("/")
	public List<Application> getAllApplications() {
		return applicationService.getAllApplications();
	}

	@GetMapping("/{id}")
	public List<Application> getStudentApplications(@PathVariable("id") Long id) {
		return applicationService.getStudentApplications(id);
	}

	@PostMapping("/stud")
	public ResponseEntity<String> addApplication(@RequestBody Map<String, Object> requestBody){
		try {
			Long studentId = Long.valueOf((Integer) requestBody.get("studentId"));
			String courseName = (String) requestBody.get("courseName");
			Integer priority = (Integer) requestBody.get("priority");
			applicationService.addApplicationAsStudent(studentId, courseName, priority);
			emailService.sendNewApplicationMail(studentId, courseName, priority);
			return new ResponseEntity<>("Application added successfully.", HttpStatus.CREATED);

		} catch (NoSuchObjectExistsException e) {
			return new ResponseEntity<>(e.getMessage(), e.getHttpStatus());

		} catch (MismatchedFacultySectionException e) {
			return new ResponseEntity<>(e.getMessage(), e.getHttpStatus());

		} catch (MismatchedIdTypeException e) {
			return new ResponseEntity<>(e.getMessage(), e.getHttpStatus());

		} catch (DuplicatePriorityException e) {
			return new ResponseEntity<>(e.getMessage(), e.getHttpStatus());
		}
	}

	@PutMapping("/{id}")
	public ResponseEntity<String> updateApplication(@PathVariable("id") Long id, Integer priority, Status status) {
		try {
			applicationService.updateApplication(id, priority, status);
			return new ResponseEntity<>("Application with id:" + id + " successfully updated.", HttpStatus.OK);

		} catch (NoSuchObjectExistsException e) {
			return new ResponseEntity<>(e.getMessage(), e.getHttpStatus());
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteApplication(@PathVariable("id") Long id) {
		try {
			emailService.sendDeleteApplicationMail(id);
			applicationService.deleteApplication(id);
			return new ResponseEntity<>(gson.toJson("Application with id:" + id + " successfully deleted."), HttpStatus.OK);

		} catch (NoSuchObjectExistsException e) {
			return new ResponseEntity<>(e.getMessage(), e.getHttpStatus());
		}
	}

	@PutMapping("/stud/{id}")
	public ResponseEntity<String> updateApplicationAsStudent(@PathVariable("id") Long id,
															 @RequestBody Map<String, Object> requestBody) {
		try {
			Integer priority = (Integer) requestBody.get("priority");
			applicationService.updateApplicationAsStudent(id, priority);
			emailService.sendUpdateApplicationMail(id, priority);
			return new ResponseEntity<>(gson.toJson("Application updated successfully."), HttpStatus.OK);

		} catch (NoSuchObjectExistsException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}
}
