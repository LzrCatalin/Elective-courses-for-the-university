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
import org.springframework.http.ResponseEntity;
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
	public ResponseEntity<String> addApplication(Long studentId, Long courseId, Integer priority, Status status) {
		try {
			// Check if id of student exists
			System.out.println(studentId);
			Student addedStudent = studentRepository.findStudentById(studentId);

			// Check if id of course exists
			System.out.println(courseId);
			Course addedCourse = courseRepository.findCourseById(courseId);

			// Check existing ids
			if(addedStudent == null && addedCourse == null) {
				return ResponseEntity.badRequest().body("Student with id:" + studentId + " and course with id:" + courseId + " not found.");

			} else if (addedStudent == null) {
				return ResponseEntity.badRequest().body("Student with id:" + studentId + " not found");

			} else if (addedCourse == null) {
				return ResponseEntity.badRequest().body("Course with id:" + courseId + " not found");
			}

			Application newApplication = new Application(addedStudent, addedCourse, priority, status);
			newApplication.setStudent(addedStudent);
			newApplication.setCourse(addedCourse);
			newApplication.setPriority(priority);
			newApplication.setStatus(status);
			applicationRepository.save(newApplication);

			return ResponseEntity.ok().body("Successfully added new application");
		} catch (Exception e) {
			e.printStackTrace();
		}

		return ResponseEntity.badRequest().body("Failed to add new application");
	}

	@Override
	public ResponseEntity<String> updateApplication(Long id, Integer newPriority, Status newStatus) {
		try {
			// Check if application exists by id
			Application application = applicationRepository.findApplicationById(id);
			if (application == null) {
				return ResponseEntity.badRequest().body("Application with id:" + id + " not found");
			}

			application.setPriority(newPriority);
			application.setStatus(newStatus);
			applicationRepository.save(application);

			return ResponseEntity.ok().body("Successfully updated application with id:" + id);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return ResponseEntity.badRequest().body("Failed to update an application");
	}

	@Override
	public ResponseEntity<String> deleteApplication(Long id) {
		try {
			// Check if application exists by id
			Application application = applicationRepository.findApplicationById(id);
			if (application == null) {
				return ResponseEntity.badRequest().body("Application with id:" + id + " not found");
			}

			applicationRepository.delete(application);
			return ResponseEntity.ok().body("Successfully deleted application with id:" + id);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return ResponseEntity.badRequest().body("Failed to delete application");
	}

}
