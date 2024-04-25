package org.example.springproject.controller;

import org.example.springproject.entity.Student;
import org.example.springproject.enums.FacultySection;
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

	@GetMapping
	public String displayGreetings() {
		return "Welcome to students";
	}

	@GetMapping("/")
	public List<Student> getAllStudents() {
		return studentService.getAllStudents();
	}

	@PostMapping("/")
	public ResponseEntity<String> addStudent(String name, Integer studyYear, Float grade, FacultySection facultySection) {
		studentService.addStudent(name, studyYear, grade, facultySection);
		return new ResponseEntity<>("Student added successfully.", HttpStatus.CREATED);
	}

	@PutMapping("/{id}")
	public ResponseEntity<String> updateStudent(@PathVariable("id") Long id, String name, Integer studyYear, Float grade, FacultySection facultySection) {
		studentService.updateStudent(id, name, studyYear, grade, facultySection);
		return new ResponseEntity<>("Student with id:" + id + " successfully updated.", HttpStatus.OK);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteStudent(@PathVariable("id") Long id) {
		studentService.deleteStudent(id);
		return new ResponseEntity<>("Student with id:" + id + " successfully deleted.", HttpStatus.OK);
	}
}
