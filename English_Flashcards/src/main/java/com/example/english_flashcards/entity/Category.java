package com.example.english_flashcards.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

/**
 * Entity class representing a category for English flashcards.
 */
@Entity
@Table(name = "Category")
@Getter
@Setter
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    /**
     * Retrieves the unique identifier of the category.
     * @return The category ID.
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets the unique identifier of the category.
     * @param id The category ID to set.
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Retrieves the name of the category.
     * @return The category name.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the category.
     * @param name The category name to set.
     */
    public void setName(String name) {
        this.name = name;
    }
}
