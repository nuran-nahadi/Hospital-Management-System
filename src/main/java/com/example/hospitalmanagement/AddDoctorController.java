package com.example.hospitalmanagement;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

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


}

