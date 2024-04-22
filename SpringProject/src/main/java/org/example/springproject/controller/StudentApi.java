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
		return studentService.addStudent(name, studyYear, grade, facultySection);
	}

	@PutMapping("/{id}")
	public ResponseEntity<String> updateStudent(@PathVariable("id") Long id, String name, Integer studyYear, Float grade, FacultySection facultySection) {
		return studentService.updateStudent(id, name, studyYear, grade, facultySection);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteStudent(@PathVariable("id") Long id) {
		return studentService.deleteStudent(id);
	}
}
