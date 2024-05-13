package org.example.springproject.services.implementation;

import org.example.springproject.entity.Student;
import org.example.springproject.enums.FacultySection;
import org.example.springproject.exceptions.InvalidGradeException;
import org.example.springproject.exceptions.InvalidNameException;
import org.example.springproject.exceptions.NoSuchObjectExistsException;
import org.example.springproject.repository.StudentRepository;
import org.example.springproject.services.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

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
	public Student getData(Long id) {
		return repository.findById(id).orElse(null);
	}

	@Override
	public Student addStudent(String name, Integer studyYear, Float grade, FacultySection facultySection) {

		// Verify inserted name
		for (char c : name.toCharArray()) {
			if (Character.isDigit(c)) {
				throw new InvalidNameException("The provided name contains invalid characters or format. Names cannot include numbers.", HttpStatus.BAD_REQUEST);
			}
		}

		// Verify inserted grade
		if (grade < 1 || grade > 10) {
			throw new InvalidGradeException("Ensure that the grade falls within the range of 1 to 10.", HttpStatus.BAD_REQUEST);
		}

		Student newStudent = new Student(studyYear, grade, facultySection);
		newStudent.setName(name);
		/*
		Auto complete role, i.e: here we add a new student
		 */
		newStudent.setRole("student");

		return repository.save(newStudent);

	}

	@Override
	public Student updateStudent(Long id, String name, Integer studyYear, Float grade, FacultySection facultySection) {
		// Verify inserted name
		for (char c : name.toCharArray()) {
			if (Character.isDigit(c)) {
				throw new InvalidNameException("The provided name contains invalid characters or format. Names cannot include numbers.", HttpStatus.BAD_REQUEST);
			}
		}

		Student updateStudent = repository.findById(id).orElse(null);
		if (updateStudent == null) {
			throw new NoSuchObjectExistsException("Student with id: " + id + " not found.", HttpStatus.NOT_FOUND);
		}

		// Verify inserted grade
		if (grade < 1 || grade > 10) {
			throw new InvalidGradeException("Grade needs to be between 1 and 10.", HttpStatus.BAD_REQUEST);
		}

		updateStudent.setName(name);
		updateStudent.setGrade(grade);
		updateStudent.setStudyYear(studyYear);
		updateStudent.setFacultySection(facultySection);

		return repository.save(updateStudent);
	}

	@Override
	public void deleteStudent(Long id) {
		Student deleteStudent = repository.findById(id).orElse(null);

		if (deleteStudent == null) {
			throw new NoSuchObjectExistsException("Student with id: " + id + " not found.", HttpStatus.NOT_FOUND);
		}

		repository.deleteById(id);
	}
}
