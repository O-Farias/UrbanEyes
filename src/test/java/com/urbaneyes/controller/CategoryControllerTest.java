package com.urbaneyes.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.urbaneyes.model.Category;
import com.urbaneyes.service.CategoryService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CategoryController.class)
class CategoryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CategoryService categoryService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void shouldGetAllCategories() throws Exception {
        Category category1 = new Category(1L, "Infrastructure");
        Category category2 = new Category(2L, "Environment");

        Mockito.when(categoryService.getAllCategories()).thenReturn(List.of(category1, category2));

        mockMvc.perform(get("/api/categories"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(2))
                .andExpect(jsonPath("$[0].name").value("Infrastructure"))
                .andExpect(jsonPath("$[1].name").value("Environment"));
    }

    @Test
    void shouldGetCategoryById() throws Exception {
        Category category = new Category(1L, "Infrastructure");

        Mockito.when(categoryService.getCategoryById(eq(1L))).thenReturn(Optional.of(category));

        mockMvc.perform(get("/api/categories/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Infrastructure"));
    }

    @Test
    void shouldReturnNotFoundForInvalidCategoryId() throws Exception {
        Mockito.when(categoryService.getCategoryById(eq(999L))).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/categories/999"))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldCreateCategory() throws Exception {
        Category category = new Category(null, "Infrastructure");
        Category savedCategory = new Category(1L, "Infrastructure");

        Mockito.when(categoryService.createCategory(any(Category.class))).thenReturn(savedCategory);

        mockMvc.perform(post("/api/categories")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(category)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Infrastructure"));
    }

    @Test
    void shouldUpdateCategory() throws Exception {
        Category updatedCategory = new Category(1L, "Updated Infrastructure");

        Mockito.when(categoryService.updateCategory(eq(1L), any(Category.class))).thenReturn(updatedCategory);

        mockMvc.perform(put("/api/categories/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedCategory)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Updated Infrastructure"));
    }

    @Test
    void shouldDeleteCategory() throws Exception {
        mockMvc.perform(delete("/api/categories/1"))
                .andExpect(status().isNoContent());

        Mockito.verify(categoryService).deleteCategory(1L);
    }
}
