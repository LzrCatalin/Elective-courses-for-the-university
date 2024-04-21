package org.example.springproject.services;

import org.example.springproject.entity.Student;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
public interface StudentService {
	List<Student> getAllStudents();
	ResponseEntity<String> addStudent(String name, String role, Integer studyYear, Float grade, String facultySection);
	ResponseEntity<String> updateStudent(Long id, String name, String role, Integer studyYear, Float grade, String facultySection);
	ResponseEntity<String> deleteStudent(Long id);
}
