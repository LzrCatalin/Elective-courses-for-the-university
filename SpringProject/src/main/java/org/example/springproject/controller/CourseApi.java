package org.example.springproject.controller;

import com.google.gson.Gson;
import com.sun.jdi.request.InvalidRequestStateException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.constraints.Email;
import org.example.springproject.entity.Course;
import org.example.springproject.enums.FacultySection;
import org.example.springproject.exceptions.InvalidCapacityException;
import org.example.springproject.exceptions.InvalidNameException;
import org.example.springproject.exceptions.InvalidStudyYearException;
import org.example.springproject.exceptions.NoSuchObjectExistsException;
import org.example.springproject.services.CourseService;
import org.example.springproject.services.EmailService;
import org.example.springproject.services.implementation.EmailServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


@CrossOrigin
@RestController
@RequestMapping("/courses")

public class CourseApi {
    private static final Logger logger = LoggerFactory.getLogger(CourseApi.class);
    private static final Gson gson = new Gson();
    @Autowired
    private CourseService courseService;
    @Autowired
    private EmailService emailService;

    @GetMapping
    public List<Course> getAllCourses(){
        return courseService.getAllCourses();
    }

    @PostMapping("/")
    public ResponseEntity<String> addCourse(@RequestBody Map<String, Object> requestBody){
        try {
            logger.info("Add course: LOGGER");
            String name = (String) requestBody.get("name");
            logger.info("Received name: " + name);
            String category = (String) requestBody.get("category");
            logger.info("Received category: " + category);
            Integer studyYear = (Integer) requestBody.get("studyYear");
            logger.info("Received studyYear: " + studyYear);
            String teacher = (String) requestBody.get("teacher");
            logger.info("Received teacher: " + teacher);
            Integer maxCapacity = (Integer) requestBody.get("maxCapacity");
            logger.info("Received capacity: " + maxCapacity);
            String facultySectionString = (String) requestBody.get("facultySection");
            FacultySection facultySection = FacultySection.valueOf(facultySectionString);
            logger.info("Received facultySection: " + facultySection);

            courseService.addCourse(name, category, studyYear, teacher, maxCapacity, facultySection);
            logger.info("Before mail sender...");
            emailService.sendNewCourseMail(name);
            logger.info("After mail sender...");
            return new ResponseEntity<>(gson.toJson("Course added successfully!"), HttpStatus.CREATED);

        } catch (InvalidNameException | InvalidCapacityException | InvalidStudyYearException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
	}

    @PutMapping("/{id}")
    public ResponseEntity<String> updateCourse(@PathVariable("id") Long id,
                                               @RequestBody Map<String, Object> requestBody){
        try {
            logger.info("Update course: LOGGER");
            logger.info("Course id: " + id + "; type:" + id.getClass());
            String name = (String) requestBody.get("name");
            logger.info("Received name: " + name);
            String category = (String) requestBody.get("category");
            logger.info("Received category: " + category);
            Integer studyYear = (Integer) requestBody.get("studyYear");
            logger.info("Received study year: " + studyYear);
            String teacher = (String) requestBody.get("teacher");
            logger.info("Received teacher: " + teacher);
            Integer maxCapacity = (Integer) requestBody.get("maxCapacity");
            logger.info("Received capacity: " + maxCapacity);
            String facultySectionString = (String) requestBody.get("facultySection");
            FacultySection facultySection = FacultySection.valueOf(facultySectionString);
            logger.info("Received facultySection: " + facultySection);
            Integer applicationsCount = (Integer) requestBody.get("applicationsCount");
            logger.info("Received count: " + applicationsCount);

            courseService.updateCourse(id, name, category, studyYear, teacher, maxCapacity, facultySection, applicationsCount);
            return new ResponseEntity<>("Course with id: " + id + " successfully updated!", HttpStatus.OK);

        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);

        } catch (InvalidNameException | InvalidCapacityException | InvalidStudyYearException | InvalidRequestStateException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
	}

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCourse(@PathVariable("id") Long id) {
        try {
            courseService.deleteCourse(id);
            return new ResponseEntity<>(gson.toJson("Application with id:" + id + " successfully deleted."), HttpStatus.OK);

        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>("Course id: " + id + " not found.", HttpStatus.NOT_FOUND);
        }
    }
}