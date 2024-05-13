package org.example.springproject.repository;

import org.example.springproject.entity.Application;
import org.example.springproject.entity.Student;
import org.example.springproject.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ApplicationRepository extends JpaRepository<Application, Long> {
    Application findByCourseId(Long id);
	Application findByStudentId(Long id);
	List<Application> findApplicationsByStudentId(Long id);
	Application findByStudentIdAndCourseId(Long studentId, Long courseId);
	@Query("select a.student" +
			" from Application a " +
			"where a.course.id = :courseId and a.status = 'PENDING'")
	List<Student> findStudentsIdThatAppliedCourse(@Param("courseId") Long courseId);
}
