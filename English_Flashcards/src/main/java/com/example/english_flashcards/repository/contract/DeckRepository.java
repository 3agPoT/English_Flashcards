package com.example.english_flashcards.repository.contract;

import com.example.english_flashcards.entity.Deck;

public interface DeckRepository {
    void saveDeck(Deck deck);
}
