package com.example.english_flashcards.service.contact;

import com.example.english_flashcards.entity.Cards;
import java.util.List;

/**
 * Service interface for managing card entities.
 */
public interface CardsService {

    /**
     * Saves a new card entity.
     *
     * @param card The card entity to save.
     * @return The saved card entity.
     */
    Cards saveCard(Cards card);

    /**
     * Retrieves all card entities.
     *
     * @return A list of all card entities.
     */
    List<Cards> getAllCards();
}
