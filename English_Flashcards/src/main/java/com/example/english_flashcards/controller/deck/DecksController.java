package com.example.english_flashcards.controller.deck;

import com.example.english_flashcards.controller.game.GameController;
import com.example.english_flashcards.controller.menu.MenuController;
import com.example.english_flashcards.entity.Deck;
import com.example.english_flashcards.entity.User;
import com.example.english_flashcards.repository.impl.DeckRepositoryImpl;
import com.example.english_flashcards.service.contact.UserService;
import com.example.english_flashcards.service.impl.UserServiceImpl;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class DecksController {

    private final DeckRepositoryImpl deckRepository = new DeckRepositoryImpl();

    @FXML
    private AnchorPane moreOptionsPane;

    private UserService userService = UserServiceImpl.getInstance(); // UserService instance

    private String username; // Username variable

    @FXML
    private VBox decksVBox;

    /**
     * Initializes user data based on the provided username.
     *
     * @param username The username of the current user.
     */
    public void initData(String username) {
        this.username = username;

        User user = userService.getUserByUsername(username);

        List<Deck> decks = deckRepository.getAllDecks();
        displayDecks(decks);
    }

    public void menu() {
        openMenuView();
    }

    private void openMenuView() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/english_flashcards/menu-view.fxml"));
            AnchorPane questsView = loader.load();

            MenuController menuController = loader.getController();
            menuController.initData(username);

            Stage stage = (Stage) moreOptionsPane.getScene().getWindow();

            stage.setScene(new Scene(questsView));

            stage.setTitle("Меню");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void displayDecks(List<Deck> decks) {
        decksVBox.getChildren().clear(); // Очищаємо вміст VBox перед відображенням нових колод
        decksVBox.setSpacing(10); // Додаємо відступи між рядами
        decksVBox.setPadding(new Insets(10)); // Додаємо відступи від країв VBox
        HBox row = new HBox();
        row.setSpacing(10); // Відступи між колонками
        for (int i = 0; i < decks.size(); i++) {
            Deck deck = decks.get(i);
            VBox column = new VBox();
            column.setPrefWidth(100); // Ширина колонок
            column.setPrefHeight(50); // Висота колонок
            column.setStyle("-fx-background-color: #ffffff; -fx-alignment: center; -fx-border-color: #e0e0e0; -fx-border-radius: 10; -fx-background-radius: 10; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.3), 10, 0, 0, 0);");

            Label deckLabel = new Label(deck.getName());
            deckLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-alignment: center; -fx-text-fill: #333333;"); // Зміна розміру шрифту, жирного шрифту та вирівнювання по центру

            // Додавання обробників подій для курсору
            deckLabel.setOnMouseEntered(event -> deckLabel.getScene().setCursor(javafx.scene.Cursor.HAND));
            deckLabel.setOnMouseExited(event -> deckLabel.getScene().setCursor(javafx.scene.Cursor.DEFAULT));

            // Додавання обробника подій для відкриття game-view
            deckLabel.setOnMouseClicked(event -> {
                if (deckLabel.getTextFill().equals(Color.RED)) {
                    deleteDeck(deck);
                } else {
                    openGameView(deck);
                }
            });

            column.getChildren().add(deckLabel);
            row.getChildren().add(column);

            // Додаємо новий рядок після кожних 3 колонок
            if ((i + 1) % 9 == 0) {
                decksVBox.getChildren().add(row);
                row = new HBox();
                row.setSpacing(10);
            }
        }
        // Додаємо залишковий рядок, якщо він є
        if (!row.getChildren().isEmpty()) {
            decksVBox.getChildren().add(row);
        }
    }

    private void openGameView(Deck deck) {
        try {
            Deck detailedDeck = deckRepository.getDeckWithCards(deck.getId()); // Переконайтеся, що картки ініціалізовані

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/english_flashcards/game-view.fxml"));
            AnchorPane gameView = loader.load();

            GameController gameController = loader.getController();
            gameController.initData(username, detailedDeck);

            Stage stage = (Stage) moreOptionsPane.getScene().getWindow();
            stage.setScene(new Scene(gameView));
            stage.setTitle("Гра");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void deleteDeck(Deck deck) {
        deckRepository.deleteDeck(deck);
        List<Deck> updatedDecks = deckRepository.getAllDecks();
        displayDecks(updatedDecks);
    }

    @FXML
    private void highlightLabels() {
        toggleLabelColors(decksVBox);
    }

    private void toggleLabelColors(javafx.scene.Parent parent) {
        for (javafx.scene.Node node : parent.getChildrenUnmodifiable()) {
            if (node instanceof Label) {
                Label label = (Label) node;
                // Перевірка поточного кольору тексту
                if (label.getTextFill().equals(Color.RED)) {
                    // Встановлення кольору тексту на чорний, якщо він червоний
                    label.setTextFill(Color.BLACK);
                } else {
                    // Встановлення кольору тексту на червоний, якщо він чорний
                    label.setTextFill(Color.RED);
                }
            } else if (node instanceof javafx.scene.Parent) {
                toggleLabelColors((javafx.scene.Parent) node); // Рекурсивно обробляємо всіх дітей
            }
        }
    }
}
