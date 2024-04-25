package org.example.springproject.services.implementation;

import org.example.springproject.entity.Admin;
import org.example.springproject.repository.AdminRepository;
import org.example.springproject.services.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class AdminServiceImpl implements AdminService {

	@Autowired
	public AdminRepository repository;

	@Override
	public List<Admin> getAllAdmins() {
		return repository.findAll();
	}

	@Override
	public Admin addAdmin(String name) {
		try {
			// Create admin object
			Admin newAdmin = new Admin();

			// Add new object values
			newAdmin.setName(name);
			/*
			Auto complete the user role, i.e: here we add a new admin
			 */
			newAdmin.setRole("admin");

			// Save the object
			return repository.save(newAdmin);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	@Override
	public Admin updateAdmin(Long id, String name) {
		try {
			// Search the admin using id
			Admin updateAdmin = repository.findAdminById(id);

			// Check if admin not exists
//			if (updateAdmin == null) {
//				return ResponseEntity.badRequest().body("Admin with id: " + id + " not found");
//			}

			// Set the new values of attributes
			updateAdmin.setName(name);
			// Save updated object
			return repository.save(updateAdmin);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	@Override
	public void deleteAdmin(Long id) {
		try {
			// Search wanted admin by id
			Admin deleteAdmin = repository.findAdminById(id);

			// Check if admin not exists
//			if (deleteAdmin == null) {
//				return ResponseEntity.badRequest().body("Admin with id: " + id + " not found.");
//			}

			// Delete found id
			repository.deleteById(id);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
