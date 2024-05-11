package org.example.springproject.services.implementation;

import org.example.springproject.entity.Admin;
import org.example.springproject.exceptions.InvalidNameException;
import org.example.springproject.exceptions.NoSuchObjectExistsException;
import org.example.springproject.repository.AdminRepository;
import org.example.springproject.services.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


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

		// Verify inserted name
		for (char c : name.toCharArray()) {
			if (Character.isDigit(c)) {
				throw new InvalidNameException("Verify inserted name. Can not use integers in it.", HttpStatus.BAD_REQUEST);
			}
		}

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

	}

	@Override
	public Admin updateAdmin(Long id, String name) {

		// Verify inserted name
		for (char c : name.toCharArray()) {
			if (Character.isDigit(c)) {
				throw new InvalidNameException("Verify inserted name. Can not use integers in it.", HttpStatus.BAD_REQUEST);
			}
		}

		// Search the admin using id
		Admin updateAdmin = repository.findById(id).orElse(null);

		 //Check if admin not exists
		if (updateAdmin == null) {
			throw new NoSuchObjectExistsException("Admin with id: " + id + " not found.", HttpStatus.NOT_FOUND);
		}

		// Set the new values of attributes
		updateAdmin.setName(name);
		// Save updated object
		return repository.save(updateAdmin);

	}

	@Override
	public void deleteAdmin(Long id) {
		// Search wanted admin by id
		Admin deleteAdmin = repository.findById(id).orElse(null);

		// Check if admin not exists
		if (deleteAdmin == null) {
			throw new NoSuchObjectExistsException("Admin with id: " + id + " not found.", HttpStatus.NOT_FOUND);
		}

		// Delete found id
		repository.deleteById(id);
	}
}
