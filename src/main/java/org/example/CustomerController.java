package org.example;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class CustomerController {

    // Your existing autowired CustomerRepository
    private final CustomerRepository customerRepository;

    public CustomerController(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @GetMapping("/cart")
    public String viewCart(HttpSession session, Model model) {
        List<Product> cart = (List<Product>) session.getAttribute("cart");
        if (cart == null) {
            cart = new ArrayList<>();
            // Add an empty cart to the session if one doesn't exist
            session.setAttribute("cart", cart);
        }

        int total = cart.stream().mapToInt(Product::getPrice).sum(); // Assuming you have a getPrice method in Product
        model.addAttribute("cartItems", cart);
        model.addAttribute("totalPrice", total);

        return "cart";
    }
}

