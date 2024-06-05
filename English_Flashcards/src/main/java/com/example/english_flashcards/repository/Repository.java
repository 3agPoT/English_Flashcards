package com.example.english_flashcards.repository;

import java.util.List;

/**
 * Generic repository interface defining basic CRUD operations.
 *
 * @param <E> The entity type managed by the repository.
 */
public interface Repository<E> {

    /**
     * Creates a new entity in the repository.
     *
     * @param entity The entity to create.
     */
    void create(E entity);

    /**
     * Retrieves all entities from the repository.
     *
     * @return A list of all entities in the repository.
     */
    List<E> getAll();

    /**
     * Retrieves an entity by its identifier from the repository.
     *
     * @param id The identifier of the entity to retrieve.
     * @return The entity with the specified identifier, or null if not found.
     */
    E getById(Integer id);

    /**
     * Updates an existing entity in the repository.
     *
     * @param entity The entity to update.
     * @return The updated entity.
     */
    E update(E entity);

    /**
     * Deletes an entity from the repository.
     *
     * @param entity The entity to delete.
     */
    void delete(E entity);
}