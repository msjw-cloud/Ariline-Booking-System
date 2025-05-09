package com.example;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class WaitlistPageController implements Initializable {

    @FXML
    private Button changeS;

    @FXML
    private TextField enterF;

    @FXML
    private TextField enterS;

    @FXML
    private Button searchF;

    @FXML
    private TableColumn<Waitlist, Integer> seat_id;
    @FXML
    private TableColumn<Waitlist, Integer> flight_id;

    @FXML
    private Label seat_type_label;

    @FXML
    private TableView<Waitlist> table;

    @FXML
    private TableColumn<Waitlist, String> type;

    @FXML
    private TableColumn<Waitlist, String> waitlist;

    @FXML
    private Label waitlist_status_label;

    int currentFlightID;
    int currentSeatID;

    ObservableList<Waitlist> observableList = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try{
            Connection connection = JDBC.getConnection();
            String sql = "SELECT * FROM waitlist";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                int seatIDQuery = resultSet.getInt(1);
                int flightIDQuery = resultSet.getInt(2);
                String typeQuery = resultSet.getNString(3);
                String statusQuery = resultSet.getNString(4);

                observableList.add(new Waitlist(seatIDQuery,flightIDQuery,typeQuery,statusQuery));
            }

            seat_id.setCellValueFactory(new PropertyValueFactory<>("seat_id"));
            flight_id.setCellValueFactory(new PropertyValueFactory<>("flight_id"));
            type.setCellValueFactory(new PropertyValueFactory<>("seat_type"));
            waitlist.setCellValueFactory(new PropertyValueFactory<>("w_status"));

            table.setItems(observableList);

        }
        catch(SQLException ex){
            System.out.println(ex.getMessage());
        }

        //////
        ObservableList<Flight_ad> observableList2;
        table.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                seat_type_label.setText("Seat-Type: " + newSelection.getSeat_type());
                waitlist_status_label.setText(newSelection.getW_status());
                currentFlightID = newSelection.getFlight_id();
                currentSeatID = newSelection.getSeat_id();
            }
        });
        /////

        FilteredList<Waitlist> waitlistFilteredListilteredList = new FilteredList<>(observableList, e -> true);
        enterF.textProperty().addListener((observable, oldValue, newValue) -> {
            waitlistFilteredListilteredList.setPredicate(Waitlist -> {
                if(newValue.isEmpty() || newValue.isBlank() || newValue == null){
                    return true;
                }

                if(Waitlist.getFlight_id() == Integer.parseInt(newValue) ){
                    return true;
                }
                else{
                    return false;
                }
            });
        });

        // Listener for destination field
        SortedList<Waitlist> sortedList = new SortedList<>(waitlistFilteredListilteredList);
        sortedList.comparatorProperty().bind(table.comparatorProperty());
        table.setItems(sortedList) ;

    }

    @FXML
    public void change_seat_status(){
        try{
            Connection connection = JDBC.getConnection();
            String sql = "UPDATE seat SET status = ? WHERE flightID = ? AND seatID = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,enterS.getText().toLowerCase());
            preparedStatement.setInt(2,currentFlightID);
            preparedStatement.setInt(3,currentSeatID);
            preparedStatement.executeUpdate();
        }
        catch(SQLException ex){
            System.out.println(ex.getMessage());
        }
    }
}

