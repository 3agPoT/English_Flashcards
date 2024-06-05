package com.example.english_flashcards.repository.impl;

import com.example.english_flashcards.entity.Cards;
import com.example.english_flashcards.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.Collections;
import java.util.List;

/**
 * Implementation class for managing Card entities using Hibernate.
 */
public class CardRepositoryImpl {

    private final SessionFactory sessionFactory = HibernateUtil.getInstance().getSessionFactory();

    /**
     * Saves a card entity to the database.
     *
     * @param card The card entity to save.
     * @return The saved card entity if successful, otherwise null.
     */
    public Cards save(Cards card) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            if (card.getId() != null) {
                // If the card already has an ID, it means it's an existing record,
                // so we should update it instead of saving a new one.
                session.update(card);
            } else {
                // If the card doesn't have an ID, it means it's a new record,
                // so we should save it.
                session.persist(card);
            }
            session.getTransaction().commit();

            return card;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Retrieves all card entities from the database.
     *
     * @return A list of all card entities, or an empty list if no cards are found.
     */
    public List<Cards> getAllCards() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("FROM Cards", Cards.class).list();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }
}
