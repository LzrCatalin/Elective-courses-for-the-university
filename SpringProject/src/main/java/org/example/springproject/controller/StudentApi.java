package org.example.springproject.controller;

import org.example.springproject.entity.Student;
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

	@PostMapping("/add")
	public ResponseEntity<String> addStudent(String name, String role, Integer studyYear, Float grade, String facultySection) {
		return studentService.addStudent(name, role, studyYear, grade, facultySection);
	}

	@PutMapping("/update/{id}")
	public ResponseEntity<String> updateStudent(@PathVariable("id") Long id, String name, String role, Integer studyYear, Float grade, String facultySection) {
		return studentService.updateStudent(id, name, role, studyYear, grade, facultySection);
	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<String> deleteStudent(@PathVariable("id") Long id) {
		return studentService.deleteStudent(id);
	}
}
