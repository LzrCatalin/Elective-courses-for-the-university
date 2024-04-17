package org.example.springproject.repository;

import org.example.springproject.entity.Application;
import org.example.springproject.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApplicationRepository extends JpaRepository<Application, Long> {
    Application findByCourse(Course course);
}
