package org.example.springproject.Services.Implementation;

import org.example.springproject.Entity.User;
import org.example.springproject.Repository.UserRepository;
import org.example.springproject.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository repository;

	@Override
	public List<User> getAllUsers() {
		return repository.findAll();
	}
}
