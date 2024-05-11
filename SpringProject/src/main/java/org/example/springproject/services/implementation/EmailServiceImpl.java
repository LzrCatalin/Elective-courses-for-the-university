package org.example.springproject.services.implementation;

import org.example.springproject.entity.Application;
import org.example.springproject.entity.Course;
import org.example.springproject.entity.EmailDetails;
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

@Service
public class EmailServiceImpl implements EmailService {
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

		// Setting up necessary details
		mailMessage.setFrom(sender);
//		String recipient = studentRepository.findStudentById(studentId).getName();
//		mailMessage.setTo(recipient);
		mailMessage.setTo("florin.lazar02@e-uvt.ro");

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
		Application application = applicationRepository.findById(id).orElse(null);

		// Creating a simple mail message
		SimpleMailMessage mailMessage = new SimpleMailMessage();

		// Setting up necessary details
		mailMessage.setFrom(sender);
//		String recipient = foundedApplication.getStudent().getName();
//		mailMessage.setTo(recipient);
		mailMessage.setTo("florin.lazar02@e-uvt.ro");

		// Customizing email text
		assert application != null;
		String courseName = application.getCourse().getName();
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
	public String sendMailWithAttachment(EmailDetails details) {
		return null;
	}
}
