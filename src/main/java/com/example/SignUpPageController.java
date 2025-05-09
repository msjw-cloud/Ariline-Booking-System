package com.example;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

import javafx.scene.Node;

public class SignUpPageController implements Initializable {

    @FXML
    Hyperlink SignUp_hyperlink;
    @FXML
    ChoiceBox<String> SignUp_choiceButton;
    String[] str = {"Male","Female"};

    @FXML
    TextField SignUp_username;
    @FXML
    TextField SignUp_password;
    @FXML
    TextField SignUp_email;
    @FXML
    Button SignUp_button;

    @FXML
    Label SignUp_error;


    Stage stage;
    Scene scene;



    public void switchToSignIn(ActionEvent e) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("LoginPage.fxml"));
        Parent root = loader.load();

        scene = new Scene(root);
        stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();

    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        SignUp_choiceButton.getItems().addAll(str);
    }

    public void signedUp(ActionEvent e) throws SQLException, IOException {
        boolean eflag = true;
        boolean sflag = true;
        String username = SignUp_username.getText();
        int password = Integer.parseInt(SignUp_password.getText());
        String email = SignUp_email.getText();
        char gender = 'M'; // Default to male
        if ("Male".equals(SignUp_choiceButton.getValue())) {
            gender = 'M';
        } else {
            gender = 'F';
        }


        Connection connection = JDBC.getConnection();

        PreparedStatement preparedStatement2 = connection.prepareStatement("SELECT username, email FROM security");
        ResultSet  resultSet = preparedStatement2.executeQuery();
        while(resultSet.next()){
            String str = resultSet.getNString(1);
            String str2 = resultSet.getNString(2);
            if(str.equals(username)){
                sflag = false;
            }
            else if(str2.equals(email)){
                eflag = false;
            }

        }

        String query = "INSERT INTO security VALUES (?, ?, ?, ?)";
        PreparedStatement pstatement = connection.prepareStatement(query);
        if(sflag && eflag){
            pstatement.setString(1, username);
            pstatement.setInt(2, password);
            pstatement.setString(3, email);
            pstatement.setString(4, String.valueOf(gender));
            pstatement.executeUpdate();

            SignUpPageController signUpPageController = new SignUpPageController();
            signUpPageController.switchToSignIn(e);
        }
        else{
            if(!eflag){
                SignUp_error.setText("this email already exist ");
            }
            else if(!sflag){
                SignUp_error.setText("this username already exist");
            }

        }

        connection.close();
        pstatement.close();


    }


}
