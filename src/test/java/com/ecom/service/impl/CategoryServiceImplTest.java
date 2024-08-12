package com.ecom.service.impl;

import com.ecom.model.Category;
import com.ecom.repository.CategoryRepository;
import com.ecom.service.CategoryService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class CategoryServiceImplTest {

    @InjectMocks
    private CategoryServiceImpl categoryService;

    @Mock
    private CategoryRepository categoryRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSaveCategory() {
        Category category = new Category();
        when(categoryRepository.save(any(Category.class))).thenReturn(category);

        Category savedCategory = categoryService.saveCategory(category);

        assertNotNull(savedCategory);
        verify(categoryRepository, times(1)).save(category);
    }

    @Test
    public void testGetAllCategory() {
        Category category1 = new Category();
        Category category2 = new Category();
        List<Category> categories = new ArrayList<>();
        categories.add(category1);
        categories.add(category2);

        when(categoryRepository.findAll()).thenReturn(categories);

        List<Category> result = categoryService.getAllCategory();

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(categoryRepository, times(1)).findAll();
    }

    @Test
    public void testExistCategory() {
        when(categoryRepository.existsByName("testCategory")).thenReturn(true);

        Boolean exists = categoryService.existCategory("testCategory");

        assertTrue(exists);
        verify(categoryRepository, times(1)).existsByName("testCategory");
    }

    // @Test
    // public void testDeleteCategory() {
    //     Category category = new Category();
    //     when(categoryRepository.findById(1)).thenReturn(Optional.of(category));
    //     doNothing().when(categoryRepository).delete(any(Category.class));

    //     Boolean result = categoryService.deleteCategory(1);

    //     assertTrue(result);
    //     verify(categoryRepository, times(1)).findById(1);
    //     verify(categoryRepository, times(1)).delete(category);
    // }

    // @Test
    // public void testDeleteCategoryNotFound() {
    //     when(categoryRepository.findById(1)).thenReturn(Optional.empty());

    //     Boolean result = categoryService.deleteCategory(1);

    //     assertFalse(result);
    //     verify(categoryRepository, times(1)).findById(1);
    //     verify(categoryRepository, never()).delete(any(Category.class));
    // }

    @Test
    public void testGetCategoryById() {
        Category category = new Category();
        when(categoryRepository.findById(1)).thenReturn(Optional.of(category));

        Category result = categoryService.getCategoryById(1);

        assertNotNull(result);
        verify(categoryRepository, times(1)).findById(1);
    }

    @Test
    public void testGetCategoryByIdNotFound() {
        when(categoryRepository.findById(1)).thenReturn(Optional.empty());

        Category result = categoryService.getCategoryById(1);

        assertNull(result);
        verify(categoryRepository, times(1)).findById(1);
    }

    @Test
    public void testGetAllActiveCategory() {
        Category activeCategory = new Category();
        activeCategory.setIsActive(true);
        List<Category> activeCategories = new ArrayList<>();
        activeCategories.add(activeCategory);

        when(categoryRepository.findByIsActiveTrue()).thenReturn(activeCategories);

        List<Category> result = categoryService.getAllActiveCategory();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertTrue(result.get(0).getIsActive());
        verify(categoryRepository, times(1)).findByIsActiveTrue();
    }
}
