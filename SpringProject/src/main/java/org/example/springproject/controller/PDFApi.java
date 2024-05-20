package org.example.springproject.controller;

import com.lowagie.text.DocumentException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletResponse;
import org.example.springproject.services.implementation.PDFServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.OutputStream;

@RestController
@CrossOrigin
@RequestMapping("/pdf")
public class PDFApi {
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
}
