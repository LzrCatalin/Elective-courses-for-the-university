package org.example.springproject.controller;

import org.example.springproject.entity.Course;
import org.example.springproject.enums.FacultySection;
import org.example.springproject.services.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/")
    public ResponseEntity<String> addCourse(String name, String category, Integer studyYear, String teacher, Integer maxCapacity, FacultySection facultySection){
        courseService.addCourse(name,category,studyYear,teacher,maxCapacity,facultySection);
        return new ResponseEntity<>("Course added successfully!", HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateCourse( @PathVariable("id") Long id,String name, String category, Integer studyYear, String teacher, Integer maxCapacity, FacultySection facultySection){
        courseService.updateCourse(id,name,category,studyYear,teacher,maxCapacity,facultySection);
        return new ResponseEntity<>("Course with id: " + id + " successfully updated!", HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCourse(@PathVariable("id") Long id){
        courseService.deleteCourse(id);
        return new ResponseEntity<>("Course with id: " + id + " successfully deleted!", HttpStatus.OK);
    }
}