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
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;


public class ReceptionistRegistrationController {
    @FXML
    private DatePicker RegDateofBirth;

    @FXML
    private TextField RegEmailText;

    @FXML
    private TextField RegFullNametext;

    @FXML
    private PasswordField RegPasswordText;

    @FXML
    private TextField RegPnoneNumberText;

    @FXML
    private TextField RegUsernameText;

    @FXML
    private Button RegistrationButton;

    private Connection connect;
    private PreparedStatement prepare;
    private ResultSet result;

    private AlertMessage alert = new AlertMessage();



    public void registerAccount(ActionEvent event){

        LocalDate birthday = RegDateofBirth.getValue();
        String str = birthday.toString();
        if(RegFullNametext.getText().isEmpty()||RegEmailText.getText().isEmpty() || RegUsernameText.getText().isEmpty()|| RegPasswordText.getText().isEmpty()||RegPnoneNumberText.getText().isEmpty()|| str.isEmpty()){
            alert.errorMessage("Please fill all blank fields");
        }
        else{
            String checkUser = "SELECT * FROM receptionist WHERE username= '" +RegUsernameText.getText()+"'" ;
            connect =HospitalManagementDatabase.connectDB();
            try{
                prepare = connect.prepareStatement(checkUser);
                result = prepare.executeQuery();
                if(result.next()){
                    alert.errorMessage(RegUsernameText.getText()+" is already existed!");
                }
                else{
                    String insertData = "INSERT INTO receptionist (fullname,username,email,phonenumber,password,date_of_birth,date) VALUES(?,?,?,?,?,?,?)";
                    Date date = new Date(0,0,0);
                    java.sql.Date sqlDate = new java.sql.Date(date.getTime());
                    prepare = connect.prepareStatement(insertData);
                    prepare.setString(1,RegFullNametext.getText());
                    prepare.setString(2,RegUsernameText.getText());
                    prepare.setString(3,RegEmailText.getText());
                    prepare.setString(4,RegPnoneNumberText.getText());
                    prepare.setString(5,RegPasswordText.getText());
                    prepare.setString(6,str);
                    prepare.setString(7,String.valueOf(sqlDate));

                    prepare.executeUpdate();
                    alert.successMessage("Registration Successful!");

                }
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    public void switchfromregistertologin(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader =new FXMLLoader(HospitalManagementSystem.class.getResource("ReceptionistLogin.fxml"));
        Scene scene = new Scene(fxmlLoader.load(),1280, 800);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setX(100);
        stage.setY(0);
        stage.setTitle("Hospital Management System");
        stage.setScene(scene);
        stage.show();

    }





}
