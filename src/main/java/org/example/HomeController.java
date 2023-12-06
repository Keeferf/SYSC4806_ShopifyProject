package org.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
public class HomeController {
    private final ShopRepository shopRepository;

    // Constructor injection of the ShopRepository
    public HomeController(ShopRepository shopRepository) {
        this.shopRepository = shopRepository;
    }

    @GetMapping("/")
    public String redirect() {
        return "redirect:/miniShopify";
    }

    // Show all the shops
    @GetMapping("/miniShopify")
    public String miniShopify(Model model) {
        List<Shop> shops = (List<Shop>) shopRepository.findAll(); // Assuming you have a method to find all shops
        model.addAttribute("shops", shops);
        return "miniShopify"; // This should match the name of the HTML file without the .html extension
    }

    // Show all the products in a shop
    @GetMapping("/shop/{shopId}")
    public String viewShopDetails(@PathVariable Long shopId, Model model) {
        // Find the shop by its ID
        Shop shop = shopRepository.findById(shopId).orElse(null);
        if (shop == null) {
            // Handle the case where the shop is not found
            return "redirect:/miniShopify";
        }

        // Assuming getProducts is a method in your Shop class that returns a list of products
        List<Product> products = shop.getProducts();

        model.addAttribute("shop", shop);
        model.addAttribute("products", products);

        return "shop-details"; // This should match the name of the HTML file for the shop details view
    }
}

