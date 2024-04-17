package org.example.springproject.entity;

import jakarta.persistence.*;

@MappedSuperclass
public abstract class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	protected Long id;
	protected String name;
	protected String role;

	// Empty constructor
	public User() {};

	// Constructor
	public User(Long id, String name, String role) {
		this.id = id;
		this.name = name;
		this.role = role;
	}

	// Getters
	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getRole() {
		return role;
	}

	// Setters
	public void setName(String newName) {
		this.name = newName;
	}

	public void setRole(String newRole) {
		this.role = newRole;
	}
}
