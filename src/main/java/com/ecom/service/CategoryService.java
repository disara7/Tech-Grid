package com.ecom.service;

import java.util.List;

import com.ecom.model.Category;

public interface CategoryService {

    Category saveCategory(Category category);

    Boolean existCategory(String name);

    List<Category> getAllCategory();

    Boolean deleteCategory(int id);

    Category getCategoryById(int id);

    List<Category> getAllActiveCategory();
}
