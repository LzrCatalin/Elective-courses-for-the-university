package org.example.springproject.controller;

import org.example.springproject.enums.WeekDay;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/weekday")
public class weekDayApi {

    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("/")
    public WeekDay[] getWeekDay(){
        return WeekDay.values();
    }
}
