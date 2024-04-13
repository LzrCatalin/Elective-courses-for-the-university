package org.example.springproject.Repository;

import org.example.springproject.Entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StudentRepository extends JpaRepository<Student, Long> {
	Student findStudentByUserId(Long userid);
	Student findStudentByGrade(Float grade);
}
