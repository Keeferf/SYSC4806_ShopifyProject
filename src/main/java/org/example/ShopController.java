package org.example;

import org.springframework.security.core.Authentication;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
public class ShopController {

    private final ShopRepository shopRepository;
    private final ProductRepository productRepository;

    @Autowired
    private CartService cartService;

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
        //if (authentication != null && authentication.isAuthenticated()) {
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
            Customer newCustomer = new Customer(); // Create a new customer
            newCustomer.setShop(newShop); // Associate the customer with the shop
            newShop.getCustomers().add(newCustomer); // Add the customer to the shop's list of customers

            shopRepository.save(newShop);
            model.addAttribute("shop", newShop);

            return "redirect:/miniShopify"; // Redirect to the main page
        }/* else {
            // Redirect to the login page if the user is not authenticated
            return "redirect:/login";
        }
    }*/

    @PostMapping("/shop/{shopId}/add-to-cart")
    public String addToCart(@PathVariable Long shopId, @RequestParam Long productId, HttpSession session, RedirectAttributes redirectAttributes) {
        // Retrieve the cart from the session or create a new one if it doesn't exist
        List<Product> cart = (List<Product>) session.getAttribute("cart");
        if (cart == null) {
            cart = new ArrayList<>();
            session.setAttribute("cart", cart);
        }

        // Find the product and add it to the cart
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        if (product.getProductInventory() > 0) {
            cart.add(product);
            product.setProductInventory(product.getProductInventory() - 1); // Decrement inventory
            productRepository.save(product); // Save the updated product
        } else {
            redirectAttributes.addFlashAttribute("error", "Product is out of stock");
            return "redirect:/shop/" + shopId;
        }

        redirectAttributes.addFlashAttribute("success", "Product added to cart successfully!");
        return "redirect:/shop/" + shopId;
    }


}