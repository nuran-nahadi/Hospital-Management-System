package com.example.hospitalmanagement;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class AdminHomePageController implements Initializable {

    @FXML
    private TableColumn<Doctor, String> Address;

    @FXML
    private TableColumn<Doctor, String> DateOfbirth;

    @FXML
    private TableColumn<Doctor, String> EduQualification;

    @FXML
    private TableColumn<Doctor, String> Email;

    @FXML
    private TableColumn<Doctor, String> FullName;

    @FXML
    private TableColumn<Doctor, String> PhoneNum;

    @FXML
    private TableColumn<Doctor, String> Specialization;

    @FXML
    private TableColumn<Doctor, Integer> Status;

    @FXML
    private TableColumn<Doctor, String> UserName;

    @FXML
    private TextField address;

    @FXML
    private Button btAddDoctor;

    @FXML
    private Button btRemoveDoctor;

    @FXML
    private Button btSearchDoctor;

    @FXML
    private Button button_logout;

    @FXML
    private TextField dob;

    @FXML
    private TextField email;

    @FXML
    private TextField gender;

    @FXML
    private Label label_admin;

    @FXML
    private TextField name;

    @FXML
    private TextField phonenumber;

    @FXML
    private TextField religion;

    @FXML
    private Tab tab_doctors;

    @FXML
    private Tab tab_overview;

    @FXML
    private Tab tab_patient;

    @FXML
    private Tab tab_personalinfo;

    @FXML
    private Tab tab_rooms;

    @FXML
    private Tab tab_staff;

    @FXML
    private TableView<Doctor> tableview;

    @FXML
    private TextField totalpatientcount;

    @FXML
    private TextField username;

    private Connection connect = null;
    private PreparedStatement prepare = null;
    private ResultSet result = null;

    public int getTotalPatientCount() {
        int totalCount=0;
        try {
            connect = HospitalManagementDatabase.connectDB();
            prepare = connect.prepareStatement("SELECT COUNT(*) AS total FROM patient");
            result = prepare.executeQuery();

            if (result.next()) {
                totalCount = result.getInt("total");


            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Close resources
            try {
                if (result != null) {
                    result.close();
                }
                if (prepare != null) {
                    prepare.close();
                }
                if (connect != null) {
                    connect.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return totalCount;
    }



    public void setTab_overview() {
        totalpatientcount.setText(String.valueOf(getTotalPatientCount()));

    }


    public void setProfile(String user, String pass) throws SQLException {
        try {
            connect = HospitalManagementDatabase.connectDB();
            prepare = connect.prepareStatement("SELECT * FROM admin WHERE username = ? AND password = ?");
            prepare.setString(1, user);
            prepare.setString(2, pass);
            result = prepare.executeQuery();

            while (result.next()) {

                name.setText(result.getString("fullname"));
                username.setText(result.getString("username"));
                email.setText(result.getString("email"));
                phonenumber.setText(result.getString("phonenumber"));
                dob.setText(result.getString("date_of_birth"));
                address.setText(result.getString("address"));
                gender.setText(result.getString("gender"));
                //religion.setText(result.getString("religion"));

            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Close resources
            if (result != null) {
                result.close();
            }
            if (prepare != null) {
                prepare.close();
            }
            if (connect != null) {
                connect.close();
            }
        }
    }


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Initialize TableView columns
        FullName.setCellValueFactory(new PropertyValueFactory<>("FullName"));
        UserName.setCellValueFactory(new PropertyValueFactory<>("UserName"));
        Email.setCellValueFactory(new PropertyValueFactory<>("Email"));
        PhoneNum.setCellValueFactory(new PropertyValueFactory<>("PhoneNumber"));
        DateOfbirth.setCellValueFactory(new PropertyValueFactory<>("DateOfBirth"));
        Specialization.setCellValueFactory(new PropertyValueFactory<>("Specialization"));
        EduQualification.setCellValueFactory(new PropertyValueFactory<>("EducationalQualification"));
        Address.setCellValueFactory(new PropertyValueFactory<>("Address"));
        Status.setCellValueFactory(new PropertyValueFactory<>("Status"));

        // Populate TableView with data
        try {
            tableview.setItems(getDoctors());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ObservableList<Doctor> getDoctors() throws SQLException {
        ObservableList<Doctor> list = FXCollections.observableArrayList();
        try (Connection connect = HospitalManagementDatabase.connectDB();
             PreparedStatement ps = connect.prepareStatement("SELECT * FROM doctor");
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
