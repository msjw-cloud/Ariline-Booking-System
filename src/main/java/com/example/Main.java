package com.example;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;


public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("LoginPage.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("LoginPage.css").toExternalForm());
        stage.setScene(scene);
        stage.setTitle("ICS321 Project");
        stage.getIcons().add(new Image("image01.jpg"));
        stage.show();

        stage.setOnCloseRequest(e -> {
            e.consume();
            exit(stage);
        });
    }

    public static void main(String[] args) {
        launch();
    }

    public void exit(Stage stage){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Alert");
        alert.setHeaderText("Confirmation message");
        alert.setContentText("you will exit without saving!");
        if(alert.showAndWait().get() == ButtonType.OK){
            stage.close();
        }

    }


}