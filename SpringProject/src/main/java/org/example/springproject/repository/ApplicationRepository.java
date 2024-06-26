package org.example.springproject.repository;

import org.example.springproject.entity.Application;
import org.example.springproject.entity.Course;
import org.example.springproject.entity.Student;
import org.example.springproject.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ApplicationRepository extends JpaRepository<Application, Long> {
    Application findByCourseId(Long id);
	Application findByStudentId(Long id);
	Application findByPriorityAndStudentId(Integer priority, Long studentId);
	List<Application> findApplicationsByStudentId(Long id);
	Application findByStudentIdAndCourseId(Long studentId, Long courseId);
	@Query("SELECT a.student " +
			"FROM Application a " +
			"WHERE a.course.id = :courseId AND a.status IN :statuses")
	List<Student> findStudentsThatAppliedCourse(@Param("courseId") Long courseId, @Param("statuses") List<Status> statuses);

	@Query("SELECT COUNT( DISTINCT a.student)" +
			"FROM Application a " +
			"WHERE a.course.id = :courseId")
	int getCourseApplicationsCount(@Param("courseId") Long courseId);

	@Query("SELECT COUNT(DISTINCT a.course)" +
			"FROM Application a " +
			"WHERE a.student.id = :studentId")
	int getStudentApplicationsCount(@Param("studentId") Long studentId);

	@Query("select a.priority" +
			" from Application a " +
			" where a.student.id = :studentId")
	List<Integer> findStudentPriorities(@Param("studentId") Long studentId);

	@Query("select a.course.id from Application a where a.student.id = :studentId")
	List<Long> findStudentAppliedCoursesId(@Param("studentId") Long studentId);

	@Query("SELECT s FROM Student s " +
			"LEFT JOIN Application app ON s.id = app.student.id AND app.status = 'ACCEPTED' " +
			"GROUP BY s.id " +
			"HAVING (s.studyYear = 2 AND COUNT(app.id) < 2) OR (s.studyYear = 3 AND COUNT(app.id) < 3)")
	List<Student> findStudentsWithoutNecessaryCourses();

	@Query("SELECT c FROM Course c " +
			"WHERE c.maxCapacity > " +
			"(SELECT COUNT(a.id) FROM Application a " +
			"WHERE a.course = c AND a.status IN (org.example.springproject.enums.Status.ACCEPTED, org.example.springproject.enums.Status.REASSIGNED)) " +
			"ORDER BY (SELECT (c.maxCapacity - COUNT(a.id)) FROM Application a " +
			"WHERE a.course = c AND a.status IN (org.example.springproject.enums.Status.ACCEPTED, org.example.springproject.enums.Status.REASSIGNED)) DESC")

	List<Course> findAvailableCourses();

	@Query("SELECT a.course.name FROM Application a WHERE a.student.id = :studentId AND a.status IN (org.example.springproject.enums.Status.ACCEPTED, org.example.springproject.enums.Status.REASSIGNED)")
	List<String> findStudentAcceptedCourses(@Param("studentId") Long studentId);

	@Query("SELECT DISTINCT a.student FROM Application a " +
			"WHERE a.course.id = :courseId AND a.status IN (org.example.springproject.enums.Status.ACCEPTED, org.example.springproject.enums.Status.REASSIGNED)")
	List<Student> getCourseAcceptedStudents(@Param("courseId") Long courseId);

	@Query("SELECT DISTINCT c.category FROM Course c " +
			"JOIN Application a ON c = a.course " +
			"WHERE a.student.id = :studentId AND a.status IN (org.example.springproject.enums.Status.ACCEPTED, org.example.springproject.enums.Status.REASSIGNED)")
	List<String> findAcceptedCourseCategoriesByStudentId(@Param("studentId") Long studentId);

	/*
	TODO: RESOLVE QUERY FOR RETRIEVING STUDENT CLASSMATES WITHOUT HIM
	 */
	@Query("SELECT DISTINCT a2.student FROM Application a " +
			"JOIN a.course c " +
			"JOIN Application a2 ON c.id = a2.course.id " +
			"WHERE a.student.id = :studentId " +
			"AND a2.course.id = :courseId " +
			"AND a2.student.id != :studentId " +
			"AND a2.status IN (org.example.springproject.enums.Status.ACCEPTED, org.example.springproject.enums.Status.REASSIGNED)")
	List<Student> getStudentClassmatesOnCourse(@Param("courseId") Long courseId, @Param("studentId") Long studentId);

	@Query("SELECT a FROM Application a WHERE a.student.id = :studentId ORDER BY a.priority ASC")
	List<Application> findByStudentIdOrderByPriorityAsc(@Param("studentId") Long studentId);
}
