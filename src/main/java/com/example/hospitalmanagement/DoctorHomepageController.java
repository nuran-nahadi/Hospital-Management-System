package com.example.hospitalmanagement;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class DoctorHomepageController implements Initializable {



    @FXML
    private TableView<Patient> AppointmentTableView;

    @FXML
    private TextField PatientfilterField;

    @FXML
    private TextField PatientfilterField1;


    @FXML
    private TableView<Patient> PatientTableview1;

    @FXML
    private TextField addressField;

    @FXML
    private Button button_Update;

    @FXML
    private Button button_logout;

    @FXML
    private TextField dobField;

    @FXML
    private TextArea eduQualificationField;

    @FXML
    private TextField emailField;

    @FXML
    private Label label_doc;

    @FXML
    private TextField nameField;

    @FXML
    private TextField passwordField;

    @FXML
    private TableColumn<Patient, String> patientDOB;

    @FXML
    private TableColumn<Patient, String> patientDOB1;

    @FXML
    private TableColumn<Patient, String> patientDisease;

    @FXML
    private TableColumn<Patient, String> patientDisease1;

    @FXML
    private TableColumn<Patient, String> patientDoctorName1;

    @FXML
    private TableColumn<Patient, String> patientEmail;

    @FXML
    private TableColumn<Patient, String> patientEmail1;

    @FXML
    private TableColumn<Patient, String> patientFullName;

    @FXML
    private TableColumn<Patient, String> patientFullName1;

    @FXML
    private TableColumn<Patient, String> patientUserName;

    @FXML
    private TableColumn<Patient, String> patientUserName1;

    @FXML
    private TableColumn<Patient, String> patientphonenum;

    @FXML
    private TableColumn<Patient, String> patientphonenum1;

    @FXML
    private TextField phoneField;

    @FXML
    private TextField specializationField;

    @FXML
    private Tab tab_appointments;

    @FXML
    private Tab tab_overview;

    @FXML
    private Tab tab_patients;

    @FXML
    private Tab tab_profile;

    @FXML
    private TextField usernameField;



    private Connection connect=null;
    private PreparedStatement prepare=null;
    private ResultSet result=null;
    public String DoctorUsername;



    public void setTab_overview() {
    }


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
    protected void onlogOUTClick(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HospitalManagementSystem.class.getResource("Homepage.fxml"));
        Scene scene = new Scene(fxmlLoader.load(),743, 480);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle("Hospital Management System");
        stage.setScene(scene);
        stage.show();

    }

    public  void SearchPatient(){
        try{
            ObservableList<Patient> Plist=getPatients();
            FilteredList<Patient> filteredData = new FilteredList<>(Plist, b -> true);
            PatientfilterField.textProperty().addListener((observable,oldValue,newValue)->{
                filteredData.setPredicate(Patient->{
                    if(newValue==null || newValue.isEmpty()){
                        return true;
                    }
                    String lowerCaseFilter = newValue.toLowerCase();
                    if(Patient.getFullName().toLowerCase().indexOf(lowerCaseFilter)!=-1){
                        return true;
                    }
                    else if(Patient.getUserName().toLowerCase().indexOf(lowerCaseFilter)!=-1){
                        return true;
                    }
                    else if(Patient.getPhoneNumber().toLowerCase().indexOf(lowerCaseFilter)!=-1){
                        return true;
                    }
                    else{
                        return false;
                    }
                });

            });
            SortedList<Patient> sortedData = new SortedList<>(filteredData);
            sortedData.comparatorProperty().bind(AppointmentTableView.comparatorProperty());
            AppointmentTableView.setItems(sortedData);

        }
        catch (SQLException e){
            e.printStackTrace();
        }

    }


    public  void updatePatientTable()throws SQLException{
        patientFullName.setCellValueFactory(new PropertyValueFactory<>("fullName"));
        patientUserName.setCellValueFactory(new PropertyValueFactory<>("userName"));
        patientEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        patientphonenum.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        patientDOB.setCellValueFactory(new PropertyValueFactory<>("dateOfBirth"));
        patientDisease.setCellValueFactory(new PropertyValueFactory<>("disease"));

        SearchPatient();
    }





    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            updatePatientTable();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }




    public ObservableList<Patient>getPatients() throws SQLException{
        ObservableList<Patient> Plist = FXCollections.observableArrayList();
        try {
            System.out.println(DoctorUsername);
            connect = HospitalManagementDatabase.connectDB();
             PreparedStatement ps = connect.prepareStatement("SELECT * FROM doctorappointment WHERE doctorusername=doctorusername");
             ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Plist.add(new Patient(
                        rs.getString("patientfullname"),
                        rs.getString("patientusername"),
                        rs.getString("patientemail"),
                        rs.getString("patientphonenumber"),
                        rs.getString("patientdateofbirth"),
                        rs.getString("patientdisease")));


            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Plist;
    }
}
