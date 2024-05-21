package org.example.springproject.controller;

import com.google.gson.Gson;
import org.example.springproject.entity.CourseSchedule;
import org.example.springproject.enums.WeekDay;
import org.example.springproject.enums.WeekParity;
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
            Integer courseIdNumber = (Integer) requestBody.get("courseId");
            Long courseId = courseIdNumber.longValue();  // Convert to Long
            String startTime = (String) requestBody.get("startTime");
            String endTime = (String) requestBody.get("endTime");
            String weekDayString = (String) requestBody.get("weekDay");
            WeekDay weekDay = WeekDay.valueOf(weekDayString);
            String weekParityString = (String) requestBody.get("weekParity");
            WeekParity weekParity = WeekParity.valueOf(weekParityString);

            courseScheduleService.addCourseSchedule(courseId,startTime,endTime,weekDay,weekParity);
            return new ResponseEntity<>(gson.toJson("Schedule added successfully!"), HttpStatus.CREATED);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }


    }
}
