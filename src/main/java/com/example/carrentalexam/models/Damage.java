package com.example.carrentalexam.models;

import com.example.carrentalexam.enums.DamageStatus;

public class Damage {
    private int damageId;
    private int rentalContractId;
    private String descriptionOfDamage;
    private double repairCosts;
    private DamageStatus damageStatus;

    public Damage() {
    }

    public int getDamageId() {
        return damageId;
    }

    public void setDamageId(int damageId) {
        this.damageId = damageId;
    }

    public int getRentalContractId() {
        return rentalContractId;
    }

    public void setRentalContractId(int rentalContractId) {
        this.rentalContractId = rentalContractId;
    }

    public String getDescriptionOfDamage() {
        return descriptionOfDamage;
    }

    public void setDescriptionOfDamage(String descriptionOfDamage) {
        this.descriptionOfDamage = descriptionOfDamage;
    }

    public double getRepairCosts() {
        return repairCosts;
    }

    public void setRepairCosts(double repairCosts) {
        this.repairCosts = repairCosts;
    }

    public DamageStatus getDamageStatus() {
        return damageStatus;
    }

    public void setDamageStatus(DamageStatus damageStatus) {
        this.damageStatus = damageStatus;
    }
}
