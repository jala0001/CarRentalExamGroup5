package com.example.carrentalexam.controllers;

import com.example.carrentalexam.services.CarService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Controller for handling car-related operations within the car rental system.
 * This class provides web endpoints for creating and managing cars.
 */
@Controller
public class CarsController {

    private final CarService carService;

    /**
     * Constructs a new CarsController with a specific instance of CarService.
     * @param carService the service that provides car management functionality, used by this controller to handle business logic.
     */
    public CarsController(CarService carService) {
        this.carService = carService;
    }

    /**
     * Displays a form to create a new car in the system.
     * @param employeeUserId the ID of the employee logged in to provide context for the operation.
     * @param message optional message to be displayed on the form, typically used for feedback on previous operations.
     * @param model the Spring MVC Model object used for passing data to the view.
     * @return the name of the view to display the form for creating a new car.
     */
    @GetMapping("/createNewCar")
    public String createNewCar(@RequestParam int employeeUserId, @RequestParam(required = false) String message, Model model) {
        model.addAttribute("employeeUserId", employeeUserId);
        model.addAttribute("message", message);
        return "home/createNewCar";
    }

    /**
     * Processes the submission of a new car creation form.
     * This method attempts to create a new car based on the provided details.
     * @param frameNumber the frame number of the new car.
     * @param brand the brand of the new car.
     * @param model the model name of the new car.
     * @param registrationNumber the registration number of the new car.
     * @param status the status of the new car (e.g., "Available", "Maintenance").
     * @param employeeUserId the ID of the employee performing the operation, used for tracking and authorization purposes.
     * @return a redirect URL to the create new car page with a message indicating the outcome of the operation.
     */
    @PostMapping("/createNewCarAction")
    public String createNewCarAction(@RequestParam String frameNumber, @RequestParam String brand,
                                     @RequestParam String model, @RequestParam String registrationNumber,
                                     @RequestParam String status, @RequestParam int employeeUserId) {
        try {
            carService.createNewCar(frameNumber, brand, model, registrationNumber, status);
            return "redirect:/createNewCar?employeeUserId=" + employeeUserId + "&message=Car+has+been+created";
        } catch (Exception e) {
            return "redirect:/createNewCar?employeeUserId=" + employeeUserId + "&message=Something+went+wrong.+Please+try+again.";
        }
    }
}
