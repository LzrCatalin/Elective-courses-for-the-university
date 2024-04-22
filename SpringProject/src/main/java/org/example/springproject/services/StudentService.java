package org.example.springproject.services;

import org.example.springproject.entity.Student;
import org.example.springproject.enums.FacultySection;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
public interface StudentService {
	List<Student> getAllStudents();
	ResponseEntity<String> addStudent(String name, Integer studyYear, Float grade, FacultySection facultySection);
	ResponseEntity<String> updateStudent(Long id, String name, Integer studyYear, Float grade, FacultySection facultySection);
	ResponseEntity<String> deleteStudent(Long id);
}
