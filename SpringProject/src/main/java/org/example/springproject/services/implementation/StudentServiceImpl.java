package org.example.springproject.services.implementation;

import org.example.springproject.entity.Student;
import org.example.springproject.enums.FacultySection;
import org.example.springproject.repository.StudentRepository;
import org.example.springproject.services.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.ReactiveQueryByExampleExecutor;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Service
public class StudentServiceImpl implements StudentService {

	@Autowired
	public StudentRepository repository;

	@Override
	public List<Student> getAllStudents() {
		return repository.findAll();
	}

	@Override
	public ResponseEntity<String> addStudent(String name, Integer studyYear, Float grade, FacultySection facultySection) {
		try {
			Student newStudent = new Student(studyYear, grade, facultySection);
			System.out.printf("Grade: %.2f\n Faculty: %s\n StudyYear: %d\n", grade, facultySection, studyYear);
			newStudent.setName(name);
			/*
			Auto complete role, i.e: here we add a new student
			 */
			newStudent.setRole("student");

			repository.save(newStudent);
			return ResponseEntity.ok("Successfully added new student");

		} catch (Exception e) {
			e.printStackTrace();
		}

		return ResponseEntity.badRequest().body("Failed adding student.");
	}

	@Override
	public ResponseEntity<String> updateStudent(Long id, String name, Integer studyYear, Float grade, FacultySection facultySection) {
		try {
			Student updateStudent = repository.findStudentById(id);

			if (updateStudent == null) {
				return ResponseEntity.badRequest().body("Student with id: " + id + " not found");
			}

			updateStudent.setName(name);
			updateStudent.setGrade(grade);
			updateStudent.setStudyYear(studyYear);
			updateStudent.setFacultySection(facultySection);
			repository.save(updateStudent);

			return ResponseEntity.ok("Successfully updated student with id: " + id);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return ResponseEntity.badRequest().body("Failed to update student");
	}

	@Override
	public ResponseEntity<String> deleteStudent(Long id) {
		try {
			Student deleteStudent = repository.findStudentById(id);

			if (deleteStudent == null) {
				return ResponseEntity.badRequest().body("Student with id: " + id + " not found");
			}

			repository.deleteById(id);
			return ResponseEntity.ok("Successfully deleted student with id: " + id);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return ResponseEntity.badRequest().body("Failed to delete student");
	}
}
