package org.example;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
@Entity
public class Shop {
    @Id
    @GeneratedValue
    private Long id = null;
    private String shopName = null;
    private String shopDescription = null;
    @OneToMany
    private List<Product> products = null;

    //Enum for the category possibly
    public Shop(String name){
        this.shopName = name;
        this.products = new ArrayList<>();
    }
    public Shop(){}
    public Long getId(){
        return this.id;
    }
    public void setId(Long id){
        this.id = id;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void addProduct(Product product){
        if (product != null){
            products.add(product);
        }
    }

    public void removeProduct(Product product){
        products.remove(product);
    }
}
