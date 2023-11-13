package org.example;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ProductController {
    private final ProductRepository productRepository;
    private final ShopRepository shopRepository; // You need the ShopRepository to associate a product with a shop

    // Constructor with repository injections
    public ProductController(ProductRepository productRepository, ShopRepository shopRepository) {
        this.productRepository = productRepository;
        this.shopRepository = shopRepository;
    }
    @GetMapping("/shop/{shopId}/create-product")
    public String showCreateProductForm(@PathVariable Long shopId, Model model) {
        model.addAttribute("product", new Product());
        model.addAttribute("shopId", shopId); // Add the shopId to the model so you can use it in the form
        return "create-product"; // This is a new Thymeleaf template for the product form
    }

    @PostMapping("/shop/{shopId}/save-product")
    public String saveProduct(@PathVariable Long shopId, @ModelAttribute Product product, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "create-product";
        }
        // Find the shop and set it to the product before saving
        Shop shop = shopRepository.findById(shopId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid shop Id:" + shopId));
        product.setShop(shop); // Set the shop to the product
        shop.addProduct(product);
        shopRepository.save(shop);
        productRepository.save(product);
        return "redirect:/shop/" + shopId; // Redirect to the shop's details page
    }
}
