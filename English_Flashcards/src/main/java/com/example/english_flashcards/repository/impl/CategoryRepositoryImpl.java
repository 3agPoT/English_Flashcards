package com.example.english_flashcards.repository.impl;

import com.example.english_flashcards.entity.Category;
import com.example.english_flashcards.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

/**
 * Implementation class for managing Category entities using Hibernate.
 */
public class CategoryRepositoryImpl {

    private final SessionFactory sessionFactory = HibernateUtil.getInstance().getSessionFactory();

    /**
     * Creates a new category entity in the database.
     *
     * @param entity The category entity to be created.
     * @return The created category entity if successful, otherwise null.
     */
    public Category create(Category entity) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.persist(entity);
            session.getTransaction().commit();
            return entity;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
