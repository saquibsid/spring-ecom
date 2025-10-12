package com.ecommerce.project.service;

import com.ecommerce.project.exceptions.ResourceNotFoundException;
import com.ecommerce.project.model.Category;
import com.ecommerce.project.model.Product;
import com.ecommerce.project.payload.ProductDTO;
import com.ecommerce.project.payload.ProductResponse;
import com.ecommerce.project.repositories.CategoryRepository;
import com.ecommerce.project.repositories.ProductRepositary;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    ProductRepositary productRepositary;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    ModelMapper modelMapper;

    @Override
    public ProductDTO addProduct(ProductDTO productDTO, Long categoryId) {
        Product product = modelMapper.map(productDTO, Product.class);
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "categoryId", categoryId));
        product.setCategory(category);
        product.setImage("default.png");
        double specialPrice =  product.getPrice() - (product.getDiscount() * 0.01 * product.getPrice());
        product.setSpecialPrice(specialPrice);
        Product saveProduct = productRepositary.save(product);
        return modelMapper.map(saveProduct, ProductDTO.class);
    }

    @Override
    public ProductResponse getAllProducts(){
       List<Product> products = productRepositary.findAll();
       List<ProductDTO> productDTOS = products.stream()
               .map(product -> modelMapper.map(product, ProductDTO.class))
               .toList();
       ProductResponse productResponse = new ProductResponse();
       productResponse.setContent(productDTOS);
       return productResponse;
    }

    @Override
    public ProductResponse searchByCategory(Long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "categoryId", categoryId));
        List<Product> products = productRepositary.findByCategoryOrderByPriceAsc(category);
        List<ProductDTO> productDTOS = products.stream()
                .map(product -> modelMapper.map(product, ProductDTO.class))
                .toList();
        ProductResponse productResponse = new ProductResponse();
        productResponse.setContent(productDTOS);
        return productResponse;
    }

    @Override
    public ProductResponse searchByKeyword(String keyword) {
        List<Product> products = productRepositary.findByProductNameLikeIgnoreCase('%' + keyword + '%');
        List<ProductDTO> productDTOS = products.stream()
                .map(product -> modelMapper.map(product, ProductDTO.class))
                .toList();
        ProductResponse productResponse = new ProductResponse();
        productResponse.setContent(productDTOS);
        return productResponse;
    }

    @Override
    public ProductDTO updateProduct(Long productId, ProductDTO productDTO) {
        // get existing product from db
        Product product = modelMapper.map(productDTO, Product.class);
        Product productFromDb = productRepositary.findById(productId)
                .orElseThrow(()-> new ResourceNotFoundException("Products", "productId", productId));
        //update product data from user
        productFromDb.setProductName(product.getProductName());
        productFromDb.setPrice(product.getPrice());
        productFromDb.setDiscount(product.getDiscount());
        productFromDb.setSpecialPrice(product.getSpecialPrice());
        // save to database
        Product savedProduct = productRepositary.save(productFromDb);

        // return productDto using modelmapper
        return modelMapper.map(savedProduct, ProductDTO.class);
    }

    @Override
    public ProductDTO deleteProductById(Long productId) {
        Product product = productRepositary.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Products","productId", productId));
        productRepositary.delete(product);
        return modelMapper.map(product, ProductDTO.class);
    }

    @Override
    public ProductDTO uploadProductImage(Long productId, MultipartFile image) throws IOException {
        // get the product details from DB
        Product productFromDb = productRepositary.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product", "productId", productId));

        //Upload image to the server
        // Get the file name of uploaded image
        String path = "images/";
        String fileName = uploadImage(path, image);

        //update the new filename to product
        productFromDb.setImage(fileName);

        // save updated product
        Product updatedProduct = productRepositary.save(productFromDb);

        // return dto after mapping product to DTO
        return  modelMapper.map(updatedProduct, ProductDTO.class);
    }

    private String uploadImage(String path, MultipartFile file) throws IOException {
        // File name of current / original file name
        String originalFilename = file.getOriginalFilename();

        // generate unique filename
        String randomId = UUID.randomUUID().toString();
        String fileName = randomId.concat(originalFilename.substring(originalFilename.lastIndexOf('.')));

        String filePath = path + File.pathSeparator + fileName;

        // check if path exist or not
        File folder = new File(path);
        if(!folder.exists())
            folder.mkdir();

        // upload to server
        Files.copy(file.getInputStream(), Paths.get(filePath));
        return fileName;
    }
}
