package org.example;
import jakarta.persistence.*;
@Entity
public class Product {
    @Id
    @GeneratedValue
    private Long id = null;
    private String productName;
    private String productDescription;
    private int productInventory;

    //image of the product

    public Product(String name, String description, int inventory){
        this.productName = name;
        this.productDescription = description;
        this.productInventory = inventory;
    }
    public Product(){}
}
