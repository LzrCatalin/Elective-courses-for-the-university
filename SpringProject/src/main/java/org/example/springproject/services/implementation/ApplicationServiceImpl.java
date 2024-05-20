package org.example.springproject.services.implementation;

import jakarta.persistence.EntityNotFoundException;
import org.example.springproject.controller.CourseApi;
import org.example.springproject.entity.Application;
import org.example.springproject.entity.Course;
import org.example.springproject.entity.Student;
import org.example.springproject.enums.Status;
import org.example.springproject.exceptions.DuplicatePriorityException;
import org.example.springproject.exceptions.InvalidStudyYearException;
import org.example.springproject.exceptions.MismatchedFacultySectionException;
import org.example.springproject.exceptions.MismatchedIdTypeException;
import org.example.springproject.repository.ApplicationRepository;
import org.example.springproject.repository.CourseRepository;
import org.example.springproject.repository.StudentRepository;
import org.example.springproject.services.ApplicationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ApplicationServiceImpl implements ApplicationService {
	private static final Logger logger = LoggerFactory.getLogger(CourseApi.class);

	@Autowired
    private ApplicationRepository applicationRepository;
	@Autowired
	private StudentRepository studentRepository;
	@Autowired
	private CourseRepository courseRepository;

    /**
     * Method to get all the applications
     * @return a list of all applications
     */
    @Override
    public List<Application> getAllApplications() {
        return applicationRepository.findAll();
    }

	@Override
	public List<Application> getStudentApplications(Long id) {
		return applicationRepository.findApplicationsByStudentId(id);
	}

	@Override
	public List<Student> getStudentsOfCourse(Long courseId) {
		return applicationRepository.findStudentsIdThatAppliedCourse(courseId);
	}

	@Override
	public Application addApplication(Long studentId, Long courseId, Integer priority) {
		// Verify inserted IDs
		if (!(studentId instanceof Long) && !(courseId instanceof Long)) {
			throw new MismatchedIdTypeException("IDs must be of type Long.");

		} else if (!(studentId instanceof Long)) {
			throw new MismatchedIdTypeException("ID of student must be of type Long.");

		} else if (!(courseId instanceof Long)) {
			throw new MismatchedIdTypeException("ID of course must be of type Long.");
		}

		// Retrieve data based on IDs
		Student addedStudent = studentRepository.findById(studentId).orElseThrow(EntityNotFoundException::new);
		Course addedCourse = courseRepository.findById(courseId).orElseThrow(EntityNotFoundException::new);

		// Check existing ids
		if(addedStudent == null && addedCourse == null) {
			throw new EntityNotFoundException("Student id: " + studentId + ", Course id: " + courseId + " not found.");

		} else if (addedStudent == null) {
			throw new EntityNotFoundException("Student with id:" + studentId + " not found");

		} else if (addedCourse == null) {
			throw new EntityNotFoundException("Course with id:" + courseId + " not found");
		}

		/*
		Check if faculty section is the same for student and course
		 */
		if (!addedStudent.getFacultySection().equals(addedCourse.getFacultySection())) {
			throw new MismatchedFacultySectionException("Can not assign application for different faculty section.");
		}

		// All the above will be featured by unique constraint :)
		Application addApplication = new Application(addedStudent, addedCourse, priority);
		addApplication.setStatus(Status.PENDING);
		return applicationRepository.save(addApplication);
	}

	@Override
	public Application addApplicationAsStudent(Long studentId, String courseName, Integer priority) {
		Student student = studentRepository.findById(studentId).orElseThrow(EntityNotFoundException::new);
		Course course = courseRepository.findByName(courseName);

		if (course == null) {
			throw new EntityNotFoundException("Course name: " + courseName + " not found.");
		}

		// Verify for duplicate course try
		List<Long> appliedCoursesIDs = applicationRepository.findStudentAppliedCoursesId(studentId);
		if (appliedCoursesIDs.contains(course.getId())) {
			throw new IllegalArgumentException("Student has already applied for this course.");
		}

		// Verify facultySection
		if (!student.getFacultySection().equals(course.getFacultySection())) {
			throw new MismatchedFacultySectionException("Can not assign application for different faculty section.");
		}

		// Verify studyYear
		if (!student.getStudyYear().equals(course.getStudyYear())) {
			throw new InvalidStudyYearException("Can not assign application for different study year.");
		}

		// Verify if wanted priority is free
		List<Integer> studentPriorities = applicationRepository.findStudentPriorities(studentId);
		if (studentPriorities.contains(priority)) {
			throw new DuplicatePriorityException("Wanted priority: " + priority + " is taken.");
		}

		Application addApplication = new Application(student, course, priority);
		addApplication.setStatus(Status.PENDING);

		// Increase counter after an application add
		Integer count = course.getApplicationsCount();
		course.setApplicationsCount(count + 1);

		return applicationRepository.save(addApplication);
	}

	@Override
	public Application updateApplication(Long id, Integer newPriority, Status newStatus) {

		// Check if application exists by id
		Application application = applicationRepository.findById(id).orElseThrow(EntityNotFoundException::new);

		application.setPriority(newPriority);
		application.setStatus(newStatus);
		return applicationRepository.save(application);
	}

	@Override
	public Application updateApplicationAsStudent(Long id, Integer priority) {
		logger.info("Wanted priority: " + priority);
		Application application = applicationRepository.findById(id).orElseThrow(EntityNotFoundException::new);

		List<Integer> studentPriorities = applicationRepository.findStudentPriorities(application.getStudent().getId());
		logger.info("Priorities: " + studentPriorities);
		// Verify if wanted priority is free
		if (studentPriorities.contains(priority)) {
			logger.info("Inside taken path: ... ");
			// TAKEN PATH: Switch priorities between wanted application and founded one
			Application foundApplication = applicationRepository.findByPriorityAndStudentId(priority, application.getStudent().getId());
			logger.info("Found application: " + foundApplication);
			foundApplication.setPriority(application.getPriority());
			logger.info("Found application new priority: " + application.getPriority());
			application.setPriority(priority);

		} else {
			logger.info("Inside free path: ... ");
			// FREE PATH: Just apply the new priority to application
			application.setPriority(priority);
		}

		return applicationRepository.save(application);
	}

	@Override
	public void deleteApplication(Long id) {
		// Check if application exists by id
		Application application = applicationRepository.findById(id).orElseThrow(EntityNotFoundException::new);

		// Decrease counter after an application deletion
		Course course = application.getCourse();
		Integer counter = course.getApplicationsCount();
		course.setApplicationsCount(counter - 1);
		applicationRepository.delete(application);
	}
}
