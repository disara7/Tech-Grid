package com.ecom.service.impl;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);

    @Autowired
    private ProductRepository productRepository;

    @Value("${upload.dir}")
    private String uploadDir;

    @Override
    public Product saveProduct(Product product) {
        logger.info("Saving new product: {}", product.getTitle());
        return productRepository.save(product);
    }

    @Override
    public List<Product> getAllProducts() {
        logger.info("Fetching all products");
        return productRepository.findAll();
    }

    @Override
    public Boolean deleteProduct(Integer id) {
        logger.info("Attempting to delete product with ID: {}", id);
        Product product = productRepository.findById(id).orElse(null);

        if (!ObjectUtils.isEmpty(product)) {
            productRepository.delete(product);
            logger.info("Product with ID: {} deleted successfully", id);
            return true;
        }
        logger.warn("Product with ID: {} not found", id);
        return false;
    }

    @Override
    public Product getProductById(Integer id) {
        logger.info("Fetching product with ID: {}", id);
        return productRepository.findById(id).orElse(null);
    }

    @Override
public Product updateProduct(Product product, MultipartFile image) {
    Product dbProduct = getProductById(product.getId());

    if (dbProduct == null) {
        return null; // Or handle as needed
    }

    // Set the image name, retaining the old one if no new image is provided
    String imageName = (image == null || image.isEmpty()) ? dbProduct.getImage() : image.getOriginalFilename();

    // Update product details
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

    if (updatedProduct != null && image != null && !image.isEmpty()) {
        try {
            // Ensure the upload directory is not null or empty
            if (uploadDir == null || uploadDir.isEmpty()) {
                logger.error("Upload directory is not set or is empty.");
                throw new IOException("Upload directory is not set.");
            }

            // Ensure upload directory exists
            File uploadDirectory = new File(uploadDir + File.separator + "product_img");
            if (!uploadDirectory.exists()) {
                uploadDirectory.mkdirs();
            }

            Path path = Paths.get(uploadDir + File.separator + "product_img" + File.separator + image.getOriginalFilename());
            Files.copy(image.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
            logger.info("Image uploaded successfully for product ID: {}", product.getId());
        } catch (IOException e) {
            logger.error("Error occurred while uploading image for product ID: {}", product.getId(), e);
        }
    }

    return updatedProduct;
}


    @Override
    public List<Product> getAllActiveProducts(String category) {
        logger.info("Fetching all active products. Category: {}", category);
        if (ObjectUtils.isEmpty(category)) {
            return productRepository.findByIsActiveTrue();
        } else {
            return productRepository.findByCategoryAndIsActiveTrue(category);
        }
    }
}
