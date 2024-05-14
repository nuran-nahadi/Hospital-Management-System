package com.example.hospitalmanagement;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class HomepageController {
    @FXML
    private Button btadmin;

    @FXML
    private Button btdoctor;

    @FXML
    private Button btgnstaff;

    @FXML
    private Button btpatient;

    @FXML
    private Button btreceiptionist;

    @FXML
    private Button btstaff;

    @FXML
    private Label lgtx;

    @FXML
    private Label wltxt;

    @FXML
    protected void onAdminbuttonClick(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader =new FXMLLoader(HospitalManagementSystem.class.getResource("Login.fxml"));
        Scene scene = new Scene(fxmlLoader.load(),400, 370);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle("Hospital Management System");
        stage.setScene(scene);
        stage.show();

    }

    @FXML
    protected void onDoctorbuttonClick(ActionEvent event) throws IOException {

        FXMLLoader fxmlLoader =new FXMLLoader(HospitalManagementSystem.class.getResource("DoctorLogin.fxml"));
        Scene scene = new Scene(fxmlLoader.load(),400, 370);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle("Hospital Management System");
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    protected void onPatientbuttonClick(ActionEvent event) throws IOException {

        FXMLLoader fxmlLoader =new FXMLLoader(HospitalManagementSystem.class.getResource("Login.fxml"));
        Scene scene = new Scene(fxmlLoader.load(),400, 370);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle("Hospital Management System");
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    protected   void onRecieptionistbuttonClick(ActionEvent event) throws IOException {

        FXMLLoader fxmlLoader =new FXMLLoader(HospitalManagementSystem.class.getResource("Login.fxml"));
        Scene scene = new Scene(fxmlLoader.load(),400, 370);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle("Hospital Management System");
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    protected   void onStaffbuttonClick(ActionEvent event) throws IOException {

        FXMLLoader fxmlLoader =new FXMLLoader(HospitalManagementSystem.class.getResource("Login.fxml"));
        Scene scene = new Scene(fxmlLoader.load(),400, 370);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle("Hospital Management System");
        stage.setScene(scene);
        stage.show();
    }
}

