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
	public Student addStudent(String name, Integer studyYear, Float grade, FacultySection facultySection) {
		try {
			Student newStudent = new Student(studyYear, grade, facultySection);
			newStudent.setName(name);
			/*
			Auto complete role, i.e: here we add a new student
			 */
			newStudent.setRole("student");

			return repository.save(newStudent);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	@Override
	public Student updateStudent(Long id, String name, Integer studyYear, Float grade, FacultySection facultySection) {
		try {
			Student updateStudent = repository.findStudentById(id);

//			if (updateStudent == null) {
//				// Exception Handling
//			}

			updateStudent.setName(name);
			updateStudent.setGrade(grade);
			updateStudent.setStudyYear(studyYear);
			updateStudent.setFacultySection(facultySection);
			return repository.save(updateStudent);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	@Override
	public void deleteStudent(Long id) {
		try {
			Student deleteStudent = repository.findStudentById(id);

//			if (deleteStudent == null) {
//				return ResponseEntity.badRequest().body("Student with id: " + id + " not found");
//			}

			repository.deleteById(id);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
