package org.example.springproject.services;

import org.example.springproject.entity.Course;
import org.example.springproject.enums.FacultySection;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface CourseService {
	public List<Course> getAllCourses();

  Course addCourse(String name, String category, Integer studyYear, String teacher, Integer maxCapacity, FacultySection facultySection);

  void deleteCourse(Long id);

   Course updateCourse(Long id, String name, String category, Integer studyYear, String teacher, Integer maxCapacity, FacultySection facultySection, Integer applicationsCount);

   Course getCourse(String courseName);
}