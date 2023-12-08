package org.example;

import jakarta.persistence.*;
import java.util.List;

@Entity
public class Customer {
    @Id
    @GeneratedValue
    private Long id;

    private String name;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Product> cart;

    @ManyToOne
    private Shop shop;

    // Constructors, getters, setters, and other methods

    public Customer() {
        // Default constructor
    }

    public Customer(String name, List<Product> cart, Shop shop) {
        this.name = name;
        this.cart = cart;
        this.shop = shop;
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

    public int checkOut(List<Product> cart){
        int sum = 0;

        for(int i = 0; i < cart.size(); i++){
            sum = sum + cart.get(i).getPrice();
        }
        return sum;
    }

    public Shop getShop() {
        return shop;
    }

    public void setShop(Shop shop) {
        this.shop = shop;
    }
}
