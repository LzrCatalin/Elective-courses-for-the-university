package org.example.springproject.services.implementation;

import org.example.springproject.entity.Application;
import org.example.springproject.entity.Course;
import org.example.springproject.entity.Student;
import org.example.springproject.enums.Status;
import org.example.springproject.exceptions.DuplicatePriorityException;
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
	public Application addApplication(Long studentId, Long courseId, Integer priority, Status status) {
		// Verify inserted IDs
		if (!(studentId instanceof Long) && !(courseId instanceof Long)) {
			throw new MismatchedIdTypeException("IDs must be of type Long.", HttpStatus.BAD_REQUEST);

		} else if (!(studentId instanceof Long)) {
			throw new MismatchedIdTypeException("ID of student must be of type Long.", HttpStatus.BAD_REQUEST);

		} else if (!(courseId instanceof Long)) {
			throw new MismatchedIdTypeException("ID of course must be of type Long.", HttpStatus.BAD_REQUEST);
		}

		// Retrieve data based on IDs
		Student addedStudent = studentRepository.findStudentById(studentId);
		Course addedCourse = courseRepository.findCourseById(courseId);

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

		// Check if student wants to add new application with an existing priority
		List<Application> existingApplications = applicationRepository.findApplicationsByStudentId(studentId);
		for (Application exitingApplication : existingApplications) {
			if (exitingApplication.getPriority().equals(priority)) {
				throw new DuplicatePriorityException("Duplicate priority detected. A student cannot apply for more than one course with the same priority.",
						HttpStatus.BAD_REQUEST
				);
			}
		}

		Application addApplication = new Application(addedStudent, addedCourse, priority, status);
		return applicationRepository.save(addApplication);
	}

	@Override
	public Application updateApplication(Long id, Integer newPriority, Status newStatus) {

		// Check if application exists by id
		Application application = applicationRepository.findApplicationById(id);
		if (application == null) {
			throw new NoSuchObjectExistsException("Application with id: " + id + " not found.", HttpStatus.NOT_FOUND);
		}


		application.setPriority(newPriority);
		application.setStatus(newStatus);
		return applicationRepository.save(application);
	}

	@Override
	public void deleteApplication(Long id) {

		// Check if application exists by id
		Application application = applicationRepository.findApplicationById(id);
		if (application == null) {
			throw new NoSuchObjectExistsException("Application with id: " + id + " not found.", HttpStatus.NOT_FOUND);
		}

		applicationRepository.delete(application);
	}
}
