package org.example.springproject.services;


import org.example.springproject.entity.CourseSchedule;
import org.example.springproject.enums.WeekDay;
import org.example.springproject.enums.WeekParity;


import java.util.List;

public interface CourseScheduleService {
    public List<CourseSchedule> getAllCourseSchedule();

    CourseSchedule addCourseSchedule(Long course, String startTime, String endTime, WeekDay weekDay, WeekParity weekParity);

    void deleteCourseSchedule(Long id);

    CourseSchedule updateCourseSchedule(Long id, String startTime, String endTime, WeekDay weekDay, WeekParity weekParity);
}
