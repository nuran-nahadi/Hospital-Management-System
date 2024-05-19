package com.example.hospitalmanagement;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AddStaffController {

    @FXML
    private TableColumn<Staff, String> StaffAddress;

    @FXML
    private TableColumn<Staff, String> StaffDOB;

    @FXML
    private TableColumn<Staff, String> StaffEmail;

    @FXML
    private TableColumn<Staff, String> StaffFullName;

    @FXML
    private TableColumn<Staff, String> StaffPhoneNumber;

    @FXML
    private TableView<Staff> StaffTableview;

    @FXML
    private TableColumn<Staff, String> StaffUserName;

    @FXML
    private Button button_logout;

    @FXML
    private Button button_logout1;

    private Connection connect = null;
    private PreparedStatement prepare = null;
    private ResultSet result = null;
    AlertMessage alert = new AlertMessage();

    @FXML
    public void gotoHome(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setHeaderText("Are you sure you want to close this window");
        //alert.setContentText("");

        if (alert.showAndWait().get().equals(ButtonType.OK)) {
            stage.close();
        } else {
            alert.close();

        }
    }

    public void updateStaffTable() {
        StaffFullName.setCellValueFactory(new PropertyValueFactory<>("fullName"));
        StaffUserName.setCellValueFactory(new PropertyValueFactory<>("userName"));
        StaffEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        StaffPhoneNumber.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        StaffDOB.setCellValueFactory(new PropertyValueFactory<>("dateOfBirth"));
        StaffAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        try {
            StaffTableview.setItems(getStaffs());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ObservableList<Staff> getStaffs() throws SQLException {
        ObservableList<Staff> Stafflist = FXCollections.observableArrayList();
        try (Connection connect = HospitalManagementDatabase.connectDB();
             PreparedStatement ps = connect.prepareStatement("SELECT * FROM staff WHERE status = 0");
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Stafflist.add(new Staff(
                        rs.getString("fullname"),
                        rs.getString("username"),
                        rs.getString("email"),
                        rs.getString("phonenumber"),
                        rs.getString("date_of_birth"),
                        rs.getString("address"),
                        rs.getInt("status")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Stafflist;
    }

    @FXML
    void addStaff(ActionEvent event) {
        connect = HospitalManagementDatabase.connectDB();
        Staff staff = StaffTableview.getSelectionModel().getSelectedItem();
        String sql = "UPDATE staff SET status = ? WHERE username= ?";

        try{
            prepare =connect.prepareStatement(sql);
            prepare.setString(1,"1");
            prepare.setString(2,staff.getUserName());
            prepare.executeUpdate();
            alert.successMessage("Added Successfully");
            updateStaffTable();

        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

}



