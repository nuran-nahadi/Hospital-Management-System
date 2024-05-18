package com.example.hospitalmanagement;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DoctorHomepageController {




    public void setTab_overview() {
    }


    @FXML
    private TextField addressField;

    @FXML
    private Button button_Update;

    @FXML
    private TextField dobField;

    @FXML
    private TextField emailField;

    @FXML
    private TextField nameField;

    @FXML
    private TextField passwordField;

    @FXML
    private TextField phoneField;

    @FXML
    private TextField usernameField;

    @FXML
    private TextField specializationField;

    @FXML
    private TextField eduQualificationField;

    private Connection connect;
    private PreparedStatement prepare;
    private ResultSet result;


    public void setProfile(String user, String pass) throws SQLException {
        try {
            connect = HospitalManagementDatabase.connectDB();
            prepare = connect.prepareStatement("SELECT * FROM doctor WHERE username = ? AND password = ?");
            prepare.setString(1, user);
            prepare.setString(2, pass);
            result = prepare.executeQuery();

            while (result.next()) {

                nameField.setText(result.getString("fullname"));
                usernameField.setText(result.getString("username"));
                emailField.setText(result.getString("email"));
                phoneField.setText(result.getString("phonenumber"));
                dobField.setText(result.getString("date_of_birth"));
                addressField.setText(result.getString("address"));
                passwordField.setText(result.getString("password"));
                eduQualificationField.setText(result.getString("educationalqualification"));
                specializationField.setText(result.getString("specialization"));
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

    public void updateInfo(ActionEvent event) {
        try {
            String insertData = "UPDATE doctor SET fullname = ?, username = ?, email = ?, phonenumber = ?, date_of_birth = ?, address = ?, specializationField = ? , eduQualificationField = ? , password = ? WHERE username = ?";
            connect = HospitalManagementDatabase.connectDB();
            prepare = connect.prepareStatement(insertData);
            prepare.setString(1, nameField.getText());
            prepare.setString(2, usernameField.getText());
            prepare.setString(3, emailField.getText());
            prepare.setString(4, phoneField.getText());
            prepare.setString(5, dobField.getText());
            prepare.setString(6, addressField.getText());
            prepare.setString(7, passwordField.getText());
            prepare.setString(8, specializationField.getText());
            prepare.setString(9, eduQualificationField.getText());
            prepare.setString(10, usernameField.getText());

            prepare.executeUpdate();
            AlertMessage alert = new AlertMessage();
            alert.successMessage("Update Successful!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @FXML
    private Button button_logout;

    @FXML
    protected void onlogOUTClick(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HospitalManagementSystem.class.getResource("Homepage.fxml"));
        Scene scene = new Scene(fxmlLoader.load(),1280, 800);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setX(100);
        stage.setY(0);
        stage.setTitle("Hospital Management System");
        stage.setScene(scene);
        stage.show();

    }



}
