package com.example.english_flashcards.controller.cards;

import com.example.english_flashcards.controller.menu.MenuController;
import com.example.english_flashcards.entity.Cards;
import com.example.english_flashcards.entity.Category;
import com.example.english_flashcards.entity.User;
import com.example.english_flashcards.repository.impl.CardRepositoryImpl;
import com.example.english_flashcards.repository.impl.CategoryRepositoryImpl;
import com.example.english_flashcards.service.contact.UserService;
import com.example.english_flashcards.service.impl.CardsServiceImpl;
import com.example.english_flashcards.service.impl.CategoryServiceImpl;
import com.example.english_flashcards.service.impl.UserServiceImpl;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Controller;
import java.io.*;
import java.nio.file.*;
import java.util.UUID;

/**
 * Controller class for creating new flashcards.
 */
@Controller
@AllArgsConstructor
@NoArgsConstructor
public class CreatingCardsController {
    // Repository and service instances for managing cards and categories
    private CardRepositoryImpl cardsRepository = new CardRepositoryImpl();
    private CardsServiceImpl cardsService = new CardsServiceImpl(cardsRepository);

    private CategoryRepositoryImpl categoryRepository = new CategoryRepositoryImpl();
    private CategoryServiceImpl categoryService = new CategoryServiceImpl(categoryRepository);

    private File selectedFile; // File object for holding the selected image file

    private UserService userService = UserServiceImpl.getInstance(); // UserService instance for user operations

    private String username; // Username of the logged-in user

    private User user; // User object representing the logged-in user

    /**
     * Initializes the controller with the given username.
     *
     * @param username The username of the logged-in user.
     */
    public void initData(String username) {
        this.username = username;
        this.user = userService.getUserByUsername(username);
    }

    @FXML
    private AnchorPane moreOptionsPane; // AnchorPane for additional options in the UI

    /**
     * Opens the main menu view.
     */
    public void menu() {
        openMenuView();
    }

    private void openMenuView() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/english_flashcards/menu-view.fxml"));
            AnchorPane shopView = loader.load();

            MenuController menuController = loader.getController();
            menuController.initData(username);

            Stage stage = (Stage) moreOptionsPane.getScene().getWindow();

            stage.setScene(new Scene(shopView));

            stage.setTitle("Меню"); // Set title for the stage
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showError(String message) {
        errorLabel.setText(message); // Set error message in the label
        errorLabel.setStyle("-fx-text-fill: red;"); // Set text color to red
    }

    @FXML
    private Label errorLabel; // Label for displaying error messages

    @FXML
    private void openImageFileChooser() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Виберіть зображення"); // Set file chooser title

        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Зображення", "*.png", "*.jpg", "*.gif")
        );

        Stage stage = (Stage) moreOptionsPane.getScene().getWindow();
        selectedFile = fileChooser.showOpenDialog(stage);

        if (selectedFile != null) {
            imageUrl.getChildren().clear(); // Clear existing children of imageUrl

            Image image = new Image(selectedFile.toURI().toString());

            ImageView imageView = new ImageView(image);
            imageView.setFitWidth(300);
            imageView.setFitHeight(300);

            imageUrl.getChildren().add(imageView); // Add ImageView to imageUrl AnchorPane

            double centerX = (imageUrl.getWidth() - imageView.getFitWidth()) / 2;
            double centerY = (imageUrl.getHeight() - imageView.getFitHeight()) / 2;
            imageView.setTranslateX(centerX);
            imageView.setTranslateY(centerY);
        }
    }

    @FXML
    private TextField englishText; // TextField for entering English word

    @FXML
    private TextField translation; // TextField for entering translation

    @FXML
    private AnchorPane imageUrl; // AnchorPane for displaying image preview

    @FXML
    private void addCard() {
        String word = englishText.getText().trim();
        String translationText = translation.getText().trim();
        String categoryText = category.getText().trim();

        if (selectedFile == null) {
            showError("Додайте зображення"); // Display error if no image selected
            return;
        }

        if (categoryText.isEmpty()) {
            showError("Введіть категорію"); // Display error if category is empty
            return;
        }

        if (word.isEmpty()) {
            showError("Введіть слово"); // Display error if word is empty
            return;
        }

        if (translationText.isEmpty()) {
            showError("Введіть переклад"); // Display error if translation is empty
            return;
        }

        try {
            String saveDirectory = "imagesCards";

            File directory = new File(saveDirectory);
            if (!directory.exists()) {
                directory.mkdirs();
            }

            String uniqueFileName = UUID.randomUUID() + ".png";

            Path targetPath = Paths.get(saveDirectory, uniqueFileName);
            Files.copy(selectedFile.toPath(), targetPath, StandardCopyOption.REPLACE_EXISTING);

            Category savedCategory = categoryService.saveCategory(categoryText);

            Cards newCard = new Cards();
            newCard.setEnglishText(word);
            newCard.setTranslation(translationText);
            newCard.setImageUrl(targetPath.toString());
            newCard.setCategory(savedCategory);

            if (cardsService != null) {
                cardsService.saveCard(newCard);
                showSuccess("Картка успішно додана!"); // Display success message
                imageUrl.getChildren().clear(); // Clear imageUrl AnchorPane
                selectedFile = null; // Reset selectedFile
            }

        } catch (IOException e) {
            e.printStackTrace();
            showError("Помилка збереження зображення"); // Display error if image saving fails
        }
    }

    private void showSuccess(String message) {
        errorLabel.setText(message); // Set success message in the label
        errorLabel.setStyle("-fx-text-fill: green;"); // Set text color to green
    }

    @FXML
    private TextField category; // TextField for entering category
}
