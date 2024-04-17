package org.example.springproject.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false, name = "name")
	private String name;

	@Column(nullable = false, name = "role")
	private String role;

	// Empty constructor
	public User() {}

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
	public void setId(Long id) {
		this.id = id;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setRole(String role) {
		this.role = role;
	}
}
