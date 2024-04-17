package org.example.springproject.services.implementation;

import org.example.springproject.entity.Course;
import org.example.springproject.repository.CourseRepository;
import org.example.springproject.services.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseServiceImpl implements CourseService {

    @Autowired
    private CourseRepository repository;

    /**
     * Method to get all courses
     * @return a list of all courses
     */
    @Override
    public List<Course> getAllCourses() {
        return (List<Course>) repository.findAll();
    }
}
