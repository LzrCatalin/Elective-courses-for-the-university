package org.example.springproject.services.implementation;

import com.sun.jdi.request.DuplicateRequestException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.NonUniqueResultException;
import org.example.springproject.controller.CourseApi;
import org.example.springproject.entity.Course;
import org.example.springproject.entity.CourseSchedule;
import org.example.springproject.entity.Student;
import org.example.springproject.enums.WeekDay;
import org.example.springproject.enums.WeekParity;
import org.example.springproject.exceptions.InvalidTimeException;
import org.example.springproject.exceptions.ScheduleConflictException;
import org.example.springproject.repository.ApplicationRepository;
import org.example.springproject.repository.CourseRepository;
import org.example.springproject.repository.CourseScheduleRepository;
import org.example.springproject.services.CourseScheduleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Service
public class CourseScheduleServiceImpl implements CourseScheduleService {
    private static final Logger logger = LoggerFactory.getLogger(CourseApi.class);

    @Autowired
    private CourseScheduleRepository repository;
    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private ApplicationRepository applicationRepository;

    @Override
    public List<CourseSchedule> getAllCourseSchedule(){
        return repository.findAll();
    }


    @Override
    public CourseSchedule addCourseSchedule(String courseName, String startTime, String endTime, WeekDay weekDay, WeekParity weekParity){

        Course course = courseRepository.findByName(courseName);
        if (course == null) {
            throw new EntityNotFoundException("Course with name: " + courseName + " not found");
        }

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

        // Calculate the duration of course
        long startMinutes = parsedStartTime.getHour() * 60 + parsedStartTime.getMinute();
        long endMinutes = parsedEndTime.getHour() * 60 + parsedEndTime.getMinute();
        long durationMinutes = endMinutes - startMinutes;

        // Verify if the duration is 1 hour and 30 minutes (90 minutes)
        if (durationMinutes != 90) {
            throw new InvalidTimeException("The course duration must be 1 hour and 30 minutes.");
        }

        // Remake the startTime and endTime to be string
        startTime = parsedStartTime.format(formatter);
        endTime = parsedEndTime.format(formatter);

        // Get a list of courses that exists in the same timeslot as our wanted timeslot for our course
        List<Course> existingCourses;
        if (weekParity.equals(WeekParity.WEEKLY)) {
            // Weekly PATH: BE SURE TO CHECK SCHEDULES IN EVEN AND ODD PARITY
            existingCourses = repository.findCoursesAtSelectedTimeSlot(weekDay, Arrays.asList(weekParity, WeekParity.ODD, WeekParity.EVEN), startTime, endTime);

        } else {
            // Not Weekly PATH: BE SURE TO CHECK SCHEDULES IN RECEIVED PARITY AND WEEKLY
            existingCourses = repository.findCoursesAtSelectedTimeSlot(weekDay, Arrays.asList(weekParity, WeekParity.WEEKLY), startTime, endTime);
        }

//        logger.info("Found course in the same timeslot : ");
//        for(Course c : existingCourses) {
//            logger.info(c.getName());
//        }

        // Be sure not adding more schedules for a course
        int courseSchedules = repository.courseAppearances(course.getId());
        if (courseSchedules == 1) {
            throw new DuplicateRequestException("This course already has a schedule");
        }

        // Get a list of students and teachers for found course
        List<Student> courseStudents = applicationRepository.getCourseAcceptedStudents(course.getId());
        String courseTeacher = applicationRepository.getCourseTeachers(course.getId());

        // For each found course , get list of students
        for (Course existingCourse : existingCourses) {
            /*
            From list to arraylist, helps us to verify if students and teachers dont need to split
            for course attendance
             */
            List<Student> existingCourseStudents = applicationRepository.getCourseAcceptedStudents(existingCourse.getId());
            ArrayList<Student> checkStudentsList = new ArrayList<>(existingCourseStudents);

            // Get existing course teacher
            String existingCourseTeacher = applicationRepository.getCourseTeachers(existingCourse.getId());

            // Remove all obj that exists in other list
            checkStudentsList.removeAll(courseStudents);

            // If size changed after remove stage, that means there are students in both of courses
            if (checkStudentsList.size() == existingCourseStudents.size() && !Objects.equals(courseTeacher, existingCourseTeacher)) {
                CourseSchedule newCourseSchedule = new CourseSchedule(course,startTime,endTime,weekDay,weekParity);
                return repository.save(newCourseSchedule);

            } else {
                throw new ScheduleConflictException("Unable to add course schedule: students or teachers cannot be in multiple places at the same time." +
                        "Conflicting course: " + existingCourse.getName());
            }

        }

        return null;
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
