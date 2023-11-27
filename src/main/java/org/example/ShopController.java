package org.example;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
public class ShopController {

    private final ShopRepository shopRepository;
    private final ProductRepository productRepository;

    // Constructor with repository injections
    public ShopController(ShopRepository shopRepository, ProductRepository productRepository) {
        this.shopRepository = shopRepository;
        this.productRepository = productRepository;
    }

    @GetMapping("/create-shop")
    public String showCreateShopForm(Model model) {
        return "create-shop"; // Return the name of the HTML file containing the form
    }

    @PostMapping("/create-shop")
    public String createShop(@RequestParam String shopName,
                             @RequestParam String shopDescription,
                             @RequestParam List<String> categories,
                             @RequestParam String imageURL,
                             Model model,
                             Authentication authentication) {

        Set<Category> categorySet = new HashSet<>();
        // Check to see if user is authenticated
        if (authentication != null && authentication.isAuthenticated()) {
            if (categories != null) {
                for (String categoryStr : categories) {
                    try {
                        Category category = Category.valueOf(categoryStr.toUpperCase());
                        categorySet.add(category);
                    } catch (IllegalArgumentException e) {
                        // Handle the case where the provided category does not match the enum values
                        // You could log this error and/or send a message back to the user
                    }
                }
            }

            Shop newShop = new Shop(shopName, shopDescription, categorySet, imageURL);
            shopRepository.save(newShop);
            model.addAttribute("shop", newShop);

            return "redirect:/miniShopify"; // Redirect to the main page
        } else {
            // Redirect to the login page if the user is not authenticated
            return "redirect:/login";
        }

    }
}