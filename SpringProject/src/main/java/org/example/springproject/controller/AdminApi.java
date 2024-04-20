package org.example.springproject.controller;

import org.example.springproject.entity.Admin;
import org.example.springproject.services.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@ResponseBody
@RestController
@RequestMapping("/admin")
public class AdminApi {

	@Autowired
	public AdminService adminService;

	@GetMapping("/")
	public List<Admin> getAllAdmins() {
		return adminService.getAllAdmins();
	}

	@PostMapping("/add")
	public ResponseEntity<String> addAdmin(Long id, String name, String role) {
		return adminService.addAdmin(id, name, role);
	}

	@PutMapping("/update/{id}")
	public ResponseEntity<String> updateAdmin(@PathVariable("id") Long id, String name, String role) {
		return adminService.updateAdmin(id, name, role);
	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<String> deleteAdmin(@PathVariable("id") Long id) {
		return adminService.deleteAdmin(id);
	}
}
