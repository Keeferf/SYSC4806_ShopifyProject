package org.example;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.HashSet;
import java.util.Set;

@SpringBootApplication
public class MiniShopify {
    private static final Logger log = LoggerFactory.getLogger(MiniShopify.class);
    public static void main(String[] args) {
        SpringApplication.run(MiniShopify.class, args);
    }

    @Bean
    public CommandLineRunner demo(ShopRepository shopRepository, ProductRepository productRepository, CustomerRepository customerRepository) {
        return (args) -> {
            //create and save some products
            Product product1 = new Product("microwave", "kitchen appliance", 3);
            Product product2 = new Product("knife", "to cut things", 6);
            Product product3 = new Product("Air Forces 2", "Mint Condition, 2oz", 1);
            Product product4 = new Product("Diamond Necklace", "$1300, pure quality, 100% guarantee", 5);
            Product product5 = new Product("Wooden Desk", "Oak hardwood", 3);
            Product product6 = new Product("Lenovo Laptop", "$1250, 1060 GPU, limited sale", 2);
            Product product7 = new Product("Navy Blue Jeans", "Skinny fit, size 36 waist", 10);

            //create a shop and add products
            Set<Category> categories = new HashSet<>();
            categories.add(Category.APPLIANCES);
            Shop shop1 = new Shop("Carl's Appliances","Everything Kitchen Related!", categories, "https://images.unsplash.com/photo-1556911220-bff31c812dba?q=80&w=3736&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D");
            shop1.addProduct(product1);
            shop1.addProduct(product2);

            Set<Category> categories2 = new HashSet<>();
            categories2.add(Category.CLOTHES);
            Shop shop2 = new Shop("Nike","Swoosh!", categories, "https://images.unsplash.com/photo-1542291026-7eec264c27ff?q=80&w=3870&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D");
            shop2.addProduct(product3);

            Set<Category> categories3 = new HashSet<>();
            categories3.add(Category.JEWELRY);
            Shop shop3 = new Shop("Tiffany's","A diamond is your best friend!", categories, "https://images.unsplash.com/photo-1524058485864-4f7a057f53a8?q=80&w=3873&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D");
            shop3.addProduct(product4);

            Set<Category> categories4 = new HashSet<>();
            categories4.add(Category.DECOR);
            Shop shop4 = new Shop("Harold's","Design and interior decorate on a budget!", categories, "https://images.unsplash.com/photo-1572048572872-2394404cf1f3?q=80&w=3871&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D");
            shop4.addProduct(product5);

            Set<Category> categories5 = new HashSet<>();
            categories5.add(Category.ELECTRONICS);
            Shop shop5 = new Shop("BestBuy","Everything you need for school and more!", categories, "https://images.unsplash.com/photo-1498049794561-7780e7231661?q=80&w=3870&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D");
            shop5.addProduct(product6);

            Set<Category> categories6 = new HashSet<>();
            categories6.add(Category.CLOTHES);
            Shop shop6 = new Shop("Old Navy","Sandals. Shirt. Sweaters. Something for everyone! ", categories, "https://images.unsplash.com/photo-1565084888279-aca607ecce0c?q=80&w=3870&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D");
            shop6.addProduct(product7);

            //save to repo

            shopRepository.save(shop1);
            shopRepository.save(shop2);
            shopRepository.save(shop3);
            shopRepository.save(shop4);
            shopRepository.save(shop5);
            shopRepository.save(shop6);

            // fetch individual buddy by ID
            Product foundProduct = productRepository.findById(product1.getId()).get();
            log.info("product found with findById():");
            log.info("--------------------------------");
            if (foundProduct != null) {
                log.info(foundProduct.toString());
            }
            log.info("");

            // Create and save a customer to repo
            Customer customer = new Customer("John Doe");

            // Add products to the customer's cart
            customer.addToCart(product1);
            customer.addToCart(product3);

            // Save the updated customer to repo
            customerRepository.save(customer);

            // Fetch the customer by ID
            Customer foundCustomer = customerRepository.findById(customer.getId()).orElse(null);

            if (foundCustomer != null) {
                log.info("Customer found with ID {}: {}", foundCustomer.getId(), foundCustomer.getName());

                // Access the cart to force loading before logging
                foundCustomer.getCart().forEach(product -> log.info("Product in the cart: {}", product));

                log.info("HTML template: customer");
            }
        };
    }
}