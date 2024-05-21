package org.example.springproject.services;

import org.example.springproject.entity.CourseSchedule;
import org.example.springproject.enums.WeekDay;
import org.example.springproject.enums.WeekParity;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface CourseScheduleService {
    public List<CourseSchedule> getAllCourseSchedule();

    ResponseEntity<String> addCourseSchedule(Long course, String startTime, String endTime, WeekDay weekDay, WeekParity weekParity);
}
