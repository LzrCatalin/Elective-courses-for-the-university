package org.example.springproject.services.implementation;

import jakarta.persistence.EntityNotFoundException;
import org.example.springproject.entity.Course;
import org.example.springproject.entity.CourseSchedule;
import org.example.springproject.enums.WeekDay;
import org.example.springproject.enums.WeekParity;
import org.example.springproject.exceptions.InvalidTimeException;
import org.example.springproject.repository.CourseRepository;
import org.example.springproject.repository.CourseScheduleRepository;
import org.example.springproject.services.CourseScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
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
    public CourseSchedule addCourseSchedule(String courseName, String startTime, String endTime, WeekDay weekDay, WeekParity weekParity){

        Course course = courseRepository.findByName(courseName);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        LocalTime parsedStartTime = LocalTime.parse(startTime,formatter);
        LocalTime parsedEndTime = LocalTime.parse(endTime,formatter);

        // Verify if the startTIme is before endTime
        if(parsedStartTime.isAfter(parsedEndTime)){
            throw new InvalidTimeException("The start time must be before the end time.");
        }

        // Verify if the startTime and endTime are in the formatter format
        if(!parsedStartTime.format(formatter).equals(startTime) || !parsedEndTime.format(formatter).equals(endTime)){
            throw new InvalidTimeException("The start time or end time is not in the correct format. (hh:mm)");
        }

        // Verify if the startTime is greater than 08:00 and the endTime is less than 21:00
        if(parsedStartTime.isBefore(LocalTime.parse("08:00",formatter)) || parsedEndTime.isAfter(LocalTime.parse("21:00",formatter))){
            throw new InvalidTimeException("The course schedule must be between 08:00 - 21:00");
        }

        // Remake the startTime and endTime to be string
        startTime = parsedStartTime.format(formatter);
        endTime = parsedEndTime.format(formatter);

        CourseSchedule newCourseSchedule = new CourseSchedule(course,startTime,endTime,weekDay,weekParity);

        return repository.save(newCourseSchedule);
    }

    @Override
    public void deleteCourseSchedule(Long id){
        repository.findById(id).orElseThrow(EntityNotFoundException::new);
        repository.deleteById(id);
    }

    @Override
    public CourseSchedule updateCourseSchedule(Long courseScheduleId, String startTime, String endTime, WeekDay weekDay, WeekParity weekParity){
        CourseSchedule courseScheduleToBeUpdated = repository.findById(courseScheduleId).orElseThrow(EntityNotFoundException::new);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        LocalTime parsedStartTime = LocalTime.parse(startTime,formatter);
        LocalTime parsedEndTime = LocalTime.parse(endTime,formatter);

        // Verify if the startTIme is before endTime
        if(parsedStartTime.isAfter(parsedEndTime)){
            throw new InvalidTimeException("The start time must be before the end time.");
        }

        // Verify if the startTime and endTime are in the formatter format
        if(!parsedStartTime.format(formatter).equals(startTime) || !parsedEndTime.format(formatter).equals(endTime)){
            throw new InvalidTimeException("The start time or end time is not in the correct format. (hh:mm)");
        }

        // Verify if the startTime is greater than 08:00 and the endTime is less than 21:00
        if(parsedStartTime.isBefore(LocalTime.parse("08:00",formatter)) || parsedEndTime.isAfter(LocalTime.parse("21:00",formatter))){
            throw new InvalidTimeException("The course schedule must be between 08:00 - 21:00");
        }

        // Remake the startTime and endTime to be string
        startTime = parsedStartTime.format(formatter);
        endTime = parsedEndTime.format(formatter);

        courseScheduleToBeUpdated.setStartTime(startTime);
        courseScheduleToBeUpdated.setEndTime(endTime);
        courseScheduleToBeUpdated.setWeekDay(weekDay);
        courseScheduleToBeUpdated.setWeekParity(weekParity);

        return repository.save(courseScheduleToBeUpdated);

    }
}
