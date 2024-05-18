package org.example.springproject.services.implementation;

import jakarta.persistence.EntityNotFoundException;
import org.example.springproject.entity.Course;
import org.example.springproject.enums.FacultySection;
import org.example.springproject.exceptions.InvalidCapacityException;
import org.example.springproject.exceptions.InvalidNameException;
import org.example.springproject.exceptions.InvalidStudyYearException;
import org.example.springproject.repository.CourseRepository;
import org.example.springproject.services.CourseService;
import org.example.springproject.utilities.NameValidator;
import org.springframework.beans.factory.annotation.Autowired;
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
        // Verify name
        if(!NameValidator.validateString(name)) {
            throw new InvalidNameException("Error: Name string contains only digits.");
        }

        // Verify category
        if(!NameValidator.validateString(category)) {
            throw new InvalidNameException("Error: Category string contains only digits.");
        }

        // Verify teacher
        if(!NameValidator.validateString(teacher)) {
            throw new InvalidNameException("Error: Teacher string contains only digits.");
        }

        // Verify course's capacity info
        if (maxCapacity <= 0 || maxCapacity > 50) {
            throw new InvalidCapacityException("The provided capacity value is unrealistic. Capacity must be a realistic number, typically not greater than 50.");
        }

        // Verify study year
        if (studyYear <= 0 || studyYear > 4) {
            throw new InvalidStudyYearException("The provided study year is invalid. Study year must be a positive integer between 1 and 4 (inclusive).");
        }

        Course newCourse = new Course(name,category,studyYear,teacher,maxCapacity,facultySection);
        newCourse.setApplicationsCount(0);
        return repository.save(newCourse);
    }

    @Override
    public Course updateCourse(Long id,String name, String category, Integer studyYear, String teacher, Integer maxCapacity, FacultySection facultySection, Integer applicationsCount){
        Course courseToBeUpdated = repository.findById(id).orElseThrow(EntityNotFoundException::new);

        // verify course name
        if(!NameValidator.validateString(name)) {
            throw new InvalidNameException("Error: Name string contains only digits.");
        }

        // Verify category
        if(!NameValidator.validateString(category)) {
            throw new InvalidNameException("Error: Category string contains only digits.");
        }

        // Verify teacher name
        if(!NameValidator.validateString(teacher)) {
            throw new InvalidNameException("Error: Teacher string contains only digits.");
        }

        // Verify study year
        if (studyYear <= 0 || studyYear > 4) {
            throw new InvalidStudyYearException("The provided study year is invalid. Study year must be a positive integer between 1 and 4 (inclusive).");
        }

        // Verify course's capacity info
        if (maxCapacity <= 0 || maxCapacity > 50) {
            throw new InvalidCapacityException("The provided capacity value is unrealistic. Capacity must be a realistic number, typically not greater than 50.");
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

    @Override
    public void deleteCourse(Long id){
        repository.findById(id).orElseThrow(EntityNotFoundException::new);
        repository.deleteById(id);
    }
}