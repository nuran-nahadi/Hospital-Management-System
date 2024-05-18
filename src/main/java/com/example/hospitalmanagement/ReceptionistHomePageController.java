package com.example.hospitalmanagement;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ReceptionistHomePageController implements Initializable {
    @FXML
    private AnchorPane AddPatient;
    @FXML
    private TableView<Patient> AddPatientTable;

    @FXML
    private TableView<Doctor> DoctorTableview;

    @FXML
    private TextField DoctorfilterField;

    @FXML
    private TableColumn<Patient, String> PatientDisease;

    @FXML
    private TableColumn<Patient, String> PatientDoctorName;

    @FXML
    private TextField PatientFilterField;

    @FXML
    private TableColumn<Patient, String> PatientPhonenumber;

    @FXML
    private TableColumn<Patient, String> PatientUserName;

    @FXML
    private TextField addressField;

    @FXML
    private Button button_Update;

    @FXML
    private Button button_logout;

    @FXML
    private TextField dobField;

    @FXML
    private TableColumn<Doctor, String> doctorEduQualification;

    @FXML
    private TableColumn<Doctor, String> doctorFullName;

    @FXML
    private TableColumn<Doctor, String> doctorPhoneNum;

    @FXML
    private TableColumn<Doctor, String> doctorSpecialization;

    @FXML
    private TableColumn<Doctor, String> doctorUserName;

    @FXML
    private TextField emailField;

    @FXML
    private TextField nameField;

    @FXML
    private TextField passwordField;

    @FXML
    private TableColumn<Patient, String> patientEmail;

    @FXML
    private TableColumn<Patient, String> patientFullName;

    @FXML
    private TextField phoneField;

    @FXML
    private TextField usernameField;

    private Connection connect=null;
    private PreparedStatement prepare=null;
    private ResultSet result =null;
    AlertMessage alert = new AlertMessage();




    public  void SearchDoctor() {
        try {
            ObservableList<Doctor> Dlist;
            Dlist = getDoctors();
            FilteredList<Doctor> filteredData = new FilteredList<>(Dlist, b -> true);
            DoctorfilterField.textProperty().addListener((observable, oldValue, newValue) -> {
                filteredData.setPredicate(Doctor -> {
                    if (newValue == null || newValue.isEmpty()) {
                        return true;
                    }
                    String lowerCaseFilter = newValue.toLowerCase();
                    if (Doctor.getFullName().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                        return true;
                    } else if (Doctor.getUserName().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                        return true;
                    } else if (Doctor.getSpecialization().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                        return true;
                    } else {
                        return false;
                    }
                });

            });
            SortedList<Doctor> sortedData = new SortedList<>(filteredData);
            sortedData.comparatorProperty().bind(DoctorTableview.comparatorProperty());
            DoctorTableview.setItems(sortedData);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public  void SearchPatient(){
        try{
            ObservableList<Patient> Plist=getPatients();
            FilteredList<Patient> filteredData = new FilteredList<>(Plist, b -> true);
            PatientFilterField.textProperty().addListener((observable,oldValue,newValue)->{
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
            sortedData.comparatorProperty().bind(AddPatientTable.comparatorProperty());
            AddPatientTable.setItems(sortedData);

        }
        catch (SQLException e){
            e.printStackTrace();
        }

    }




    public  void updatePatientTable()throws SQLException{
        patientFullName.setCellValueFactory(new PropertyValueFactory<>("fullName"));
        PatientUserName.setCellValueFactory(new PropertyValueFactory<>("userName"));
        patientEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        PatientPhonenumber.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        PatientDisease.setCellValueFactory(new PropertyValueFactory<>("disease"));
        PatientDoctorName.setCellValueFactory(new PropertyValueFactory<>("doctorName"));
        SearchPatient();
    }




        public void UpdateDoctorTable()throws SQLException{
            doctorFullName.setCellValueFactory(new PropertyValueFactory<>("fullName"));
            doctorUserName.setCellValueFactory(new PropertyValueFactory<>("userName"));
            doctorPhoneNum.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
            doctorSpecialization.setCellValueFactory(new PropertyValueFactory<>("specialization"));
            doctorEduQualification.setCellValueFactory(new PropertyValueFactory<>("educationalQualification"));
            SearchDoctor();

        }


    public void initialize(URL url, ResourceBundle rb) {
        try {
            UpdateDoctorTable();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        try {
            updatePatientTable();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
    public ObservableList<Doctor> getDoctors() throws SQLException {
        ObservableList<Doctor> list = FXCollections.observableArrayList();
        try (Connection connect = HospitalManagementDatabase.connectDB();
             PreparedStatement ps = connect.prepareStatement("SELECT * FROM doctor");
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(new Doctor(
                        rs.getString("fullname"),
                        rs.getString("username"),
                        rs.getString("email"),
                        rs.getString("password"),
                        rs.getString("phonenumber"),
                        rs.getString("date_of_birth"),
                        rs.getString("specialization"),
                        rs.getString("educationalqualification"),
                        rs.getString("address"),
                        rs.getInt("status")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    public ObservableList<Patient>getPatients() throws SQLException{
        ObservableList<Patient> Plist = FXCollections.observableArrayList();
        try (Connection connect = HospitalManagementDatabase.connectDB();
             PreparedStatement ps = connect.prepareStatement("SELECT * FROM patient");
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Plist.add(new Patient(
                        rs.getString("fullname"),
                        rs.getString("username"),
                        rs.getString("email"),
                        rs.getString("phonenumber"),
                        rs.getString("date_of_birth"),
                        rs.getString("disease"),
                        rs.getString("doctorname"),
                        rs.getString("address")

                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Plist;
    }



    public void setProfile(String user, String pass) throws SQLException {
        try {
            connect = HospitalManagementDatabase.connectDB();
            prepare = connect.prepareStatement("SELECT * FROM receptionist WHERE username = ? AND password = ?");
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

    public void updateInfo(ActionEvent event) {
        try {
            String insertData = "UPDATE receptionist SET fullname = ?, username = ?, email = ?, phonenumber = ?, date_of_birth = ?, address = ?, password = ? WHERE username = ?";
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



    @FXML
    protected void onlogOUTClick(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HospitalManagementSystem.class.getResource("Homepage.fxml"));
        Scene scene = new Scene(fxmlLoader.load(),743, 480);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle("Hospital Management System");
        stage.setScene(scene);
        stage.show();

    }

   public void AddPatient(ActionEvent event) {

       Doctor doctor =DoctorTableview.getSelectionModel().getSelectedItem();
       String doctorUsername=doctor.getUserName();
       Patient patient =AddPatientTable.getSelectionModel().getSelectedItem();
       String patienUsername =patient.getUserName();
      // System.out.println(doctorUsername);
       //System.out.println(patienUsername);
       connect =HospitalManagementDatabase.connectDB();
       String query = "insert into doctorappointment(doctorusername,patientusername)values(?,?)";

       try{
           prepare =connect.prepareStatement(query);
           prepare.setString(1,doctorUsername);
           prepare.setString(2,patienUsername);
           prepare.execute();
           alert.successMessage("Appointment successful");

       }
       catch (Exception e){
           e.printStackTrace();
       }
   }

}
