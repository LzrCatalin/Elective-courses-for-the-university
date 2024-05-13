package org.example.springproject.services.implementation;

import org.example.springproject.entity.Application;
import org.example.springproject.entity.Course;
import org.example.springproject.entity.Student;
import org.example.springproject.enums.Status;
import org.example.springproject.exceptions.InvalidStudyYearException;
import org.example.springproject.exceptions.MismatchedFacultySectionException;
import org.example.springproject.exceptions.MismatchedIdTypeException;
import org.example.springproject.exceptions.NoSuchObjectExistsException;
import org.example.springproject.repository.ApplicationRepository;
import org.example.springproject.repository.CourseRepository;
import org.example.springproject.repository.StudentRepository;
import org.example.springproject.services.ApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ApplicationServiceImpl implements ApplicationService {
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
	public Application addApplication(Long studentId, Long courseId, Integer priority) {
		// Verify inserted IDs
		if (!(studentId instanceof Long) && !(courseId instanceof Long)) {
			throw new MismatchedIdTypeException("IDs must be of type Long.", HttpStatus.BAD_REQUEST);

		} else if (!(studentId instanceof Long)) {
			throw new MismatchedIdTypeException("ID of student must be of type Long.", HttpStatus.BAD_REQUEST);

		} else if (!(courseId instanceof Long)) {
			throw new MismatchedIdTypeException("ID of course must be of type Long.", HttpStatus.BAD_REQUEST);
		}

		// Retrieve data based on IDs
		Student addedStudent = studentRepository.findById(studentId).orElse(null);
		Course addedCourse = courseRepository.findById(courseId).orElse(null);

		// Check existing ids
		if(addedStudent == null && addedCourse == null) {
			throw new NoSuchObjectExistsException("Student id: " + studentId + ", Course id: " + courseId + " not found.", HttpStatus.NOT_FOUND);

		} else if (addedStudent == null) {
			throw new NoSuchObjectExistsException("Student with id:" + studentId + " not found", HttpStatus.NOT_FOUND);

		} else if (addedCourse == null) {
			throw new NoSuchObjectExistsException("Course with id:" + courseId + " not found", HttpStatus.NOT_FOUND);
		}

		/*
		Check if faculty section is the same for student and course
		 */
		if (!addedStudent.getFacultySection().equals(addedCourse.getFacultySection())) {
			throw new MismatchedFacultySectionException("Can not assign application for different faculty section.", HttpStatus.BAD_REQUEST);
		}

		// All the above will be featured by unique constraint :)
		Application addApplication = new Application(addedStudent, addedCourse, priority);
		addApplication.setStatus(Status.PENDING);
		return applicationRepository.save(addApplication);
	}

	@Override
	public Application addApplicationAsStudent(Long studentId, String courseName, Integer priority) {
		Student student = studentRepository.findById(studentId).orElse(null);
		Course course = courseRepository.findByName(courseName);
		// Increase counter after an application add
		Integer count = course.getApplicationsCount();
		course.setApplicationsCount(count + 1);

		assert student != null;
		if (!student.getFacultySection().equals(course.getFacultySection())) {
			throw new MismatchedFacultySectionException("Can not assign application for different faculty section.", HttpStatus.BAD_REQUEST);
		}

		if (!student.getStudyYear().equals(course.getStudyYear())) {
			throw new InvalidStudyYearException("Can not assign application for different study year.");
		}

		Application addApplication = new Application(student, course, priority);
		addApplication.setStatus(Status.PENDING);
		return applicationRepository.save(addApplication);
	}

	@Override
	public Application updateApplication(Long id, Integer newPriority, Status newStatus) {

		// Check if application exists by id
		Application application = applicationRepository.findById(id).orElse(null);
		if (application == null) {
			throw new NoSuchObjectExistsException("Application with id: " + id + " not found.", HttpStatus.NOT_FOUND);
		}


		application.setPriority(newPriority);
		application.setStatus(newStatus);
		return applicationRepository.save(application);
	}

	@Override
	public Application updateApplicationAsStudent(Long id, Integer priority) {
		Application application = applicationRepository.findById(id).orElse(null);

		if (application == null) {
			throw new NoSuchObjectExistsException("Application with id: " + id + " not found.");
		}

		application.setPriority(priority);
		return applicationRepository.save(application);
	}

	@Override
	public void deleteApplication(Long id) {

		// Check if application exists by id
		Application application = applicationRepository.findById(id).orElse(null);
		if (application == null) {
			throw new NoSuchObjectExistsException("Application with id: " + id + " not found.", HttpStatus.NOT_FOUND);
		}

		// Decrease counter after an application deletion
		Course course = application.getCourse();
		Integer counter = course.getApplicationsCount();
		course.setApplicationsCount(counter - 1);
		applicationRepository.delete(application);
	}
}
