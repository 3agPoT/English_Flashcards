package com.example.english_flashcards.repository.contract;

import com.example.english_flashcards.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for managing Category entities.
 * Extends JpaRepository which provides CRUD operations for Category entities with Long as the ID type.
 * This interface inherits all basic CRUD methods from JpaRepository:
 * - save(S entity)
 * - findById(ID id)
 * - findAll()
 * - deleteById(ID id)
 * - ... and more
 */
@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    // No additional methods are defined here because JpaRepository provides all necessary CRUD methods
    // for managing Category entities. Custom queries can be added if needed by defining method signatures
    // following Spring Data JPA conventions, which automatically generates queries based on method names.

}
