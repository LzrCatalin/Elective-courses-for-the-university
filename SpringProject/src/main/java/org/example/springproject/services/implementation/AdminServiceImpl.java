package org.example.springproject.services.implementation;

import jakarta.persistence.EntityNotFoundException;
import org.example.springproject.controller.CourseApi;
import org.example.springproject.entity.Admin;
import org.example.springproject.exceptions.InvalidNameException;
import org.example.springproject.repository.AdminRepository;
import org.example.springproject.services.AdminService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.example.springproject.utilities.NameValidator;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class AdminServiceImpl implements AdminService {
	private static final Logger logger = LoggerFactory.getLogger(CourseApi.class);

	@Autowired
	public AdminRepository repository;

	@Override
	public Admin getAdmin(String email){return repository.findByEmail(email);}
	@Override
	public List<Admin> getAllAdmins() {
		return repository.findAll();
	}

	@Override
	public Admin addAdmin(String name) {
		// Verify inserted name
		if(!NameValidator.validateString(name)) {
			throw new InvalidNameException("The string contains only digits.");
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
		// Search the admin using id
		Admin updateAdmin = repository.findById(id).orElseThrow(EntityNotFoundException::new);

		// Verify inserted name
		if(!NameValidator.validateString(name)) {
			throw new InvalidNameException("The string contains only digits.");
		}

		// Set the new values of attributes
		updateAdmin.setName(name);
		// Save updated object
		return repository.save(updateAdmin);

	}

	@Override
	public void deleteAdmin(Long id) {
		// Search wanted admin by id
		repository.findById(id).orElseThrow(EntityNotFoundException::new);

		// Delete found id
		repository.deleteById(id);
	}
}
