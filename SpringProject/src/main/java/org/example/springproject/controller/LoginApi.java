package org.example.springproject.controller;

import org.example.springproject.entity.Admin;
import org.example.springproject.entity.User;
import org.example.springproject.services.AdminService;
import org.slf4j.ILoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@CrossOrigin
@RequestMapping("/login")
@RestController
public class LoginApi {

    @Autowired
    public AdminService adminService;
}
