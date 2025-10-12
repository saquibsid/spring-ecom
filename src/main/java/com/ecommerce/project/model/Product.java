package com.ecommerce.project.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
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
    @NotBlank
    @Size(min = 3, message = "Product name should have atleast 3 characters")
    public String productName;
    public String image;
    @NotBlank
    @Size(min = 6, message = "Product description should have atleast 6 characters")
    public String description;
    public Double price; // 100
    public Double discount; // 25
    public  Double specialPrice;



    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;
}
