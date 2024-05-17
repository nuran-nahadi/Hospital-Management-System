package com.example.hospitalmanagement;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class AdminHomePageController implements Initializable {

    @FXML
    private TableView<Doctor> DoctorTableview;

    @FXML
    private TableView<Patient> PatientTableview;

    @FXML
    private TableView<Receptionist> ReceptionistTableview;

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
    private Button btRemoveDoctor;

    @FXML
    private Button btSearchDoctor;

    @FXML
    private Button button_logout;

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
                //religion.setText(result.getString("religion"));
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

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Initialize Doctor TableView columns
        doctorFullName.setCellValueFactory(new PropertyValueFactory<>("fullName"));
        doctorUserName.setCellValueFactory(new PropertyValueFactory<>("userName"));
        doctorEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        doctorPhoneNum.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        doctorDateOfbirth.setCellValueFactory(new PropertyValueFactory<>("dateOfBirth"));
        doctorSpecialization.setCellValueFactory(new PropertyValueFactory<>("specialization"));
        doctorEduQualification.setCellValueFactory(new PropertyValueFactory<>("educationalQualification"));
        doctorAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        doctorStatus.setCellValueFactory(new PropertyValueFactory<>("status"));

        // Populate Doctor TableView with data
        try {
            DoctorTableview.setItems(getDoctors());
        } catch (SQLException e) {
            e.printStackTrace();
        }

        //
        patientFullName.setCellValueFactory(new PropertyValueFactory<>("fullName"));
        patientUserName.setCellValueFactory(new PropertyValueFactory<>("userName"));
        patientEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        patientphonenum.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        patientDOB.setCellValueFactory(new PropertyValueFactory<>("dateOfBirth"));
        patientDisease.setCellValueFactory(new PropertyValueFactory<>("disease"));
        patientDoctorName.setCellValueFactory(new PropertyValueFactory<>("doctorName"));
        patientDoctorName.setCellValueFactory(new PropertyValueFactory<>("address"));

        try{
            PatientTableview.setItems(getPatients());
        }
        catch (SQLException e){
            e.printStackTrace();
        }


        // Initialize Staff TableView columns
        StaffFullName.setCellValueFactory(new PropertyValueFactory<>("fullName"));
        StaffUserName.setCellValueFactory(new PropertyValueFactory<>("userName"));
        StaffEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        StaffPhoneNumber.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        StaffDOB.setCellValueFactory(new PropertyValueFactory<>("dateOfBirth"));
        StaffAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        StaffStatus.setCellValueFactory(new PropertyValueFactory<>("status"));

        // Populate Staff TableView with data
        try {
            StaffTableview.setItems(getStaffs());
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Initialize Receptionist TableView columns
        receptionistFullName.setCellValueFactory(new PropertyValueFactory<>("fullName"));
        receptionistUserName.setCellValueFactory(new PropertyValueFactory<>("userName"));
        receptionistEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        receptionistPhonenum.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        receptionistDOB.setCellValueFactory(new PropertyValueFactory<>("dateOfBirth"));
        receptionistAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        receptionistStatus.setCellValueFactory(new PropertyValueFactory<>("status"));

        // Populate Staff TableView with data
        try {
            ReceptionistTableview.setItems(getReceptionists());
        } catch (SQLException e) {
            e.printStackTrace();
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



    public ObservableList<Staff> getStaffs() throws SQLException {
        ObservableList<Staff> Stafflist = FXCollections.observableArrayList();
        try (Connection connect = HospitalManagementDatabase.connectDB();
             PreparedStatement ps = connect.prepareStatement("SELECT * FROM staff");
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
             PreparedStatement ps = connect.prepareStatement("SELECT * FROM receptionist");
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



}
