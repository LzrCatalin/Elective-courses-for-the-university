package org.example.springproject.controller;

import org.example.springproject.entity.Course;
import org.example.springproject.enums.FacultySection;
import org.example.springproject.exceptions.InvalidCapacityException;
import org.example.springproject.exceptions.InvalidNameException;
import org.example.springproject.exceptions.InvalidStudyYearException;
import org.example.springproject.exceptions.NoSuchObjectExistsException;
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
    public ResponseEntity<String> addCourse(@RequestParam String name, String category, Integer studyYear,
                                            @RequestParam String teacher,
                                            @RequestParam Integer maxCapacity, FacultySection facultySection){
        try {
            courseService.addCourse(name, category, studyYear, teacher, maxCapacity, facultySection);
            return new ResponseEntity<>("Course added successfully!", HttpStatus.CREATED);

        } catch (InvalidNameException e) {
            return new ResponseEntity<>(e.getMessage(), e.getHttpStatus());

        } catch (InvalidCapacityException e) {
            return new ResponseEntity<>(e.getMessage(), e.getHttpStatus());

        } catch (InvalidStudyYearException e) {
            return new ResponseEntity<>(e.getMessage(), e.getHttpStatus());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateCourse(@PathVariable("id") Long id,
                                               @RequestParam String name, String category,
                                               @RequestParam Integer studyYear,
                                               @RequestParam String teacher, Integer maxCapacity, FacultySection facultySection){
        try {
            courseService.updateCourse(id, name, category, studyYear, teacher, maxCapacity, facultySection);
            return new ResponseEntity<>("Course with id: " + id + " successfully updated!", HttpStatus.OK);

        } catch (NoSuchObjectExistsException e) {
            return new ResponseEntity<>(e.getMessage(), e.getHttpStatus());

        } catch (InvalidNameException e) {
            return new ResponseEntity<>(e.getMessage(), e.getHttpStatus());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCourse(@PathVariable("id") Long id){
        try {
            courseService.deleteCourse(id);
            return new ResponseEntity<>("Course with id: " + id + " successfully deleted!", HttpStatus.OK);

        } catch (NoSuchObjectExistsException e) {
            return new ResponseEntity<>(e.getMessage(), e.getHttpStatus());
        }
    }
}