package org.example;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String redirect() {
        return "redirect:/miniShopify";
    }

    @GetMapping("/miniShopify")
    public String miniShopify() {
        return "miniShopify"; // Name of the Thymeleaf template for the main page
    }
}

