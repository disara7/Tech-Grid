package com.ecom.service;

import com.ecom.model.Category;
import com.ecom.repository.CategoryRepository;
import com.ecom.service.impl.CategoryServiceImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

public class CategoryServiceTest {

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
        category.setName("Electronics");
        when(categoryRepository.save(any(Category.class))).thenReturn(category);

        Category savedCategory = categoryService.saveCategory(category);

        assertNotNull(savedCategory);
        assertEquals("Electronics", savedCategory.getName());
    }

    @Test
    public void testExistCategory() {
        when(categoryRepository.existsByName("Electronics")).thenReturn(true);

        Boolean exists = categoryService.existCategory("Electronics");

        assertTrue(exists);
    }

    @Test
    public void testExistCategoryNotFound() {
        when(categoryRepository.existsByName("NonExistentCategory")).thenReturn(false);

        Boolean exists = categoryService.existCategory("NonExistentCategory");

        assertFalse(exists);
    }

    @Test
    public void testGetAllCategory() {
        Category cat1 = new Category();
        Category cat2 = new Category();
        List<Category> categories = Arrays.asList(cat1, cat2);

        when(categoryRepository.findAll()).thenReturn(categories);

        List<Category> categoryList = categoryService.getAllCategory();

        assertNotNull(categoryList);
        assertEquals(2, categoryList.size());
    }

    @Test
    public void testGetAllCategoryEmpty() {
        when(categoryRepository.findAll()).thenReturn(Collections.emptyList());

        List<Category> categoryList = categoryService.getAllCategory();

        assertNotNull(categoryList);
        assertTrue(categoryList.isEmpty());
    }

    @Test
    public void testDeleteCategory() {
        doNothing().when(categoryRepository).deleteById(anyInt());

        Boolean result = categoryService.deleteCategory(1);

        verify(categoryRepository, times(1)).deleteById(1);
        assertTrue(result);
    }

    @Test
    public void testDeleteCategoryNotFound() {
        doThrow(new RuntimeException("Category not found")).when(categoryRepository).deleteById(anyInt());

        Boolean result = categoryService.deleteCategory(1);

        assertFalse(result);
    }

    @Test
    public void testGetCategoryById() {
        Category category = new Category();
        category.setId(1);
        when(categoryRepository.findById(1)).thenReturn(Optional.of(category));

        Category foundCategory = categoryService.getCategoryById(1);

        assertNotNull(foundCategory);
        assertEquals(1, foundCategory.getId());
    }

    @Test
    public void testGetCategoryByIdNotFound() {
        when(categoryRepository.findById(1)).thenReturn(Optional.empty());

        Category foundCategory = categoryService.getCategoryById(1);

        assertNull(foundCategory);
    }

    @Test
    public void testGetAllActiveCategory() {
        Category activeCat = new Category();
        activeCat.setIsActive(true);
        Category inactiveCat = new Category();
        inactiveCat.setIsActive(false);
        List<Category> categories = Arrays.asList(activeCat, inactiveCat);

        when(categoryRepository.findByIsActiveTrue()).thenReturn(Collections.singletonList(activeCat));

        List<Category> activeCategoryList = categoryService.getAllActiveCategory();

        assertNotNull(activeCategoryList);
        assertEquals(1, activeCategoryList.size());
        assertTrue(activeCategoryList.get(0).getIsActive());
    }
}
