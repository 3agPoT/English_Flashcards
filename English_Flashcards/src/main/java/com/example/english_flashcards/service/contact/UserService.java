package com.example.english_flashcards.service.contact;

import com.example.english_flashcards.entity.User;

/**
 * Service interface for managing user entities.
 */
public interface UserService {

    /**
     * Creates a new user.
     *
     * @param user The user entity to create.
     */
    void create(User user);

    /**
     * Updates an existing user.
     *
     * @param user The user entity to update.
     */
    void update(User user);

    /**
     * Retrieves a user by their username.
     *
     * @param username The username of the user to retrieve.
     * @return The user entity corresponding to the username, or null if not found.
     */
    User getUserByUsername(String username);

    /**
     * Retrieves a user by their email address.
     *
     * @param email The email address of the user to retrieve.
     * @return The user entity corresponding to the email address, or null if not found.
     */
    User getUserByEmail(String email);
}