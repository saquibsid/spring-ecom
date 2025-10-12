package com.ecommerce.project.service;


import com.ecommerce.project.payload.ProductDTO;
import com.ecommerce.project.payload.ProductResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ProductService {
    ProductDTO addProduct(ProductDTO productDTO, Long categoryId);

    ProductResponse getAllProducts();

    ProductResponse searchByCategory(Long categoryId);

    ProductResponse searchByKeyword(String keyword);

    ProductDTO updateProduct(Long productId, ProductDTO productDTO);

    ProductDTO deleteProductById(Long productId);

    ProductDTO uploadProductImage(Long productId, MultipartFile image) throws IOException;
}
