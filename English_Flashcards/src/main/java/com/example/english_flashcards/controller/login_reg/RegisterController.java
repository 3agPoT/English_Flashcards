package com.example.english_flashcards.controller.login_reg;

import com.example.english_flashcards.entity.User;
import com.example.english_flashcards.service.impl.UserServiceImpl;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.io.IOException;

/**
 * Controller class for handling user registration functionality.
 */
public class RegisterController {

    @FXML
    private TextField usernameField;

    @FXML
    private TextField emailField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Label errorLabel;

    private final UserServiceImpl userService = UserServiceImpl.getInstance();

    /**
     * Handles the registration action.
     * @param event The ActionEvent triggered by register button click.
     * @throws IOException If an error occurs while loading the login view.
     */
    @FXML
    private void register(ActionEvent event) throws IOException {
        String username = usernameField.getText();
        String email = emailField.getText();
        String password = passwordField.getText();

        // Validation checks for empty fields and password length
        if (username.isEmpty() || email.isEmpty() || password.isEmpty()) {
            showError("Будь ласка, заповніть всі поля");
            return;
        }

        if (password.length() < 8) {
            showError("Пароль повинен містити принаймні 8 символів");
            return;
        }

        // Regex checks for username and email format
        if (!username.matches("[a-zA-Z1-9]{3,10}")) {
            showError("Ім'я користувача повинне містити від 3 до 10 латинських літер");
            return;
        }

        // Check for existing username and email
        User existingUser = userService.getUserByUsername(username);
        if (existingUser != null) {
            showError("Користувач з таким іменем вже існує");
            return;
        }

        User existingEmailUser = userService.getUserByEmail(email);
        if (existingEmailUser != null) {
            showError("Користувач з такою електронною адресою вже існує");
            return;
        }

        if (!email.matches("[a-zA-Z0-9._%+-]+@[gmail]+\\.[com]{2,}")) {
            showError("Будь ласка, введіть коректну електронну адресу");
            return;
        }

        // Create a new user and register
        User newUser = new User(username, email, password);
        userService.create(newUser);

        // Clear fields and display success message
        clearFields();
        errorLabel.setText("Користувач зареєстрований успішно!");

        // Load the login view
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/english_flashcards/login-view.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) usernameField.getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.setTitle("Логін");
    }

    /**
     * Switches to the login view.
     * @param event The ActionEvent triggered by login button click.
     * @throws IOException If an error occurs while loading the login view.
     */
    @FXML
    private void login(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/english_flashcards/login-view.fxml"));
        Parent root = fxmlLoader.load();

        Scene scene = usernameField.getScene();
        Stage stage = (Stage) scene.getWindow();

        stage.setScene(new Scene(root));
        stage.setTitle("Логін");
    }

    /**
     * Displays an error message on the error label.
     * @param message The error message to display.
     */
    private void showError(String message) {
        errorLabel.setText(message);
    }

    /**
     * Clears the text fields.
     */
    private void clearFields() {
        usernameField.clear();
        emailField.clear();
        passwordField.clear();
    }
}