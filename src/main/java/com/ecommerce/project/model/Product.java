package com.ecommerce.project.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;
    public String productName;
    public String image;
    public String description;
    public Double price; // 100
    public Double discount; // 25
    public  Double specialPrice;



    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;
}
