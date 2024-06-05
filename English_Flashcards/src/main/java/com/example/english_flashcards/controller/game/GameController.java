package com.example.english_flashcards.controller.game;

import com.example.english_flashcards.controller.menu.MenuController;
import com.example.english_flashcards.entity.Cards;
import com.example.english_flashcards.entity.Deck;
import com.example.english_flashcards.entity.User;
import com.example.english_flashcards.repository.impl.DeckRepositoryImpl;
import com.example.english_flashcards.service.contact.UserService;
import com.example.english_flashcards.service.impl.UserServiceImpl;
import javafx.animation.RotateTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.animation.RotateTransition;

import java.io.IOException;
import java.util.ArrayList;

public class GameController {

    private final DeckRepositoryImpl deckRepository = new DeckRepositoryImpl();

    @FXML
    private AnchorPane moreOptionsPane;

    @FXML
    private Label deckNameLabel;

    @FXML
    private Label translationLabel;

    @FXML
    private ImageView imageViewFront;

    @FXML
    private ImageView imageViewBack;

    @FXML
    private Label englishWordLabel;

    @FXML
    private TextField englishWordTextField;

    @FXML
    private Label errorLabel;

    @FXML
    private AnchorPane cardPane;

    @FXML
    private AnchorPane frontSide;

    @FXML
    private AnchorPane backSide;

    private UserService userService = UserServiceImpl.getInstance(); // UserService instance

    private String username; // Username variable

    private Deck currentDeck; // Current deck
    private int currentCardIndex = 0; // Index for the current card
    private boolean isFlipped = false; // Boolean to track card state

    public void initData(String username, Deck deck) {
        this.username = username;
        this.currentDeck = deck;

        User user = userService.getUserByUsername(username);
        deckNameLabel.setText(deck.getName()); // Установка назви колоди

        // Load the first card
        loadCard();

        // Завантажуємо стилі
        loadStyles();
    }

    private void loadCard() {
        if (currentDeck.getCards().isEmpty()) {
            return; // No cards to display
        }

        Cards card = currentDeck.getCards().toArray(new Cards[0])[currentCardIndex];
        translationLabel.setText(card.getTranslation());
        englishWordLabel.setText(card.getEnglishText());
        String imageUrl = card.getImageUrl();
        if (imageUrl != null && !imageUrl.isEmpty()) {
            imageUrl = imageUrl.replace("\\", "/"); // Заміна одинарних обернених косих рисок на подвійні
            try {
                Image image = new Image("file:" + imageUrl);
                imageViewFront.setImage(image);
                imageViewBack.setImage(image);
            } catch (IllegalArgumentException e) {
                System.err.println("Invalid URL: " + imageUrl);
                imageViewFront.setImage(null); // Очистити зображення, якщо URL неправильний
                imageViewBack.setImage(null); // Очистити зображення, якщо URL неправильний
            }
        } else {
            imageViewFront.setImage(null); // Очистити зображення, якщо URL пустий
            imageViewBack.setImage(null); // Очистити зображення, якщо URL пустий
        }

        // Очищуємо попередній текст і повідомлення про помилку
        englishWordTextField.setText("");
        errorLabel.setText("");
        isFlipped = false; // Reset flip state
        frontSide.setVisible(true);
        backSide.setVisible(false);
    }

    @FXML
    public void onReadyButtonClicked() {
        String englishWord = englishWordTextField.getText().trim();
        Cards card = currentDeck.getCards().toArray(new Cards[0])[currentCardIndex];
        if (card.getEnglishText().equalsIgnoreCase(englishWord)) {
            errorLabel.setText("Вірно!");
            errorLabel.setTextFill(javafx.scene.paint.Color.GREEN);
        } else {
            errorLabel.setText("Невірно! Правильне слово: " + card.getEnglishText());
            errorLabel.setTextFill(javafx.scene.paint.Color.RED);
        }
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

    private void updateCard() {
        if (currentDeck.getCards().isEmpty()) {
            return; // No cards to display
        }

        ArrayList<Cards> cardList = new ArrayList<>(currentDeck.getCards());
        if (currentCardIndex >= 0 && currentCardIndex < cardList.size()) {
            Cards card = cardList.get(currentCardIndex);
            translationLabel.setText(card.getTranslation());
            englishWordLabel.setText(card.getEnglishText());
            String imageUrl = card.getImageUrl();
            if (imageUrl != null && !imageUrl.isEmpty()) {
                imageUrl = imageUrl.replace("\\", "/");
                try {
                    Image image = new Image("file:" + imageUrl);
                    imageViewFront.setImage(image);
                    imageViewBack.setImage(image);
                    // Додаємо стиль для округлення
                    imageViewFront.setStyle("-fx-background-radius: 20;");
                    imageViewBack.setStyle("-fx-background-radius: 20;");
                } catch (IllegalArgumentException e) {
                    System.err.println("Invalid URL: " + imageUrl);
                    imageViewFront.setImage(null);
                    imageViewBack.setImage(null);
                }
            } else {
                imageViewFront.setImage(null);
                imageViewBack.setImage(null);
            }

            // Оновлюємо текстове поле
            englishWordTextField.setText(""); // Очищуємо попереднє введене слово
        }

        // Оновлюємо errorLabel
        errorLabel.setText(""); // Очищуємо попереднє повідомлення
        isFlipped = false; // Reset flip state
        frontSide.setVisible(true);
        backSide.setVisible(false);
    }

    @FXML
    public void onNextCardButtonClicked() {
        currentCardIndex = (currentCardIndex + 1) % currentDeck.getCards().size();
        updateCard();
    }

    @FXML
    public void onPreviousCardButtonClicked() {
        currentCardIndex = (currentCardIndex - 1 + currentDeck.getCards().size()) % currentDeck.getCards().size();
        updateCard();
    }

    private void loadStyles() {
        String stylesheetPath = getClass().getResource("/com/example/english_flashcards/styles.css").toExternalForm();
        moreOptionsPane.getStylesheets().add(stylesheetPath);
        englishWordTextField.getStyleClass().add("text-field");
    }

    private boolean isAnimating = false;

    @FXML
    private void flipCard(MouseEvent event) {
        if (!isAnimating) {
            isAnimating = true;
            RotateTransition rotateOut = new RotateTransition(Duration.millis(300), cardPane);
            rotateOut.setAxis(Rotate.Y_AXIS);
            rotateOut.setFromAngle(0);
            rotateOut.setToAngle(isFlipped ? -90 : 90);
            rotateOut.setOnFinished(e -> {
                if (!isFlipped) {
                    frontSide.setVisible(false);
                    backSide.setVisible(true);
                } else {
                    frontSide.setVisible(true);
                    backSide.setVisible(false);
                }
                RotateTransition rotateIn = new RotateTransition(Duration.millis(300), cardPane);
                rotateIn.setAxis(Rotate.Y_AXIS);
                rotateIn.setFromAngle(isFlipped ? 90 : -90);
                rotateIn.setToAngle(0);
                rotateIn.setOnFinished(f -> isAnimating = false);
                rotateIn.play();
            });
            rotateOut.play();
            isFlipped = !isFlipped;
        }
    }
}
