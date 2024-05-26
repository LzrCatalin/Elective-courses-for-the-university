package org.example.springproject.repository;

import org.example.springproject.entity.Course;
import org.example.springproject.entity.CourseSchedule;
import org.example.springproject.enums.Status;
import org.example.springproject.enums.WeekDay;
import org.example.springproject.enums.WeekParity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CourseScheduleRepository extends JpaRepository<CourseSchedule,Long> {

	@Query("SELECT course FROM CourseSchedule " +
	"WHERE weekDay = :weekDay AND weekParity IN :weekParities AND startTime = :startTime AND endTime = :endTime")
	List<Course> findCoursesAtSelectedTimeSlot(@Param("weekDay")WeekDay weekDay, @Param("weekParities") List<WeekParity> parities,
											   @Param("startTime") String startTime, @Param("endTime") String endTime);

	@Query("SELECT COUNT(*) FROM CourseSchedule cs WHERE cs.course.id =:courseId")
	int courseAppearances(@Param("courseId") Long courseId);
}
