package org.example;
import jakarta.persistence.*;
@Entity
public class Product {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String description;
    private int inventory;

    @ManyToOne
    @JoinColumn(name = "shop_id") // This is the foreign key column in the Product table.
    private Shop shop;

    //image of the product

    public Product(String name, String description, int inventory){
        this.name = name;
        this.description = description;
        this.inventory = inventory;
    }
    public Product(){}

    public Long getId(){
        return this.id;
    }
    public void setId(Long id){
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getDescription() {
        return description;
    }
    public int getInventory() {
        return inventory;
    }
    public Long getShopId() {
        return shop != null ? shop.getId() : null;
    }
    public Shop getShop() {
        return shop;
    }

    public void setShop(Shop shop) {
        this.shop = shop;
    }
    @Override
    public String toString(){
        return "Product name: " + name + "\n Description: " + description + "\n Inventory: " + inventory;
    }
}
