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
    public CommandLineRunner demo(ShopRepository shopRepository, ProductRepository productRepository) {
        return (args) -> {
            //create and save some products
            Product product1 = new Product("microwave", "kitchen appliance", 3);
            Product product2 = new Product("knife", "to cut things", 6);

            //create a shop and add products
            Set<Category> categories = new HashSet<>();
            categories.add(Category.APPLIANCES);
            Shop shop1 = new Shop("Carl's Appliances","kitchen stuff", categories);
            shop1.addProduct(product1);
            shop1.addProduct(product2);

            //save to repo
            shopRepository.save(shop1);

            //check id
            log.info("New shop saved with ID: {}", shop1.getId());
            log.info("New product1 saved with ID: {}", product1.getId());

            // fetch individual buddy by ID
            Product foundProduct = productRepository.findById(product1.getId()).get();
            log.info("product found with findById():");
            log.info("--------------------------------");
            if (foundProduct != null) {
                log.info(foundProduct.toString());
            }
            log.info("");

        };
    }
}