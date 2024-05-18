package com.example.hospitalmanagement;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class StaffHomePageController {

    @FXML
    private TextField addressField;

    @FXML
    private Button button_Update;

    @FXML
    private Button button_attendance;

    @FXML
    private Button button_logout;

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
    private TextField shiftField;

    @FXML
    private TextField shiftText;

    @FXML
    private TextField usernameField;

    private Connection connect;
    private PreparedStatement prepare;
    private ResultSet result;

    public void setProfile(String user, String pass) throws SQLException {
        try {
            connect = HospitalManagementDatabase.connectDB();
            prepare = connect.prepareStatement("SELECT * FROM staff WHERE username = ? AND password = ?");
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
    public void initialize() {
        button_logout.setOnAction(event -> {
            try {
                switchToHomePage(event);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    private void switchToHomePage(ActionEvent event) throws  IOException {
        try {
            FXMLLoader fxmlLoader =new FXMLLoader(HospitalManagementSystem.class.getResource("Homepage.fxml"));
            Scene scene = new Scene(fxmlLoader.load(),1280, 800);
            Stage stage = (Stage) button_logout.getScene().getWindow();
            stage.setX(100);
            stage.setY(0);
            stage.setTitle("Hospital Management System");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void updateInfo(ActionEvent event) {
        try {
            String insertData = "UPDATE staff SET fullname = ?, username = ?, email = ?, phonenumber = ?, date_of_birth = ?, address = ?, password = ? WHERE username = ?";
            connect = HospitalManagementDatabase.connectDB();
            prepare = connect.prepareStatement(insertData);
            prepare.setString(1, nameField.getText());
            prepare.setString(2, usernameField.getText());
            prepare.setString(3, emailField.getText());
            prepare.setString(4, phoneField.getText());
            prepare.setString(5, dobField.getText());
            prepare.setString(6, addressField.getText());
            prepare.setString(7, passwordField.getText());
            prepare.setString(8, usernameField.getText());

            prepare.executeUpdate();
            AlertMessage alert = new AlertMessage();
            alert.successMessage("Update Successful!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
