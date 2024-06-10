package org.example.springproject.dto;

import org.example.springproject.entity.User;

public class UserDTO {
	private Long id;
	private String name;
	private String email;
	private String role;

	// Empty Constructor
	public UserDTO() {}

	// Constructor with param
	public UserDTO(Long id, String name, String email, String role) {
		this.id = id;
		this.name = name;
		this.email = email;
		this.role = role;
	}

	// Getters Setter
	public Long getId() {
		return id;
	}
	public String getName() {
		return name;
	}
	public String getEmail() {
		return email;
	}
	public String getRole() {
		return role;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public void setRole(String role) {
		this.role = role;
	}
}
