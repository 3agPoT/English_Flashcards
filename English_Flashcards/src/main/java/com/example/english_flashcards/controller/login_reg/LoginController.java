package com.example.english_flashcards.controller.login_reg;

import com.example.english_flashcards.controller.menu.MenuController;
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
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import java.io.IOException;

/**
 * Controller class for handling user login functionality.
 */
public class LoginController {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Label errorLabel;

    private final UserServiceImpl userService = UserServiceImpl.getInstance();

    /**
     * Handles the login action.
     * @param event The ActionEvent triggered by login button click.
     */
    @FXML
    public void menu(ActionEvent event) {
        String username = usernameField.getText();
        String password = passwordField.getText();

        User user = userService.getUserByUsername(username);

        if (user == null) {
            showError("Користувач не знайдений");
            return;
        }

        if (!user.getPassword().equals(password)) {
            showError("Невірний пароль");
            return;
        }

        // Styling for successful login
        usernameField.setStyle("-fx-background-color: transparent; -fx-border-color: #435563; -fx-border-width: 0px 0px 2px 0px;");
        passwordField.setStyle("-fx-background-color: transparent; -fx-border-color: #435563; -fx-border-width: 0px 0px 2px 0px;");

        openMenuView(username);
    }

    /**
     * Opens the registration view.
     * @param event The ActionEvent triggered by register button click.
     */
    @FXML
    public void register(ActionEvent event) {
        openRegisterView();
    }

    /**
     * Opens the password reset view.
     * @param event The ActionEvent triggered by forgot password button click.
     */
    @FXML
    public void forgotPassword(ActionEvent event) {
        openPassResetView();
    }

    /**
     * Opens the main menu view after successful login.
     */
    @FXML
    public void menu() {
        String username = usernameField.getText();
        openMenuView(username);
    }

    /**
     * Displays error message on login failure.
     * @param message The error message to display.
     */
    private void showError(String message) {
        errorLabel.setText(message);
        usernameField.setStyle(usernameField.getStyle() + "-fx-border-color: #FF0000; -fx-border-width: 0px 0px 2px 0px;");
        passwordField.setStyle(passwordField.getStyle() + "-fx-border-color: #FF0000; -fx-border-width: 0px 0px 2px 0px;");
    }

    /**
     * Opens the main menu view with the logged-in user's username.
     * @param username The username of the logged-in user.
     */
    private void openMenuView(String username) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/english_flashcards/menu-view.fxml"));
            AnchorPane menuView = loader.load();

            MenuController menuController = loader.getController();
            menuController.initData(username);

            Stage newStage = new Stage();
            newStage.setScene(new Scene(menuView));
            newStage.setTitle("Меню");
            newStage.setMaximized(true);

            Stage currentStage = (Stage) usernameField.getScene().getWindow();
            currentStage.close();

            newStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Opens the registration view.
     */
    private void openRegisterView() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/english_flashcards/register-view.fxml"));
            Parent root = fxmlLoader.load();

            Stage stage = (Stage) usernameField.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Реєстрація");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Opens the password reset view.
     */
    private void openPassResetView() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/english_flashcards/passreset-view.fxml"));
            Parent root = fxmlLoader.load();

            Stage stage = (Stage) usernameField.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Скидання пароля");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
