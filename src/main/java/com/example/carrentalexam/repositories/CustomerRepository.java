package com.example.carrentalexam.repositories;

import com.example.carrentalexam.models.Car;
import com.example.carrentalexam.models.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CustomerRepository {

    @Autowired
    JdbcTemplate jdbcTemplate;
    public void createNewCustomer(String name, String address, String number, String email) {
        String query = "insert into customers(name, address, number, email)" +
                "values(?, ?, ?, ?);";
        jdbcTemplate.update(query, name, address, number, email);
    }

    public List<Customer> getAllCustomers() {
        String query = "SELECT * FROM customers;";
        RowMapper rowMapper = new BeanPropertyRowMapper(Customer.class);
        return jdbcTemplate.query(query, rowMapper);
    }
}