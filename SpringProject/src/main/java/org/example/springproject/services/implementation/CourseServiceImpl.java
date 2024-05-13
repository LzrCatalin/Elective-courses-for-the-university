package org.example.springproject.services.implementation;

import org.example.springproject.entity.Course;
import org.example.springproject.enums.FacultySection;
import org.example.springproject.exceptions.InvalidCapacityException;
import org.example.springproject.exceptions.InvalidNameException;
import org.example.springproject.exceptions.InvalidStudyYearException;
import org.example.springproject.exceptions.NoSuchObjectExistsException;
import org.example.springproject.repository.CourseRepository;
import org.example.springproject.services.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseServiceImpl implements CourseService {
    @Autowired
    private CourseRepository repository;

    @Override
    public List<Course> getAllCourses() {
        return (List<Course>) repository.findAll();
    }

    @Override
    public Course getCourse(String courseName) {
        return repository.findByName(courseName);
    }

    @Override
    public Course addCourse(String name, String category, Integer studyYear, String teacher, Integer maxCapacity, FacultySection facultySection){
        // Verify inserted name
        for (char c : name.toCharArray()) {
            if (Character.isDigit(c)) {
                throw new InvalidNameException("Verify inserted course name. Can not use numbers.", HttpStatus.BAD_REQUEST);
            }
        }

        // Verify teacher name
        for (char c : teacher.toCharArray()) {
            if (Character.isDigit(c)) {
                throw new InvalidNameException("Verify inserted teacher name. Can not use numbers.", HttpStatus.BAD_REQUEST);
            }
        }

        // Verify course's capacity info
        if (maxCapacity <= 0 || maxCapacity > 50) {
            throw new InvalidCapacityException("The provided capacity value is unrealistic. Capacity must be a realistic number, typically not greater than 50.",
                    HttpStatus.BAD_REQUEST
            );
        }

        // Verify study year
        if (studyYear <= 0 || studyYear > 4) {
            throw new InvalidStudyYearException("The provided study year is invalid. Study year must be a positive integer between 1 and 4 (inclusive).",
                    HttpStatus.BAD_REQUEST
            );
        }

        Course newCourse = new Course(name,category,studyYear,teacher,maxCapacity,facultySection);
        newCourse.setApplicationsCount(0);
        return repository.save(newCourse);
    }

    @Override
    public void deleteCourse(Long id){

        Course courseToBeDeleted = repository.findById(id).orElse(null);

        if(courseToBeDeleted == null){
            throw new NoSuchObjectExistsException("Course with id: " + id + " not found.", HttpStatus.NOT_FOUND);
        }

        repository.deleteById(id);
    }

    @Override
    public Course updateCourse(Long id,String name, String category, Integer studyYear, String teacher, Integer maxCapacity, FacultySection facultySection, Integer applicationsCount){
        // verify course name
        for (char c : name.toCharArray()) {
            if (Character.isDigit(c)) {
                throw new InvalidNameException("Verify inserted teacher name. Can not use numbers.", HttpStatus.BAD_REQUEST);
            }
        }

        // Verify teacher name
        for (char c : teacher.toCharArray()) {
            if (Character.isDigit(c)) {
                throw new InvalidNameException("Verify inserted teacher name. Can not use numbers.", HttpStatus.BAD_REQUEST);
            }
        }

        Course courseToBeUpdated = repository.findById(id).orElse(null);
        if(courseToBeUpdated == null){
            throw new NoSuchObjectExistsException("Course with id: " + id + " not found.", HttpStatus.NOT_FOUND);
        }

        courseToBeUpdated.setName(name);
        courseToBeUpdated.setCategory(category);
        courseToBeUpdated.setStudyYear(studyYear);
        courseToBeUpdated.setTeacher(teacher);
        courseToBeUpdated.setMaxCapacity(maxCapacity);
        courseToBeUpdated.setFacultySection(facultySection);
        courseToBeUpdated.setApplicationsCount(applicationsCount);

        return repository.save(courseToBeUpdated);
    }
}