package org.example.springproject.services.implementation;

import jakarta.persistence.EntityNotFoundException;
import org.example.springproject.entity.Course;
import org.example.springproject.entity.CourseSchedule;
import org.example.springproject.enums.WeekDay;
import org.example.springproject.enums.WeekParity;
import org.example.springproject.repository.CourseRepository;
import org.example.springproject.repository.CourseScheduleRepository;
import org.example.springproject.services.CourseScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseScheduleServiceImpl implements CourseScheduleService {

    @Autowired
    private CourseScheduleRepository repository;
    @Autowired
    private CourseRepository courseRepository;

    @Override
    public List<CourseSchedule> getAllCourseSchedule(){
        return (List<CourseSchedule>) repository.findAll();
    }
    @Override
    public CourseSchedule addCourseSchedule(Long courseId, String startTime, String endTime, WeekDay weekDay, WeekParity weekParity){

        Course course = courseRepository.findById(courseId).orElseThrow(EntityNotFoundException::new);

        CourseSchedule newCourseSchedule = new CourseSchedule(course,startTime,endTime,weekDay,weekParity);

        return repository.save(newCourseSchedule);

    }

}
