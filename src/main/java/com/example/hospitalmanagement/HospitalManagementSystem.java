package com.example.hospitalmanagement;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HospitalManagementSystem extends Application {
    @Override
    public void start(Stage stage) throws IOException {

        FXMLLoader fxmlLoader =new FXMLLoader(HospitalManagementSystem.class.getResource("Homepage.fxml"));
        Scene scene = new Scene(fxmlLoader.load(),1280, 800);
        stage.setX(100);
        stage.setY(0);
        stage.setTitle("Hospital Management System");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}