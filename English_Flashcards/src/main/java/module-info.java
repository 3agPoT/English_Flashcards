module com.example.english_flashcards {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires org.hibernate.orm.core;
    requires lombok;
    requires jakarta.persistence;
    requires java.naming;
    requires java.mail;
    requires java.desktop;
    requires spring.data.jpa;
    requires spring.beans;
    requires spring.context;
    requires spring.tx;
    requires spring.jdbc;
    requires java.persistence;
    requires java.smartcardio;
    requires spring.security.crypto;
    requires spring.boot;
    requires spring.boot.autoconfigure;


    opens com.example.english_flashcards to javafx.fxml;
    opens com.example.english_flashcards.controller.login_reg to javafx.fxml;
    opens com.example.english_flashcards.entity to org.hibernate.orm.core, javafx.base;
    opens com.example.english_flashcards.controller.passreset to javafx.fxml;
    opens com.example.english_flashcards.controller.menu to javafx.fxml;
    opens com.example.english_flashcards.controller.menu.quests to javafx.fxml;
    opens com.example.english_flashcards.controller.menu.shop to javafx.fxml;
    opens com.example.english_flashcards.controller.menu.profile to javafx.fxml;
    opens com.example.english_flashcards.controller.cards to javafx.fxml;
    opens com.example.english_flashcards.controller.deck to javafx.fxml;
    opens com.example.english_flashcards.controller.game to javafx.fxml;

    exports com.example.english_flashcards.controller.passreset;
    exports com.example.english_flashcards;
    exports com.example.english_flashcards.controller.passreset.email;
    exports com.example.english_flashcards.controller.menu.quests;
    exports com.example.english_flashcards.controller.menu.shop;
    exports com.example.english_flashcards.controller.menu.profile;
    exports com.example.english_flashcards.controller.cards;
    exports com.example.english_flashcards.controller.deck;
    exports com.example.english_flashcards.controller.game;
}
