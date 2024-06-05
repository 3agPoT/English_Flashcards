package com.example.english_flashcards.service.impl;

import com.example.english_flashcards.entity.Role;
import com.example.english_flashcards.entity.User;
import com.example.english_flashcards.repository.impl.UserRepositoryImpl;
import com.example.english_flashcards.service.contact.UserService;

/**
 * Implementation of the {@link UserService} interface for managing user entities.
 */
public class UserServiceImpl implements UserService {

    private static final UserServiceImpl INSTANCE = new UserServiceImpl();
    private final UserRepositoryImpl userRepository = UserRepositoryImpl.getInstance();

    private UserServiceImpl() {}

    /**
     * Gets the singleton instance of {@code UserServiceImpl}.
     *
     * @return The singleton instance of {@code UserServiceImpl}.
     */
    public static UserServiceImpl getInstance() {
        return INSTANCE;
    }

    /**
     * Creates a new user and assigns the default user role (id=2) to the user.
     *
     * @param user The user entity to create.
     */
    @Override
    public void create(User user) {
        // Assign default user role (id=2) to the user
        Role userRole = new Role();
        userRole.setId(2L);
        user.setRole(userRole);

        // Create the user using the userRepository
        userRepository.create(user);
    }

    /**
     * Retrieves a user by the given username.
     *
     * @param username The username of the user to retrieve.
     * @return The user entity corresponding to the given username, or null if not found.
     */
    @Override
    public User getUserByUsername(String username) {
        return userRepository.getByUsername(username);
    }

    /**
     * Retrieves a user by the given email address.
     *
     * @param email The email address of the user to retrieve.
     * @return The user entity corresponding to the given email address, or null if not found.
     */
    @Override
    public User getUserByEmail(String email) {
        return userRepository.getByEmail(email);
    }

    /**
     * Updates an existing user entity.
     *
     * @param user The user entity to update.
     */
    @Override
    public void update(User user) {
        userRepository.update(user);
    }
}
