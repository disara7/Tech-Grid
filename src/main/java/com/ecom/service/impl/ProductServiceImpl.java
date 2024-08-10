package com.ecom.service.impl;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import com.ecom.model.Product;
import com.ecom.repository.ProductRepository;
import com.ecom.service.ProductService;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Value("${upload.dir}")
    private String uploadDir;

    @Override
    public Product saveProduct(Product product) {
        return productRepository.save(product);
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public Boolean deleteProduct(Integer id) {
        Product product = productRepository.findById(id).orElse(null);

        if (!ObjectUtils.isEmpty(product)) {
            productRepository.delete(product);
            return true;
        }
        return false;
    }

    @Override
    public Product getProductById(Integer id) {
        return productRepository.findById(id).orElse(null);
    }

    @Override
    public Product updateProduct(Product product, MultipartFile image) {
        Product dbProduct = getProductById(product.getId());

        if (dbProduct == null) {
            return null; // Or handle as needed
        }

        String imageName = image.isEmpty() ? dbProduct.getImage() : image.getOriginalFilename();

        dbProduct.setTitle(product.getTitle());
        dbProduct.setDescription(product.getDescription());
        dbProduct.setCategory(product.getCategory());
        dbProduct.setPrice(product.getPrice());
        dbProduct.setStock(product.getStock());
        dbProduct.setImage(imageName);
        dbProduct.setIsActive(product.getIsActive());
        dbProduct.setDiscount(product.getDiscount());

        // Calculate discount price
        double discount = product.getPrice() * (product.getDiscount() / 100.0);
        double discountPrice = product.getPrice() - discount;
        dbProduct.setDiscountPrice(discountPrice);

        Product updatedProduct = productRepository.save(dbProduct);

        if (updatedProduct != null && !image.isEmpty()) {
            try {
                // Ensure upload directory exists
                File uploadDirectory = new File(uploadDir + File.separator + "product_img");
                if (!uploadDirectory.exists()) {
                    uploadDirectory.mkdirs();
                }

                Path path = Paths.get(uploadDir + File.separator + "product_img" + File.separator + image.getOriginalFilename());
                Files.copy(image.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException e) {
                e.printStackTrace(); // Consider logging the error
            }
        }

        return updatedProduct;
    }

    @Override
    public List<Product> getAllActiveProducts(String category) {
        if (ObjectUtils.isEmpty(category)) {
            return productRepository.findByIsActiveTrue();
        } else {
            return productRepository.findByCategoryAndIsActiveTrue(category);
        }
    }
}
