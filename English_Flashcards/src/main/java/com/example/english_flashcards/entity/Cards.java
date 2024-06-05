package com.example.english_flashcards.entity;

import jakarta.persistence.*;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "Cards")
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Cards {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "english_text")
    private String englishText;

    @Column(name = "translation")
    private String translation;

    @ManyToOne
    @JoinColumn(name = "CATEGORY_ID")
    private Category category;

    // Additional column for mapping the CATEGORY_ID from Category entity
    @Column(name = "CATEGORY_ID", insertable = false, updatable = false)
    private Long categoryId;

    @ManyToMany(mappedBy = "cards")
    private Set<Deck> decks = new HashSet<>();

    @Transient
    private final BooleanProperty selected = new SimpleBooleanProperty();

    public BooleanProperty selectedProperty() {
        return selected;
    }

    public String getCategoryName() {
        return category != null ? category.getName() : null;
    }
}
