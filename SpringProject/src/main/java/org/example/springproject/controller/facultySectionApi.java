package org.example.springproject.controller;

import org.example.springproject.enums.FacultySection;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/facultysections")

public class facultySectionApi {

    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping
    public FacultySection[] getFacultySection(){
        return FacultySection.values();
    }
}
