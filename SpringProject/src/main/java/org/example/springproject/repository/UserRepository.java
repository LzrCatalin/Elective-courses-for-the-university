package org.example.springproject.repository;

import org.example.springproject.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
	User findByName(String name);
}
