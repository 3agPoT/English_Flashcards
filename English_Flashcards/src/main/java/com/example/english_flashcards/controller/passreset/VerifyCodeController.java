package com.example.english_flashcards.controller.passreset;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Controller responsible for handling verification code entry and password reset initiation.
 */
public class VerifyCodeController {

    @FXML
    private TextField codeField1;
    @FXML
    private TextField codeField2;
    @FXML
    private TextField codeField3;
    @FXML
    private TextField codeField4;
    @FXML
    private TextField codeField5;
    @FXML
    private TextField codeField6;
    @FXML
    private Label emailLabel;
    @FXML
    private Label errorLabel;

    private String email;
    private String verificationCode;

    /**
     * Sets the verification code received for validation.
     *
     * @param verificationCode The verification code to set
     */
    public void setVerificationCode(String verificationCode) {
        this.verificationCode = verificationCode;
    }

    /**
     * Sets the email address for which the verification is being done and updates the email label.
     *
     * @param email The email address to set
     */
    public void setEmail(String email) {
        this.email = email;
        emailLabel.setText(email);
    }

    /**
     * Opens the change password view after successful verification.
     */
    private void openChangePasswordView() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/english_flashcards/changepassword-view.fxml"));
            Parent root = fxmlLoader.load();

            ChangePasswordController changePasswordController = fxmlLoader.getController();
            changePasswordController.setEmail(email);

            Stage stage = (Stage) codeField1.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Change Password");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Initializes the controller with necessary event listeners and constraints.
     */
    @FXML
    private void initialize() {
        addDigitLimitAndFocusListener(codeField1, codeField2);
        addDigitLimitAndFocusListener(codeField2, codeField3);
        addDigitLimitAndFocusListener(codeField3, codeField4);
        addDigitLimitAndFocusListener(codeField4, codeField5);
        addDigitLimitAndFocusListener(codeField5, codeField6);
        addDigitLimitAndFocusListener(codeField6, null);

        setBackspaceListeners();
    }

    /**
     * Adds a listener to enforce digit input and focus switching between verification code text fields.
     *
     * @param currentField The current text field
     * @param nextField    The next text field to focus on after input limit is reached
     */
    private void addDigitLimitAndFocusListener(TextField currentField, TextField nextField) {
        currentField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d")) {
                currentField.setText(oldValue);
            } else {
                if (newValue.length() > 0 && nextField != null) {
                    nextField.requestFocus();
                }
            }
        });
    }

    /**
     * Sets event listeners for handling backspace key press to delete entered verification code digits.
     */
    private void setBackspaceListeners() {
        setBackspaceDeleteListener(codeField1);
        setBackspaceDeleteListener(codeField2);
        setBackspaceDeleteListener(codeField3);
        setBackspaceDeleteListener(codeField4);
        setBackspaceDeleteListener(codeField5);
        setBackspaceDeleteListener(codeField6);
    }

    /**
     * Adds a listener to handle backspace key press for deleting verification code digits.
     *
     * @param textField The text field to attach the listener to
     */
    private void setBackspaceDeleteListener(TextField textField) {
        textField.addEventHandler(KeyEvent.KEY_PRESSED, event -> {
            if (event.getCode() == KeyCode.BACK_SPACE) {
                String text = textField.getText();
                if (!text.isEmpty()) {
                    String newText = text.substring(0, text.length() - 1);
                    textField.setText(newText);
                    textField.positionCaret(newText.length());
                }
                event.consume();
            }
        });
    }

    /**
     * Handles the action to reset password after successful verification.
     */
    @FXML
    private void handleResetPassword() {
        openChangePasswordView();
    }
}
