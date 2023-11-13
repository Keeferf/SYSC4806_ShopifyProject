package org.example;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;
import org.springframework.web.server.ResponseStatusException;

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
                             Model model) {
        Set<Category> categorySet = new HashSet<>();
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

        Shop newShop = new Shop(shopName, shopDescription, categorySet);
        shopRepository.save(newShop);
        model.addAttribute("shop", newShop);
        return "redirect:/miniShopify"; // Redirect to the main page
    }

    @GetMapping("/shop/{id}")
    public String viewShop(@PathVariable Long id, Model model) {
        Shop shop = shopRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Shop not found"));
        model.addAttribute("shop", shop);
        return "shop-details"; // The name of the Thymeleaf template for viewing shop details
    }
}