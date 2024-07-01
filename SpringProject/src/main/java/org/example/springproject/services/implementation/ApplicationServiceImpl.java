package org.example.springproject.services.implementation;

import jakarta.persistence.EntityNotFoundException;
import org.example.springproject.controller.CourseApi;
import org.example.springproject.entity.Application;
import org.example.springproject.entity.Course;
import org.example.springproject.entity.Student;
import org.example.springproject.enums.Status;
import org.example.springproject.exceptions.*;
import org.example.springproject.repository.ApplicationRepository;
import org.example.springproject.repository.CourseRepository;
import org.example.springproject.repository.StudentRepository;
import org.example.springproject.services.ApplicationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.management.InvalidAttributeValueException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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
	public List<Student> getStudentsOfCourse(Long courseId, Status status) {
		return applicationRepository.findStudentsThatAppliedCourse(courseId, Arrays.asList(status, Status.REASSIGNED));
	}

	@Override
	public List<Student> getStudentClassmatesOnCourse(Long courseId, Long studentId) {
		return applicationRepository.getStudentClassmatesOnCourse(courseId, studentId);
	}

	@Override
	public Application addApplication(Long studentId, Long courseId) {
		// Verify inserted IDs
		if (!(studentId instanceof Long) && !(courseId instanceof Long)) {
			throw new MismatchedIdTypeException("IDs must be of type Long.");

		} else if (!(studentId instanceof Long)) {
			throw new MismatchedIdTypeException("ID of student must be of type Long.");

		} else if (!(courseId instanceof Long)) {
			throw new MismatchedIdTypeException("ID of course must be of type Long.");
		}

		// Retrieve data based on IDs
		Student student = studentRepository.findById(studentId).orElseThrow(EntityNotFoundException::new);
		Course course = courseRepository.findById(courseId).orElseThrow(EntityNotFoundException::new);

		// Check existing ids
		if(student == null && course == null) {
			throw new EntityNotFoundException("Student id: " + studentId + ", Course id: " + courseId + " not found.");

		} else if (student == null) {
			throw new EntityNotFoundException("Student with id:" + studentId + " not found");

		} else if (course == null) {
			throw new EntityNotFoundException("Course with id:" + courseId + " not found");
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

		int studentApplicationsCount = applicationRepository.getStudentApplicationsCount(studentId);

		Application addApplication = new Application(student, course, studentApplicationsCount + 1);
		addApplication.setStatus(Status.PENDING);

		// Increase counter after an application add
		int courseCount = applicationRepository.getCourseApplicationsCount(course.getId());
		course.setApplicationsCount(courseCount + 1);

		return applicationRepository.save(addApplication);
	}

	@Override
	public Application updateApplication(Long id, Long studentId, Long courseId, Integer newPriority, Status newStatus) {

		// Check if application exists by id
		Application application = applicationRepository.findById(id).orElseThrow(EntityNotFoundException::new);
		Student student = studentRepository.findById(studentId).orElseThrow(EntityNotFoundException::new);
		Course course = courseRepository.findById(courseId).orElseThrow(EntityNotFoundException::new);

		application.setStudent(student);
		application.setCourse(course);
		application.setPriority(newPriority);
		application.setStatus(newStatus);
		return applicationRepository.save(application);
	}

	@Override
	public Application updateApplicationAsAdmin(Long studentId, String courseName, String newCourseName) {
//		logger.info("AM INTRAT IN FUNCTIA DIN SERVICE ... ");
		// Validate student id
		Student student = studentRepository.findById(studentId).orElseThrow(EntityNotFoundException::new);

//		logger.info("Received course in function: " + courseName);
		// Validate course id
		Course course = courseRepository.findByName(courseName);
//		logger.info("Course found with id {}", course.getId());

//		logger.info("Received new course in function: " + newCourseName);
		// Validate course id
		Course newCourse = courseRepository.findByName(newCourseName);
		if (newCourse == null) {
			throw new EntityNotFoundException("Course: " + newCourseName + " not found.");
		}

//		logger.info("New course found with id {}", newCourse.getId());

		// Validate application by student and course ids
		Application application = applicationRepository.findByStudentIdAndCourseId(studentId, course.getId());
//		logger.info("Found application: " + application.getId());
		Application wantedApplication = applicationRepository.findByStudentIdAndCourseId(studentId, newCourse.getId());

		// Get course capacity info
		int newCourseEnrolls = applicationRepository.getCourseAcceptedStudents(newCourse.getId()).size();

//		logger.info("New course enrolls number: " + newCourseEnrolls);
//		logger.info("New course capacity: " + newCourse.getMaxCapacity());

		List<String> studentEnrolledCategories = applicationRepository.findAcceptedCourseCategoriesByStudentId(studentId);
//		studentEnrolledCategories.remove(course.getCategory());

		if (application == wantedApplication) {
			throw new DuplicateCourseAssignmentException("Can not assign student at the same course.");
		}

		if (!student.getStudyYear().equals(newCourse.getStudyYear())) {
			throw new InvalidStudyYearException("Can not assign student to a different study year course. Student year: " + student.getStudyYear());
		}

		if (studentEnrolledCategories.contains(newCourse.getCategory())) {
			throw new DuplicateCategoryAssignmentException("The student is already assigned to the category: " + course.getCategory());
		}

		if (newCourseEnrolls == newCourse.getMaxCapacity()) {
			logger.debug("New Course enrolls: " + newCourseEnrolls);
			throw new CourseCapacityExceededException("The course " + newCourseName + " has no more available seats.");
		}

		if (student.getStudyYear().equals(newCourse.getStudyYear()) &&
				!studentEnrolledCategories.contains(newCourse.getCategory()) &&
				newCourseEnrolls < newCourse.getMaxCapacity()) {

			if (wantedApplication == null) {

				logger.debug("Can not find any application for the new course assign.");
				application.setStudent(student);
				application.setCourse(newCourse);
				application.setPriority(0);
				application.setStatus(Status.REASSIGNED);

				return applicationRepository.save(application);

			} else {
				logger.debug("Found application for the new course assign.");
				wantedApplication.setStatus(Status.REASSIGNED);
				wantedApplication.setPriority(0);

				application.setStatus(Status.REJECTED);
				application.setPriority(-1);

				return applicationRepository.save(wantedApplication);
			}
		}

		logger.info("Assigned fail");
		return applicationRepository.save(application);
	}

	@Override
	public List<Application> updateApplicationAsStudent(Long id, Integer newPriority) {
		logger.debug("Wanted priority: " + newPriority);
		Application application = applicationRepository.findById(id).orElseThrow(EntityNotFoundException::new);
		int currentPriority = application.getPriority();

		if (newPriority <= 0 || newPriority > 100) {
			throw new InvalidPriorityException("Priority needs to be greater than 0.");
		}

		Long studentId = application.getStudent().getId();
		List<Application> studentApplications = applicationRepository.findByStudentIdOrderByPriorityAsc(studentId);

		logger.debug("Current priorities: " + studentApplications.stream().map(Application::getPriority).toList());

		boolean isPriorityTaken = studentApplications.stream().anyMatch(app -> app.getPriority().equals(newPriority));

		if (isPriorityTaken) {
			if (newPriority > currentPriority) {
				logger.debug("Branch where newPriority greater than current");
				// Adjust priorities to make room for the new priority
				for (Application app : studentApplications) {
					logger.debug("Target application priority: " + app.getPriority());
					logger.debug("Wanted application priority: " + application.getPriority());
					if (app.getId().equals(id)) {
						logger.debug("Found application. Set new priority.");
						app.setPriority(newPriority);

					} else if (app.getPriority() <= newPriority && app.getPriority() >= currentPriority) {
						logger.debug("Decrease priority to re-establish priorities.");
						app.setPriority(app.getPriority() - 1);
					}
				}

			} else {
				logger.debug("Branch where newPriority less than current");
				// Adjust priorities to make room for the new priority
				for (Application app : studentApplications) {
					logger.debug("Target application priority: " + app.getPriority());
					logger.debug("Wanted application priority: " + application.getPriority());
					if (app.getId().equals(id)) {
						logger.debug("Found application. Set new priority.");
						app.setPriority(newPriority);

					} else if (app.getPriority() >= newPriority && app.getPriority() <= currentPriority) {
						logger.debug("Decrease priority to re-establish priorities.");
						app.setPriority(app.getPriority() + 1);
					}
				}
			}
		} else {
			// If the new priority is free, just set it
			application.setPriority(newPriority);
		}

		// Save all changes
		applicationRepository.saveAll(studentApplications);

		// Re-fetch the updated list of applications for the student
		return applicationRepository.findByStudentIdOrderByPriorityAsc(studentId);
	}

	@Override
	public List<Application> deleteApplication(Long id) {
		// Check if application exists by id
		Application application = applicationRepository.findById(id).orElseThrow(EntityNotFoundException::new);

		// Retrieve student
		long studentId = application.getStudent().getId();

		// Delete application
		applicationRepository.delete(application);

		// Retrieve student list of applications
		List<Application> studentApplications = applicationRepository.findByStudentIdOrderByPriorityAsc(studentId);

		// Re-establish priorities
		studentApplications.stream()
				.filter(app -> app.getPriority() > application.getPriority())
				.forEach(app -> app.setPriority(app.getPriority() - 1));

		// Decrease counter after an application deletion
		Course course = application.getCourse();
		int courseCount = applicationRepository.getCourseApplicationsCount(course.getId());
		course.setApplicationsCount(courseCount - 1);

		applicationRepository.saveAll(studentApplications);

		// Re-fetch updated list of applications
		return applicationRepository.findApplicationsByStudentId(studentId);
	}
}
