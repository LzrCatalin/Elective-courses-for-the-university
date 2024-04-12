package org.example.springproject.Services.Implementation;

import org.example.springproject.Entity.Course;
import org.example.springproject.Repository.CourseRepository;
import org.example.springproject.Services.CourseService;
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
