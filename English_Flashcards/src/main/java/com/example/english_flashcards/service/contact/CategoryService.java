package com.example.english_flashcards.service.contact;

import com.example.english_flashcards.entity.Category;

/**
 * Service interface for managing category entities.
 */
public interface CategoryService {

    /**
     * Saves a new category with the given name.
     *
     * @param categoryName The name of the category to save.
     * @return The saved category entity.
     */
    Category saveCategory(String categoryName);
}
