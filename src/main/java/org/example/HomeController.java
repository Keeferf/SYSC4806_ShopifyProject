package org.example;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

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

    @GetMapping("/miniShopify")
    public String miniShopify(Model model) {
        List<Shop> shops = (List<Shop>) shopRepository.findAll(); // Assuming you have a method to find all shops
        model.addAttribute("shops", shops);
        return "miniShopify"; // This should match the name of the HTML file without the .html extension
    }
}

