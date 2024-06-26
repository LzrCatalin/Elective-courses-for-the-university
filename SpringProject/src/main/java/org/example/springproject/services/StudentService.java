package org.example.springproject.services;

import org.example.springproject.entity.Student;
import org.example.springproject.enums.FacultySection;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
public interface StudentService {
    Student getStudent(String email);

    List<Student> getAllStudents();

	int getSecondyearStudents();

	int getThirdYearStudents();

	Student getData(Long id);
	Student addStudent(String name, Integer studyYear, Float grade, FacultySection facultySection);
	Student updateStudent(Long id, String name, Integer studyYear, Float grade, FacultySection facultySection);
	void deleteStudent(Long id);
	int getFirstYearStudents();
}
