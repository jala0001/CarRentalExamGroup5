package com.example.carrentalexam.services;

import com.example.carrentalexam.models.Damage;
import com.example.carrentalexam.repositories.DamageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DamageService {
    @Autowired
    private DamageRepository damageRepository;
    public void createDamageReport(int rentalContractId, String descriptionOfDamage, double repairCosts, String status) {
        damageRepository.createDamageReport(rentalContractId, descriptionOfDamage, repairCosts, status);
    }

    public List<Damage> getDamagesByContractId(int rentalContractId) {
        return damageRepository.getDamagesByContractId(rentalContractId);
    }

    public void changeDamageFromReportedToProcessed(int damageId) {
        damageRepository.changeDamageFromReportedToProcessed(damageId);
    }


}