package org.example.springproject.controller;

import org.example.springproject.entity.Admin;
import org.example.springproject.exceptions.InvalidNameException;
import org.example.springproject.exceptions.NoSuchObjectExistsException;
import org.example.springproject.services.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@ResponseBody
@RestController
@RequestMapping("/admins")
public class AdminApi {

	@Autowired
	public AdminService adminService;

	@GetMapping("/")
	public List<Admin> getAllAdmins() {
		return adminService.getAllAdmins();
	}

	@PostMapping("/")
	public ResponseEntity<String> addAdmin(@RequestParam String name) {
		try {
			adminService.addAdmin(name);
			return new ResponseEntity<>("Successfully added new admin.", HttpStatus.CREATED);

		} catch (InvalidNameException e) {
			return new ResponseEntity<>(e.getMessage(), e.getHttpStatus());
		}
	}

	@PutMapping("/{id}")
	public ResponseEntity<String> updateAdmin(@PathVariable("id") Long id,
											  @RequestParam String name) {
		try {
			adminService.updateAdmin(id, name);
			return new ResponseEntity<>("Admin with id:" + id + " successfully updated.", HttpStatus.OK);

		} catch (NoSuchObjectExistsException e) {
			return new ResponseEntity<>(e.getMessage(), e.getHttpStatus());

		} catch (InvalidNameException e) {
			return new ResponseEntity<>(e.getMessage(), e.getHttpStatus());
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteAdmin(@PathVariable("id") Long id) {
		try {
			adminService.deleteAdmin(id);
			return new ResponseEntity<>("Admin with id:" + id + " successfully deleted.", HttpStatus.OK);

		} catch (NoSuchObjectExistsException e) {
			return new ResponseEntity<>(e.getMessage(), e.getHttpStatus());
		}
	}
}
