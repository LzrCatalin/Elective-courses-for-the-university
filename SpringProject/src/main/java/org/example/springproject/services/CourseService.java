package org.example.springproject.services;

import com.zaxxer.hikari.util.ClockSource;
import org.example.springproject.entity.Course;
import org.example.springproject.enums.FacultySection;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface CourseService {
	public List<Course> getAllCourses();

	ResponseEntity<String> addCourse(String name, String category, Integer studyYear, String teacher, Integer maxCapacity, FacultySection facultySection);

	ResponseEntity<String> deleteCourse(Long id);

	ResponseEntity<String> updateCourse(Long id, String name, String category, Integer studyYear, String teacher, Integer maxCapacity, FacultySection facultySection);
}
