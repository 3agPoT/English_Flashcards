package com.example.english_flashcards.controller.menu.quests;

import com.example.english_flashcards.controller.menu.MenuController;
import com.example.english_flashcards.controller.menu.profile.ProfileController;
import com.example.english_flashcards.controller.menu.shop.ShopController;
import com.example.english_flashcards.entity.User;
import com.example.english_flashcards.service.contact.UserService;
import com.example.english_flashcards.service.impl.UserServiceImpl;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import java.io.IOException;

/**
 * Controller class for the quests view in the menu.
 */
public class QuestsController {

    @FXML
    private AnchorPane moreOptionsPane;

    /**
     * Handles hover event on more options pane to display it.
     */
    @FXML
    private void handleMorePaneHover() {
        moreOptionsPane.setVisible(true);
        moreOptionsPane.toFront();
    }

    /**
     * Handles exit event from more options pane to hide it.
     */
    @FXML
    private void handleMorePaneExit() {
        moreOptionsPane.setVisible(false);
    }

    /**
     * Handles hover event on more button to display more options pane.
     */
    @FXML
    private void handleMoreButtonHover() {
        moreOptionsPane.setVisible(true);
        moreOptionsPane.toFront();
    }

    /**
     * Handles exit event from more button to hide more options pane.
     */
    @FXML
    private void handleMoreButtonExit() {
        moreOptionsPane.setVisible(false);
    }

    /**
     * Opens the menu view when called.
     */
    public void menu() {
        openMenuView();
    }

    /**
     * Opens the shop view when called.
     */
    public void shop() {
        openShopView();
    }

    /**
     * Opens the profile view when called.
     */
    public void profile() {
        openProfileView();
    }

    /**
     * Opens the menu view by loading the corresponding FXML file and initializing its controller.
     */
    private void openMenuView() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/english_flashcards/menu-view.fxml"));
            AnchorPane shopView = loader.load();

            MenuController menuController = loader.getController();
            menuController.initData(username);

            Stage stage = (Stage) moreOptionsPane.getScene().getWindow();

            stage.setScene(new Scene(shopView));

            stage.setTitle("Меню");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Opens the shop view by loading the corresponding FXML file and initializing its controller.
     */
    private void openShopView() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/english_flashcards/shop-view.fxml"));
            AnchorPane shopView = loader.load();

            ShopController shopController = loader.getController();
            shopController.initData(username);

            Stage stage = (Stage) moreOptionsPane.getScene().getWindow();

            stage.setScene(new Scene(shopView));

            stage.setTitle("Магазин");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Opens the profile view by loading the corresponding FXML file and initializing its controller.
     */
    private void openProfileView() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/english_flashcards/profile-view.fxml"));
            AnchorPane profileView = loader.load();

            ProfileController profileController = loader.getController();
            profileController.initData(username);

            Stage stage = (Stage) moreOptionsPane.getScene().getWindow();

            stage.setScene(new Scene(profileView));

            stage.setTitle("Профіль");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Handles the button click event for exiting and returning to the login view.
     */
    @FXML
    private void handleExitButtonClick() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/english_flashcards/login-view.fxml"));
            AnchorPane loginView = loader.load();

            Stage stage = new Stage();
            stage.setTitle("Логін");
            stage.setScene(new Scene(loginView));

            stage.setResizable(false);

            stage.show();

            Stage currentStage = (Stage) moreOptionsPane.getScene().getWindow();
            currentStage.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private UserService userService = UserServiceImpl.getInstance(); // UserService instance

    private String username; // Username variable

    /**
     * Initializes user data based on the provided username.
     * @param username The username of the current user.
     */
    public void initData(String username) {
        this.username = username;

        User user = userService.getUserByUsername(username);
    }

    /**
     * Opens the profile view when the corresponding image button is clicked.
     */
    @FXML
    private void imageusergame() {
        openProfileView();
    }

    /**
     * Opens the shop view when the corresponding image button is clicked.
     */
    @FXML
    private void imageshop() {
        openShopView();
    }

    /**
     * Opens the menu view when the corresponding image button is clicked.
     */
    @FXML
    private void imagehouse() {
        openMenuView();
    }
}
