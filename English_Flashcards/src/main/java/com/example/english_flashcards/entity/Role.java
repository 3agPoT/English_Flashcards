package com.example.english_flashcards.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

/**
 * Entity class representing a role assigned to users in the system.
 */
@Entity
@Table(name = "Role")
@Getter
@Setter
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    /**
     * Retrieves the unique identifier of the role.
     * @return The role ID.
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets the unique identifier of the role.
     * @param id The role ID to set.
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Retrieves the name of the role.
     * @return The role name.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the role.
     * @param name The role name to set.
     */
    public void setName(String name) {
        this.name = name;
    }
}