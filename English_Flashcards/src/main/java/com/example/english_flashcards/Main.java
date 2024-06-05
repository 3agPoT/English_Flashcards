package com.example.english_flashcards;

import com.example.english_flashcards.util.HibernateUtil;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * The main class to start the JavaFX application.
 */
public class Main extends Application {

    /**
     * The main entry point of the JavaFX application.
     *
     * @param primaryStage The primary stage for the application.
     * @throws IOException If an I/O exception occurs while loading the FXML file.
     */
    @Override
    public void start(Stage primaryStage) throws IOException {
        // Initialize the Hibernate session factory at application start-up
        HibernateUtil.getInstance().getSessionFactory();

        // Load the FXML file for the login view
        Parent root = FXMLLoader.load(getClass().getResource("/com/example/english_flashcards/login-view.fxml"));

        // Set the title for the primary stage
        primaryStage.setTitle("Логін");

        // Create a new scene with the loaded root node
        Scene scene = new Scene(root);

        // Set the scene for the primary stage
        primaryStage.setScene(scene);

        // Disable window resizing
        primaryStage.setResizable(false);

        // Show the primary stage
        primaryStage.show();
    }

    /**
     * The main method to launch the JavaFX application.
     *
     * @param args The command line arguments.
     */
    public static void main(String[] args) {
        launch(args);
    }
}