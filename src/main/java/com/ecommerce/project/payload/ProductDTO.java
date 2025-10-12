package com.ecommerce.project.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {
    private Long productId;
    public String productName;
    public String image;
    public String description;
    public Double price;
    public Double discount;
    public  Double specialPrice;
}
