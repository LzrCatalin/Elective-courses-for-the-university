package org.example.springproject.controller;

import org.example.springproject.entity.Application;
import org.example.springproject.services.ApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/applications")
public class ApplicationApi {
    @Autowired
    private ApplicationService applicationService;

    @GetMapping("/")
    public List<Application> getAllApplications(){
        return applicationService.getAllApplications();
    }
}
