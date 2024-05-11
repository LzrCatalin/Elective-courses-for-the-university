package org.example.springproject.repository;

import org.example.springproject.entity.Application;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ApplicationRepository extends JpaRepository<Application, Long> {
    Application findByCourseId(Long id);
	Application findByStudentId(Long id);
	List<Application> findApplicationsByStudentId(Long id);
	Application findByStudentIdAndCourseId(Long studentId, Long courseId);
}
