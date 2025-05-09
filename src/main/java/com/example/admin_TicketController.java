package com.example;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ResourceBundle;

public class admin_TicketController implements Initializable {

    @FXML
    private Button buttonDELETE;

    @FXML
    private Button buttonINSERT;

    @FXML
    private Button buttonUPDATE;

    @FXML
    private TextField tid_;

    @FXML
    private TextField user_;

    @FXML
    private TextField flightid_;

    @FXML
    private TableColumn<Ticket_ad, Integer> flightID;

    @FXML
    private TableColumn<Ticket_ad, Integer> seatID;

    @FXML
    private TextField seatid_;

    @FXML
    private TableView<Ticket_ad> table;

    @FXML
    private TableColumn<Ticket_ad, Integer> ticketID;

    @FXML
    private TextField ticket_search;

    @FXML
    private TextField totalprice_;

    @FXML
    private TableColumn<Ticket_ad, Double> totalPrice;

    @FXML
    private TableColumn<Ticket_ad, String> user;

    ObservableList<Ticket_ad> observableList = FXCollections.observableArrayList();
    FilteredList<Ticket_ad> ticketFilteredList;

    @FXML
    void delteFromTicketTable(ActionEvent event) {
        try{
        Connection connection = JDBC.getConnection();
        String sql = "DELETE FROM ticket WHERE ticketID = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, Integer.parseInt(tid_.getText()));
        preparedStatement.executeUpdate();
        connection.close();
        preparedStatement.close();
        observableList.clear();
        refresh();
        }
        catch(SQLException ex){
            System.out.println(ex.getMessage());
        }
    }

    @FXML
    void insertToTicketTable(ActionEvent event) throws SQLException{
        try{
        Connection connection = JDBC.getConnection();
        String sql = "INSERT INTO ticket (user, flightID, seatID, totalPrice) VALUES(?,?,?,?)";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, user_.getText());
        preparedStatement.setString(2, flightid_.getText());
        preparedStatement.setString(3, seatid_.getText());
        preparedStatement.setString(4, totalprice_.getText());

        preparedStatement.executeUpdate();
        connection.close();
        preparedStatement.close();
        observableList.clear();
        refresh();
        }
        catch(SQLException ex){
            System.out.println("check if such flight (flight-id), seat (seat-id) or passenger (username) acually exist in the system");
        }

    }

    @FXML
    void updateTicektTable(ActionEvent event) throws SQLException{
        try{
        Connection connection = JDBC.getConnection();
        String sql = "UPDATE ticket SET user = ?, flightID = ?, seatID = ?, totalPrice = ? WHERE ticketID = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, user_.getText());
        preparedStatement.setString(2, flightid_.getText());
        preparedStatement.setString(3, seatid_.getText());
        preparedStatement.setString(4, totalprice_.getText());
        preparedStatement.setString(5, tid_.getText());
        preparedStatement.executeUpdate();
        connection.close();
        preparedStatement.close();
        observableList.clear();
        refresh();
        }
        catch(SQLException ex){
            System.out.println("check if such flight (flight-id), seat (seat-id) or passenger (username) acually exist in the system");
        }

    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        refresh();

        ObservableList<Flight_ad> observableList2;
        table.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                tid_.setText(Integer.toString(newSelection.getTicket_id()));
                user_.setText(newSelection.getUser());
                flightid_.setText(Integer.toString(newSelection.getFlight_id()));
                seatid_.setText(Integer.toString(newSelection.getSeat_id()));
                totalprice_.setText(Double.toString(newSelection.getTotal_price()));
            }
        });

    }

    public void refresh(){
        try{
            Connection connection = JDBC.getConnection();
            String sql = "SELECT ticketID, user, flightID, seatID, totalPrice FROM ticket";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                int TIDQuery = resultSet.getInt(1);
                String USER = resultSet.getNString(2);
                int FIDQuery = resultSet.getInt(3);
                int SIDQuery = resultSet.getInt(4);
                double TPQuery = resultSet.getDouble(5);




                observableList.add(new Ticket_ad (TIDQuery,USER,FIDQuery,SIDQuery,TPQuery));

            }
            ticketID.setCellValueFactory(new PropertyValueFactory<>("ticket_id"));
            user.setCellValueFactory(new PropertyValueFactory<>("user"));
            flightID.setCellValueFactory(new PropertyValueFactory<>("flight_id"));
            seatID.setCellValueFactory(new PropertyValueFactory<>("seat_id"));
            totalPrice.setCellValueFactory(new PropertyValueFactory<>("total_price"));

            table.setItems(observableList);
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }

        ticketFilteredList = new FilteredList<>(observableList, e -> true);
        ticket_search.textProperty().addListener((observable, oldValue, newValue) -> {
            ticketFilteredList.setPredicate(Ticket_ad -> {
                if(newValue.isEmpty() || newValue.isBlank() || newValue == null){
                    return true;
                }

                if(Ticket_ad.getTicket_id() == Integer.parseInt(newValue) ){
                    return true;
                }
                else{
                    return false;
                }
            });


                });

        // Listener for destination field


        SortedList<Ticket_ad> sortedList = new SortedList<>(ticketFilteredList);
        sortedList.comparatorProperty().bind(table.comparatorProperty());
        table.setItems(sortedList) ;
    }



}

