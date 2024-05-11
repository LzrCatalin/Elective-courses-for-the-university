package org.example.springproject.services;

import org.example.springproject.entity.EmailDetails;

public interface EmailService {
	String sendSimpleMail(EmailDetails details);
	void sendNewApplicationMail(Long studentId, String courseName, Integer priority);
	void sendDeleteApplicationMail(Long id);
	void sendUpdateApplicationMail(Long id, Integer priority);
	void sendNewCourseMail(String courseName);
	String sendMailWithAttachment(EmailDetails details);

}
