package org.example;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Customer {
    @Id
    @GeneratedValue
    private Long id;

    private String name;

    @ManyToMany
    @JoinTable(name = "customer_cart",
            joinColumns = @JoinColumn(name = "customer_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id"))
    private List<Product> cart;

    public Customer(String name) {
        this.name = name;
        this.cart = new ArrayList<>();
    }

    public Customer() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Product> getCart() {
        return cart;
    }

    public void setCart(List<Product> cart) {
        this.cart = cart;
    }

    public void addToCart(Product product) {
        cart.add(product);
    }

    public void removeFromCart(Product product) {
        cart.remove(product);
    }
}
