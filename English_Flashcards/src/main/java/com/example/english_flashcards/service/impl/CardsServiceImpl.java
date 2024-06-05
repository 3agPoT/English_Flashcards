package com.example.english_flashcards.service.impl;

import com.example.english_flashcards.entity.Cards;
import com.example.english_flashcards.repository.impl.CardRepositoryImpl;
import com.example.english_flashcards.service.contact.CardsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * Implementation of the {@link CardsService} interface for managing card entities.
 */
@Service
@RequiredArgsConstructor
public class CardsServiceImpl implements CardsService {

    private final CardRepositoryImpl cardsRepository;

    /**
     * Saves a card entity.
     *
     * @param card The card entity to save.
     * @return The saved card entity.
     */
    @Override
    public Cards saveCard(Cards card) {
        return cardsRepository.save(card);
    }

    /**
     * Retrieves all card entities.
     *
     * @return A list of all card entities.
     */
    @Override
    public List<Cards> getAllCards() {
        return cardsRepository.getAllCards();
    }
}
