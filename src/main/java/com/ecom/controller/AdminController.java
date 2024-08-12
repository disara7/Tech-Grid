package com.ecom.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.ecom.model.Category;
import com.ecom.model.Product;
import com.ecom.service.CategoryService;
import com.ecom.service.ProductService;

import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private static final Logger log = LoggerFactory.getLogger(AdminController.class);

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ProductService productService;

    @Value("${file.upload.dir}")
    private String fileUploadDir;

    @GetMapping("/")
    public String index() {
        return "admin/index";
    }

    @GetMapping("/loadAddProduct")
    public String loadAddProduct(Model model) {
        List<Category> categories = categoryService.getAllCategory();
        model.addAttribute("categories", categories);
        return "admin/add_product";
    }

    @GetMapping("/category")
    public String category(Model model) {
        model.addAttribute("categories", categoryService.getAllCategory());
        return "admin/category";
    }

    @PostMapping("/saveCategory")
    public String saveCategory(@ModelAttribute Category category, @RequestParam("file") MultipartFile file,
                               HttpSession session) {
        try {
            String imageName = file != null && !file.isEmpty() ? file.getOriginalFilename() : "default.jpg";
            category.setImageName(imageName);

            Boolean existCategory = categoryService.existCategory(category.getName());

            if (existCategory) {
                session.setAttribute("errorMsg", "Category Name already exists");
            } else {
                Category savedCategory = categoryService.saveCategory(category);

                if (ObjectUtils.isEmpty(savedCategory)) {
                    session.setAttribute("errorMsg", "Not saved! Internal server error");
                } else {
                    saveFile(file, "category_img");
                    session.setAttribute("succMsg", "Saved successfully");
                }
            }
        } catch (IOException e) {
            session.setAttribute("errorMsg", "File upload failed: " + e.getMessage());
            log.error("File upload failed", e);
        }

        return "redirect:/admin/category";
    }

    @GetMapping("/deleteCategory/{id}")
    public String deleteCategory(@PathVariable int id, HttpSession session) {
        Boolean deleteCategory = categoryService.deleteCategory(id);

        if (deleteCategory) {
            session.setAttribute("succMsg", "Category deleted successfully");
        } else {
            session.setAttribute("errorMsg", "Something went wrong on the server");
        }

        return "redirect:/admin/category";
    }

    @GetMapping("/loadEditCategory/{id}")
    public String loadEditCategory(@PathVariable int id, Model model) {
        model.addAttribute("category", categoryService.getCategoryById(id));
        return "admin/edit_category";
    }

    @PostMapping("/updateCategory")
    public String updateCategory(@ModelAttribute Category category, @RequestParam("file") MultipartFile file,
                                 HttpSession session) {
        try {
            Category oldCategory = categoryService.getCategoryById(category.getId());
            String imageName = file.isEmpty() ? oldCategory.getImageName() : file.getOriginalFilename();

            oldCategory.setName(category.getName());
            oldCategory.setIsActive(category.getIsActive());
            oldCategory.setImageName(imageName);

            Category updatedCategory = categoryService.saveCategory(oldCategory);

            if (ObjectUtils.isEmpty(updatedCategory)) {
                session.setAttribute("errorMsg", "Something went wrong on the server");
            } else {
                if (!file.isEmpty()) {
                    saveFile(file, "category_img");
                }
                session.setAttribute("succMsg", "Category updated successfully");
            }
        } catch (IOException e) {
            session.setAttribute("errorMsg", "File upload failed: " + e.getMessage());
            log.error("File upload failed", e);
        }

        return "redirect:/admin/loadEditCategory/" + category.getId();
    }

    @PostMapping("/saveProduct/save")
    public String saveProduct(Product product,
                              @RequestParam("file") MultipartFile image,
                              HttpSession session) {
        try {
            String imageName = image.isEmpty() ? "default.jpg" : image.getOriginalFilename();
            product.setImage(imageName);
            product.setDiscount(0); // Default value for discount
            product.setDiscountPrice(product.getPrice() - (product.getPrice() * product.getDiscount() / 100));

            Product savedProduct = productService.saveProduct(product);

            if (!ObjectUtils.isEmpty(savedProduct)) {
                if (!image.isEmpty()) {
                    saveFile(image, "product_img");
                }
                session.setAttribute("succMsg", "Product saved successfully");
            } else {
                session.setAttribute("errorMsg", "Something went wrong on the server");
            }
        } catch (IOException e) {
            session.setAttribute("errorMsg", "File upload failed: " + e.getMessage());
            log.error("File upload failed", e);
        }

        return "redirect:/admin/loadAddProduct";
    }

    @GetMapping("/products")
    public String loadViewProduct(Model model) {
        model.addAttribute("products", productService.getAllProducts());
        return "admin/products";
    }

    @GetMapping("/deleteProduct/{id}")
    public String deleteProduct(@PathVariable int id, HttpSession session) {
        Boolean deleteProduct = productService.deleteProduct(id);
        if (deleteProduct) {
            session.setAttribute("succMsg", "Product deleted successfully");
        } else {
            session.setAttribute("errorMsg", "Something went wrong on the server");
        }
        return "redirect:/admin/products";
    }

    @GetMapping("/editProduct/{id}")
    public String editProduct(@PathVariable int id, Model model) {
        model.addAttribute("product", productService.getProductById(id));
        model.addAttribute("categories", categoryService.getAllCategory());
        return "admin/edit_product";
    }

    @PostMapping("/updateProduct")
    public String updateProduct(@ModelAttribute Product product, @RequestParam("file") MultipartFile image,
                                HttpSession session) {
        try {
            if (product.getDiscount() < 0 || product.getDiscount() > 100) {
                session.setAttribute("errorMsg", "Invalid discount value");
            } else {
                Product updatedProduct = productService.updateProduct(product, image);
                if (!ObjectUtils.isEmpty(updatedProduct)) {
                    session.setAttribute("succMsg", "Product updated successfully");
                } else {
                    session.setAttribute("errorMsg", "Something went wrong on the server");
                }
            }
        } catch (IOException e) {
            session.setAttribute("errorMsg", "File upload failed: " + e.getMessage());
            log.error("File upload failed", e);
        }

        return "redirect:/admin/editProduct/" + product.getId();
    }

    private void saveFile(MultipartFile file, String folderName) throws IOException {
        File saveFile = new File(fileUploadDir + folderName);
        if (!saveFile.exists()) {
            saveFile.mkdirs();
        }
        Path path = Paths.get(saveFile.getAbsolutePath() + File.separator + file.getOriginalFilename());
        Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
    }
}
