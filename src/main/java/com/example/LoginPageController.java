package com.example;

import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import javafx.scene.Node;
public class LoginPageController implements Initializable{

    @FXML
    ImageView plainImage;
    @FXML
    Hyperlink login_hyperlink;
    @FXML
    Button Signin_button;
    @FXML
    TextField Login_username,Login_password;
    @FXML
    Label LginPage_error;
    @FXML
    CheckBox Login_admin;



    Stage stage;
    Scene scene;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        TranslateTransition translate = new TranslateTransition();
        translate.setNode(plainImage);
        translate.setDuration(Duration.millis(1000));
        translate.setCycleCount(1);
        translate.setByX(400);
        translate.play();
    }

    public void switchToSignUpPage(ActionEvent e) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("SginUpPage.fxml"));
        Parent root = loader.load();

        scene = new Scene(root);
        stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();

    }

    public void switchToPassengerHP(ActionEvent e) throws IOException, SQLException {
        if(Login_admin.isSelected()){
            if(Login_username.getText().equals("admin") && Login_password.getText().equals("0")){
                FXMLLoader loader = new FXMLLoader(getClass().getResource("adminHomePage.fxml"));
                Parent root = loader.load();

                scene = new Scene(root);
                stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
                stage.setScene(scene);
                stage.show();
            }
            else{
                LginPage_error.setText("you are not the admin");
            }

        }
        else {
            boolean flag = false;
            Connection connection = JDBC.getConnection();
            //check username
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT COUNT(*) AS count FROM security WHERE username = ? AND password = ?");
            preparedStatement.setString(1, Login_username.getText());
            preparedStatement.setString(2,Login_password.getText());
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int count = resultSet.getInt("count");
                flag = (count > 0);
            }



            if (flag) {
                if (!Login_admin.isSelected()) {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("passengerHomePage.fxml"));
                    Parent root = loader.load();
                    FXMLLoader loader1 = new FXMLLoader(getClass().getResource("PHP_TicketPage.fxml"));
                    Parent root1 = loader1.load();

                    ///
                    passengerHomePageController passengerHomePageController = loader.getController();
                    passengerHomePageController.welcome.setText("Welcome "+Login_username.getText().toUpperCase());
                    passengerHomePageController.usernameINticket.setText(Login_username.getText().toUpperCase());

                    PHP_TicketPageController phpTicketPageController = loader1.getController();
                    PHP_TicketPageController.username = Login_username.getText().toLowerCase();

                    ///

                    scene = new Scene(root);
                    stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
                    stage.setScene(scene);
                    stage.show();
                } else {
                    LginPage_error.setText("you are not the admin");
                }
            }
            else {
                LginPage_error.setText("incorrect password or username");
            }
        }

    }



    /*public void submit(ActionEvent e){
        ProgressBar progressBar = new ProgressBar();
        pane.setBottom(progressBar);
    }*/
}
