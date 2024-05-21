package org.example.springproject.repository;

import org.example.springproject.entity.CourseSchedule;
import org.springframework.data.repository.CrudRepository;

public interface CourseScheduleRepository extends CrudRepository<CourseSchedule,Long> {

}
