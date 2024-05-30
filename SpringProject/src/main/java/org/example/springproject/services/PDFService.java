package org.example.springproject.services;

import org.example.springproject.enums.Status;

import java.io.OutputStream;

public interface PDFService {
	void generateStudentCertificate(Long studentId, OutputStream outputStream);
	void generateAllocationsPDF(Long courseId, Status status, OutputStream outputStream);
	void generateSchedulePDF(Long studentId, OutputStream out);
}
