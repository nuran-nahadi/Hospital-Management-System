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
    private TableView<Doctor> DoctorTableview;

    @FXML
    private TextField DoctorfilterField;

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
        try {
            ObservableList<Doctor> Dlist;
            Dlist =getDoctors();
            FilteredList<Doctor> filteredData = new FilteredList<>(Dlist, b -> true);
            SortedList<Doctor> sortedData = new SortedList<>(filteredData);
            sortedData.comparatorProperty().bind(DoctorTableview.comparatorProperty());
            DoctorTableview.setItems(sortedData);

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

    @FXML
    private TextField addressField;

    @FXML
    private Button button_Update;

    @FXML
    private TextField dobField;

    @FXML
    private TextField emailField;

    @FXML
    private TextField nameField;

    @FXML
    private TextField passwordField;

    @FXML
    private TextField phoneField;

    @FXML
    private TextField usernameField;

    private Connection connect;
    private PreparedStatement prepare;
    private ResultSet result;


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
    private Button button_logout;

    @FXML
    protected void onlogOUTClick(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HospitalManagementSystem.class.getResource("Homepage.fxml"));
        Scene scene = new Scene(fxmlLoader.load(),743, 480);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle("Hospital Management System");
        stage.setScene(scene);
        stage.show();

    }
}
