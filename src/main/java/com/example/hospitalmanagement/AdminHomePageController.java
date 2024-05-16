package com.example.hospitalmanagement;

import javafx.fxml.FXML;

import javafx.scene.control.*;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AdminHomePageController {


    @FXML
    private TextField address;

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
    private TextField totalpatientcount;

    @FXML
    private TextField username;

    private Connection connect;
    private PreparedStatement prepare;
    private ResultSet result;

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


    }


