package com.example.english_flashcards.repository.impl;

import com.example.english_flashcards.entity.User;
import com.example.english_flashcards.repository.contract.UserRepository;
import com.example.english_flashcards.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Implementation class for managing User entities using Hibernate.
 */
public class UserRepositoryImpl implements UserRepository {
    private static final UserRepositoryImpl INSTANCE = new UserRepositoryImpl();
    private final SessionFactory sessionFactory = HibernateUtil.getInstance().getSessionFactory();

    private UserRepositoryImpl() {
    }

    /**
     * Retrieves the singleton instance of UserRepositoryImpl.
     *
     * @return The UserRepositoryImpl instance.
     */
    public static UserRepositoryImpl getInstance() {
        return INSTANCE;
    }

    @Override
    public User getByUsername(String username) {
        try (Session session = sessionFactory.openSession()) {
            Query<User> query = session.createQuery("FROM User WHERE username = :username", User.class);
            query.setParameter("username", username);
            return query.uniqueResult();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void create(User entity) {
        entity.setRegisterTime(LocalDateTime.now());
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.persist(entity);
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<User> getAll() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("FROM User", User.class).list();
        } catch (Exception e) {
            e.printStackTrace();
            return List.of();
        }
    }

    @Override
    public User getById(Integer id) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(User.class, id);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public User update(User entity) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            User updatedUser = session.merge(entity);
            session.getTransaction().commit();
            return updatedUser;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void delete(User user) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.remove(user);
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public User getByEmail(String email) {
        try (Session session = sessionFactory.openSession()) {
            Query<User> query = session.createQuery("FROM User WHERE email = :email", User.class);
            query.setParameter("email", email);
            return query.uniqueResult();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
