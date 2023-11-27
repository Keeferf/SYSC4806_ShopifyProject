package org.example;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
public class Shop {
    @Id
    @GeneratedValue
    private Long id = null;
    private String shopDescription;
    private String shopName = null;

    private String imageURL;
    @ElementCollection(targetClass = Category.class, fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "shop_category")
    @Column(name = "category")
    private Set<Category> categories = null;
    @OneToMany(cascade=CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Product> products = null;

    //Enum for the category possibly
    public Shop(String name, String description, Set<Category> categories, String imageURL){
        this.shopName = name;
        this.shopDescription = description;
        this.imageURL = imageURL;
        if (categories == null) {
            this.categories = new HashSet<>(); // Initialize with an empty HashSet if categories are null
        } else {
            this.categories = categories; // Use the provided categories
        }
        this.products = new ArrayList<>();
    }
    public String getShopName() {
        return shopName;
    }
    public String getShopDescription() {
        return shopDescription;
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

    public Set<Category> getCategories() {
        return categories;
    }

    public void setCategories(Set<Category> categories) {
        this.categories = categories;
    }

    public void removeProduct(Product product){
        products.remove(product);
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }
}
