package org.example.springproject.repository;

import org.example.springproject.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Long> {
	Student findStudentByGrade(Float grade);
	Student findStudentByName(String name);
	Student findStudentById(Long id);
}
