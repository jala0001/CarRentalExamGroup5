package com.example.carrentalexam.controllers;

import com.example.carrentalexam.models.RentalContract;
import com.example.carrentalexam.services.CarService;
import com.example.carrentalexam.services.DamageService;
import com.example.carrentalexam.services.RentalContractService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class DamagesController {

    private final CarService carService;
    private final DamageService damageService;
    private final RentalContractService rentalContractService;
    private int amountOfDamages = 0;


    public DamagesController(CarService carService ,DamageService damageService, RentalContractService rentalContractService) {
        this.carService = carService;
        this.damageService = damageService;
        this.rentalContractService = rentalContractService;

    }

    @GetMapping("/processCarForDamages")
    public String processCarForDamages(@RequestParam int carId, @RequestParam int rentalContractId ,@RequestParam int employeeUserId ,Model model) {
        model.addAttribute("car", carService.getCarRented(carId));
        model.addAttribute("rentalContractId", rentalContractId);
        model.addAttribute("employeeUserId", employeeUserId);
        return "home/processCar";
    }

    @GetMapping("/processCarYes")
    public String carHasDamages(@RequestParam int carId, @RequestParam int rentalContractId ,@RequestParam int employeeUserId,
                                @RequestParam(required = false) String message, Model model) {
        rentalContractService.changeConditionUponReturnToDamaged(rentalContractId); // tilføjet 20-05
        model.addAttribute("car", carService.getCarRented(carId));
        model.addAttribute("rentalContractId", rentalContractId);
        model.addAttribute("employeeUserId", employeeUserId);
        model.addAttribute("message", message);
        model.addAttribute("amountOfDamages", amountOfDamages);

        return "home/createDamageReport";
    }

    @PostMapping("/processCarYesAction")
    public String carHasDamages(@RequestParam int rentalContractId, @RequestParam String descriptionOfDamage,
                                @RequestParam double repairCosts, @RequestParam String status,
                                @RequestParam int employeeUserId, @RequestParam int carId) {
        try {
            damageService.createDamageReport(rentalContractId, descriptionOfDamage, repairCosts, status);
            amountOfDamages++; // så brugeren kan se hvor mange reports de har lavet på bilen.
            return "redirect:/processCarYes?carId=" + carId + "&rentalContractId=" +
                    rentalContractId + "&employeeUserId=" + employeeUserId + "&message=Damage+report+has+been+created."; // lille kommentar ændring.
        } catch (Exception e) {
            return "redirect:/processCarYes?carId=" + carId + "&rentalContractId=" +
                    rentalContractId + "&employeeUserId=" + employeeUserId + "&message=Something+went+wrong.+Please+try+agian.";
        }

    }

    @PostMapping("/processCarComplete")
    public String processCarComplete(@RequestParam int carId ,@RequestParam int employeeUserId) {
        carService.changeCarToMaintenance(carId);
        amountOfDamages = 0; // nulstiller antal skader som brugeren kan se når de laver skades-rapport.
        return "redirect:/mainMenuDamageAndRepair?employeeUserId=" + employeeUserId;
    }

    @PostMapping("/processCarNo")
    public String carHasNoDamages(@RequestParam int carId, @RequestParam int rentalContractId, @RequestParam int employeeUserId) {
        carService.changeCarToAvailable(carId);
        rentalContractService.concludeContract(rentalContractId); // tilføjelse 20-05
        RentalContract rentalContract = rentalContractService.getRentalContract(rentalContractId); // tilføjelse 20-05
        if (rentalContract.getConditionUponReturn().startsWith("to")) { // tilføjelse 20-05
            rentalContractService.changeConditionUponReturn(rentalContractId);
        }

        return "redirect:/mainMenuDamageAndRepair?employeeUserId=" + employeeUserId;
    }

    @PostMapping("/damageReportedToProcessed")
    public String damageReportedToProcessed(@RequestParam int damageId, @RequestParam int employeeUserId) {
        damageService.changeDamageFromReportedToProcessed(damageId);
        return "redirect:/mainMenuDamageAndRepair?employeeUserId=" + employeeUserId;

    }
}