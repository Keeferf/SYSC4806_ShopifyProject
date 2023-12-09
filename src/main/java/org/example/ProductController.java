package org.example;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class ProductController {

    private final ShopRepository shopRepository;
    private final ProductRepository productRepository;

    public ProductController(ShopRepository shopRepository, ProductRepository productRepository) {
        this.shopRepository = shopRepository;
        this.productRepository = productRepository;
    }

    @GetMapping("/shop/{shopId}/create-product")
    public String showCreateProductForm(@PathVariable Long shopId, Model model) {
        model.addAttribute("shopId", shopId); // Pass the shopId to the model so that it can be included in the form action
        model.addAttribute("product", new Product()); // You also need to add a new Product object for form binding
        return "create-product"; // This is the name of the Thymeleaf template for creating a product
    }

    @PostMapping("/shop/{shopId}/create-product")
    public String createProduct(@PathVariable Long shopId,
                                @ModelAttribute Product product,
                                Model model,
                                Authentication authentication) {

        // Checks to see if user is authenticated
        //if (authentication != null && authentication.isAuthenticated()) {
            Shop shop = shopRepository.findById(shopId)
                    .orElseThrow(() -> new IllegalArgumentException("Invalid Shop ID: " + shopId));

            shop.addProduct(product);
            productRepository.save(product);
            shopRepository.save(shop);

            return "redirect:/shop/" + shopId; // Redirect to the specific shop's details page
        }/* else {
            return "redirect:/login"; // Redirect to log in, not authenticated yet
        }
    }*/
}