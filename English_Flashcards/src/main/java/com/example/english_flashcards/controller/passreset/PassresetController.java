package com.example.english_flashcards.controller.passreset;

import com.example.english_flashcards.controller.passreset.email.EmailUtil;
import com.example.english_flashcards.entity.User;
import com.example.english_flashcards.service.impl.UserServiceImpl;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

/**
 * Controller responsible for handling password reset functionality.
 */
public class PassresetController {

    @FXML
    private TextField emailField;

    @FXML
    private Label errorLabel;

    /**
     * Resets the password for the user associated with the provided email.
     * Validates the email, retrieves the user by email, and initiates the password reset process.
     */
    @FXML
    private void resetPassword() {
        String email = emailField.getText();

        if (isValidEmail(email)) {
            try {
                User user = UserServiceImpl.getInstance().getUserByEmail(email);

                if (user != null) {
                    // Send password reset email
                    EmailUtil.sendPasswordResetEmail(email);

                    // Switch to verify code view
                    switchToVerifyCodeView(email);

                    // Close current window
                    closeWindow();
                } else {
                    showError("User with this email address not found");
                }

            } catch (Exception e) {
                e.printStackTrace();
                showError("Failed to send password reset email");
            }
        } else {
            showError("Please enter a valid email address");
        }
    }

    /**
     * Checks if the given email is valid based on a simple regex pattern.
     *
     * @param email Email address to validate
     * @return True if the email is valid, otherwise false
     */
    private boolean isValidEmail(String email) {
        return email.matches("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}");
    }

    /**
     * Displays an error message on the error label and styles the email field to indicate an error.
     *
     * @param message Error message to display
     */
    private void showError(String message) {
        errorLabel.setText(message);
        errorLabel.setTextFill(Color.RED);
        emailField.setStyle(emailField.getStyle() + "-fx-border-color: #FF0000; -fx-border-width: 0px 0px 2px 0px;");
    }

    /**
     * Closes the current window.
     */
    private void closeWindow() {
        Stage stage = (Stage) emailField.getScene().getWindow();
        stage.close();
    }

    /**
     * Switches to the verification code view for the given email.
     *
     * @param email Email address for which the verification code will be sent
     */
    private void switchToVerifyCodeView(String email) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/english_flashcards/verifycode-view.fxml"));
            Parent root = loader.load();

            VerifyCodeController verifyCodeController = loader.getController();
            verifyCodeController.setEmail(email);

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Verification Code");
            stage.initStyle(StageStyle.DECORATED);
            stage.setResizable(false);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
