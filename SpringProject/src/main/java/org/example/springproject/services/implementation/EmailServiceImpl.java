package org.example.springproject.services.implementation;

import jakarta.persistence.EntityNotFoundException;
import org.example.springproject.entity.Application;
import org.example.springproject.entity.Course;
import org.example.springproject.entity.EmailDetails;
import org.example.springproject.entity.Student;
import org.example.springproject.enums.Status;
import org.example.springproject.exceptions.NoSuchObjectExistsException;
import org.example.springproject.repository.ApplicationRepository;
import org.example.springproject.repository.CourseRepository;
import org.example.springproject.repository.StudentRepository;
import org.example.springproject.services.EmailService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@Service
public class EmailServiceImpl implements EmailService {
	private static final Logger logger = LoggerFactory.getLogger(EmailServiceImpl.class);
	@Autowired
	private StudentRepository studentRepository;
	@Autowired
	private CourseRepository courseRepository;
	@Autowired
	private ApplicationRepository applicationRepository;
	@Autowired
	private JavaMailSender javaMailSender;

	@Value("${spring.mail.username}")
	private String sender;

	@Override
	public String sendSimpleMail(EmailDetails details) {
		// Creating a simple mail message
		SimpleMailMessage mailMessage = new SimpleMailMessage();

		// Setting up necessary details
		mailMessage.setFrom(sender);
		mailMessage.setTo(details.getRecipient());
		mailMessage.setText(details.getMsgBody());
		mailMessage.setSubject(details.getSubject());

		// Sending the mail
		javaMailSender.send(mailMessage);
		return "Mail Sent Successfully...";
	}

	@Override
	public void sendNewApplicationMail(Long studentId, String courseName, Integer priority) {
		// Creating a simple mail message
		SimpleMailMessage mailMessage = new SimpleMailMessage();
		Student student = studentRepository.findById(studentId).orElse(null);
		// Setting up necessary details
		mailMessage.setFrom(sender);
		mailMessage.setTo(student.getEmail());

		// Customizing email text
		String emailText = "Hi!\n\n";
		emailText += "Your application for the course \"" + courseName + "\" has been received.\n";
		emailText += "Priority: " + priority + "\n";
		emailText += "Status: " + Status.PENDING + "\n";
		emailText += "\nHave a good day!\n\nBest regards,\nYour University";

		mailMessage.setText(emailText);

		// Customizing email subject
		String emailSubject = "Application Received: " + courseName;
		mailMessage.setSubject(emailSubject);

		// Sending the mail
		javaMailSender.send(mailMessage);
	}

	@Override
	public void sendDeleteApplicationMail(Long id) {
		Application deleteApplication = applicationRepository.findById(id).orElseThrow(EntityNotFoundException::new);

		// Creating a simple mail message
		SimpleMailMessage mailMessage = new SimpleMailMessage();

		// Setting up necessary details
		mailMessage.setFrom(sender);
		assert deleteApplication != null;
		mailMessage.setTo(deleteApplication.getStudent().getEmail());

		// Customizing email text
		String courseName = deleteApplication.getCourse().getName();
		String emailText = "Hi!\n\n";
		emailText += "Your application for the course \"" + courseName + "\" has been deleted.\n";
		emailText += "\nHave a good day!\n\nBest regards,\nYour University";

		mailMessage.setText(emailText);

		// Customizing email subject
		String emailSubject = "Deletion Received: " + courseName;
		mailMessage.setSubject(emailSubject);

		// Sending the mail
		javaMailSender.send(mailMessage);
	}

	@Override
	public void sendUpdateApplicationMail(Long id, Integer priority) {
		Application application = applicationRepository.findById(id).orElseThrow(EntityNotFoundException::new);

		// Creating a simple mail message
		SimpleMailMessage mailMessage = new SimpleMailMessage();

		// Setting up necessary details
		mailMessage.setFrom(sender);
		mailMessage.setTo(application.getStudent().getEmail());

		// Customizing email text
		String courseName = application.getCourse().getName();
		String emailText = "Hi!\n\n";
		emailText += "Your application for the course \"" + courseName + "\" has been changed.\n";
		emailText += "New priority: " + priority;
		emailText += "\nHave a good day!\n\nBest regards,\nYour University";

		mailMessage.setText(emailText);

		// Customizing email subject
		String emailSubject = "Update Received: " + courseName;
		mailMessage.setSubject(emailSubject);

		// Sending the mail
		javaMailSender.send(mailMessage);
	}

	@Override
	public void sendNewCourseMail(String courseName) {
		logger.info("Inside course mail sender...");
		Course course = courseRepository.findByName(courseName);
		// Get all students within course study year and faculty section
//		List<Student> recipientStudents = studentRepository.findByStudyYearAndFacultySection(course.getStudyYear(), course.getFacultySection());

		// Creating a simple mail message
		SimpleMailMessage mailMessage = new SimpleMailMessage();

		// Take each student
//		for (Student student : recipientStudents) {
//			logger.info("Student: " + student.getName() + ", Email: " + student.getEmail());
		mailMessage.setFrom(sender);
		mailMessage.setTo("florin.lazar02@e-uvt.ro");

		String emailText = "Hi, Florin!\n\n";
		emailText += "We added new course that could interests you.\n\n";
		emailText += "Name: " + course.getName();
		emailText += "\nCategory: " + course.getCategory();
		emailText += "\nTeacher: " + course.getTeacher();
		emailText += "\nCapacity: " + course.getMaxCapacity();
		emailText += "\nHave a good day!\n\nBest regards,\nYour University";
		mailMessage.setText(emailText);

		String emailSubject = "New Course Alert: " + courseName;
		mailMessage.setSubject(emailSubject);
		javaMailSender.send(mailMessage);
//		}
	}

	@Override
	public String sendMailWithAttachment(EmailDetails details) {
		return null;
	}

	@Override
	public void sendAllocationProcessMail() {
		logger.info("Inside allocation process mail ...");
//		List<Student> students = studentRepository.findAll();
		SimpleMailMessage mailMessage = new SimpleMailMessage();


//		for (Student student : students) {
//		logger.info("Student: " + student.getName() + ", Email: " + student.getEmail());
		mailMessage.setFrom(sender);
		mailMessage.setTo("florin.lazar02@e-uvt.ro");

		String emailText = "Hi, Florin!\n\n";
		emailText += "We are pleased to inform you that the allocation process has been completed.";
		emailText += "You can now view your allocated courses on our website.\n\n";
		emailText += "Wishing you a successful academic term ahead!\n\nBest regards,\nYour University";

		mailMessage.setText(emailText);

		String emailSubject = "Allocations Result";
		mailMessage.setSubject(emailSubject);
		javaMailSender.send(mailMessage);
//		}
	}
}
