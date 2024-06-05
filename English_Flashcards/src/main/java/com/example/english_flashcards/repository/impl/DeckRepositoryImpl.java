package com.example.english_flashcards.repository.impl;

import com.example.english_flashcards.entity.Deck;
import com.example.english_flashcards.repository.contract.DeckRepository;
import com.example.english_flashcards.util.HibernateUtil;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;

@Repository
public class DeckRepositoryImpl implements DeckRepository {

    private final SessionFactory sessionFactory;

    public DeckRepositoryImpl() {
        this.sessionFactory = HibernateUtil.getInstance().getSessionFactory();
    }

    @Override
    public void saveDeck(Deck deck) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.save(deck);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null && transaction.getStatus().canRollback()) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    public List<Deck> getAllDecks() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("FROM Deck", Deck.class).getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    public Deck getDeckWithCards(Long deckId) {
        try (Session session = sessionFactory.openSession()) {
            Deck deck = session.get(Deck.class, deckId);
            if (deck != null) {
                Hibernate.initialize(deck.getCards());
            }
            return deck;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void deleteDeck(Deck deck) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.delete(deck);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null && transaction.getStatus().canRollback()) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }
}
