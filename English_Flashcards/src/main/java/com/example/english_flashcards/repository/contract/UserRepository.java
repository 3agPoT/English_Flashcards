package com.example.english_flashcards.repository.contract;

import com.example.english_flashcards.entity.User;
import com.example.english_flashcards.repository.Repository;

/**
 * Repository interface for managing User entities.
 * Extends the custom Repository interface, which defines basic CRUD operations for User entities.
 * This interface includes additional methods specific to User entities:
 * - getByUsername(String username): Retrieve a user by their username.
 * - getByEmail(String email): Retrieve a user by their email address.
 */
public interface UserRepository extends Repository<User> {

    /**
     * Retrieve a user entity by their username.
     *
     * @param username The username of the user to retrieve.
     * @return The User entity corresponding to the provided username, or null if not found.
     */
    User getByUsername(String username);

    /**
     * Retrieve a user entity by their email address.
     *
     * @param email The email address of the user to retrieve.
     * @return The User entity corresponding to the provided email address, or null if not found.
     */
    User getByEmail(String email);
}
