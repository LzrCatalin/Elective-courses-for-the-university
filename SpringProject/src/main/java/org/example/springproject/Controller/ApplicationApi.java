package org.example.springproject.Controller;

import org.example.springproject.Entity.Application;
import org.example.springproject.Services.ApplicationService;
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
