package com.example;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class adminHomePageController {

    @FXML
    private BorderPane adm_borderPane;

    @FXML
    private VBox adminPage_RightPane;

    @FXML
    private AnchorPane adminPage_background;

    @FXML
    private Button booking;

    @FXML
    private Button waitlist;

    @FXML
    private Button plain;



    @FXML
    void switchToBooking(ActionEvent event) throws IOException {
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("admin_bookingSecene.fxml"));
        adm_borderPane.setCenter(anchorPane);
    }

    @FXML
    void switchToWaitlist(ActionEvent event) throws IOException{
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("Waitlist.fxml"));
        adm_borderPane.setCenter(anchorPane);
    }

    @FXML
    void switchToPlain(ActionEvent event) throws IOException{
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("admin_Ticket.fxml"));
        adm_borderPane.setCenter(anchorPane);
    }

}

