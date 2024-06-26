package org.example.springproject.services;


import org.example.springproject.entity.Course;
import org.example.springproject.entity.CourseSchedule;
import org.example.springproject.enums.WeekDay;
import org.example.springproject.enums.WeekParity;



import java.util.List;

public interface CourseScheduleService {
    public List<CourseSchedule> getAllCourseSchedule();

    CourseSchedule addCourseSchedule(String courseName, String startTime, String endTime, WeekDay weekDay, WeekParity weekParity);

    void deleteCourseSchedule(Long id);

    CourseSchedule updateCourseSchedule(Long id, String startTime, String endTime, WeekDay weekDay, WeekParity weekParity);
    List<CourseSchedule> displayStudentSchedules(Long studentId);
}
