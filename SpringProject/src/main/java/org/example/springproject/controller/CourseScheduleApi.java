package org.example.springproject.controller;

import com.google.gson.Gson;
import com.sun.jdi.request.DuplicateRequestException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.NonUniqueResultException;
import org.example.springproject.entity.CourseSchedule;
import org.example.springproject.enums.WeekDay;
import org.example.springproject.enums.WeekParity;
import org.example.springproject.exceptions.InvalidTimeException;
import org.example.springproject.services.CourseScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("/courses/schedule")
public class CourseScheduleApi {

    private static final Gson gson = new Gson();

    @Autowired
    private CourseScheduleService courseScheduleService;

    @GetMapping("/")
    public List<CourseSchedule> getAllCourseSchedule(){
        return courseScheduleService.getAllCourseSchedule();
    }
    @PostMapping("/")
    public ResponseEntity<String> addCourseSchedule(@RequestBody Map<String,Object> requestBody){

        try{
            String courseName = (String) requestBody.get("courseName");
            String startTime = (String) requestBody.get("startTime");
            String endTime = (String) requestBody.get("endTime");
            String weekDayString = (String) requestBody.get("weekDay");
            WeekDay weekDay = WeekDay.valueOf(weekDayString);
            String weekParityString = (String) requestBody.get("weekParity");
            WeekParity weekParity = WeekParity.valueOf(weekParityString);

            courseScheduleService.addCourseSchedule(courseName,startTime,endTime,weekDay,weekParity);
            return new ResponseEntity<>(gson.toJson("Schedule added successfully!"), HttpStatus.CREATED);

        }catch (EntityNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);

        } catch (DuplicateRequestException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateCourseSchedule(@PathVariable("id") Long id, @RequestBody Map<String, Object> requestBody){
        try{

            String startTime = (String) requestBody.get("startTime");
            String endTime = (String) requestBody.get("endTime");
            String weekDayString = (String) requestBody.get("weekDay");
            WeekDay weekDay = WeekDay.valueOf(weekDayString);
            String weekParityString = (String) requestBody.get("weekParity");
            WeekParity weekParity = WeekParity.valueOf(weekParityString);

            courseScheduleService.updateCourseSchedule(id,startTime,endTime,weekDay,weekParity);
            return new ResponseEntity<>("Course schedule with id: " + id + " successfully updated!", HttpStatus.OK);

        }catch (EntityNotFoundException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }catch (InvalidTimeException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCourseSchedule(@PathVariable("id") Long id){
        try {
            courseScheduleService.deleteCourseSchedule(id);
            return new ResponseEntity<>(gson.toJson("Course schedule with id:" + id + " successfully deleted."), HttpStatus.OK);

        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>("Course schedule id: " + id + " not found.", HttpStatus.NOT_FOUND);
        }
    }
}
