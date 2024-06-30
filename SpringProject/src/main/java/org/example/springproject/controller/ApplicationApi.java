package org.example.springproject.controller;

import jakarta.persistence.EntityNotFoundException;
import org.example.springproject.dto.ApplicationDTO;
import org.example.springproject.dto.StudentDTO;
import org.example.springproject.entity.Application;
import org.example.springproject.entity.Student;
import org.example.springproject.enums.FacultySection;
import org.example.springproject.enums.Status;
import org.example.springproject.exceptions.*;
import org.example.springproject.services.ApplicationService;
import org.example.springproject.services.EmailService;
import org.example.springproject.utilities.DBState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
	private static final Logger logger = LoggerFactory.getLogger(CourseApi.class);
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

	@PostMapping("/studentsIDs/{courseId}")
	public List<Student> getIds(@PathVariable("courseId") Long courseId, @RequestBody Map<String, Object> requestBody) {
		logger.info("Received course id: " + courseId);
		String applicationsStatus = (String) requestBody.get("status");
		Status status = Status.valueOf(applicationsStatus);
		logger.info("Received status request: " + status);
		return applicationService.getStudentsOfCourse(courseId, status);
	}

	@GetMapping("/{id}")
	public List<Application> getStudentApplications(@PathVariable("id") Long id) {
		return applicationService.getStudentApplications(id);
	}

	@GetMapping("/{studentId}/classmates")
	public ResponseEntity<List<StudentDTO>> getStudentClassmatesOnCourse(@PathVariable("studentId") Long studentId, @RequestParam Long courseId) {
//		Long courseId = Long.valueOf((Integer) requestBody.get("courseId"));
		List<Student> classmates = applicationService.getStudentClassmatesOnCourse(courseId, studentId);

		if (classmates.isEmpty()) {
			return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
		}

		List<StudentDTO> classmatesDTO = StudentDTO.convertToDTO(classmates);
		return new ResponseEntity<>(classmatesDTO, HttpStatus.OK);
	}

	@PostMapping("/stud")
	public ResponseEntity<Object> addApplication(@RequestBody Map<String, Object> requestBody){
		try {
			if (DBState.getInstance().isReadOnly()) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", "Read-Only is enable."));
			}

			Long studentId = Long.valueOf((Integer) requestBody.get("studentId"));
			logger.debug("Received student id: " + studentId);
			Long courseId = Long.valueOf((Integer) requestBody.get("courseId"));
			logger.debug("Received course name: " + courseId);
			Integer priority = (Integer) requestBody.get("priority");
			logger.debug("Received priority: " + priority);
			Application application = applicationService.addApplication(studentId, courseId);
//			emailService.sendNewApplicationMail(studentId, courseName, priority);
			ApplicationDTO applicationDTO = new ApplicationDTO(application.getId(), application.getStudent().getId(), application.getCourse().getId(),
					application.getPriority(), application.getStatus().toString());
			return new ResponseEntity<>(applicationDTO, HttpStatus.CREATED);

		} catch (EntityNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", e.getMessage()));

		} catch (MismatchedFacultySectionException | MismatchedIdTypeException | InvalidStudyYearException | DuplicatePriorityException | IllegalArgumentException
		| InvalidPriorityException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", e.getMessage()));
		}
	}

	@PutMapping("/")
	public ResponseEntity<String> updateApplication(@RequestBody Map<String, Object> requestBody) {
		try {
			Integer id = (Integer) requestBody.get("applicationId");
			Long applicationId = Long.valueOf(id);
			logger.info("Received application id: " + applicationId);
			Integer sID = (Integer) requestBody.get("studentId");
			Long studentId = Long.valueOf(sID);
			logger.info("Received student id: " + studentId);
			Integer cID = (Integer) requestBody.get("courseId");
			Long courseId = Long.valueOf(cID);
			logger.info("Received course id: " + courseId);
			Integer priority = (Integer) requestBody.get("priority");
			logger.info("Received priority: " + priority);
			String receivedStatus = (String) requestBody.get("status");
			Status status = Status.valueOf(receivedStatus);
			logger.info("Received status: " + status);
			applicationService.updateApplication(applicationId, studentId, courseId, priority, status);
			return new ResponseEntity<>("Application with id:" + id + " successfully updated.", HttpStatus.OK);

		} catch (EntityNotFoundException e) {
			return new ResponseEntity<>("Application not found.", HttpStatus.NOT_FOUND);
		}
	}

	@PutMapping("/admin")
	public ResponseEntity<String> updateApplicationAsAdmin(@RequestBody Map<String, Object> requestBody) {
		try {
			Long studentId = Long.valueOf((Integer) requestBody.get("studentId"));
			logger.info("Received student id: " + studentId);
			String courseName = (String) requestBody.get("courseName");
			logger.info("Received course name: " + courseName);
			String newCourseName = (String) requestBody.get("newCourseName");
			logger.info("Received new course name: " + newCourseName);

			applicationService.updateApplicationAsAdmin(studentId, courseName, newCourseName);
			return new ResponseEntity<>("Application successfully updated.", HttpStatus.OK);

		} catch (EntityNotFoundException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);

		} catch (DuplicateCourseAssignmentException | CourseCapacityExceededException | DuplicateCategoryAssignmentException | InvalidStudyYearException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteApplication(@PathVariable("id") Long id) {
		try {
			if (DBState.getInstance().isReadOnly()) {
				return new ResponseEntity<>("Read-Only is ON. Can not modify anything.", HttpStatus.BAD_REQUEST);
			}

			emailService.sendDeleteApplicationMail(id);
			applicationService.deleteApplication(id);
			return new ResponseEntity<>(gson.toJson("Application with id:" + id + " successfully deleted."), HttpStatus.OK);

		} catch (EntityNotFoundException e) {
			logger.info("Inside exception...");
			return new ResponseEntity<>("Application id: " + id + " not found.", HttpStatus.NOT_FOUND);
		}
	}

	@PutMapping("/stud/{id}")
	public ResponseEntity<Object> updateApplicationAsStudent(@PathVariable("id") Long id,
															 @RequestBody Map<String, Object> requestBody) {
		try {
			if (DBState.getInstance().isReadOnly()) {
				return new ResponseEntity<>("Read-Only is ON. Can not modify anything.", HttpStatus.BAD_REQUEST);
			}

			Integer priority = (Integer) requestBody.get("priority");
			List<Application> receivedApplications = applicationService.updateApplicationAsStudent(id, priority);
//			emailService.sendUpdateApplicationMail(id, priority);
			List<ApplicationDTO> applicationsDTO = ApplicationDTO.convertToDTO(receivedApplications);
			return new ResponseEntity<>(applicationsDTO, HttpStatus.OK);

		} catch (EntityNotFoundException e) {
			return new ResponseEntity<>("Application id: " + id + " not found.", HttpStatus.NOT_FOUND);

		} catch (InvalidPriorityException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
}
