package com.example.english_flashcards.controller.passreset;

import com.example.english_flashcards.entity.User;
import com.example.english_flashcards.repository.contract.UserRepository;
import com.example.english_flashcards.repository.impl.UserRepositoryImpl;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Controller responsible for handling password change functionality.
 */
public class ChangePasswordController {

    @FXML
    private PasswordField newPasswordField;

    @FXML
    private PasswordField newPasswordField1;

    @FXML
    private Label errorLabel;

    private final UserRepository userRepository = UserRepositoryImpl.getInstance();

    private String userEmail;

    /**
     * Sets the email of the user whose password is being changed.
     *
     * @param email Email address of the user
     */
    public void setEmail(String email) {
        this.userEmail = email;
    }

    /**
     * Changes the password for the current user.
     * Retrieves the user by email, validates the new password,
     * and updates the user's password if all conditions are met.
     */
    @FXML
    private void changePassword() {
        User currentUser = userRepository.getByEmail(userEmail);

        if (currentUser != null) {
            String newPassword = newPasswordField.getText();
            String newPassword1 = newPasswordField1.getText();

            if (newPassword.isEmpty() || newPassword1.isEmpty()) {
                showError("Please fill in all fields");
                return;
            }

            if (newPassword.length() < 8) {
                showError("Password must be at least 8 characters");
                return;
            }

            if (!newPassword.equals(newPassword1)) {
                showError("Entered passwords do not match");
                return;
            }

            currentUser.setPassword(newPassword);
            userRepository.update(currentUser);

            navigateToLogin();

        } else {
            errorLabel.setText("User not found");
        }
    }

    /**
     * Displays an error message on the error label.
     *
     * @param message Error message to display
     */
    private void showError(String message) {
        errorLabel.setText(message);
    }

    /**
     * Navigates to the login view after a successful password change.
     */
    private void navigateToLogin() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/english_flashcards/login-view.fxml"));
            Parent root = fxmlLoader.load();

            Scene scene = newPasswordField.getScene();
            Stage stage = (Stage) scene.getWindow();

            stage.setScene(new Scene(root));
            stage.setTitle("Login");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
