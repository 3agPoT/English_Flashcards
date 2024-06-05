package com.example.english_flashcards.service.impl;

import com.example.english_flashcards.entity.Category;
import com.example.english_flashcards.repository.impl.CategoryRepositoryImpl;
import com.example.english_flashcards.service.contact.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Implementation of the {@link CategoryService} interface for managing category entities.
 */
@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepositoryImpl categoryRepository;

    /**
     * Saves a new category with the specified name.
     *
     * @param categoryName The name of the category to be saved.
     * @return The saved category entity.
     */
    @Override
    public Category saveCategory(String categoryName) {
        // Create a new category entity with the given name
        Category category = new Category();
        category.setName(categoryName);

        // Persist the category using the category repository
        return categoryRepository.create(category);
    }
}
