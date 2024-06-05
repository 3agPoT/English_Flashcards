package com.example.english_flashcards.controller.cards;

import javafx.scene.control.TableCell;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.springframework.stereotype.Controller;
import com.example.english_flashcards.controller.menu.MenuController;
import com.example.english_flashcards.entity.Cards;
import com.example.english_flashcards.entity.User;
import com.example.english_flashcards.repository.impl.CardRepositoryImpl;
import com.example.english_flashcards.service.contact.CardsService;
import com.example.english_flashcards.service.contact.UserService;
import com.example.english_flashcards.service.impl.CardsServiceImpl;
import com.example.english_flashcards.service.impl.UserServiceImpl;

import java.io.File;
import java.io.IOException;
import javafx.animation.FadeTransition;
import javafx.scene.Node;
import javafx.util.Duration;

@Controller
public class EditCardsController {

    private final CardRepositoryImpl cardRepository = new CardRepositoryImpl();
    private final CardsService cardsService = new CardsServiceImpl(cardRepository);
    private int currentPageIndex = 0;
    private int pageSize = 15;
    private ObservableList<Cards> displayedCards = FXCollections.observableArrayList();

    @FXML
    private TableView<Cards> tableView;
    @FXML
    private TableColumn<Cards, Long> idColumn;
    @FXML
    private TableColumn<Cards, String> imageUrlColumn;
    @FXML
    private TableColumn<Cards, String> englishTextColumn;
    @FXML
    private TableColumn<Cards, String> translationColumn;
    @FXML
    private TableColumn<Cards, String> categoryColumn;
    @FXML
    private TextField searchField;
    @FXML
    private AnchorPane moreOptionsPane;
    @FXML
    private Label pageNumberLabel;

    private UserService userService = UserServiceImpl.getInstance();

    private String username;

    private final ObservableList<Cards> cardsObservableList = FXCollections.observableArrayList();

    private User user;

    public void initData(String username) {
        this.username = username;
        this.user = userService.getUserByUsername(username);
    }

    @FXML
    private void initialize() {
        cardsObservableList.addAll(cardsService.getAllCards());
        updateDisplayedCards();

        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        imageUrlColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getImageUrl()));
        englishTextColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEnglishText()));
        translationColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTranslation()));
        categoryColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCategoryName()));

        setColumnAlignment(idColumn, javafx.geometry.Pos.CENTER);
        setColumnAlignment(imageUrlColumn, javafx.geometry.Pos.CENTER);
        setColumnAlignment(englishTextColumn, javafx.geometry.Pos.CENTER);
        setColumnAlignment(translationColumn, javafx.geometry.Pos.CENTER);
        setColumnAlignment(categoryColumn, javafx.geometry.Pos.CENTER);

        tableView.setItems(displayedCards);

        // Enable editing
        tableView.setEditable(true);

        englishTextColumn.setEditable(true);
        translationColumn.setEditable(true);

        englishTextColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        translationColumn.setCellFactory(TextFieldTableCell.forTableColumn());

        englishTextColumn.setOnEditCommit(event -> {
            event.getRowValue().setEnglishText(event.getNewValue());
            cardRepository.save(event.getRowValue()); // Save changes to the database
        });

        translationColumn.setOnEditCommit(event -> {
            event.getRowValue().setTranslation(event.getNewValue());
            cardRepository.save(event.getRowValue()); // Save changes to the database
        });

        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            filterTable(newValue);
        });

        imageUrlColumn.setCellFactory(column -> new TableCell<Cards, String>() {
            private final ImageView imageView = new ImageView();
            @Override
            protected void updateItem(String imageUrl, boolean empty) {
                super.updateItem(imageUrl, empty);
                if (imageUrl == null || empty) {
                    setGraphic(null);
                } else {
                    // Get local image file
                    File file = new File(imageUrl);
                    // Create URL from file
                    String localUrl = file.toURI().toString();
                    // Create image
                    Image image = new Image(localUrl);
                    // Set image for ImageView
                    imageView.setImage(image);
                    imageView.setFitWidth(75); // Adjust width
                    imageView.setPreserveRatio(true);
                    setGraphic(imageView);
                }
            }
        });
    }

    private void setColumnAlignment(TableColumn<?, ?> column, javafx.geometry.Pos alignment) {
        column.setStyle("-fx-alignment: " + alignment.toString().toLowerCase());
    }

    @FXML
    private void showNextPage() {
        if ((currentPageIndex + 1) * pageSize < cardsObservableList.size()) {
            animateTransition(() -> {
                currentPageIndex++;
                updateDisplayedCards();
                updatePageNumberLabel();
            });
        }
    }

    private void updatePageNumberLabel() {
        int pageNumber = currentPageIndex + 1;
        pageNumberLabel.setText(String.valueOf(pageNumber));
    }

    @FXML
    private void showPreviousPage() {
        if (currentPageIndex > 0) {
            animateTransition(() -> {
                currentPageIndex--;
                updateDisplayedCards();
                updatePageNumberLabel();
            });
        }
    }

    private void animateTransition(Runnable transition) {
        FadeTransition fadeOut = new FadeTransition(Duration.seconds(0.5), tableView);
        fadeOut.setFromValue(1);
        fadeOut.setToValue(0);
        fadeOut.setOnFinished(event -> {
            transition.run();
            FadeTransition fadeIn = new FadeTransition(Duration.seconds(0.5), tableView);
            fadeIn.setFromValue(0);
            fadeIn.setToValue(1);
            fadeIn.play();
        });
        fadeOut.play();
    }

    private void updateDisplayedCards() {
        int startIdx = currentPageIndex * pageSize;
        int endIdx = Math.min(startIdx + pageSize, cardsObservableList.size());
        displayedCards.setAll(cardsObservableList.subList(startIdx, endIdx));
    }

    private void filterTable(String searchText) {
        ObservableList<Cards> filteredList = FXCollections.observableArrayList();

        for (Cards card : cardsObservableList) {
            if (card.getEnglishText().toLowerCase().contains(searchText.toLowerCase()) ||
                    card.getTranslation().toLowerCase().contains(searchText.toLowerCase()) ||
                    (card.getCategoryName() != null && card.getCategoryName().toLowerCase().contains(searchText.toLowerCase()))) {
                filteredList.add(card);
            }
        }

        tableView.setItems(filteredList);
    }

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
            stage.setTitle("Меню");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
