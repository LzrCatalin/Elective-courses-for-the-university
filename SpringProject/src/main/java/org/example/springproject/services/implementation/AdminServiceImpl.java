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

	// TODO : Make auto-insert role 'admin' when adding a new admin

	@Autowired
	public AdminRepository repository;

	@Override
	public List<Admin> getAllAdmins() {
		return repository.findAll();
	}

	@Override
	public ResponseEntity<String> addAdmin(String name, String role) {
		try {
			// Create admin object
			Admin newAdmin = new Admin();

			// Add new object values
			newAdmin.setName(name);
			newAdmin.setRole(role);

			// Save the object
			repository.save(newAdmin);
			return ResponseEntity.ok("Successfully added new admin");

		} catch (Exception e) {
			e.printStackTrace();
		}

		return ResponseEntity.badRequest().body("Failed while adding new admin");
	}

	@Override
	public ResponseEntity<String> updateAdmin(Long id, String name, String role) {
		try {
			// Search the admin using id
			Admin updateAdmin = repository.findAdminById(id);

			// Check if admin not exists
			if (updateAdmin == null) {
				return ResponseEntity.badRequest().body("Admin with id: " + id + " not found");
			}

			// Set the new values of attributes
			updateAdmin.setName(name);
			updateAdmin.setRole(role);
			// Save updated object
			repository.save(updateAdmin);

			return ResponseEntity.ok().body("Successfully updated admin with id: " + id);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return ResponseEntity.badRequest().body("Failed while updating admin with id: " + id);
	}

	@Override
	public ResponseEntity<String> deleteAdmin(Long id) {
		try {
			// Search wanted admin by id
			Admin deleteAdmin = repository.findAdminById(id);

			// Check if admin not exists
			if (deleteAdmin == null) {
				return ResponseEntity.badRequest().body("Admin with id: " + id + " not found.");
			}

			// Delete found id
			repository.deleteById(id);
			return ResponseEntity.ok().body("Successfully deleted admin with id: " + id);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return ResponseEntity.badRequest().body("Failed while deleting admin with id: " + id);
	}
}
