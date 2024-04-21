package org.example.springproject.services;

import org.apache.coyote.Response;
import org.example.springproject.entity.Admin;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface AdminService {
	List<Admin> getAllAdmins();
	ResponseEntity<String> addAdmin(Long id, String name, String role);
	ResponseEntity<String> updateAdmin(Long id, String name, String role);
	ResponseEntity<String> deleteAdmin(Long id);
}