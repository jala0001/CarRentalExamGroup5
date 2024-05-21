package com.example.carrentalexam.repositories;

import com.example.carrentalexam.models.Damage;
import com.example.carrentalexam.models.EmployeeUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class DamageRepository {
    @Autowired
    JdbcTemplate jdbcTemplate;

    public void createDamageReport(int rentalContractId, String descriptionOfDamage, double repairCosts, String status) {
        String query = "insert into damages(rental_contract_id, description_of_damage, repair_costs, damage_status)" +
                "values(?, ?, ?, ?);";
        jdbcTemplate.update(query, rentalContractId, descriptionOfDamage, repairCosts, status);
    }

    public List<Damage> getDamagesByContractId(int rentalContractId) {
        String query = "select * from damages where rental_contract_id = ?;";
        RowMapper rowMapper = new BeanPropertyRowMapper(Damage.class);
        return jdbcTemplate.query(query, rowMapper, rentalContractId);
    }

    public void changeDamageFromReportedToProcessed(int damageId) {
        String query = "update damages set damage_status = 'PROCESSED' where damage_id = ?;";
        jdbcTemplate.update(query, damageId);
    }
}