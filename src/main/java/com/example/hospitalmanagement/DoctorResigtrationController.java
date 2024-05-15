package com.example.hospitalmanagement;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;

public class DoctorResigtrationController {

    @FXML
    private TextField DoctorFullName;

    @FXML
    private TextField DoctorRegAddress;

    @FXML
    private DatePicker DoctorRegDoB;

    @FXML
    private TextArea DoctorRegEQ;

    @FXML
    private TextField DoctorRegEmail;

    @FXML
    private Hyperlink DoctorRegLogin;

    @FXML
    private PasswordField DoctorRegPassword;

    @FXML
    private TextField DoctorRegPhoneNumber;

    @FXML
    private TextField DoctorRegSpecialization;

    @FXML
    private Button DoctorRegbt;

    @FXML
    private TextField DoctorReguserName;

    private Connection connect;
    private PreparedStatement prepare;
    private ResultSet result;

    private AlertMessage alert = new AlertMessage();




   public void OnLoginClick(ActionEvent event) throws IOException {
       FXMLLoader fxmlLoader =new FXMLLoader(HospitalManagementSystem.class.getResource("DoctorLogin.fxml"));
       Scene scene = new Scene(fxmlLoader.load(),743, 480);
       Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
       stage.setTitle("Hospital Management System");
       stage.setScene(scene);
       stage.show();
    }



   public void OnRegisterButtonClick(ActionEvent event) {
       LocalDate birthday = DoctorRegDoB.getValue();
       String str = birthday.toString();
       if(DoctorFullName.getText().isEmpty() || DoctorRegEmail.getText().isEmpty() || DoctorReguserName.getText().isEmpty()|| DoctorRegPassword.getText().isEmpty()||DoctorRegPhoneNumber.getText().isEmpty()|| str.isEmpty()||DoctorRegSpecialization.getText().isEmpty()||DoctorRegAddress.getText().isEmpty()||DoctorRegEQ.getText().isEmpty()){
           alert.errorMessage("Please fill all blank fields");
       }
       else{
           String checkUser = "SELECT * FROM doctor WHERE username= '" +DoctorReguserName.getText()+"'" ;
           connect =HospitalManagementDatabase.connectDB();
           try{
               prepare = connect.prepareStatement(checkUser);
               result = prepare.executeQuery();
               if(result.next()){
                   alert.errorMessage(DoctorReguserName.getText()+" is already existed!");
               }
               else{
                   String insertData = "INSERT INTO doctor (fullname,username,password,email,phonenumber,date_of_birth,specialization,educational_qualification,address,date) VALUES(?,?,?,?,?,?,?,?,?,?)";
                   Date date = new Date(0,0,0);
                   java.sql.Date sqlDate = new java.sql.Date(date.getTime());
                   prepare = connect.prepareStatement(insertData);
                   prepare.setString(1,DoctorFullName.getText());
                   prepare.setString(2,DoctorReguserName.getText());
                   prepare.setString(3,DoctorRegPassword.getText());
                   prepare.setString(4,DoctorRegEmail.getText());
                   prepare.setString(5,DoctorRegPhoneNumber.getText());

                   prepare.setString(6,str);
                   prepare.setString(7,DoctorRegSpecialization.getText());
                   prepare.setString(8,DoctorRegEQ.getText());
                   prepare.setString(9,DoctorRegAddress.getText());
                   prepare.setString(10,String.valueOf(sqlDate));

                   prepare.executeUpdate();
                   alert.successMessage("Registration Successful!");

               }
           }
           catch (Exception e){
               e.printStackTrace();
           }
       }
   }


}
