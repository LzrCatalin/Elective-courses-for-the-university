package org.example.springproject.controller;

import jakarta.persistence.EntityNotFoundException;
import org.example.springproject.entity.Student;
import org.example.springproject.enums.FacultySection;
import org.example.springproject.exceptions.InvalidGradeException;
import org.example.springproject.exceptions.InvalidNameException;
import org.example.springproject.exceptions.InvalidStudyYearException;
import org.example.springproject.exceptions.NoSuchObjectExistsException;
import org.example.springproject.services.StudentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/students")
@CrossOrigin(origins = "http://localhost:4200")
public class StudentApi {
	private static final Logger logger = LoggerFactory.getLogger(CourseApi.class);

	@Autowired
	public StudentService studentService;

	@GetMapping
	public List<Student> getAllStudents() {
		return studentService.getAllStudents();
	}

	@GetMapping("/{id}")
	public ResponseEntity<Student> getStudentData(@PathVariable("id") Long id) {
		return new ResponseEntity<>(studentService.getData(id), HttpStatus.OK);
	}

	@PostMapping("/")
	public ResponseEntity<String> addStudent(@RequestBody Map<String, Object> requestBody) {
		try {
			String name = (String) requestBody.get("name");
			logger.info("Received name: " + name);
			Integer studyYear = (Integer) requestBody.get("studyYear");
			logger.info("Received studyYear: " + studyYear);
			Double gradeDouble = (Double) requestBody.get("grade");
			Float grade = gradeDouble.floatValue();
			logger.info("Received grade: " + grade);
			String facultySectionString = (String) requestBody.get("facultySection");
			FacultySection facultySection = FacultySection.valueOf(facultySectionString);
			logger.info("Received facultySection: " + facultySection);
			studentService.addStudent(name, studyYear, grade, facultySection);
			return new ResponseEntity<>("Student added successfully.", HttpStatus.CREATED);

		} catch (InvalidGradeException | InvalidNameException | InvalidStudyYearException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	@PutMapping("/{id}")
	public ResponseEntity<String> updateStudent(@PathVariable("id") Long id,
												@RequestBody Map<String, Object> requestBody) {
		try {
			String name = (String) requestBody.get("name");
			logger.info("Received name: " + name);
			Integer studyYear = (Integer) requestBody.get("studyYear");
			logger.info("Received studyYear: " + studyYear);
			Double gradeDouble = (Double) requestBody.get("grade");
			Float grade = gradeDouble.floatValue();
			logger.info("Received grade: " + grade);
			String facultySectionString = (String) requestBody.get("facultySection");
			FacultySection facultySection = FacultySection.valueOf(facultySectionString);
			logger.info("Received facultySection: " + facultySection);
			studentService.addStudent(name, studyYear, grade, facultySection);
			studentService.updateStudent(id, name, studyYear, grade, facultySection);
			return new ResponseEntity<>("Student with id:" + id + " successfully updated.", HttpStatus.OK);

		} catch (EntityNotFoundException e) {
			return new ResponseEntity<>("Student id: " + id + " not found.", HttpStatus.NOT_FOUND);

		} catch (InvalidGradeException | InvalidNameException | InvalidStudyYearException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteStudent(@PathVariable("id") Long id) {
		try {
			studentService.deleteStudent(id);
			return new ResponseEntity<>("Student with id:" + id + " successfully deleted.", HttpStatus.OK);

		} catch (EntityNotFoundException e) {
			return new ResponseEntity<>("Student id: " + id + " not found.", HttpStatus.NOT_FOUND);
		}
	}
}
