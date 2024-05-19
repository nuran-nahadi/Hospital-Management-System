package com.example.hospitalmanagement;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
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
import java.sql.SQLException;


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

    public void loginAccount(ActionEvent event){

        if(DoctorLoginUsername.getText().isEmpty() || DoctorPasswordHidden.getText().isEmpty()){
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
                prepare.setString(2,DoctorPasswordHidden.getText());
                result =prepare.executeQuery();
                if(result.next()){
                    if (result.getInt("status")==0){
                        alert.errorMessage("You are not approved by Admin.Wait to be Approved");
                    }
                    else {
                        switchToDoctorHomepage(event);
                    }


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

    public void switchToDoctorHomepage(ActionEvent event) throws IOException, SQLException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("DoctorHomePage.fxml"));
        Parent root = fxmlLoader.load();
        DoctorHomepageController doctorHomepageController = fxmlLoader.getController();
        doctorHomepageController.setProfile(DoctorLoginUsername.getText(),DoctorPasswordHidden.getText());

        doctorHomepageController.setTab_overview();
        Scene scene = new Scene(root,743,480);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        //Stage stage= new Stage();
        stage.setTitle("Doctor Homepage");
        stage.setScene(scene);
        stage.show();
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
        FXMLLoader fxmlLoader =new FXMLLoader(HospitalManagementSystem.class.getResource("DoctorRegistration.fxml"));
        Scene scene = new Scene(fxmlLoader.load(),400, 370);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle("Hospital Management System");
        stage.setScene(scene);
        stage.show();
    }


    @FXML
    private Button Go_Back ;
    public void SwitchToMainHomePage(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader =new FXMLLoader(HospitalManagementSystem.class.getResource("HomePage.fxml"));
        Scene scene = new Scene(fxmlLoader.load(),743, 480);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle("Hospital Management System");
        stage.setScene(scene);
        stage.show();
    }



}
