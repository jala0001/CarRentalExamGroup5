package com.example.carrentalexam.controllers;

import com.example.carrentalexam.services.RentalContractService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;

@Controller
public class RentalContractsController {
    private final RentalContractService rentalContractService;

    public RentalContractsController(RentalContractService rentalContractService) {
        this.rentalContractService = rentalContractService;
    }

    @GetMapping("/createRentalContract")
    public String createRentalContract(@RequestParam int employeeUserId, Model model) {
        model.addAttribute("employeeUserId", employeeUserId);
        return "home/createNewRentalContract";
    }

    @PostMapping("/createNewRentalContractAction")
    public String createRentalContract(@RequestParam int customerId, @RequestParam int carId,
                                       @RequestParam LocalDate startDate,
                                       @RequestParam LocalDate endDate, @RequestParam double price,
                                       @RequestParam String pickUpLocation,
                                       @RequestParam String conditionOnDelivery,
                                       @RequestParam String conditionUponReturn,
                                       @RequestParam int employeeUserId) {
        rentalContractService.createRentalContract(customerId, carId, startDate, endDate, price,
                pickUpLocation, conditionOnDelivery, conditionUponReturn);

        return "redirect:/mainMenuDataRegistration?employeeUserId=" + employeeUserId; // Redirect til EmployeeUserController
    }
}
