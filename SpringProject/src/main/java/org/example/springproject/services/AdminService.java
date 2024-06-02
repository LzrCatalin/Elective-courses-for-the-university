package org.example.springproject.services;

import org.apache.coyote.Response;
import org.example.springproject.entity.Admin;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface AdminService {
	Admin getAdmin(String email);

	List<Admin> getAllAdmins();
	Admin addAdmin(String name);
	Admin updateAdmin(Long id, String name);
	void deleteAdmin(Long id);
}
