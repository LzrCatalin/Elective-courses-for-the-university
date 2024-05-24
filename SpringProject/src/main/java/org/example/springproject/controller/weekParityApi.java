package org.example.springproject.controller;

import org.example.springproject.enums.WeekParity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/weekparity")
public class weekParityApi {

    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("/")
    public WeekParity[] getWeekParity(){
        return WeekParity.values();
    }
}
