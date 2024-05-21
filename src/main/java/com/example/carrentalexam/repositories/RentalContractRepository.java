package com.example.carrentalexam.repositories;

import com.example.carrentalexam.models.Car;
import com.example.carrentalexam.models.EmployeeUser;
import com.example.carrentalexam.models.RentalContract;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public class RentalContractRepository {
    @Autowired
    JdbcTemplate jdbcTemplate;

    public RentalContractRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void createRentalContract(int customerId, int carId, LocalDate startDate, LocalDate endDate, double price, String pickUpLocation, String conditionOnDelivery, String conditionUponReturn) {
        String query = "insert into rental_contracts(customer_id, car_id, start_date, end_date, " +
                "price, pick_up_location, condition_on_delivery, condition_upon_return, is_rental_contract_ended)" + // ÆNDRING 20-05
                "values(?, ?, ?, ?, ?, ?, ?, ?, ?);";
        jdbcTemplate.update(query, customerId, carId, startDate, endDate, price, pickUpLocation, conditionOnDelivery, conditionUponReturn, "no");
    }


    public List<Car> getRentedCarsCount() { // NY - ændret at den modtager CAR objekter og ikke RentalContract objekter
        String query = "SELECT * FROM cars where status = 'RENTED'";
        RowMapper rowMapper = new BeanPropertyRowMapper(Car.class);
        return jdbcTemplate.query(query, rowMapper);
    }


    public double getTotalRevenue() { // NY - ændret så der kun returnes beløb fra kontrakter som er igangværende.
        String query = "SELECT SUM(price) FROM rental_contracts WHERE start_date <= CURDATE() AND end_date >= CURDATE()";
        return jdbcTemplate.queryForObject(query, Double.class);
    }


    public List<RentalContract> getAllRentalContractWhereTheCarHasBeenReturned() { // ÆNDRING 20-05
        String query = "SELECT * FROM rental_contracts WHERE end_date < CURDATE() AND is_rental_contract_ended = 'no';";
        RowMapper<RentalContract> rowMapper = new BeanPropertyRowMapper<>(RentalContract.class);
        return jdbcTemplate.query(query, rowMapper);
    }


    public List<RentalContract> getAllRentalContractsThatsActive() { // ÆNDRING 20-05
        String query = "select * from rental_contracts where is_rental_contract_ended = 'no';";
        RowMapper rowMapper = new BeanPropertyRowMapper(RentalContract.class);
        return jdbcTemplate.query(query, rowMapper);

    }

    public void concludeContract(int rentalContractId) { // tilføjelse 20-05
        String query = "update rental_contracts set is_rental_contract_ended = 'yes' where rental_contract_id = ?;";
        jdbcTemplate.update(query, rentalContractId);
    }

    public RentalContract getRentalContract(int rentalContractId) { // tilføjelse 20-05
        String query = "Select * from rental_contracts where rental_contract_id = ?;";
        RowMapper<RentalContract> rowMapper = new BeanPropertyRowMapper<>(RentalContract.class);
        return jdbcTemplate.queryForObject(query, rowMapper, rentalContractId);
    }

    public void changeConditionUponReturn(int rentalContractId) { // tilføjelse 20-05
        String query = "update rental_contracts set condition_upon_return = 'same condition as before' where rental_contract_id = ?;";
        jdbcTemplate.update(query, rentalContractId);
    }

    public void changeConditionUponReturnToDamaged(int rentalContractId) {
        String query = "update rental_contracts set condition_upon_return = 'had one or more damages' where rental_contract_id = ?;";
        jdbcTemplate.update(query, rentalContractId);
    }
}