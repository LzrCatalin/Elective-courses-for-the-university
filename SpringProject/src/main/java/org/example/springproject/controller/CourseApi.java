package org.example.springproject.controller;

import com.google.gson.Gson;
import jakarta.validation.constraints.Email;
import org.example.springproject.entity.Course;
import org.example.springproject.enums.FacultySection;
import org.example.springproject.exceptions.InvalidCapacityException;
import org.example.springproject.exceptions.InvalidNameException;
import org.example.springproject.exceptions.InvalidStudyYearException;
import org.example.springproject.exceptions.NoSuchObjectExistsException;
import org.example.springproject.services.CourseService;
import org.example.springproject.services.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@CrossOrigin
@RestController
@RequestMapping("/courses")

public class CourseApi {

    private static final Gson gson = new Gson();
    @Autowired
    private CourseService courseService;
    @Autowired
    private EmailService emailService;

    @GetMapping
    public List<Course> getAllCourses(){
        return courseService.getAllCourses();
    }

    @RequestMapping(method = RequestMethod.POST)
    @PostMapping("/")
    public ResponseEntity<String> addCourse(@RequestParam String name, String category, Integer studyYear,
                                            @RequestParam String teacher,
                                            @RequestParam Integer maxCapacity, FacultySection facultySection){
        try {
            courseService.addCourse(name, category, studyYear, teacher, maxCapacity, facultySection);
            emailService.sendNewCourseMail(name);
            return new ResponseEntity<>(gson.toJson("Course added successfully!"), HttpStatus.CREATED);

        } catch (InvalidNameException e) {
            return new ResponseEntity<>(e.getMessage(), e.getHttpStatus());

        } catch (InvalidCapacityException e) {
            return new ResponseEntity<>(e.getMessage(), e.getHttpStatus());

        } catch (InvalidStudyYearException e) {
            return new ResponseEntity<>(e.getMessage(), e.getHttpStatus());
        }
    }

    @RequestMapping(method = RequestMethod.PUT)
    @PutMapping("/{id}")
    public ResponseEntity<String> updateCourse(@PathVariable("id") Long id,
                                               @RequestParam String name, String category,
                                               @RequestParam Integer studyYear,
                                               @RequestParam String teacher, Integer maxCapacity, FacultySection facultySection, Integer applicationsCount){
        try {
            courseService.updateCourse(id, name, category, studyYear, teacher, maxCapacity, facultySection, applicationsCount);
            return new ResponseEntity<>("Course with id: " + id + " successfully updated!", HttpStatus.OK);

        } catch (NoSuchObjectExistsException e) {
            return new ResponseEntity<>(e.getMessage(), e.getHttpStatus());

        } catch (InvalidNameException e) {
            return new ResponseEntity<>(e.getMessage(), e.getHttpStatus());
        }
    }
    //@RequestMapping(method = RequestMethod.DELETE)
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCourse(@PathVariable("id") Long id) {
        try {
            //emailService.sendDeleteApplicationMail(id);
            courseService.deleteCourse(id);
            return new ResponseEntity<>(gson.toJson("Application with id:" + id + " successfully deleted."), HttpStatus.OK);

        } catch (NoSuchObjectExistsException e) {
            return new ResponseEntity<>(e.getMessage(), e.getHttpStatus());
        }
    }
}