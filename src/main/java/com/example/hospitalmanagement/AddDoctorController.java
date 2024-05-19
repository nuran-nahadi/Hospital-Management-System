package com.example.hospitalmanagement;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class AddDoctorController implements Initializable {

    @FXML
    private TableView<Doctor> DoctorTableview;

    @FXML
    private Button button_add;

    @FXML
    private Button button_cancel;

    @FXML
    private TableColumn<Doctor, String> dAddress;

    @FXML
    private TableColumn<Doctor, String> dDateOfbirth;

    @FXML
    private TableColumn<Doctor, String> dEduQualification;

    @FXML
    private TableColumn<Doctor, String> dEmail;

    @FXML
    private TableColumn<Doctor, String> dFullName;

    @FXML
    private TableColumn<Doctor, String> dPhoneNum;

    @FXML
    private TableColumn<Doctor, String> dSpecialization;

    @FXML
    private TableColumn<Doctor, String> dUserName;



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



    public void updateDoctorTable()throws SQLException {

    dFullName.setCellValueFactory(new PropertyValueFactory<>("fullName"));
    dUserName.setCellValueFactory(new PropertyValueFactory<>("userName"));
    dEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
    dPhoneNum.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
    dDateOfbirth.setCellValueFactory(new PropertyValueFactory<>("dateOfBirth"));
    dSpecialization.setCellValueFactory(new PropertyValueFactory<>("specialization"));
    dEduQualification.setCellValueFactory(new PropertyValueFactory<>("educationalQualification"));
    dAddress.setCellValueFactory(new PropertyValueFactory<>("address"));

        try {
            DoctorTableview.setItems(getDoctors());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            updateDoctorTable();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ObservableList<Doctor> getDoctors() throws SQLException {
        ObservableList<Doctor> list = FXCollections.observableArrayList();
        try (Connection connect = HospitalManagementDatabase.connectDB();
             PreparedStatement ps = connect.prepareStatement("SELECT * FROM doctor WHERE status = 0");
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(new Doctor(
                        rs.getString("fullname"),
                        rs.getString("username"),
                        rs.getString("email"),
                        rs.getString("password"),
                        rs.getString("phonenumber"),
                        rs.getString("date_of_birth"),
                        rs.getString("specialization"),
                        rs.getString("educationalqualification"),
                        rs.getString("address"),
                        rs.getInt("status")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    public void addDoctor() {
        connect = HospitalManagementDatabase.connectDB();
        Doctor doctor = DoctorTableview.getSelectionModel().getSelectedItem();
        String sql = "UPDATE doctor SET status = ? WHERE username= ?";

        try{
            prepare =connect.prepareStatement(sql);
            prepare.setString(1,"1");
            prepare.setString(2,doctor.getUserName());
            prepare.executeUpdate();
            alert.successMessage("Added Successfully");
            updateDoctorTable();

        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

}

