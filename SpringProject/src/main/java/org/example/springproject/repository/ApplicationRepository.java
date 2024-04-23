package org.example.springproject.repository;

import org.example.springproject.entity.Application;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApplicationRepository extends JpaRepository<Application, Long> {
    Application findApplicationByCourseId(Long id);
	Application findApplicationById(Long id);
	Application findApplicationByStudentId(Long id);
}
