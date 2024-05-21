package com.example.carrentalexam.services;

import com.example.carrentalexam.controllers.EmployeeUsersController;
import com.example.carrentalexam.enums.EmployeeUserDepartment;
import com.example.carrentalexam.models.EmployeeUser;
import com.example.carrentalexam.repositories.EmployeeUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeUserService {
    @Autowired
    private EmployeeUserRepository employeeUserRepository;


    public void addEmployee(String username, String password,
                            EmployeeUserDepartment employeeUserDepartment) {
        employeeUserRepository.addEmployee(username, password, employeeUserDepartment);
    }


    public List<EmployeeUser> getAllEmployees() {
        return employeeUserRepository.getAllEmployees();
    }

    public EmployeeUser getEmployee(int employeeId) {
        return employeeUserRepository.getEmployee(employeeId);
    }
}