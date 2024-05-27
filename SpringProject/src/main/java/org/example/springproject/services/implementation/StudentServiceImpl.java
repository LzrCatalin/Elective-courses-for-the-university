package org.example.springproject.services.implementation;

import jakarta.persistence.EntityNotFoundException;
import org.example.springproject.entity.Student;
import org.example.springproject.enums.FacultySection;
import org.example.springproject.exceptions.InvalidGradeException;
import org.example.springproject.exceptions.InvalidNameException;
import org.example.springproject.exceptions.InvalidStudyYearException;
import org.example.springproject.repository.StudentRepository;
import org.example.springproject.services.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.example.springproject.utilities.NameValidator;
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
	public int getFirstYearStudents() {
		return repository.countFirstYearStudents();
	}
	@Override
	public int getSecondyearStudents(){
		return repository.countSecondYearStudents();
	}
	@Override
	public int getThirdYearStudents(){
		return repository.countThirdYearStudents();
	}
	@Override
	public Student getData(Long id) {
		return repository.findById(id).orElse(null);
	}

	@Override
	public Student addStudent(String name, Integer studyYear, Float grade, FacultySection facultySection) {
		// Verify inserted name
		if (!NameValidator.validateString(name)) {
			throw new InvalidNameException("The name contains only digits.");
		}

		// Verify inserted grade
		if (grade < 1 || grade > 10) {
			throw new InvalidGradeException("Ensure that the grade falls within the range of 1 to 10.");
		}

		// Verify study year
		if (studyYear <= 0 || studyYear > 4) {
			throw new InvalidStudyYearException("The provided study year is invalid. Study year must be a positive integer between 1 and 4 (inclusive).");
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
		// Check if exists
		Student updateStudent = repository.findById(id).orElseThrow(EntityNotFoundException::new);

		// Verify inserted name
		if (!NameValidator.validateString(name)) {
			throw new InvalidNameException("The string contains only digits.");
		}

		// Verify inserted grade
		if (grade < 1 || grade > 10) {
			throw new InvalidGradeException("Grade needs to be between 1 and 10.");
		}

		// Verify study year
		if (studyYear <= 0 || studyYear > 4) {
			throw new InvalidStudyYearException("The provided study year is invalid. Study year must be a positive integer between 1 and 4 (inclusive).");
		}

		updateStudent.setName(name);
		updateStudent.setGrade(grade);
		updateStudent.setStudyYear(studyYear);
		updateStudent.setFacultySection(facultySection);

		return repository.save(updateStudent);
	}

	@Override
	public void deleteStudent(Long id) {
		repository.findById(id).orElseThrow(EntityNotFoundException::new);
		repository.deleteById(id);
	}
}
