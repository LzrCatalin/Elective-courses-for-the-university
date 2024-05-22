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
	private static final Logger logger = LoggerFactory.getLogger(CourseApi.class);

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
		List<Application> allocationsResult = applicationRepository.findAll(Sort.by("student.grade").descending()
				.and(Sort.by("student.id").ascending()
						.and(Sort.by("priority").ascending())));

		// Store for each student, list of enrolled IDs to help us further
		Map<Long, List<Long>> studentAllocations = new HashMap<>();

		// Store for each course, number of enrolled students
		Map<Long, Integer> courseRegistrations = new HashMap<>();

		// Iterate through sorted applications
		for (Application application : allocationsResult) {
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
			if (allocatedCountPerStudent < necessaryCoursesPerStudent &&
					courseRegistrations.getOrDefault(course.getId(), 0) < course.getMaxCapacity())
			{
				courseRegistrations.put(course.getId(), courseRegistrations.getOrDefault(course.getId(), 0) + 1);
				studentAllocations.computeIfAbsent(student.getId(), k -> new ArrayList<>()).add(course.getId());
				application.setStatus(Status.ACCEPTED);

			} else {
				application.setStatus(Status.REJECTED);
			}
		}

		// Save changes after allocation round
		applicationRepository.saveAll(allocationsResult);

		logger.info("Students info after first allocation: ");
		for (Map.Entry<Long, List<Long>> entry : studentAllocations.entrySet()) {
			logger.info("Student id: " + entry.getKey() + ": " + entry.getValue());
		}

		logger.info("Courses info after first allocation: ");
		for (Map.Entry<Long, Integer> entry : courseRegistrations.entrySet()) {
			logger.info("Course id: " + entry.getKey() + ": " + entry.getValue());
		}

		///////////////////////////////////////////////////////////////
		/*
		PROCESS WHERE WE ARE TRYING TO ALLOCATE REMAINING STUDENTS
		AT REMAINING COURSES
		 */
		///////////////////////////////////////////////////////////////
		// Get all students without necessary number of courses
		List<Student> uncompletedStudents = applicationRepository.findStudentsWithoutNecessaryCourses();
		uncompletedStudents.sort(Comparator.comparing(Student::getGrade).reversed().thenComparing(Student::getId));
		logger.info("Display uncompleted students: ");
		for (Student st : uncompletedStudents) {
			logger.info(st.getId().toString());
		}

		// Get all courses with available slots
		List<Course> availableCourses = applicationRepository.findAvailableCourses();
		logger.info("Display available courses: ");
		for (Course c : availableCourses) {
			logger.info(c.getId().toString());
		}

		// Allocation process : YEY N X M TIME
		for (Student student : uncompletedStudents) {

			// Get the number of allocated courses for standing student
			int allocatedCountPerStudent = studentAllocations.getOrDefault(student.getId(), new ArrayList<>()).size();
			logger.info("Student id: " + student.getId() + " with: " + allocatedCountPerStudent + " until now");

			// Check necessary number of courses of each study year
			int necessaryCoursesPerStudent = (student.getStudyYear() <= 2) ? 2 : 3;
			logger.info("Student id: " + student.getId() + " with: " + necessaryCoursesPerStudent + " necessary");

			// Keep track of how many courses the student still needs
			int remainingCoursesNeeded = necessaryCoursesPerStudent - allocatedCountPerStudent;
			logger.info("Remaining necessary courses:" + remainingCoursesNeeded);

			for (Course course : availableCourses) {

				if (remainingCoursesNeeded > 0 &&
						student.getStudyYear().equals(course.getStudyYear()) &&
						!hasStudentEnrolledInCourse(student, studentAllocations, course.getCategory()) &&
						courseRegistrations.getOrDefault(course.getId(), 0) < course.getMaxCapacity())
				{
					courseRegistrations.put(course.getId(), courseRegistrations.getOrDefault(course.getId(), 0) + 1);
					studentAllocations.computeIfAbsent(student.getId(), k -> new ArrayList<>()).add(course.getId());

					// Add the new application to student applications list
					Application newApplication = new Application(student, course, 0, Status.ACCEPTED);
					applicationRepository.save(newApplication);

					// Once allocated, decrease the value of remaining courses
					remainingCoursesNeeded--;
				}
			}
		}

		logger.info("Students info after second allocation: ");
		for (Map.Entry<Long, List<Long>> entry : studentAllocations.entrySet()) {
			logger.info("Student id: " + entry.getKey() + ": " + entry.getValue());
		}

		logger.info("Courses info after second allocation: ");
		for (Map.Entry<Long, Integer> entry : courseRegistrations.entrySet()) {
			logger.info("Course id: " + entry.getKey() + ": " + entry.getValue());
		}

		return allocationsResult;
	}


	/*
	The exact copy function from above condition used at setting reject status on
	students applications, but here we use it for uncompleted students
	where we just assign them at remaining courses.
	 */
	public boolean hasStudentEnrolledInCourse(Student student, Map<Long, List<Long>> studentAllocations, String category) {
		List<Long> enrolledCourses = studentAllocations.getOrDefault(student.getId(), new ArrayList<>());
		for (Long id : enrolledCourses) {
			Course course = courseRepository.findById(id).orElseThrow(EntityNotFoundException::new);

			if (course.getCategory().equals(category)) {
				return true;
			}
		}

		return false;
	}
}
