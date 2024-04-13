package org.example.springproject.Services.Implementation;

import org.example.springproject.Entity.Student;
import org.example.springproject.Repository.StudentRepository;
import org.example.springproject.Services.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentServiceImpl implements StudentService {

	@Autowired
	public StudentRepository repository;

	@Override
	public List<Student> getAllStudents() {
		return repository.findAll();
	}
}
