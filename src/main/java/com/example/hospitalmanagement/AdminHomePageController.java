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

import javax.print.Doc;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.Optional;
import java.util.ResourceBundle;

public class AdminHomePageController implements Initializable {

    @FXML
    private TableView<Doctor> DoctorTableview;

    @FXML
    private TextField DoctorfilterField;

    @FXML
    private TableView<Patient> PatientTableview;

    @FXML
    private TextField PatientfilterField;

    @FXML
    private TableView<Receptionist> ReceptionistTableview;

    @FXML
    private TextField ReceptionistfilterField;


    @FXML
    private Button RemoveReceptionist;

    @FXML
    private TableColumn<Staff, String> StaffAddress;

    @FXML
    private TableColumn<Staff, String> StaffDOB;

    @FXML
    private TableColumn<Staff, String> StaffEmail;

    @FXML
    private TableColumn<Staff, String> StaffFullName;

    @FXML
    private TableColumn<Staff, String> StaffPhoneNumber;

    @FXML
    private TableColumn<Staff, Integer> StaffStatus;

    @FXML
    private TableView<Staff> StaffTableview;

    @FXML
    private TableColumn<Staff, String> StaffUserName;

    @FXML
    private TextField StafffilterField;

    @FXML
    private TextField TotalDoctorCount;

    @FXML
    private TextField TotalReceptionistCount;

    @FXML
    private TextField TotalStaffCount;

    @FXML
    private TextField address;

    @FXML
    private Button btAddDoctor;
    @FXML
    private Button btAddReceptionist;

    @FXML
    private Button btAddStaff;

    @FXML
    private Button btRemoveDoctor;

    @FXML
    private Button btRemoveStaff;


    @FXML
    private Button btSearchDoctor;

    @FXML
    private Button button_add;

    @FXML
    private Button button_logout;

    @FXML
    private Button button_remove;

    @FXML
    private TextField dob;

    @FXML
    private TableColumn<Doctor, String> doctorAddress;

    @FXML
    private TableColumn<Doctor, String> doctorDateOfbirth;

    @FXML
    private TableColumn<Doctor, String> doctorEduQualification;

    @FXML
    private TableColumn<Doctor, String> doctorEmail;

    @FXML
    private TableColumn<Doctor, String> doctorFullName;

    @FXML
    private TableColumn<Doctor, String> doctorPhoneNum;

    @FXML
    private TableColumn<Doctor, String> doctorSpecialization;

    @FXML
    private TableColumn<Doctor, Integer> doctorStatus;

    @FXML
    private TableColumn<Doctor, String> doctorUserName;

    @FXML
    private TextField email;

    @FXML
    private TextField gender;

    @FXML
    private Label label_admin;

    @FXML
    private TextField name;

    @FXML
    private TableColumn<Patient, String> patientDOB;

    @FXML
    private TableColumn<Patient, String> patientDisease;

    @FXML
    private TableColumn<Patient, String> patientDoctorName;

    @FXML
    private TableColumn<Patient, String> patientEmail;

    @FXML
    private TableColumn<Patient, String> patientFullName;

    @FXML
    private TableColumn<Patient, String> patientUserName;

    @FXML
    private TableColumn<Patient, String> patientphonenum;

    @FXML
    private TextField phonenumber;

    @FXML
    private TableColumn<Receptionist, String> receptionistAddress;

    @FXML
    private TableColumn<Receptionist, String> receptionistDOB;

    @FXML
    private TableColumn<Receptionist, String> receptionistEmail;

    @FXML
    private TableColumn<Receptionist, String> receptionistFullName;

    @FXML
    private TableColumn<Receptionist, String> receptionistPhonenum;

    @FXML
    private TableColumn<Receptionist, Integer> receptionistStatus;

    @FXML
    private TableColumn<Receptionist, String> receptionistUserName;

    @FXML
    private TextField religion;

    @FXML
    private Tab tab_doctors;

    @FXML
    private Tab tab_overview;

    @FXML
    private Tab tab_patient;

    @FXML
    private Tab tab_personalinfo;

    @FXML
    private Tab tab_rooms;

    @FXML
    private Tab tab_staff;

    @FXML
    private TextField totalpatientcount;

    @FXML
    private TextField username;

    private Connection connect = null;
    private PreparedStatement prepare = null;
    private ResultSet result = null;
    AlertMessage alert= new AlertMessage();



    public void onlogOUTClick(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HospitalManagementSystem.class.getResource("Homepage.fxml"));
        Scene scene = new Scene(fxmlLoader.load(),1280, 800);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setX(100);
        stage.setY(0);
        stage.setTitle("Hospital Management System");
        stage.setScene(scene);
        stage.show();

    }

    public int getTotalCount(String tablename) {
        int totalCount = 0;
        try {
            connect = HospitalManagementDatabase.connectDB();
            prepare = connect.prepareStatement("SELECT COUNT(*) AS total FROM " + tablename);
            result = prepare.executeQuery();

            if (result.next()) {
                totalCount = result.getInt("total");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Close resources
            try {
                if (result != null) {
                    result.close();
                }
                if (prepare != null) {
                    prepare.close();
                }
                if (connect != null) {
                    connect.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return totalCount;
    }

    public void setTab_overview() {
        totalpatientcount.setText(String.valueOf(getTotalCount("patient")));
        TotalDoctorCount.setText(String.valueOf(getTotalCount("doctor")));
        TotalStaffCount.setText(String.valueOf(getTotalCount("staff")));
        TotalReceptionistCount.setText(String.valueOf(getTotalCount("receptionist")));
    }

    @FXML
    public void gotoAddDoctor(ActionEvent event) throws IOException, SQLException {
        FXMLLoader fxmlLoader = new FXMLLoader(HospitalManagementSystem.class.getResource("AddDoctor.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1280, 800);
        //Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Stage stage = new Stage();
        stage.setTitle("Doctor Queue");
        stage.setScene(scene);
        stage.show();
    }

    public void gotoAddReceptionist(ActionEvent event) throws IOException{
        FXMLLoader fxmlLoader = new FXMLLoader(HospitalManagementSystem.class.getResource("AddReceptionist.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1280, 800);
        //Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Stage stage = new Stage();
        stage.setTitle("Receptionist Queue");
        stage.setScene(scene);
        stage.show();
    }

    public void gotoAddStaff(ActionEvent event) throws IOException{
        FXMLLoader fxmlLoader = new FXMLLoader(HospitalManagementSystem.class.getResource("AddStaff.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1280, 800);
        //Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Stage stage = new Stage();
        stage.setTitle("Staff Queue");
        stage.setScene(scene);
        stage.show();
    }

    public void setProfile(String user, String pass) throws SQLException {
        try {
            connect = HospitalManagementDatabase.connectDB();
            prepare = connect.prepareStatement("SELECT * FROM admin WHERE username = ? AND password = ?");
            prepare.setString(1, user);
            prepare.setString(2, pass);
            result = prepare.executeQuery();

            while (result.next()) {
                name.setText(result.getString("fullname"));
                username.setText(result.getString("username"));
                email.setText(result.getString("email"));
                phonenumber.setText(result.getString("phonenumber"));
                dob.setText(result.getString("date_of_birth"));
                address.setText(result.getString("address"));
                gender.setText(result.getString("gender"));

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


    public void updateDoctorTable()throws SQLException{
        // Initialize Doctor TableView columns
        doctorFullName.setCellValueFactory(new PropertyValueFactory<>("fullName"));
        doctorUserName.setCellValueFactory(new PropertyValueFactory<>("userName"));
        doctorEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        doctorPhoneNum.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        doctorDateOfbirth.setCellValueFactory(new PropertyValueFactory<>("dateOfBirth"));
        doctorSpecialization.setCellValueFactory(new PropertyValueFactory<>("specialization"));
        doctorEduQualification.setCellValueFactory(new PropertyValueFactory<>("educationalQualification"));
        doctorAddress.setCellValueFactory(new PropertyValueFactory<>("address"));

        SearchDoctor();


    }

    public  void updatePatientTable()throws SQLException{
        patientFullName.setCellValueFactory(new PropertyValueFactory<>("fullName"));
        patientUserName.setCellValueFactory(new PropertyValueFactory<>("userName"));
        patientEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        patientphonenum.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        patientDOB.setCellValueFactory(new PropertyValueFactory<>("dateOfBirth"));
        patientDisease.setCellValueFactory(new PropertyValueFactory<>("disease"));
        patientDoctorName.setCellValueFactory(new PropertyValueFactory<>("doctorName"));
        patientDoctorName.setCellValueFactory(new PropertyValueFactory<>("address"));

        SearchPatient();
    }


    public void updateStaffTable()throws SQLException{
        StaffFullName.setCellValueFactory(new PropertyValueFactory<>("fullName"));
        StaffUserName.setCellValueFactory(new PropertyValueFactory<>("userName"));
        StaffEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        StaffPhoneNumber.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        StaffDOB.setCellValueFactory(new PropertyValueFactory<>("dateOfBirth"));
        StaffAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        SearchStaff();
    }

    public void updateReceptionistTable()throws SQLException{
        receptionistFullName.setCellValueFactory(new PropertyValueFactory<>("fullName"));
        receptionistUserName.setCellValueFactory(new PropertyValueFactory<>("userName"));
        receptionistEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        receptionistPhonenum.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        receptionistDOB.setCellValueFactory(new PropertyValueFactory<>("dateOfBirth"));
        receptionistAddress.setCellValueFactory(new PropertyValueFactory<>("address"));

        SearchReceptionist();
    }

    public  void SearchDoctor(){
        try {
            ObservableList<Doctor> Dlist;
            Dlist =getDoctors();
            FilteredList<Doctor> filteredData = new FilteredList<>(Dlist, b -> true);
            DoctorfilterField.textProperty().addListener((observable,oldValue,newValue)->{
                filteredData.setPredicate(Doctor->{
                    if(newValue==null || newValue.isEmpty()){
                        return true;
                    }
                    String lowerCaseFilter = newValue.toLowerCase();
                    if(Doctor.getFullName().toLowerCase().indexOf(lowerCaseFilter)!=-1){
                        return true;
                    }
                    else if(Doctor.getUserName().toLowerCase().indexOf(lowerCaseFilter)!=-1){
                        return true;
                    }
                    else if(Doctor.getSpecialization().toLowerCase().indexOf(lowerCaseFilter)!=-1){
                        return true;
                    }
                    else{
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
           sortedData.comparatorProperty().bind(PatientTableview.comparatorProperty());
           PatientTableview.setItems(sortedData);

       }
       catch (SQLException e){
           e.printStackTrace();
       }

   }

   public void SearchStaff()throws SQLException{
       try {
           ObservableList<Staff> Slist=getStaffs();
           FilteredList<Staff> filteredData = new FilteredList<>(Slist, b -> true);
           StafffilterField.textProperty().addListener((observable,oldValue,newValue)->{
               filteredData.setPredicate(Staff->{
                   if(newValue==null || newValue.isEmpty()){
                       return true;
                   }
                   String lowerCaseFilter = newValue.toLowerCase();
                   if(Staff.getFullName().toLowerCase().indexOf(lowerCaseFilter)!=-1){
                       return true;
                   }
                   else if(Staff.getUserName().toLowerCase().indexOf(lowerCaseFilter)!=-1){
                       return true;
                   }
                   else if(Staff.getPhoneNumber().toLowerCase().indexOf(lowerCaseFilter)!=-1){
                       return true;
                   }
                   else{
                       return false;
                   }
               });

           });
           SortedList<Staff> sortedData = new SortedList<>(filteredData);
           sortedData.comparatorProperty().bind(StaffTableview.comparatorProperty());
           StaffTableview.setItems(sortedData);
       } catch (SQLException e) {
           e.printStackTrace();
       }


   }

   public void SearchReceptionist()throws SQLException{
      try{
          ObservableList<Receptionist> Rlist=getReceptionists();
       FilteredList<Receptionist> filteredData = new FilteredList<>(Rlist, b -> true);
       ReceptionistfilterField.textProperty().addListener((observable,oldValue,newValue)->{
           filteredData.setPredicate(Receptionist->{
               if(newValue==null || newValue.isEmpty()){
                   return true;
               }
               String lowerCaseFilter = newValue.toLowerCase();
               if(Receptionist.getFullName().toLowerCase().indexOf(lowerCaseFilter)!=-1){
                   return true;
               }
               else if(Receptionist.getUserName().toLowerCase().indexOf(lowerCaseFilter)!=-1){
                   return true;
               }
               else if(Receptionist.getPhoneNumber().toLowerCase().indexOf(lowerCaseFilter)!=-1){
                   return true;
               }
               else{
                   return false;
               }
           });

       });
       SortedList<Receptionist> sortedData = new SortedList<>(filteredData);
       sortedData.comparatorProperty().bind(ReceptionistTableview.comparatorProperty());
       ReceptionistTableview.setItems(sortedData);
   } catch (SQLException e) {
        e.printStackTrace();
    }

}

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        try {
            updateDoctorTable();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        try {
            updatePatientTable();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        try {
            updateStaffTable();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        try {
            updateReceptionistTable();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }



    }

    public ObservableList<Doctor> getDoctors() throws SQLException {
        ObservableList<Doctor> list = FXCollections.observableArrayList();
        try (Connection connect = HospitalManagementDatabase.connectDB();
             PreparedStatement ps = connect.prepareStatement("SELECT * FROM doctor WHERE status = 1");
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



    public ObservableList<Staff> getStaffs() throws SQLException {
        ObservableList<Staff> Stafflist = FXCollections.observableArrayList();
        try (Connection connect = HospitalManagementDatabase.connectDB();
             PreparedStatement ps = connect.prepareStatement("SELECT * FROM staff WHERE status = 1");
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Stafflist.add(new Staff(
                        rs.getString("fullname"),
                        rs.getString("username"),
                        rs.getString("email"),
                        rs.getString("phonenumber"),
                        rs.getString("date_of_birth"),
                        rs.getString("address"),
                        rs.getInt("status")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Stafflist;
    }

    public ObservableList<Receptionist> getReceptionists() throws SQLException {
        ObservableList<Receptionist> Receptionistlist = FXCollections.observableArrayList();
        try (Connection connect = HospitalManagementDatabase.connectDB();
             PreparedStatement ps = connect.prepareStatement("SELECT * FROM receptionist where status = 1");
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Receptionistlist.add(new Receptionist(
                        rs.getString("fullname"),
                        rs.getString("username"),
                        rs.getString("email"),
                        rs.getString("phonenumber"),
                        rs.getString("date_of_birth"),
                        rs.getString("address"),
                        rs.getInt("status")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Receptionistlist;
    }


    public void removeDoctor(){
        connect =HospitalManagementDatabase.connectDB();
        Doctor doctor =DoctorTableview.getSelectionModel().getSelectedItem();
        String sql ="DELETE FROM doctor WHERE username= ?";

        try{
            prepare =connect.prepareStatement(sql);
            prepare.setString(1,doctor.getUserName());
            prepare.executeUpdate();
            alert.successMessage("Removed");
            updateDoctorTable();

        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public void removeStaff(){
        connect =HospitalManagementDatabase.connectDB();
        Staff staff =StaffTableview.getSelectionModel().getSelectedItem();
        String sql ="DELETE FROM staff WHERE username= ?";

        try{
            prepare =connect.prepareStatement(sql);
            prepare.setString(1,staff.getUserName());
            prepare.executeUpdate();
            alert.successMessage("Removed");
            updateStaffTable();

        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public void removeReceptionist(){
        connect =HospitalManagementDatabase.connectDB();
        Receptionist receptionist =ReceptionistTableview.getSelectionModel().getSelectedItem();
        String sql ="DELETE FROM receptionist WHERE username= ?";

        try{
            prepare =connect.prepareStatement(sql);
            prepare.setString(1,receptionist.getUserName());
            prepare.executeUpdate();
            alert.successMessage("Removed");
            updateReceptionistTable();

        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
   


}
