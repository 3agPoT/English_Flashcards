package com.example.english_flashcards.controller.menu.profile;

import com.example.english_flashcards.controller.menu.MenuController;
import com.example.english_flashcards.controller.menu.quests.QuestsController;
import com.example.english_flashcards.controller.menu.shop.ShopController;
import com.example.english_flashcards.entity.User;
import com.example.english_flashcards.service.contact.UserService;
import com.example.english_flashcards.service.impl.UserServiceImpl;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.image.ImageView;
import java.io.File;
import javafx.scene.Cursor;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

/**
 * Controller for the profile view.
 */
public class ProfileController {

    @FXML
    private AnchorPane moreOptionsPane;

    @FXML
    private ImageView explorerButton;

    @FXML
    private AnchorPane formProfile;

    /**
     * Handles hover event over the more options pane.
     */
    @FXML
    private void handleMorePaneHover() {
        moreOptionsPane.setVisible(true);
        moreOptionsPane.toFront();
    }

    /**
     * Handles exit event from the more options pane.
     */
    @FXML
    private void handleMorePaneExit() {
        moreOptionsPane.setVisible(false);
    }

    /**
     * Handles hover event over the more button.
     */
    @FXML
    private void handleMoreButtonHover() {
        moreOptionsPane.setVisible(true);
        moreOptionsPane.toFront();
    }

    /**
     * Handles exit event from the more button.
     */
    @FXML
    private void handleMoreButtonExit() {
        moreOptionsPane.setVisible(false);
    }

    private ImageView userImageView;
    private ImageView editImageView;

    /**
     * Initializes the profile view.
     */
    @FXML
    private void initialize() {
        nameLabel.setText(username);
        Image editImage = new Image(getClass().getResourceAsStream("/com/example/english_flashcards/Images/edit.png"));
        editImageView = new ImageView(editImage);
        editImageView.setFitWidth(35);
        editImageView.setFitHeight(35);

        Image defaultUserImage = new Image(getClass().getResourceAsStream("/com/example/english_flashcards/Images/account.png"));
        userImageView = new ImageView(defaultUserImage);
        userImageView.setFitWidth(200);
        userImageView.setFitHeight(200);
        formProfile.getChildren().add(userImageView);

        AnchorPane.setTopAnchor(userImageView, 0.0);
        AnchorPane.setLeftAnchor(userImageView, 0.0);
        AnchorPane.setRightAnchor(userImageView, 0.0);
        AnchorPane.setBottomAnchor(userImageView, 0.0);

        editImageView.setOnMouseEntered(event -> {
            editImageView.setCursor(Cursor.HAND);
        });
        editImageView.setOnMouseExited(event -> {
            editImageView.setCursor(Cursor.DEFAULT);
        });
        formProfile.getChildren().add(editImageView);

        AnchorPane.setTopAnchor(editImageView, 10.0);
        AnchorPane.setRightAnchor(editImageView, 10.0);

        explorerButton.setOnMouseClicked(event -> {
            openImageFileChooser();
        });
    }

    /**
     * Opens a file chooser to select an image.
     */
    @FXML
    private void openImageFileChooser() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Виберіть Зображення");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Зображення", "*.png", "*.jpg", "*.gif")
        );

        File selectedFile = fileChooser.showOpenDialog(explorerButton.getScene().getWindow());
        if (selectedFile != null) {
            try {
                User user = userService.getUserByUsername(username);
                if (user != null && user.getProfileImage() != null && !user.getProfileImage().isEmpty()) {
                    Path existingImagePath = Paths.get(PROFILE_IMAGE_DIR, user.getProfileImage());
                    if (Files.exists(existingImagePath)) {
                        Image profileImage = new Image(existingImagePath.toUri().toString());
                        userImageView.setImage(profileImage);
                        return;
                    }
                }

                String uniqueFileName = UUID.randomUUID().toString() + "." + getFileExtension(selectedFile.getName());
                Path targetPath = Paths.get(PROFILE_IMAGE_DIR, uniqueFileName);

                Files.createDirectories(targetPath.getParent());
                Files.copy(selectedFile.toPath(), targetPath, StandardCopyOption.REPLACE_EXISTING);

                if (user != null) {
                    String newProfileImagePath = targetPath.getFileName().toString();
                    user.setProfileImage(newProfileImagePath);
                    userService.update(user);
                }

                Image newImage = new Image(targetPath.toUri().toString());
                userImageView.setImage(newImage);

            } catch (IOException e) {
                e.printStackTrace();
                System.err.println("Помилка завантаження зображення: " + e.getMessage());
            }
        }
    }

    /**
     * Opens the main menu view.
     */
    public void menu() {
        openMenuView();
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

    @FXML
    private Label nameLabel;

    @FXML
    private Label registrationLabel;

    private UserService userService = UserServiceImpl.getInstance();

    /**
     * Initializes data for the profile view.
     *
     * @param username The username of the current user.
     */
    public void initData(String username) {
        this.username = username;
        nameLabel.setText(username);

        User user = userService.getUserByUsername(username);

        if (user != null) {
            String profileImagePath = user.getProfileImage();

            if (profileImagePath != null && !profileImagePath.isEmpty()) {
                try {
                    loadProfileImage(profileImagePath);
                } catch (IOException e) {
                    handleProfileImageLoadError(e);
                }
            } else {
                loadDefaultProfileImage();
            }

            registrationLabel.setText("Реєстрація: " + formatRegistrationDate(user.getRegisterTime()));
        }
    }

    private void loadProfileImage(String profileImagePath) throws IOException {
        String imagePathString = PROFILE_IMAGE_DIR + profileImagePath;
        Path imagePath = FileSystems.getDefault().getPath(imagePathString);

        if (Files.exists(imagePath)) {
            Image profileImage = new Image(imagePath.toUri().toString());
            userImageView.setImage(profileImage);
        } else {
            System.err.println("Файл зображення не знайдено: " + imagePath);
        }
    }

    private void handleProfileImageLoadError(IOException e) {
        e.printStackTrace();
        System.err.println("Помилка завантаження зображення профілю: " + e.getMessage());
    }

    private void loadDefaultProfileImage() {
        try {
            String defaultProfileImagePath = "/com/example/english_flashcards/Images/account.png";
            InputStream imageStream = getClass().getResourceAsStream(defaultProfileImagePath);

            Image defaultProfileImage = new Image(imageStream);
            userImageView.setImage(defaultProfileImage);
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Помилка завантаження зображення профілю за умовчанням: " + e.getMessage());
        }
    }

    private String formatRegistrationDate(LocalDateTime registrationDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return formatter.format(registrationDate);
    }

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

    private String username;

    @FXML
    private void imagequests() {
        openQuestsView();
    }

    @FXML
    private void imageshop() {
        openShopView();
    }

    @FXML
    private void imagehouse() {
        openMenuView();
    }

    private final String PROFILE_IMAGE_DIR = "profile_images/";

    private String getFileExtension(String filename) {
        return filename.substring(filename.lastIndexOf(".") + 1);
    }

    private Path targetPath;
}