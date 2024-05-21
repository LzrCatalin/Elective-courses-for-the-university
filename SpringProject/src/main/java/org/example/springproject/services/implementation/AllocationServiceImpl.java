package org.example.springproject.services.implementation;

import jakarta.persistence.EntityNotFoundException;
import org.example.springproject.controller.CourseApi;
import org.example.springproject.entity.Application;
import org.example.springproject.entity.Course;
import org.example.springproject.entity.Student;
import org.example.springproject.enums.Status;
import org.example.springproject.repository.ApplicationRepository;
import org.example.springproject.repository.CourseRepository;
import org.example.springproject.services.AllocationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class AllocationServiceImpl implements AllocationService {
	@Autowired
	private ApplicationRepository applicationRepository;
	@Autowired
	private CourseRepository courseRepository;

	/*
		IDEA OF ALLOCATION LOGIC:
	1. ORDER STUDENTS DESC BY GRADE
	2. FOR EACH STUDENT, BASED ON PRIORITY, CHECK IF COURSE IS FREE OR NOT
	3. CHANGE STATUS
	4. FOR UNALLOCATED STUDENTS: STILL PROCESSING ...
	 */
	@Override
	public List<Application> allocationsResult() {
		// Sort applications by criteria
		List<Application> pendingAllocations = applicationRepository.findAll(Sort.by("student.grade").descending().and(Sort.by("priority").ascending()));

		// Store for each student, list of enrolled IDs to help us further
		Map<Long, List<Long>> studentAllocations = new HashMap<>();

		// Store for each course, number of enrolled students
		Map<Long, Integer> courseEnrolls = new HashMap<>();

		// Iterate through sorted applications
		for (Application application : pendingAllocations) {
			Student student = application.getStudent();
			Course course = application.getCourse();

			// Verify condition where student can not be accepted at multiple courses from the same category
			// Where the list of ids help us from studentAllocations map
			for (Map.Entry<Long, List<Long>> entry : studentAllocations.entrySet()) {
				List<Long> enrolledCourses = entry.getValue();

				for (Long id: enrolledCourses) {
					Course existedCourse = courseRepository.findById(id).orElseThrow(EntityNotFoundException::new);
					if (course.getCategory().equals(existedCourse.getCategory())) {
						application.setStatus(Status.REJECTED);
					}
				}
			}

			// Get the number of allocated courses for standing student
			int allocatedCountPerStudent = studentAllocations.getOrDefault(student.getId(), new ArrayList<>()).size();

			// Check necessary number of courses of each study year
			int necessaryCoursesPerStudent = (student.getStudyYear() <= 2) ? 2 : 3;

			// Start allocation condition
			if (allocatedCountPerStudent < necessaryCoursesPerStudent && courseEnrolls.getOrDefault(course.getId(), 0) < course.getMaxCapacity()) {
				courseEnrolls.put(course.getId(), courseEnrolls.getOrDefault(course.getId(), 0) + 1);
				studentAllocations.computeIfAbsent(student.getId(), k -> new ArrayList<>()).add(course.getId());
				application.setStatus(Status.ACCEPTED);

			} else {
				application.setStatus(Status.REJECTED);
			}
		}

//		// Verify condition where student must have at least 2, or 3 enrolled courses. Based on studyYear
//		if (studentAllocations.values().size() < studentAllocations.keySet())

		// Save changes after allocation round
		applicationRepository.saveAll(pendingAllocations);

		return pendingAllocations;
	}
}
