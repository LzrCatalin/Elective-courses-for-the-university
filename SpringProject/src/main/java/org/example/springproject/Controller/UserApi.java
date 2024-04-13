package org.example.springproject.Controller;

import org.example.springproject.Entity.User;
import org.example.springproject.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserApi {

	@Autowired
	public UserService userService;

	@GetMapping("/")
	public List<User> getAllUsers() {
		return userService.getAllUsers();
	}
}
