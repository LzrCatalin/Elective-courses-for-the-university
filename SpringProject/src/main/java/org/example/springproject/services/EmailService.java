package org.example.springproject.services;

import org.example.springproject.entity.EmailDetails;

public interface EmailService {
	String sendSimpleMail(EmailDetails details);
	void sendNewApplicationMail(Long studentId, String courseName, Integer priority);
	void sendDeleteApplicationMail(Long id);
	String sendMailWithAttachment(EmailDetails details);

}
