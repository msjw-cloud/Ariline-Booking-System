package com.example;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class PHP_TicketPageController implements Initializable {
    static String username;

    @FXML
    Label seatNo;

    @FXML
    private AnchorPane A;

    @FXML
    private Label TotalPrice;

    @FXML
    private VBox V;

    @FXML
    private VBox bookInfo;

    @FXML
    private Button delteTicket;

    @FXML
    private Label from;

    @FXML
    private Label gate;

    @FXML
    private TableView<Ticket> ticketTable;

    @FXML
    private TableColumn<Ticket, Integer> ticketTable_tid;

    @FXML
    private TableColumn<Ticket, String> ticketTable_user;

    @FXML
    private Label to;

    @FXML
    private Label usernameINticket;

    @FXML
    private Label when;

    @FXML
    private Label whenExactly;

    ObservableList<Ticket> observableList = FXCollections.observableArrayList();
    int tid = 0;
    int fid = 0;
    int sid = 0;

    @FXML
    void deleteTicket(ActionEvent event) {
        try{
            Connection connection = JDBC.getConnection();
            String sql = "DELETE FROM ticket WHERE TicketID = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1,tid);
            preparedStatement.executeUpdate();

            sql = "UPDATE seat SET status = 'y' WHERE seatID = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1,sid);
            preparedStatement.executeUpdate();

        }
        catch(SQLException ex){
            System.out.println(ex.getMessage());
        }
        observableList.clear();
        referesh();
        to.setText("TO");
        from.setText("FROM");
        when.setText("DATE");
        whenExactly.setText("TIME");
        gate.setText("Gate-No: ");
        seatNo.setText("Seat-No: ");
        usernameINticket.setText("USERNAME");
        TotalPrice.setText("Total-Price: ");


    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)  {
        referesh();

        ObservableList<Ticket> observableList2;
        ticketTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                try{
                    tid = newSelection.getTicketID();
                    double Tprice=0;
                    Connection connection = JDBC.getConnection();
                    String sql = "SELECT flightID, seatID,totalPrice FROM ticket WHERE TicketID = ?";
                    PreparedStatement preparedStatement = connection.prepareStatement(sql);
                    preparedStatement.setInt(1,tid);
                    ResultSet resultSet = preparedStatement.executeQuery();
                    while(resultSet.next()){
                        fid = resultSet.getInt(1);
                        sid = resultSet.getInt(2);
                        Tprice = resultSet.getDouble(3);
                    }
                    TotalPrice.setText("Total-Price: "+Tprice);
                    // find: from, to, date, time, gate
                    sql = "SELECT source, dest, date, time, gate FROM flight WHERE flightID = ?";
                    preparedStatement = connection.prepareStatement(sql);
                    preparedStatement.setInt(1,fid);
                    resultSet = preparedStatement.executeQuery();
                    while(resultSet.next()){
                        from.setText(resultSet.getNString(1));
                        to.setText(resultSet.getNString(2));
                        when.setText(resultSet.getDate(3).toString());
                        whenExactly.setText(resultSet.getTime(4).toString());
                        gate.setText("Gate-Number: "+resultSet.getNString(5));
                    }
                    // find: seat_no
                    sql = "SELECT SeatNumber FROM seat WHERE seatID = ?";
                    preparedStatement = connection.prepareStatement(sql);
                    preparedStatement.setInt(1,sid);
                    resultSet = preparedStatement.executeQuery();
                    while(resultSet.next()){
                        seatNo.setText("Seat-Number: "+resultSet.getNString(1));
                    }




                }
                catch(SQLException ex){
                    System.out.println(ex.getMessage());
                }




            }
        });
    }

    public void referesh(){
        try{
            System.out.println(username);
            usernameINticket.setText(username);
            //step 1
            Connection connection = JDBC.getConnection();
            String sql = "SELECT user, TicketID FROM ticket WHERE user = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,username);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                String str = resultSet.getNString(1);
                int num = resultSet.getInt(2);
                observableList.add(new Ticket(str,num));
            }
            // step 2
            ticketTable_user.setCellValueFactory(new PropertyValueFactory<>("username"));
            ticketTable_tid.setCellValueFactory(new PropertyValueFactory<>("ticketID"));
            // step 3
            ticketTable.setItems(observableList);
        }
        catch(Exception ex){
            System.out.println(ex.getMessage());
        }

    }
}
