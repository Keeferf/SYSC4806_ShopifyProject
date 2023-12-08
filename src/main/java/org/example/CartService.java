package org.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CartService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Transactional
    public void addToCart(Long customerId, Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        if (product.getProductInventory() > 0) {
            product.setProductInventory(product.getProductInventory() - 1);
            productRepository.save(product);

            Customer customer = customerRepository.findById(customerId)
                    .orElseThrow(() -> new RuntimeException("Customer not found"));
            customer.getCart().add(product);
            customerRepository.save(customer);
        } else {
            throw new RuntimeException("Product is out of stock");
        }
    }
}
