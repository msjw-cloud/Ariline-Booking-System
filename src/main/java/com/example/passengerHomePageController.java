package com.example;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;

import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;


public class passengerHomePageController implements Initializable {
    @FXML
    private TableColumn<Flight, Integer> FID;
    @FXML
    private TableColumn<Flight, Double> price;

    @FXML
    private TableColumn<Flight, LocalDate> date;

    @FXML
    private TableColumn<Flight, String> dest;

    @FXML
    private AnchorPane php_background;

    @FXML
    private AnchorPane php_rightPane;

    @FXML
    private TableColumn<Flight, String> source;

    @FXML
    private TableView<Flight> table;

    @FXML
    private TableColumn<Flight, LocalTime> time;
    @FXML
    TextField sourceFiled;
    @FXML
    TextField destFiled;
    @FXML
    BorderPane php_switchedBackground;
    @FXML
    Button booking,payment;
    @FXML
    Label from,to,when,whenExactly;
    @FXML
    Label gate;
    @FXML
    RadioButton RadioB_F;
    @FXML
    RadioButton RadioB_B;
    @FXML
    RadioButton RadioB_E;
    @FXML
    Label TotalPrice;
    @FXML
    Label welcome;
    @FXML
    Label usernameINticket;

    String seatNumberSavedForLater;

    @FXML
    ChoiceBox<String> seat_ChoiceBox = new ChoiceBox<>();

    public Button addTicket;


    double currentFPrice;
    static String currentFAType;
    static LocalDate currentMYear;
    static int currentSeatID;
    static int currentFlightID;
    double seatPrice = 0;
    String currentSeatType;
    int ticket_id = 0;


    Node initialPane;

    ObservableList<Flight> flightObservableList = FXCollections.observableArrayList();
    FilteredList<Flight> flightFilteredList;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        initialPane = php_switchedBackground.getCenter();
        try {

            Connection connection = JDBC.getConnection();
            String sql = "SELECT flightID, source, dest, date, time, price FROM flight";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                Integer FIDQuery = resultSet.getInt(1);
                String sourceQuery = resultSet.getNString(2);
                String destQuery = resultSet.getNString(3);
                LocalDate dateQuery = resultSet.getDate(4).toLocalDate();
                LocalTime timeQuery = resultSet.getTime(5).toLocalTime();
                Double priceQuery = resultSet.getDouble(6);

                flightObservableList.add(new Flight(FIDQuery,sourceQuery,destQuery,dateQuery,timeQuery,priceQuery));

            }
            FID.setCellValueFactory(new PropertyValueFactory<>("flightID"));
            source.setCellValueFactory(new PropertyValueFactory<>("source"));
            dest.setCellValueFactory(new PropertyValueFactory<>("dest"));
            date.setCellValueFactory(new PropertyValueFactory<>("date"));
            time.setCellValueFactory(new PropertyValueFactory<>("time"));
            price.setCellValueFactory(new PropertyValueFactory<>("price"));

            table.setItems(flightObservableList);


            flightFilteredList = new FilteredList<>(flightObservableList, e -> true);
            sourceFiled.textProperty().addListener((observable, oldValue, newValue) -> filterRecords());

            // Listener for destination field
            destFiled.textProperty().addListener((observable, oldValue, newValue) -> filterRecords());

            SortedList<Flight> sortedList = new SortedList<>(flightFilteredList);
            sortedList.comparatorProperty().bind(table.comparatorProperty());
            table.setItems(sortedList);

            ObservableList<Flight_ad> observableList2;
            table.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
                if (newSelection != null) {
                    from.setText(newSelection.getSource());
                    to.setText(newSelection.getDest());
                    when.setText(newSelection.getDate().toString());
                    whenExactly.setText(newSelection.getTime().toString());
                    gate.setText("Gate-No: "+getGate(newSelection.getFlightID()));
                    currentFPrice = newSelection.getPrice();
                    currentMYear = getCurrentMYear(newSelection.getFlightID());
                    currentFAType = getAirPlaneType(newSelection.getFlightID());
                    System.out.println(currentFAType);
                    TotalPrice.setText("Current-Price: "+currentFPrice);
                    currentFlightID = newSelection.getFlightID();
                    if(RadioB_F.isSelected()){
                        seat_ChoiceBox.getItems().clear();
                        populateDataIntoChoiceBox_F();
                    }
                    if(RadioB_E.isSelected()){
                        seat_ChoiceBox.getItems().clear();
                        populateDataIntoChoiceBox_E();
                    }
                    if(RadioB_B.isSelected()){
                        seat_ChoiceBox.getItems().clear();
                        populateDataIntoChoiceBox_B();
                    }


                }
            });

            RadioB_F.setOnAction(event -> {
                if (table.getSelectionModel().getSelectedItem() != null) {
                    seat_ChoiceBox.getItems().clear();
                    populateDataIntoChoiceBox_F();
                }
                String sql2 = "SELECT FCSeatPrice FROM aircraft WHERE AType = ?";
                try{
                    Connection connection2 = JDBC.getConnection();
                    PreparedStatement preparedStatement2 = connection2.prepareStatement(sql2);
                    preparedStatement2.setString(1,currentFAType);
                    ResultSet resultSet2 = preparedStatement2.executeQuery();
                    while(resultSet2.next()){
                        seatPrice = resultSet2.getDouble(1);
                    }

                }
                catch(SQLException ex){
                    System.out.println("101010101");
                }

                TotalPrice.setText("Total-Price: "+(currentFPrice+seatPrice)+"");
            });

            RadioB_E.setOnAction(event -> {
                if (table.getSelectionModel().getSelectedItem() != null) {
                    seat_ChoiceBox.getItems().clear();
                    populateDataIntoChoiceBox_E();
                }
                String sql2 = "SELECT ECSeatPrice FROM aircraft WHERE AType = ?";
                try{
                    Connection connection2 = JDBC.getConnection();
                    PreparedStatement preparedStatement2 = connection2.prepareStatement(sql2);
                    preparedStatement2.setString(1,currentFAType);
                    ResultSet resultSet2 = preparedStatement2.executeQuery();
                    while(resultSet2.next()){
                        seatPrice = resultSet2.getDouble(1);
                    }

                }
                catch(SQLException ex){
                    System.out.println("101010101");
                }

                TotalPrice.setText("Total-Price: "+(currentFPrice+seatPrice)+"");
            });

            RadioB_B.setOnAction(event -> {
                if (table.getSelectionModel().getSelectedItem() != null) {
                    seat_ChoiceBox.getItems().clear();
                    populateDataIntoChoiceBox_B();
                }
                String sql2 = "SELECT BCSeatPrice FROM aircraft WHERE AType = ?";
                try{
                    Connection connection2 = JDBC.getConnection();
                    PreparedStatement preparedStatement2 = connection2.prepareStatement(sql2);
                    preparedStatement2.setString(1,currentFAType);
                    ResultSet resultSet2 = preparedStatement2.executeQuery();
                    while(resultSet2.next()){
                        seatPrice = resultSet2.getDouble(1);
                    }

                }
                catch(SQLException ex){
                    System.out.println("101010101");
                }

                TotalPrice.setText("Total-Price: "+(currentFPrice+seatPrice)+"");
            });


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        System.out.println(usernameINticket.getText());

    }

    private void filterRecords() {
        flightFilteredList.setPredicate(flight -> {
            String sourceText = sourceFiled.getText().toLowerCase();
            String destText = destFiled.getText().toLowerCase();

            if (sourceText.isEmpty() && destText.isEmpty()) {
                return true; // Show all records if both fields are empty
            }

            boolean sourceMatch = sourceText.isEmpty() || flight.getSource().toLowerCase().contains(sourceText);
            boolean destMatch = destText.isEmpty() || flight.getDest().toLowerCase().contains(destText);

            return sourceMatch && destMatch;
        });
    }

    public void  switchToPayment(ActionEvent e) throws IOException {
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("Payment.fxml"));
        php_switchedBackground.setCenter(anchorPane);

    }

    public void  switchToBooking(ActionEvent e) throws IOException {
        seat_ChoiceBox.getItems().clear();
        from.setText("FROM");
        to.setText("TO");
        when.setText("DATE");
        whenExactly.setText("TIME");
        TotalPrice.setText("Total-Price: ");
        gate.setText("Gate-No: ");

        php_switchedBackground.setCenter(initialPane);

    }

    public String getGate(int flightID) {
        String str = "";
        try {
            Connection connection = JDBC.getConnection();
            String sql = "SELECT gate FROM flight WHERE flightID = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, flightID);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                str = resultSet.getNString(1);
            }
        }
        catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return str;
    }

    /*public void getClass(ActionEvent e){
        String sql = "";
        if(RadioB_F.isSelected()){
            sql = "SELECT FCSeatPrice FROM aircraft WHERE AType = ?";
            currentSeatType = "First Class";
        }
        else if(RadioB_E.isSelected()){
            sql = "SELECT ECSeatPrice FROM aircraft WHERE AType = ?";
            currentSeatType = "Economy Class";
        }
        else if(RadioB_B.isSelected()){
            sql = "SELECT BCSeatPrice FROM aircraft WHERE AType = ?";
            currentSeatType = "Business Class";
        }


        try{
            Connection connection = JDBC.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,currentFAType);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                seatPrice = resultSet.getDouble(1);
            }

        }
        catch(SQLException ex){
            System.out.println("101010101");
        }

        TotalPrice.setText("Total-Price: "+(currentFPrice+seatPrice)+"");





    }*/

    public String getAirPlaneType(int flightID){
        String str = "";
        try {
            Connection connection = JDBC.getConnection();
            String sql = "SELECT AType FROM flight WHERE flightID = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, flightID);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                str = resultSet.getNString(1);
            }
        }
        catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return str;
    }

    public LocalDate getCurrentMYear(int flightID){
        LocalDate date1 = null;
        try {
            Connection connection = JDBC.getConnection();
            String sql = "SELECT MYear FROM flight WHERE flightID = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, flightID);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                date1 = resultSet.getDate(1).toLocalDate();
            }
        }
        catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return date1;
    }
    public int getCurrentSeatID(String AType,LocalDate MYear){
        int id = 0;
        try {
            Connection connection = JDBC.getConnection();
            String sql = "SELECT seatID FROM seat WHERE AType = ? AND MYear = ? AND seatNumber = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setNString(1, AType);
            preparedStatement.setDate(2, java.sql.Date.valueOf(MYear));
            preparedStatement.setNString(3,seatNumberSavedForLater);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                id = resultSet.getInt(1);
            }

        }
        catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return id;
    }

    public void populateDataIntoChoiceBox_F(){
        seat_ChoiceBox.getItems().clear();
        try{
            Connection connection = JDBC.getConnection();
            String sql = "SELECT seatNumber FROM seat WHERE status = 'y' AND AType = ? AND MYear = ? AND flightID = ? AND seatType = 'f'";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,currentFAType);
            preparedStatement.setDate(2,java.sql.Date.valueOf(currentMYear));
            preparedStatement.setInt(3,currentFlightID);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                seat_ChoiceBox.getItems().add(resultSet.getNString(1));
            }

        }
        catch(SQLException e){
            System.out.println(e.getMessage());
        }
    }
    public void populateDataIntoChoiceBox_B(){
        seat_ChoiceBox.getItems().clear();
        try{
            Connection connection = JDBC.getConnection();
            String sql = "SELECT seatNumber FROM seat WHERE status = 'y' AND AType = ? AND MYear = ? AND flightID = ? AND seatType = 'b'";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,currentFAType);
            preparedStatement.setDate(2,java.sql.Date.valueOf(currentMYear));
            preparedStatement.setInt(3,currentFlightID);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                seat_ChoiceBox.getItems().add(resultSet.getNString(1));
            }

        }
        catch(SQLException e){
            System.out.println(e.getMessage());
        }
    }
    public void populateDataIntoChoiceBox_E(){
        seat_ChoiceBox.getItems().clear();
        try{
            Connection connection = JDBC.getConnection();
            String sql = "SELECT seatNumber FROM seat WHERE status = 'y' AND AType = ? AND MYear = ? AND flightID = ? AND seatType = 'e'";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,currentFAType);
            preparedStatement.setDate(2,java.sql.Date.valueOf(currentMYear));
            preparedStatement.setInt(3,currentFlightID);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                seat_ChoiceBox.getItems().add(resultSet.getNString(1));
            }

        }
        catch(SQLException e){
            System.out.println(e.getMessage());
        }
    }

    public void insertToTicketTable() throws IOException{
        seatNumberSavedForLater = seat_ChoiceBox.getValue();
        currentSeatID = getCurrentSeatID(currentFAType,currentMYear);
        System.out.println(currentSeatID);
        System.out.println(usernameINticket.getText().toLowerCase());
        System.out.println(seatNumberSavedForLater);
        try {
            Connection connection = JDBC.getConnection();
            String sql = "INSERT INTO ticket (user,flightID,seatID,totalPrice) VALUES(?,?,?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, usernameINticket.getText().toLowerCase());
            preparedStatement.setInt(2, currentFlightID);
            preparedStatement.setInt(3, currentSeatID);
            preparedStatement.setDouble(4, (currentFPrice + seatPrice));
            preparedStatement.executeUpdate();

            // Retrieve the auto-generated keys
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                // Retrieve the generated ticketID
                ticket_id = generatedKeys.getInt(1);
            } else {
                // Handle the case where no keys were generated
                throw new SQLException("Failed to retrieve auto-generated key for ticketID");
            }

            // Now, let's update the seat status
            String updateSql = "UPDATE seat SET status = 'n' WHERE seatID = ?";
            PreparedStatement updateStatement = connection.prepareStatement(updateSql);
            updateStatement.setInt(1, currentSeatID);
            updateStatement.executeUpdate();

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }


        switchToTicket();


    }

    public void switchToTicket() throws IOException{
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("PHP_TicketPage.fxml"));
        php_switchedBackground.setCenter(anchorPane);


    }

}
