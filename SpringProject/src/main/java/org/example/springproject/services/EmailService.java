package org.example.springproject.services;

import org.example.springproject.entity.EmailDetails;

public interface EmailService {
	String sendSimpleMail(EmailDetails details);

	void sendNewApplicationMail(Long studentId, Long courseId, Integer priority, String status);

	String sendMailWithAttachment(EmailDetails details);

}
