package com.example.carrentalexam.controllers;

import com.example.carrentalexam.services.CarService;
import com.example.carrentalexam.services.CustomerService;
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
    private final CustomerService customerService;
    private final CarService carService;

    public RentalContractsController(RentalContractService rentalContractService, CustomerService customerService,
                                     CarService carService) {
        this.rentalContractService = rentalContractService;
        this.customerService = customerService;
        this.carService = carService;
    }

    @GetMapping("/createRentalContract")
    public String createRentalContract(@RequestParam int employeeUserId, @RequestParam(required = false) String message,  Model model) {
        model.addAttribute("employeeUserId", employeeUserId);
        model.addAttribute("customers", customerService.getAllCustomers()); // så vi har et overblik over eksisterende kunder når man opretter en lejekontrakt
        model.addAttribute("cars", carService.getAllCarsThatAreAvailable()); // så man ikke kan leje en bil som er udlejet i forvejen
        model.addAttribute("message", message);
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
        try {
            rentalContractService.createRentalContract(customerId, carId, startDate, endDate, price,
                    pickUpLocation, conditionOnDelivery, conditionUponReturn);

            return "redirect:/createRentalContract?employeeUserId=" + employeeUserId + "&message=Rental+contract+has+been+created."; // Redirect til EmployeeUserController
        } catch (Exception e) {
            return "redirect:/createRentalContract?employeeUserId=" + employeeUserId + "&message=Something+went+wrong.+Please+try+agian.";
        }

    }
}