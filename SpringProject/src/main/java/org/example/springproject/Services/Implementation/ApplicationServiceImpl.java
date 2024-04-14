package org.example.springproject.Services.Implementation;

import org.example.springproject.Entity.Application;
import org.example.springproject.Repository.ApplicationRepository;
import org.example.springproject.Services.ApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ApplicationServiceImpl implements ApplicationService {
    @Autowired
    private ApplicationRepository repository;

    /**
     * Method to get all the applications
     * @return a list of all applications
     */
    @Override
    public List<Application> getAllApplications() {
        return repository.findAll();
    }

}
