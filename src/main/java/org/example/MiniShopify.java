package org.example;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

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
            Shop shop1 = new Shop("Carl's Appliances");
            shop1.addProduct(product1);
            shop1.addProduct(product2);

            //save to repo
            shopRepository.save(shop1);

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