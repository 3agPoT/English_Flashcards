package com.example.english_flashcards.repository.contract;

import com.example.english_flashcards.entity.Cards;
import com.example.english_flashcards.repository.Repository;

/**
 * Interface representing a repository for managing Card entities.
 * Extends the generic Repository interface for CRUD operations.
 */
public interface CardsRepository extends Repository<Cards> {

    // No additional methods are defined here because it inherits all CRUD operations
    // from the generic Repository interface, which includes methods like findById,
    // findAll, save, update, and delete.

}
