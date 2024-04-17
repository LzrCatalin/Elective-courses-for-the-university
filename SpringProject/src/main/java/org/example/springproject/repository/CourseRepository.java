package org.example.springproject.repository;

import org.example.springproject.entity.Course;
import org.springframework.data.repository.CrudRepository;

public interface CourseRepository extends CrudRepository<Course,Long> {

    /**
     * Method to find a course by name
     * @param name of the course
     * @return the course with the given name
     */
    Course findByName(String name);

}
