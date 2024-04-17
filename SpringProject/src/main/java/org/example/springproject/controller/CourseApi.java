package org.example.springproject.controller;

import org.example.springproject.entity.Course;
import org.example.springproject.services.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/courses")

public class CourseApi {

    @Autowired
    private CourseService courseService;

    @GetMapping("/")
    public List<Course> getAllCourses(){
        return courseService.getAllCourses();
    }
}
