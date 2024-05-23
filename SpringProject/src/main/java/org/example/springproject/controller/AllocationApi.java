package org.example.springproject.controller;

import org.example.springproject.entity.Application;
import org.example.springproject.services.AllocationService;
import org.example.springproject.services.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/allocation")
public class AllocationApi {
	@Autowired
	public EmailService emailService;
	@Autowired
	public AllocationService allocationService;
	@GetMapping
	public List<Application> getFinalResults() {
		emailService.sendAllocationProcessMail();
		return allocationService.allocationsResult();
	}
}
