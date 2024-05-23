package org.example.springproject.controller;

import com.lowagie.text.DocumentException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletResponse;
import org.example.springproject.enums.Status;
import org.example.springproject.services.implementation.PDFServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("/pdf")
public class PDFApi {
	private static final Logger logger = LoggerFactory.getLogger(CourseApi.class);

	@Autowired
	private PDFServiceImpl pdfService;

	@GetMapping("/export/{studentId}")
	public ResponseEntity<String> exportStudentPDF(@PathVariable("studentId") Long studentId, HttpServletResponse response) {
		try {
			response.setContentType("application/pdf");
			response.setHeader("Content-Disposition", "attachment; filename=exportedData.pdf");

			OutputStream outputStream = response.getOutputStream();
			pdfService.generateStudentCertificate(studentId, outputStream);
			outputStream.flush();
			return new ResponseEntity<>("Successfully generated pdf of student id: " + studentId, HttpStatus.CREATED);

		} catch (DocumentException | IOException e) {
			return new ResponseEntity<>("Error !!!", HttpStatus.BAD_REQUEST);

		} catch (EntityNotFoundException e) {
			return new ResponseEntity<>("Student ID: " + studentId + " not found.", HttpStatus.NOT_FOUND);
		}
	}

	@PostMapping("/export/courseAllocations")
	public ResponseEntity<String> exportCourseAllocation(@RequestBody Map<String, Object> requestBody,
														 HttpServletResponse response) {
		try {
			response.setContentType("application/pdf");
			response.setHeader("Content-Disposition", "attachment; filename=exportedData.pdf");

			Integer id = (Integer) requestBody.get("courseId");
			Long courseId = Long.valueOf(id);
			OutputStream outputStream = response.getOutputStream();
			logger.info("Received course id: " + courseId);
			String applicationsStatus = (String) requestBody.get("status");
			Status status = Status.valueOf(applicationsStatus);
			logger.info("Received status request: " + status);
			pdfService.generateAllocationsPDF(courseId, status, outputStream);
			outputStream.flush();
			return new ResponseEntity<>("Successfully generated pdf of course id: " + courseId, HttpStatus.CREATED);

		} catch (DocumentException | IOException e) {
			return new ResponseEntity<>("Error !!!", HttpStatus.BAD_REQUEST);

		} catch (EntityNotFoundException e) {
			return new ResponseEntity<>("Course not found.", HttpStatus.NOT_FOUND);
		}
	}
}
