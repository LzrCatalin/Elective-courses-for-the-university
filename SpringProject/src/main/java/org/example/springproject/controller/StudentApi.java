package org.example.springproject.controller;

import org.example.springproject.entity.Student;
import org.example.springproject.enums.FacultySection;
import org.example.springproject.exceptions.InvalidGradeException;
import org.example.springproject.exceptions.InvalidNameException;
import org.example.springproject.exceptions.NoSuchObjectExistsException;
import org.example.springproject.services.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/students")
public class StudentApi {

	@Autowired
	public StudentService studentService;

//	@GetMapping
//	public String displayGreetings() {
//		return "Welcome to students";
//	}

	@CrossOrigin(origins = "http://localhost:4200")
	@GetMapping
	public List<Student> getAllStudents() {
		return studentService.getAllStudents();
	}

	@PostMapping("/")
	public ResponseEntity<String> addStudent(@RequestParam String name,
											 @RequestParam Integer studyYear,
											 @RequestParam Float grade,
											 @RequestParam FacultySection facultySection) {
		try {
			studentService.addStudent(name, studyYear, grade, facultySection);
			return new ResponseEntity<>("Student added successfully.", HttpStatus.CREATED);

		} catch (InvalidGradeException e) {
			return new ResponseEntity<>(e.getMessage(), e.getHttpStatus());

		} catch (InvalidNameException e) {
			return new ResponseEntity<>(e.getMessage(), e.getHttpStatus());
		}
	}

	@PutMapping("/{id}")
	public ResponseEntity<String> updateStudent(@PathVariable("id") Long id,
												@RequestParam String name,
												@RequestParam Integer studyYear,
												@RequestParam Float grade,
												@RequestParam FacultySection facultySection) {
		try {
			studentService.updateStudent(id, name, studyYear, grade, facultySection);
			return new ResponseEntity<>("Student with id:" + id + " successfully updated.", HttpStatus.OK);

		} catch (NoSuchObjectExistsException e) {
			return new ResponseEntity<>(e.getMessage(), e.getHttpStatus());

		} catch (InvalidGradeException e) {
			return new ResponseEntity<>(e.getMessage(), e.getHttpStatus());

		} catch (InvalidNameException e) {
			return new ResponseEntity<>(e.getMessage(), e.getHttpStatus());
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteStudent(@PathVariable("id") Long id) {
		try {
			studentService.deleteStudent(id);
			return new ResponseEntity<>("Student with id:" + id + " successfully deleted.", HttpStatus.OK);

		} catch (NoSuchObjectExistsException e) {
			return new ResponseEntity<>(e.getMessage(), e.getHttpStatus());
		}
	}
}
