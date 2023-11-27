package org.example;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/customer")
public class CustomerController {

    // Your existing autowired CustomerRepository
    private final CustomerRepository customerRepository;

    public CustomerController(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @GetMapping("/{customerId}")
    public String showCustomerDetails(@PathVariable Long customerId, Model model) {
        // Fetch the customer by ID
        Customer foundCustomer = customerRepository.findById(customerId).orElse(null);

        if (foundCustomer != null) {
            // Add the customer to the model
            model.addAttribute("customer", foundCustomer);

            // Return the HTML template name
            return "customer-details";
        } else {
            // Handle the case when the customer is not found
            return "customer-not-found";
        }
    }
}

