package com.example.english_flashcards.controller.menu;

import com.example.english_flashcards.controller.cards.CreatingCardsController;
import com.example.english_flashcards.controller.cards.EditCardsController;
import com.example.english_flashcards.controller.deck.DeckCreationController;
import com.example.english_flashcards.controller.deck.DecksController;
import com.example.english_flashcards.controller.menu.profile.ProfileController;
import com.example.english_flashcards.controller.menu.quests.QuestsController;
import com.example.english_flashcards.controller.menu.shop.ShopController;
import com.example.english_flashcards.entity.User;
import com.example.english_flashcards.service.contact.UserService;
import com.example.english_flashcards.service.impl.UserServiceImpl;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * Controller class for the main menu view.
 */
@Component
public class MenuController {

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
     * Opens the quests view.
     */
    public void quests() {
        openQuestsView();
    }

    /**
     * Opens the shop view.
     */
    public void shop() {
        openShopView();
    }

    /**
     * Opens the profile view.
     */
    public void profile() {
        openProfileView();
    }

    /**
     * Opens the quests view by loading the corresponding FXML file and initializing its controller.
     */
    private void openQuestsView() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/english_flashcards/quests-view.fxml"));
            AnchorPane questsView = loader.load();

            QuestsController questsController = loader.getController();
            questsController.initData(username);

            Stage stage = (Stage) moreOptionsPane.getScene().getWindow();

            stage.setScene(new Scene(questsView));

            stage.setTitle("Квести");

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
     * Handles the button click event for settings.
     */
    @FXML
    private void handleSettingsButtonClick() {
        System.out.println("Налаштування натиснуто!");
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
     * Opens the quests view when the corresponding image button is clicked.
     */
    @FXML
    private void imagequests() {
        openQuestsView();
    }

    /**
     * Opens the shop view when the corresponding image button is clicked.
     */
    @FXML
    private void imageshop() {
        openShopView();
    }

    /**
     * Opens the profile view when the corresponding image button is clicked.
     */
    @FXML
    private void imageusergame() {
        openProfileView();
    }

    /**
     * Opens the view for creating new cards.
     */
    @FXML
    private void creatingcards() {
        openCreatingCardsView();
    }

    /**
     * Opens the view for editing existing cards.
     */
    @FXML
    private void editcards() {
        openEditCardsView();
    }

    /**
     * Opens the view for creating new cards by loading the corresponding FXML file and initializing its controller.
     */
    private void openCreatingCardsView() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/english_flashcards/creatingcards-view.fxml"));
            AnchorPane creatingCardsView = loader.load();

            CreatingCardsController creatingCardsController = loader.getController();
            creatingCardsController.initData(username);

            Stage stage = (Stage) moreOptionsPane.getScene().getWindow();

            stage.setScene(new Scene(creatingCardsView));

            stage.setTitle("Створення карточок");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Opens the view for editing existing cards by loading the corresponding FXML file and initializing its controller.
     */
    private void openEditCardsView() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/english_flashcards/editcards-view.fxml"));
            AnchorPane editCardsView = loader.load();

            EditCardsController editCardsController = loader.getController();
            editCardsController.initData(username);

            Stage stage = (Stage) moreOptionsPane.getScene().getWindow();

            stage.setScene(new Scene(editCardsView));

            stage.setTitle("Редагування карточок");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void openDeckCreationView() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/english_flashcards/deckcreation-view.fxml"));
            AnchorPane creatingCardsView = loader.load();

            DeckCreationController deckCreationController = loader.getController();
            deckCreationController.initData(username);

            Stage stage = (Stage) moreOptionsPane.getScene().getWindow();

            stage.setScene(new Scene(creatingCardsView));

            stage.setTitle("Створення колоди");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void deck() {
        openDeckCreationView();
    }

    private void openDecksView() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/english_flashcards/decks-view.fxml"));
            AnchorPane creatingCardsView = loader.load();

            DecksController decksController = loader.getController();
            decksController.initData(username);

            Stage stage = (Stage) moreOptionsPane.getScene().getWindow();

            stage.setScene(new Scene(creatingCardsView));

            stage.setTitle("Колоди");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void decks() {
        openDecksView();
    }
}