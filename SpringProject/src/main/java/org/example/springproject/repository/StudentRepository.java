package org.example.springproject.repository;

import org.example.springproject.entity.Student;
import org.example.springproject.enums.FacultySection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface StudentRepository extends JpaRepository<Student, Long> {
	Student findStudentByGrade(Float grade);
	Student findByEmail(String email);
	Student findStudentByName(String name);
	@Query("SELECT s FROM Student s WHERE s.studyYear = :studyYear AND s.facultySection = :facultySection")
	List<Student> findByStudyYearAndFacultySection(@Param("studyYear") int studyYear, @Param("facultySection") FacultySection facultySection);

	@Query("SELECT COUNT(s) FROM Student s WHERE s.studyYear = 1")
	int countFirstYearStudents();

	@Query("SELECT COUNT(s) FROM Student s WHERE s.studyYear = 2")
	int countSecondYearStudents();

	@Query("SELECT COUNT(s) FROM Student s WHERE s.studyYear = 3")
	int countThirdYearStudents();
}
