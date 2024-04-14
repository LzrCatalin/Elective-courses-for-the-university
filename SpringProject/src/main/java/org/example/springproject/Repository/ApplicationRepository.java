package org.example.springproject.Repository;

import org.example.springproject.Entity.Application;
import org.example.springproject.Entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ApplicationRepository extends JpaRepository<Application, Long> {
    @Query("select a from Application a where a.course = ?1")
    Application findByCourse(Course course);

}
