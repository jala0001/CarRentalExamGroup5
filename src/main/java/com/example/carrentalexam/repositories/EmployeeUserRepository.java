package com.example.carrentalexam.repositories;

import com.example.carrentalexam.enums.EmployeeUserDepartment;
import com.example.carrentalexam.models.EmployeeUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class EmployeeUserRepository {
    @Autowired
    JdbcTemplate jdbcTemplate;

    public void addEmployee(String username, String password, EmployeeUserDepartment employeeUserDepartment) {
        String query = "insert into employee_users(username, password, department)" +
                "values(?, ?, ?);";
        jdbcTemplate.update(query, username, password, employeeUserDepartment.name());
    }

    public List<EmployeeUser> getAllEmployees() {
        String query = "SELECT * FROM employee_users;";
        RowMapper rowMapper = new BeanPropertyRowMapper(EmployeeUser.class);
        return jdbcTemplate.query(query, rowMapper);
    }

    public EmployeeUser getEmployee(int employeeId) {
        String query = "Select * from employee_users where employee_user_id = ?;";
        RowMapper<EmployeeUser> rowMapper = new BeanPropertyRowMapper<>(EmployeeUser.class);
        return jdbcTemplate.queryForObject(query, rowMapper, employeeId);
    }
}