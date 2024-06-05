package com.example.english_flashcards.service.impl;

import com.example.english_flashcards.entity.Deck;
import com.example.english_flashcards.repository.contract.DeckRepository;
import com.example.english_flashcards.service.contact.DeckService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DeckServiceImpl implements DeckService {

    private final DeckRepository deckRepository;

    @Autowired
    public DeckServiceImpl(DeckRepository deckRepository) {
        this.deckRepository = deckRepository;
    }

    @Override
    public void saveDeck(Deck deck) {
        deckRepository.saveDeck(deck);
    }
}
