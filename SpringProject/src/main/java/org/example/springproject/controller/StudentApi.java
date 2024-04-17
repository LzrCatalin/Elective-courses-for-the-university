package org.example.springproject.controller;

import org.example.springproject.entity.Student;
import org.example.springproject.services.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
