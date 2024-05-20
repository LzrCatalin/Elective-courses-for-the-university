package org.example.springproject.services.implementation;

import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import jakarta.persistence.EntityNotFoundException;
import org.example.springproject.entity.Student;
import org.example.springproject.repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.io.FileOutputStream;
import java.io.OutputStream;
import java.net.URL;

@Service
public class PDFServiceImpl {

	private final StudentRepository studentRepository;

	public PDFServiceImpl(StudentRepository studentRepository) {
		this.studentRepository = studentRepository;
	}

	public void generateStudentCertificate(Long studentId, OutputStream outputStream) throws DocumentException {
		// Student Details
		Student student = studentRepository.findById(studentId).orElseThrow(EntityNotFoundException::new);

		Document document = new Document();
		PdfWriter.getInstance(document, outputStream);

		document.open();

		// Faculty Photo
		try {
			Image studentPhoto = Image.getInstance(new URL("https://www.rador.ro/wp-content/uploads/2021/01/uvt.png"));
			studentPhoto.setAlignment(Element.ALIGN_CENTER);
			studentPhoto.scaleToFit(150, 150);
			document.add(studentPhoto);
		} catch (Exception e) {
			e.printStackTrace();
		}

		// Title
		Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 24);
		Paragraph title = new Paragraph("Student Certificate", titleFont);
		title.setAlignment(Element.ALIGN_CENTER);
		document.add(title);

		// College Name
		Font collegeFont = FontFactory.getFont(FontFactory.HELVETICA, 18);
		Paragraph collegeName = new Paragraph("West University of Timisoara", collegeFont);
		collegeName.setAlignment(Element.ALIGN_CENTER);
		document.add(collegeName);

		// Add some space
		document.add(new Paragraph("\n\n"));

		Font studentDetailsFont = FontFactory.getFont(FontFactory.HELVETICA, 12);
		Paragraph studentDetails = new Paragraph();
		studentDetails.setAlignment(Element.ALIGN_LEFT);
		studentDetails.add(new Phrase("		Hereby, it is verified that the student ", studentDetailsFont));
		studentDetails.add(new Phrase(student.getName(), FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12)));
		studentDetails.add(new Phrase(" is enrolled at ", studentDetailsFont));
		studentDetails.add(new Phrase(student.getFacultySection().toString(), FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12)));
		studentDetails.add(new Phrase(" in the academic year 2023-2024, the year ", studentDetailsFont));
		studentDetails.add(new Phrase(student.getStudyYear().toString(), FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12)));
		studentDetails.add(new Phrase(" of studies, ID:  I", studentDetailsFont));
		studentDetails.add(new Phrase(student.getId().toString(), FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12)));
		studentDetails.add(new Phrase(" . Grade: ", studentDetailsFont));
		studentDetails.add(new Phrase(student.getGrade().toString(), FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12)));

		document.add(studentDetails);

		// Add some space
		document.add(new Paragraph("\n\n"));

		document.close();
	}

	private void addRow(PdfPTable table, String key, String value) {
		PdfPCell cell1 = new PdfPCell(new Phrase(key, FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12)));
		PdfPCell cell2 = new PdfPCell(new Phrase(value, FontFactory.getFont(FontFactory.HELVETICA, 12)));
		cell1.setBorder(Rectangle.NO_BORDER);
		cell2.setBorder(Rectangle.NO_BORDER);
		table.addCell(cell1);
		table.addCell(cell2);
	}
}
