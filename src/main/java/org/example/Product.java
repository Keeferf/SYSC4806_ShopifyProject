package org.example;
import jakarta.persistence.*;
import java.util.List;

@Entity
public class Product {
    @Id
    @GeneratedValue
    private Long id = null;
    private String productName;
    private String productDescription;
    private int productInventory;
    private int price;

    // Inside Product class
    @ManyToMany(mappedBy = "cart", fetch = FetchType.LAZY)
    private List<Customer> customers;

    //image of the product

    public Product(String name, String description, int inventory){
        this.productName = name;
        this.productDescription = description;
        this.productInventory = inventory;
    }
    public Product(){}

    public Long getId(){
        return this.id;
    }
    public void setId(Long id){
        this.id = id;
    }

    @Override
    public String toString(){
        return "Product name: " + productName + "\n Description: " + productDescription + "\n Inventory: " + productInventory;
    }

    public String getName(){
        return this.productName;
    }

    public void setName(String name){
        this.productName = name;
    }

    public String getProductDescription(){
        return this.productDescription;
    }

    public void setProductDescription(String description){
        this.productDescription = description;
    }

    public int getProductInventory(){
        return this.productInventory;
    }

    public void setProductInventory(int productInventory){
        this.productInventory = productInventory;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
