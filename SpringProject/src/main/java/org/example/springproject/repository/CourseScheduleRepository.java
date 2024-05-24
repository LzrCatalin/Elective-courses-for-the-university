package org.example.springproject.repository;

import org.example.springproject.entity.CourseSchedule;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseScheduleRepository extends JpaRepository<CourseSchedule,Long> {

}
