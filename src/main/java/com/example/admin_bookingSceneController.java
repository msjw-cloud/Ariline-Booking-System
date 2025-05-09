package com.example;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ResourceBundle;

public class admin_bookingSceneController implements Initializable {

    @FXML
    private TableColumn<Flight_ad, Double> Price;
    @FXML
    private TableColumn<Flight_ad, String> PlainType;
    @FXML
    private TableColumn<Flight_ad, LocalDate> MYear;
    @FXML
    private TableColumn<Flight_ad, String> gate;

    @FXML
    private AnchorPane admin_bookingScene;

    @FXML
    private TableColumn<Flight_ad, LocalDate> date_col;

    @FXML
    private TableColumn<Flight_ad, String> dest_col;

    @FXML
    private TableColumn<Flight_ad, Integer> fid_col;

    @FXML
    private TableColumn<Flight_ad, String> source_col;

    @FXML
    private TableView<Flight_ad> table;

    @FXML
    private TableColumn<Flight_ad, LocalTime> time_col;

    @FXML
    TextField sFiled;
    @FXML
    TextField dFiled;
    @FXML
    TextField sourceINSRT;
    @FXML
    TextField destINSERT;
    @FXML
    TextField dateINSERT;
    @FXML
    TextField timeINSERT;
    @FXML
    TextField fid;
    @FXML
    Button buttonUPDATE;

    //
    @FXML
    TextField priceINSERT;
    @FXML
    TextField gateINSERT;
    @FXML
    TextField ATypeINSERT;
    @FXML
    TextField MYearINSERT;

    //
    @FXML
    TextField bookedF;
    @FXML
    TextField bookedS;
    @FXML
    Label Y;
    @FXML
    Label X;
    //

    ObservableList<Flight_ad> flightAdObservableList = FXCollections.observableArrayList();
    FilteredList<Flight_ad> flightFilteredList;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        refresh();


        ObservableList<Flight_ad> observableList2;
        table.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                fid.setText(Integer.toString(newSelection.getId()));
                sourceINSRT.setText(newSelection.getSource());
                destINSERT.setText(newSelection.getDest());
                dateINSERT.setText(newSelection.getDate().toString());
                timeINSERT.setText(newSelection.getTime().toString());

                priceINSERT.setText(newSelection.getPrice() + "");
                gateINSERT.setText(newSelection.getGate());
                ATypeINSERT.setText(newSelection.getAType());
                MYearINSERT.setText(newSelection.getMYear().toString());


            }
        });

    }

    private void filterRecords() {
        flightFilteredList.setPredicate(flight -> {
            String sourceText = sFiled.getText().toLowerCase();
            String destText = dFiled.getText().toLowerCase();

            if (sourceText.isEmpty() && destText.isEmpty()) {
                return true; // Show all records if both fields are empty
            }

            boolean sourceMatch = sourceText.isEmpty() || flight.getSource().toLowerCase().contains(sourceText);
            boolean destMatch = destText.isEmpty() || flight.getDest().toLowerCase().contains(destText);

            return sourceMatch && destMatch;
        });
    }

    @FXML
    void deleteFromFlighTable(ActionEvent event) {
        try {
            Connection connection = JDBC.getConnection();
            ///
            String sql = "DELETE FROM waitlist WHERE flightID = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, Integer.parseInt(fid.getText()));
            preparedStatement.executeUpdate();
            ///
            sql = "DELETE FROM ticket WHERE flightID = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, Integer.parseInt(fid.getText()));
            preparedStatement.executeUpdate();
            ///
            sql = "DELETE FROM seat WHERE flightID = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, Integer.parseInt(fid.getText()));
            preparedStatement.executeUpdate();
            ///
            sql = "DELETE FROM flight WHERE flightID = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, Integer.parseInt(fid.getText()));
            preparedStatement.executeUpdate();
            ///
            connection.close();
            preparedStatement.close();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        finally {
            flightAdObservableList.clear();
            refresh();
        }
    }

    @FXML
    void insertToFlightTable(ActionEvent event) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = JDBC.getConnection();
            String sql = "INSERT INTO flight (source, dest, date, time, price, gate, AType, MYear) VALUES(?,?,?,?,?,?,?,?)";
            preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, sourceINSRT.getText());
            preparedStatement.setString(2, destINSERT.getText());
            preparedStatement.setDate(3, java.sql.Date.valueOf(dateINSERT.getText()));
            preparedStatement.setTime(4, java.sql.Time.valueOf(timeINSERT.getText() + ":00"));

            preparedStatement.setDouble(5, Double.parseDouble(priceINSERT.getText()));
            preparedStatement.setString(6, gateINSERT.getText());
            preparedStatement.setString(7, ATypeINSERT.getText());
            preparedStatement.setDate(8, java.sql.Date.valueOf(MYearINSERT.getText()));
            preparedStatement.executeUpdate();

            //update seat table
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            int generatedFlightID = 0;
            if (generatedKeys.next()) {
                generatedFlightID = generatedKeys.getInt(1); // Assuming flightID is the first auto-generated key column
                System.out.println("Generated Flight ID: " + generatedFlightID);
                // Now you have the value of the auto-incremented flightID
                // Use it as needed
            }

            String[] airbus_stype = {"f","f","f","f","f","f","f","f","b","b","b","b","b","b","b","b","e","e","e","e","e","e","e","e","e","e","e","e","e","e","e","e"};
            String[] airbus_sNumber = {"1a","1b","1c","1d","2a","2b","2c","2d","3a","3b","3c","3d","4a","4b","4c","4d","5a","5b","5c","5d","6a","6b","6c","6d","7a","7b","7c","7d","8a","8b","8c","8d"};

            String[] boeing_stype = {"f","f","f","f","f","f","f","f","f","f","f","f","b","b","b","b","b","b","b","b","b","b","b","b","e","e","e","e","e","e","e","e","e","e","e","e","e","e","e","e","e","e"};
            String[] boeing_sNumber = {"1a","1b","1c","1d","1e","1f","2a","2b","2c","2d","2e","2f","3a","3b","3c","3d","3e","3f","4a","4b","4c","4d","4e","4f","5a","5b","5c","5d","5e","5f","6a","6b","6c","6d","6e","6f","7a","7b","7c","7d","7e","7f"};

            if(ATypeINSERT.getText().equals("Airbus")){
                sql = "INSERT INTO seat (seatNumber,seatType,status,AType,MYear,flightID) VALUES (?,?,?,?,?,?)";
                for(int i=0; i < airbus_stype.length ; ++i){
                    preparedStatement = connection.prepareStatement(sql);
                    preparedStatement.setString(1,airbus_sNumber[i]);
                    preparedStatement.setString(2,airbus_stype[i]);
                    if(airbus_sNumber[i].equals("2c") || airbus_sNumber[i].equals("2d") || airbus_sNumber[i].equals("4c") || airbus_sNumber[i].equals("4d") || airbus_sNumber[i].equals("8c") || airbus_sNumber[i].equals("8d"))
                    {
                        preparedStatement.setString(3,"n");
                    }
                    else
                    {
                        preparedStatement.setString(3,"y");
                    }
                    preparedStatement.setString(4,"Airbus");
                    preparedStatement.setString(5,"2020-01-10");
                    preparedStatement.setInt(6,generatedFlightID);
                    preparedStatement.executeUpdate();
                }
            }
            if(ATypeINSERT.getText().equals("Boeing")){
                sql = "INSERT INTO seat (seatNumber,seatType,status,AType,MYear,flightID) VALUES (?,?,?,?,?,?)";
                for(int i=0; i < boeing_stype.length ; ++i){
                    preparedStatement = connection.prepareStatement(sql);
                    preparedStatement.setString(1,boeing_sNumber[i]);
                    preparedStatement.setString(2,boeing_stype[i]);
                    if(boeing_sNumber[i].equals("2e") || boeing_sNumber[i].equals("2f") || boeing_sNumber[i].equals("4e") || boeing_sNumber[i].equals("4f") || boeing_sNumber[i].equals("7e") || boeing_sNumber[i].equals("7f"))
                    {
                        preparedStatement.setString(3,"n");
                    }
                    else
                    {
                        preparedStatement.setString(3,"y");
                    }
                    preparedStatement.setString(4,"Boeing");
                    preparedStatement.setString(5,"2021-01-10");
                    preparedStatement.setInt(6,generatedFlightID);
                    preparedStatement.executeUpdate();
                }



            }

            sql = "SELECT seatID, seatType FROM seat WHERE flightID = ? AND status = 'n'";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1,generatedFlightID);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                int seatIDQuery = resultSet.getInt(1);
                String seatTypeQuery = resultSet.getNString(2);
                sql = "INSERT INTO waitlist VALUES(?,?,?,'n')";
                preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setInt(1,seatIDQuery);
                preparedStatement.setInt(2,generatedFlightID);
                preparedStatement.setString(3,seatTypeQuery);
                preparedStatement.executeUpdate();

            }


        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            System.out.println("there is no such plain that is consistent with what you entered (plain-type, manufacture-year)");
        }
        finally{
            try{
                connection.close();
                preparedStatement.close();
            }
            catch(SQLException ex){
                System.out.println(ex.getMessage());
            }
            flightAdObservableList.clear();
            refresh();
        }

    }

    public void updateFlighTable(ActionEvent e) {
        try {
            Connection connection = JDBC.getConnection();
            String sql = "UPDATE flight SET source = ?, dest = ?, date = ?, time = ? WHERE flightID = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, sourceINSRT.getText());
            preparedStatement.setString(2, destINSERT.getText());
            preparedStatement.setDate(3, java.sql.Date.valueOf(dateINSERT.getText()));
            preparedStatement.setTime(4, java.sql.Time.valueOf(timeINSERT.getText() + ":00"));
            preparedStatement.setInt(5, Integer.parseInt(fid.getText()));
            preparedStatement.executeUpdate();
            connection.close();
            preparedStatement.close();
        } catch (SQLException ex) {
            System.out.println("there is no such plain that is consistent with what you entered (plain-type, manufacture-year)");
        }
        finally {
            flightAdObservableList.clear();
            refresh();
        }
    }

    public void refresh() {
        try {
            Connection connection = JDBC.getConnection();
            String sql = "SELECT flightID, source, dest, date, time, price,gate, AType, MYear FROM flight";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Integer FIDQuery = resultSet.getInt(1);
                String sourceQuery = resultSet.getNString(2);
                String destQuery = resultSet.getNString(3);
                LocalDate dateQuery = resultSet.getDate(4).toLocalDate();
                LocalTime timeQuery = resultSet.getTime(5).toLocalTime();

                double priceQuery = resultSet.getDouble(6);
                String gateQuery = resultSet.getNString(7);
                String ATypeQuery = resultSet.getNString(8);
                LocalDate MYearQuery = resultSet.getDate(9).toLocalDate();


                flightAdObservableList.add(new Flight_ad(FIDQuery, sourceQuery, destQuery, dateQuery, timeQuery, priceQuery, gateQuery, ATypeQuery, MYearQuery));

            }
            fid_col.setCellValueFactory(new PropertyValueFactory<>("id"));
            source_col.setCellValueFactory(new PropertyValueFactory<>("source"));
            dest_col.setCellValueFactory(new PropertyValueFactory<>("dest"));
            date_col.setCellValueFactory(new PropertyValueFactory<>("date"));
            time_col.setCellValueFactory(new PropertyValueFactory<>("time"));

            Price.setCellValueFactory(new PropertyValueFactory<>("price"));
            gate.setCellValueFactory(new PropertyValueFactory<>("gate"));
            PlainType.setCellValueFactory(new PropertyValueFactory<>("AType"));
            MYear.setCellValueFactory(new PropertyValueFactory<>("MYear"));

            table.setItems(flightAdObservableList);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        flightFilteredList = new FilteredList<>(flightAdObservableList, e -> true);
        sFiled.textProperty().addListener((observable, oldValue, newValue) -> filterRecords());

        // Listener for destination field
        dFiled.textProperty().addListener((observable, oldValue, newValue) -> filterRecords());

        SortedList<Flight_ad> sortedList = new SortedList<>(flightFilteredList);
        sortedList.comparatorProperty().bind(table.comparatorProperty());
        table.setItems(sortedList);
    }

    @FXML
    void calculate01(ActionEvent event) {
        String date = bookedF.getText();
        int num = 0;
        int all = 0;
        try{
            Connection connection = JDBC.getConnection();
            String sql = "SELECT flightID FROM flight WHERE date = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,date);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                int fid = resultSet.getInt(1);
                sql = "SELECT COUNT(*) AS count FROM ticket WHERE flightID = ?";
                preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setInt(1,fid);
                resultSet = preparedStatement.executeQuery();
                while(resultSet.next()){
                    num += resultSet.getInt(1);
                }
            }
            sql = "SELECT COUNT(*) AS count FROM flight WHERE date = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,date);
            resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                all = resultSet.getInt(1);
            }
            X.setText((((double)num/all)*100)+"%");
        }
        catch (SQLException ex){
            System.out.println(ex.getMessage());
        }


    }

    @FXML
    void calculate02(ActionEvent event) {
        String fid = bookedS.getText();
        int booked_seats = 0;
        int all_seats = 0;
        try{
            Connection connection = JDBC.getConnection();
            String sql = "SELECT COUNT(*) AS count FROM seat WHERE flightID = ? AND status = 'n'";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,fid);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                 booked_seats = resultSet.getInt(1);
            }
            sql = "SELECT COUNT(*) AS count FROM seat WHERE flightID = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,fid);
            resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                all_seats = resultSet.getInt(1);
            }
            System.out.println(booked_seats);
            System.out.println(all_seats);


            Y.setText((((double)booked_seats/all_seats)*100)+"%");

            connection.close();
            preparedStatement.close();

        }
        catch(SQLException ex){
            System.out.println(ex.getMessage());
        }


    }

}

