package org.example.springproject.services.implementation;

import com.itextpdf.text.BaseColor;
import com.lowagie.text.*;
import com.lowagie.text.Font;
import com.lowagie.text.Image;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import jakarta.persistence.EntityNotFoundException;
import org.example.springproject.controller.CourseApi;
import org.example.springproject.entity.Course;
import org.example.springproject.entity.CourseSchedule;
import org.example.springproject.entity.Student;
import org.example.springproject.enums.Status;
import org.example.springproject.repository.ApplicationRepository;
import org.example.springproject.repository.CourseRepository;
import org.example.springproject.repository.CourseScheduleRepository;
import org.example.springproject.repository.StudentRepository;
import org.example.springproject.services.PDFService;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.io.OutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.Phaser;


@Service
public class PDFServiceImpl implements PDFService {
	private static final Logger logger = LoggerFactory.getLogger(CourseApi.class);

	@Autowired
	private StudentRepository studentRepository;
	@Autowired
	private CourseRepository courseRepository;
	@Autowired
	private ApplicationRepository applicationRepository;
	@Autowired
	private CourseScheduleRepository courseScheduleRepository;


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

	public void generateAllocationsPDF(Long courseId, Status status, OutputStream outputStream) throws DocumentException {
		Course course = courseRepository.findById(courseId).orElseThrow(EntityNotFoundException::new);
		logger.info("Course name: " + course.getName());

		List<Student> students = applicationRepository.findStudentsThatAppliedCourse(courseId, Arrays.asList(status, Status.REASSIGNED));
		logger.info("Get accepted students: ");
		for (Student student : students) {
			logger.info("Student name: " + student.getName());
		}

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

		if (status.equals(Status.PENDING)) {
			// Title for PENDING
			Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 24);
			Paragraph title = new Paragraph("Pending Results", titleFont);
			title.setAlignment(Element.ALIGN_CENTER);
			document.add(title);

		} else if (status.equals(Status.ACCEPTED)) {
			// Title for ACCEPTED
			Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 24);
			Paragraph title = new Paragraph("Final Results", titleFont);
			title.setAlignment(Element.ALIGN_CENTER);
			document.add(title);
		}


		// Course Name
		Font courseFont = FontFactory.getFont(FontFactory.HELVETICA, 18);
		Paragraph courseName = new Paragraph("Course: " + course.getName(), courseFont);
		courseName.setAlignment(Element.ALIGN_CENTER);
		document.add(courseName);

		// Add some space
		document.add(new Paragraph("\n\n"));

		// Table
		PdfPTable table = new PdfPTable(4);
		table.setWidthPercentage(100);
		table.setSpacingBefore(10f);
		table.setSpacingAfter(10f);

		// Set Column widths
		float[] columnWidths = {1f, 3f, 3f, 2f};
		table.setWidths(columnWidths);

		// Table Header
		PdfPCell cell1 = new PdfPCell(new Paragraph("ID", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12)));
		PdfPCell cell2 = new PdfPCell(new Paragraph("Name", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12)));
		PdfPCell cell3 = new PdfPCell(new Paragraph("Email", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12)));
		PdfPCell cell4 = new PdfPCell(new Paragraph("Grade", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12)));
		table.addCell(cell1);
		table.addCell(cell2);
		table.addCell(cell3);
		table.addCell(cell4);

		// Table Rows
		for (Student student : students) {
			table.addCell(new PdfPCell(new Phrase(student.getId().toString(), FontFactory.getFont(FontFactory.HELVETICA, 12))));
			table.addCell(new PdfPCell(new Phrase(student.getName(), FontFactory.getFont(FontFactory.HELVETICA, 12))));
			table.addCell(new PdfPCell(new Phrase(student.getEmail(), FontFactory.getFont(FontFactory.HELVETICA, 12))));
			table.addCell(new PdfPCell(new Phrase(student.getGrade().toString(), FontFactory.getFont(FontFactory.HELVETICA, 12))));
		}

		document.add(table);
		document.close();
	}


	public void generateSchedulePDF(Long studentId, OutputStream out) throws DocumentException {
		Student student = studentRepository.findById(studentId).orElseThrow(EntityNotFoundException::new);
		List<CourseSchedule> studentSchedules = courseScheduleRepository.findCourseSchedulesForStudent(studentId);

		Document document = new Document(PageSize.A4.rotate());

		PdfWriter.getInstance(document, out);
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
		Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 24, Color.BLUE);
		Paragraph title = new Paragraph("Weekly Schedule", titleFont);
		title.setAlignment(Element.ALIGN_CENTER);
		document.add(title);

		// Student Info
		Font infoFont = FontFactory.getFont(FontFactory.HELVETICA, 14, Color.DARK_GRAY);
		Paragraph studentInfo = new Paragraph("Student ID: " + studentId + "\nName: " + student.getName(), infoFont);
		studentInfo.setAlignment(Element.ALIGN_LEFT);
		document.add(studentInfo);

		// Define fonts
		Font font = FontFactory.getFont(FontFactory.COURIER, 12, Color.BLACK);
		Font headerFont = FontFactory.getFont(FontFactory.COURIER_BOLD, 14, Color.WHITE);

		// Create a table with 6 columns (Time slots + 5 days of the week)
		PdfPTable table = new PdfPTable(5 + 1); // 6 columns for time slots + 1 for days
		table.setWidthPercentage(100);
		table.setSpacingBefore(10f);
		table.setSpacingAfter(10f);
		table.getDefaultCell().setPadding(5);

		// Set column widths
		float[] columnWidths = {2f, 1f, 1f, 1f, 1f, 1f};
		table.setWidths(columnWidths);

		// Create table header
		String[] headers = {"Time Slot", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday"};
		for (String header : headers) {
			PdfPCell headerCell = new PdfPCell(new Phrase(header, headerFont));
			headerCell.setBackgroundColor(new Color(51, 153, 255));
			headerCell.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(headerCell);
		}

		// Define time slots
		String[] timeSlots = {"8:00-9:30", "9:40-11:10", "11:20-12:50", "13:00-14:30", "14:40-16:10", "16:20-17:50", "18:00-19:30", "19:40-21:10"};

		// Map to store the courses based on the day and time slot
		Map<String, Map<String, List<CourseSchedule>>> scheduleMap = new HashMap<>();
		for (String day : new String[]{"MONDAY", "TUESDAY", "WEDNESDAY", "THURSDAY", "FRIDAY", "SATURDAY"}) {
			scheduleMap.put(day, new HashMap<>());
			for (String timeSlot : timeSlots) {
				scheduleMap.get(day).put(timeSlot, new ArrayList<>());
			}
		}

		// Populate the schedule map
		for (CourseSchedule courseSchedule : studentSchedules) {
			String day = courseSchedule.getWeekDay().toString().toUpperCase();
			String startTime = courseSchedule.getStartTime().toString();
			String endTime = courseSchedule.getEndTime().toString();
			String timeSlot = getTimeSlot(startTime, endTime);

			if (scheduleMap.containsKey(day) && timeSlot != null) {
				scheduleMap.get(day).get(timeSlot).add(courseSchedule);
			}
		}

		// Add rows to the table
		for (String timeSlot : timeSlots) {
			// Add time slot cell
			PdfPCell timeSlotCell = new PdfPCell(new Phrase(timeSlot, font));
			timeSlotCell.setHorizontalAlignment(Element.ALIGN_CENTER);
			timeSlotCell.setBackgroundColor(new Color(204, 229, 255));
			table.addCell(timeSlotCell);

			// Add course cells for each day
			for (String day : new String[]{"MONDAY", "TUESDAY", "WEDNESDAY", "THURSDAY", "FRIDAY"}) {
				List<CourseSchedule> courses = scheduleMap.get(day).get(timeSlot);
				PdfPCell cell;
				if (!courses.isEmpty()) {
					Paragraph coursesText = new Paragraph();
					for (CourseSchedule course : courses) {
						coursesText.add(new Phrase(course.getCourse().getName(), font));
						coursesText.add(new Phrase(course.getWeekParity().toString(), font));
						coursesText.add(Chunk.NEWLINE);
					}
					cell = new PdfPCell(coursesText);
				} else {
					cell = new PdfPCell(new Phrase("", font));
				}
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setBorder(Rectangle.BOTTOM | Rectangle.LEFT | Rectangle.RIGHT);
				table.addCell(cell);
			}
		}

		// Add table to document
		document.add(table);

		// Footer
		Font footerFont = FontFactory.getFont(FontFactory.HELVETICA_OBLIQUE, 10, Color.GRAY);
		Paragraph footer = new Paragraph("Your University", footerFont);
		footer.setAlignment(Element.ALIGN_RIGHT);
		footer.setSpacingBefore(10f);
		document.add(footer);

		document.close();
	}

	private String getTimeSlot(String startTime, String endTime) {
		if (startTime.equals("08:00") && endTime.equals("09:30")) {
			return "8:00-9:30";
		} else if (startTime.equals("09:40") && endTime.equals("11:10")) {
			return "9:40-11:10";
		} else if (startTime.equals("11:20") && endTime.equals("12:50")) {
			return "11:20-12:50";
		} else if (startTime.equals("13:00") && endTime.equals("14:30")) {
			return "13:00-14:30";
		} else if (startTime.equals("14:40") && endTime.equals("16:10")) {
			return "14:40-16:10";
		} else if (startTime.equals("16:20") && endTime.equals("17:50")) {
			return "16:20-17:50";
		} else if (startTime.equals("18:00") && endTime.equals("19:30")) {
			return "18:00-19:30";
		} else if (startTime.equals("19:40") && endTime.equals("21:10")) {
			return "19:40-21:10";
		} else {
			return null;
		}
	}

}
