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

    @PutMapping("/{id}")
    public ResponseEntity<String> updateCourse(@PathVariable("id") Long id,
                                               @RequestBody Map<String, Object> requestBody){
        try {
            logger.info("INCEPE LOGGERU");
            logger.info("Am primit id-ul:" + id + " de tip:" + id.getClass());
            String name = (String) requestBody.get("name");
            logger.info("Trece de name: " + name);
            String category = (String) requestBody.get("category");
            logger.info("Trece de category: " + category);
            Integer studyYear = (Integer) requestBody.get("studyYear");
            logger.info("Trece de studyYear");
            logger.info("Study Year:" + studyYear + " type: " + studyYear.getClass());
            String teacher = (String) requestBody.get("teacher");
            logger.info("Teacher: " + teacher + " " + teacher.getClass());
            Integer maxCapacity = (Integer) requestBody.get("maxCapacity");
            logger.info("Capacity: " + maxCapacity + " " + maxCapacity.getClass());
            String facultySectionString = (String) requestBody.get("facultySection");
            FacultySection facultySection = FacultySection.valueOf(facultySectionString);
            logger.info("Faculty Section: " + facultySection.toString() + " " + facultySection.getClass());
            Integer applicationsCount = (Integer) requestBody.get("applicationsCount");
            logger.info("Count: " + applicationsCount + " " + applicationsCount.getClass());

            courseService.updateCourse(id, name, category, studyYear, teacher, maxCapacity, facultySection, applicationsCount);
            return new ResponseEntity<>("Course with id: " + id + " successfully updated!", HttpStatus.OK);

        } catch (NoSuchObjectExistsException e) {
            return new ResponseEntity<>(e.getMessage(), e.getHttpStatus());

        } catch (InvalidNameException e) {
            return new ResponseEntity<>(e.getMessage(), e.getHttpStatus());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCourse(@PathVariable("id") Long id) {
        try {
            courseService.deleteCourse(id);
            return new ResponseEntity<>(gson.toJson("Application with id:" + id + " successfully deleted."), HttpStatus.OK);

        } catch (NoSuchObjectExistsException e) {
            return new ResponseEntity<>(e.getMessage(), e.getHttpStatus());
        }
    }
}