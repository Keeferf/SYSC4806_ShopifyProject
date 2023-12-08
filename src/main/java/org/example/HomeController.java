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
import java.util.Optional;
import java.util.Set;

@Controller
public class HomeController {
    private ShopRepository shopRepository;
    private ProductRepository productRepository;
    private CustomerRepository customerRepository;

    // Constructor injection of the ShopRepository
    public HomeController(ShopRepository shopRepository, ProductRepository productRepository, CustomerRepository customerRepository) {
        this.shopRepository = shopRepository;
        this.productRepository = productRepository;
        this.customerRepository = customerRepository;
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

        // Find the products
        List<Product> products = shop.getProducts();
        List<Customer> customers = shop.getCustomers();
        // Assuming getProducts is a method in your Shop class that returns a list of products


        model.addAttribute("shop", shop);
        model.addAttribute("products", products);
        model.addAttribute("customers", customers);

        return "shop-details"; // This should match the name of the HTML file for the shop details view
    }

    @PostMapping("/create-product")
    public String createShop(@RequestParam Long shopId,
                             @RequestParam String productName,
                             @RequestParam String productDescription,
                             @RequestParam int inventory,
                             @RequestParam int price,
                             Model model) {

        Product newProduct = new Product(productName, productDescription, inventory, price);
        Shop shop = shopRepository.findById(shopId).orElse(null);

        if (shop != null) {
            shop.addProduct(newProduct);
            productRepository.save(newProduct);
            shopRepository.save(shop);
            model.addAttribute("product", newProduct);
            return "redirect:/shop/" + shopId; // Redirect to the specific shop
        } else {
            return null;
        }
    }

    @GetMapping("/search")
    public String search(@RequestParam String query, Model model) {
        // Perform the search using the query
        List<Shop> searchResults = shopRepository.findByShopNameContainingIgnoreCase(query);
        model.addAttribute("shops", searchResults);
        return "shop-grid"; // Return the view that displays the search results
    }

}

