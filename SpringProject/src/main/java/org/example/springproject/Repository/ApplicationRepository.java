package org.example.springproject.Repository;

import org.example.springproject.Entity.Application;
import org.example.springproject.Entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApplicationRepository extends JpaRepository<Application, Long> {
    Application findByCourse(Course course);
}
