package com.example.hospitalmanagement;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AdminLoginController {

     @FXML
        private Button btlg;

       @FXML
       private CheckBox checkBox;

       @FXML
       private Hyperlink hpreghere;

       @FXML
       private Label lbhnay;

        @FXML
       private Label lblg;

       @FXML
       private TextField passwordText;

       @FXML
       private PasswordField passwordhidden;

       @FXML
        private TextField txusername;

    private Connection connect;
    private PreparedStatement prepare;
    private ResultSet result;

    private AlertMessage alert = new AlertMessage();
  //  private String admintableColumn[]

    public void loginAccount(ActionEvent event){
        if(txusername.getText().isEmpty() || passwordhidden.getText().isEmpty()){
            alert.errorMessage("Incorrect Username or Password");
        }
        else{
            String sql = "SELECT * FROM admin WHERE username = ? AND password =?";
            connect =HospitalManagementDatabase.connectDB();

            try{
                //assert connect != null;
                if (connect != null) {
                    prepare = connect.prepareStatement(sql);
                }
                prepare.setString(1,txusername.getText());
                prepare.setString(2,passwordhidden.getText());
                result =prepare.executeQuery();
                if(result.next()){

                    switchToAdminHomepage(event);
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
       if(checkBox.isSelected()){
           passwordText.setText(passwordhidden.getText());
           passwordText.setVisible(true);
           passwordhidden.setVisible(false);
           return;
       }
       passwordhidden.setText(passwordText.getText());
       passwordhidden.setVisible(true);
       passwordText.setVisible(false);


    }
    public void switchToAdminHomepage(ActionEvent event) throws IOException, SQLException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("AdminHomePage.fxml"));
        Parent root = fxmlLoader.load();
        AdminHomePageController adminHomePageController = fxmlLoader.getController();
        adminHomePageController.setProfile(txusername.getText(),passwordhidden.getText());
        adminHomePageController.setTab_overview();

        Scene scene = new Scene(root,1280, 800);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setX(100);
        stage.setY(0);
        stage.setTitle("Admin Homepage");
        stage.setScene(scene);
        stage.show();
    }

    public void SwitchToRegisterform(ActionEvent event) throws IOException {

        FXMLLoader fxmlLoader =new FXMLLoader(HospitalManagementSystem.class.getResource("RegisterAdminPage.fxml"));
        Scene scene = new Scene(fxmlLoader.load(),1280, 800);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setX(100);
        stage.setY(0);
        stage.setTitle("Hospital Management System");
        stage.setScene(scene);
        stage.show();
    }



    @FXML
    private Button Go_Back ;
    public void SwitchToMainHomePage(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader =new FXMLLoader(HospitalManagementSystem.class.getResource("HomePage.fxml"));
        Scene scene = new Scene(fxmlLoader.load(),1280, 800);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setX(100);
        stage.setY(0);
        stage.setTitle("Hospital Management System");
        stage.setScene(scene);
        stage.show();
    }




}
