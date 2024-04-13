package org.example.springproject.Controller;

import org.example.springproject.Entity.Student;
import org.example.springproject.Services.StudentService;
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
