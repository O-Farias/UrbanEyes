package com.urbaneyes.service;

import com.urbaneyes.model.Category;
import com.urbaneyes.repository.CategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CategoryServiceTest {

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private CategoryService categoryService;

    private Category category;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        category = new Category();
        category.setId(1L);
        category.setName("Infrastructure");
    }

    @Test
    void shouldGetAllCategories() {
        when(categoryRepository.findAll()).thenReturn(List.of(category));

        List<Category> categories = categoryService.getAllCategories();

        assertNotNull(categories);
        assertEquals(1, categories.size());
        assertEquals("Infrastructure", categories.get(0).getName());
        verify(categoryRepository, times(1)).findAll();
    }

    @Test
    void shouldGetCategoryById() {
        when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));

        Optional<Category> fetchedCategory = categoryService.getCategoryById(1L);

        assertTrue(fetchedCategory.isPresent());
        assertEquals("Infrastructure", fetchedCategory.get().getName());
        verify(categoryRepository, times(1)).findById(1L);
    }

    @Test
    void shouldReturnEmptyWhenCategoryNotFound() {
        when(categoryRepository.findById(2L)).thenReturn(Optional.empty());

        Optional<Category> fetchedCategory = categoryService.getCategoryById(2L);

        assertFalse(fetchedCategory.isPresent());
        verify(categoryRepository, times(1)).findById(2L);
    }

    @Test
    void shouldCreateCategory() {
        when(categoryRepository.save(category)).thenReturn(category);

        Category savedCategory = categoryService.createCategory(category);

        assertNotNull(savedCategory);
        assertEquals("Infrastructure", savedCategory.getName());
        verify(categoryRepository, times(1)).save(category);
    }

    @Test
    void shouldUpdateCategory() {
        Category updatedCategory = new Category();
        updatedCategory.setName("Environment");

        when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));
        when(categoryRepository.save(category)).thenReturn(category);

        Category result = categoryService.updateCategory(1L, updatedCategory);

        assertNotNull(result);
        assertEquals("Environment", result.getName());
        verify(categoryRepository, times(1)).findById(1L);
        verify(categoryRepository, times(1)).save(category);
    }

    @Test
    void shouldThrowExceptionWhenUpdatingNonExistentCategory() {
        when(categoryRepository.findById(2L)).thenReturn(Optional.empty());

        Category updatedCategory = new Category();
        updatedCategory.setName("Environment");

        Exception exception = assertThrows(RuntimeException.class, () -> {
            categoryService.updateCategory(2L, updatedCategory);
        });

        assertEquals("Category not found", exception.getMessage());
        verify(categoryRepository, times(1)).findById(2L);
    }

    @Test
    void shouldDeleteCategory() {
        when(categoryRepository.existsById(1L)).thenReturn(true);

        assertDoesNotThrow(() -> categoryService.deleteCategory(1L));
        verify(categoryRepository, times(1)).existsById(1L);
        verify(categoryRepository, times(1)).deleteById(1L);
    }

    @Test
    void shouldThrowExceptionWhenDeletingNonExistentCategory() {
        when(categoryRepository.existsById(2L)).thenReturn(false);

        Exception exception = assertThrows(RuntimeException.class, () -> {
            categoryService.deleteCategory(2L);
        });

        assertEquals("Category not found", exception.getMessage());
        verify(categoryRepository, times(1)).existsById(2L);
    }
}
