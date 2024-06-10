package org.example.springproject.controller;

import jakarta.persistence.EntityNotFoundException;
import org.example.springproject.dto.StudentDTO;
import org.example.springproject.dto.UserDTO;
import org.example.springproject.entity.Admin;
import org.example.springproject.entity.Student;
import org.example.springproject.entity.User;
import org.example.springproject.services.AdminService;
import org.example.springproject.services.StudentService;
import org.slf4j.ILoggerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@CrossOrigin
@RequestMapping("/login")
@RestController
public class LoginApi {

    private static final Logger logger = LoggerFactory.getLogger(LoginApi.class);

    @Autowired
    public AdminService adminService;
    @Autowired
    public StudentService studentService;

    @PostMapping("/verify")
    public ResponseEntity<UserDTO> verifyCredentials(@RequestBody Map<String, Object> requestBody) {

        logger.info("Inside login ...");
        String email = (String) requestBody.get("email");
        logger.info("Received email :" + email);

        Student student = studentService.getStudent(email);
        if (student != null) {
            logger.info("Student name : " + student.getName());
            UserDTO userDTO = new UserDTO(student.getId(), student.getName(), student.getEmail(), student.getRole());
            return new ResponseEntity<>(userDTO, HttpStatus.OK);
        }

        Admin admin = adminService.getAdmin(email);
        if (admin != null) {
            logger.info("Admin name : " + admin.getName());
            UserDTO userDTO = new UserDTO(admin.getId(), admin.getName(), admin.getEmail(), admin.getRole());
            return new ResponseEntity<>(userDTO, HttpStatus.OK);
        }

        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }
}
