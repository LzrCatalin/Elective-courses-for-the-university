package org.example.springproject.services.implementation;

import org.example.springproject.entity.Application;
import org.example.springproject.entity.Course;
import org.example.springproject.entity.Student;
import org.example.springproject.enums.Status;
import org.example.springproject.repository.ApplicationRepository;
import org.example.springproject.repository.CourseRepository;
import org.example.springproject.repository.StudentRepository;
import org.example.springproject.services.ApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Logger;

@Service
public class ApplicationServiceImpl implements ApplicationService {
	private static Logger logger = LoggerFactory.getLogger(YourClassName.class);
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
		try {
			// Retrieve data based on IDs
			Student addedStudent = studentRepository.findStudentById(studentId);
			Course addedCourse = courseRepository.findCourseById(courseId);

			// Check existing ids
//			if(addedStudent == null && addedCourse == null) {
//				return ResponseEntity.badRequest().body("Student with id:" + studentId + " and course with id:" + courseId + " not found.");
//
//			} else if (addedStudent == null) {
//				return ResponseEntity.badRequest().body("Student with id:" + studentId + " not found");
//
//			} else if (addedCourse == null) {
//				return ResponseEntity.badRequest().body("Course with id:" + courseId + " not found");
//			}

			/*
			Check if faculty section is the same for student and course
			 */
			if (addedStudent.getFacultySection().equals(addedCourse.getFacultySection())) {
				Application newApplication = new Application(addedStudent, addedCourse, priority, status);

				return applicationRepository.save(newApplication);

//			} else {
//				return ResponseEntity.ok().body("Failed adding application.\n Different faculty section not allowed for student and course.");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	@Override
	public Application updateApplication(Long id, Integer newPriority, Status newStatus) {
		try {
			// Check if application exists by id
			Application application = applicationRepository.findApplicationById(id);
//			if (application == null) {
//				return ResponseEntity.badRequest().body("Application with id:" + id + " not found");
//			}

			application.setPriority(newPriority);
			application.setStatus(newStatus);
			return applicationRepository.save(application);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	@Override
	public void deleteApplication(Long id) {
		try {
			// Check if application exists by id
			Application application = applicationRepository.findApplicationById(id);
//			if (application == null) {
//				return ResponseEntity.badRequest().body("Application with id:" + id + " not found");
//			}

			applicationRepository.delete(application);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
