package org.example.springproject.controller;

import org.example.springproject.entity.EmailDetails;
import org.example.springproject.services.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;

@RestController
public class EmailApi {

	@Autowired
	private EmailService emailService;

	@PostMapping("/sendMail")
	public String sendMail(@RequestParam String recipient,
						   @RequestParam String msgBody,
						   @RequestParam String subject) {

		EmailDetails details = new EmailDetails(recipient, msgBody, subject);
		return emailService.sendSimpleMail(details);
	}

	@PostMapping("/sendMailWithAttachment")
	public String sendMailWithAttachment(@RequestBody EmailDetails details) {

		return emailService.sendMailWithAttachment(details);
	}
}
