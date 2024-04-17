package org.example.springproject.repository;

import org.example.springproject.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Long> {
	Student findStudentByUserId(Long userid);
	Student findStudentByGrade(Float grade);
}
