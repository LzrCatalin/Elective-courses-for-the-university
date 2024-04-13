package org.example.springproject.Controller;

import org.example.springproject.Entity.Course;
import org.example.springproject.Services.CourseService;
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
