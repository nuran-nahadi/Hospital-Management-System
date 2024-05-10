package com.example.hospitalmanagement;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HospitalManagementSystem extends Application {
    @Override
    public void start(Stage stage) throws IOException {

        FXMLLoader fxmlLoader =new FXMLLoader(HospitalManagementSystem.class.getResource("Login.fxml"));
       // FXMLLoader fxmlLoader1 =new FXMLLoader(HospitalManagementSystem.class.getResource("Login.fxml"));
       // Scene scene = new Scene(fxmlLoader.load(),400, 370);
        Scene scene = new Scene(fxmlLoader.load(),400, 370);

        stage.setTitle("Hospital Management System");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}