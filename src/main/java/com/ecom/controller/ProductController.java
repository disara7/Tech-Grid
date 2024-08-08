package com.ecom.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.io.IOException;

@Controller
public class ProductController {

    @Value("${upload.dir}")
    private String uploadDir;

    @PostMapping("/admin/saveProduct")
    public String saveProduct(@RequestParam("title") String title,
                              @RequestParam("description") String description,
                              @RequestParam("category") String category,
                              @RequestParam("price") double price,
                              @RequestParam("isActive") boolean isActive,
                              @RequestParam("stock") int stock,
                              @RequestParam("file") MultipartFile file,
                              RedirectAttributes redirectAttributes) {
        // Handle the other form fields

        // Save the uploaded file
        if (!file.isEmpty()) {
            try {
                File saveFile = new File(uploadDir + file.getOriginalFilename());
                file.transferTo(saveFile);
                redirectAttributes.addFlashAttribute("succMsg", "Product saved successfully.");
            } catch (IOException e) {
                e.printStackTrace();
                redirectAttributes.addFlashAttribute("errorMsg", "Failed to save the product.");
            }
        }

        return "redirect:/admin/addProduct";
    }
}
