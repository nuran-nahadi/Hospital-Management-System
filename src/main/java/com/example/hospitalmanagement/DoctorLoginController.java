package com.example.hospitalmanagement;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class DoctorLoginController {

    @FXML
    private TextField DoctorLoginUsername;

    @FXML
    private CheckBox DoctorLogincheckbox;

    @FXML
    private PasswordField DoctorPasswordHidden;

    @FXML
    private Button Doctorloginbtn;

    @FXML
    private Hyperlink Doctorreg;

    @FXML
    private Label lbhnay;

    @FXML
    private Label lblg;

    @FXML
    private TextField passwordText;

    private Connection connect;
    private PreparedStatement prepare;
    private ResultSet result;

    private AlertMessage alert = new AlertMessage();

    public void loginAccount(){
        if(DoctorLoginUsername.getText().isEmpty() || passwordText.getText().isEmpty()){
            alert.errorMessage("Incorrect Username or Password");
        }
        else{
            String sql = "SELECT * FROM doctor WHERE username = ? AND password =?";
            connect =HospitalManagementDatabase.connectDB();

            try{
                //assert connect != null;
                if (connect != null) {
                    prepare = connect.prepareStatement(sql);
                }
                prepare.setString(1,DoctorLoginUsername.getText());
                prepare.setString(2,passwordText.getText());
                result =prepare.executeQuery();
                if(result.next()){
                    alert.successMessage("Login Successfully");
                }
                else{
                    alert.errorMessage("Incorrect Username or Password");
                }
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    public void LoginPasswordShow(){
        if(DoctorLogincheckbox.isSelected()){
            passwordText.setText(DoctorPasswordHidden.getText());
            passwordText.setVisible(true);
            DoctorPasswordHidden.setVisible(false);
            return;
        }
        DoctorPasswordHidden.setText(passwordText.getText());
        DoctorPasswordHidden.setVisible(true);
        passwordText.setVisible(false);


    }
    public void SwitchToRegisterform(ActionEvent event) throws IOException {
        // FXMLLoader fxmlLoader =new FXMLLoader(HospitalManagementSystem.class.getResource("RegisterCommonforall.fxml"));
        FXMLLoader fxmlLoader =new FXMLLoader(HospitalManagementSystem.class.getResource("DoctorRegistor.fxml"));
        Scene scene = new Scene(fxmlLoader.load(),400, 370);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle("Hospital Management System");
        stage.setScene(scene);
        stage.show();
    }


}
