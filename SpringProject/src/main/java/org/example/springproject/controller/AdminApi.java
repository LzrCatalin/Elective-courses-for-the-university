package org.example.springproject.controller;

import jakarta.persistence.EntityNotFoundException;
import org.example.springproject.entity.Admin;
import org.example.springproject.exceptions.InvalidNameException;
import org.example.springproject.exceptions.NoSuchObjectExistsException;
import org.example.springproject.services.AdminService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@ResponseBody
@RestController
@RequestMapping("/admins")
public class AdminApi {
	private static final Logger logger = LoggerFactory.getLogger(CourseApi.class);

	@Autowired
	public AdminService adminService;

	@GetMapping("/")
	public List<Admin> getAllAdmins() {
		return adminService.getAllAdmins();
	}

	@PostMapping("/")
	public ResponseEntity<String> addAdmin(@RequestBody Map<String, Object> requestBody) {
		try {
			String name = (String) requestBody.get("name");
			logger.info("Received name: " + name);
			adminService.addAdmin(name);
			return new ResponseEntity<>("Successfully added new admin.", HttpStatus.CREATED);

		} catch (InvalidNameException e) {
			logger.info("Inside catch error ... ");
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	@PutMapping("/{id}")
	public ResponseEntity<String> updateAdmin(@PathVariable("id") Long id,
											  @RequestBody Map<String, Object> requestBody) {
		try {
			String name = (String) requestBody.get("name");
			adminService.updateAdmin(id, name);
			return new ResponseEntity<>("Admin with id:" + id + " successfully updated.", HttpStatus.OK);

		} catch (EntityNotFoundException e) {
			return new ResponseEntity<>("Not found id: " + id + " for admin.", HttpStatus.NOT_FOUND);

		} catch (InvalidNameException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteAdmin(@PathVariable("id") Long id) {
		try {
			adminService.deleteAdmin(id);
			return new ResponseEntity<>("Admin with id:" + id + " successfully deleted.", HttpStatus.OK);

		} catch (EntityNotFoundException e) {
			return new ResponseEntity<>("Not found id: " + id + " for admin.", HttpStatus.NOT_FOUND);
		}
	}
}
