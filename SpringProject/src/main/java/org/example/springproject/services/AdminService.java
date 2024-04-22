package org.example.springproject.services;

import org.apache.coyote.Response;
import org.example.springproject.entity.Admin;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface AdminService {
	List<Admin> getAllAdmins();
	ResponseEntity<String> addAdmin(String name);
	ResponseEntity<String> updateAdmin(Long id, String name);
	ResponseEntity<String> deleteAdmin(Long id);
}
