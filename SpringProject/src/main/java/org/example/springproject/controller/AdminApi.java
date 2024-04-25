package org.example.springproject.controller;

import org.example.springproject.entity.Admin;
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
	public ResponseEntity<String> addAdmin(String name) {
		adminService.addAdmin(name);
		return new ResponseEntity<>("Successfully added new admin.", HttpStatus.CREATED);
	}

	@PutMapping("/{id}")
	public ResponseEntity<String> updateAdmin(@PathVariable("id") Long id, String name) {
		adminService.updateAdmin(id, name);
		return new ResponseEntity<>("Admin with id:" + id + " successfully updated.", HttpStatus.OK);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteAdmin(@PathVariable("id") Long id) {
		adminService.deleteAdmin(id);
		return new ResponseEntity<>("Admin with id:" + id + " successfully deleted.", HttpStatus.OK);
	}
}
