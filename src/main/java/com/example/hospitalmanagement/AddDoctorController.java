package com.example.hospitalmanagement;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class AddDoctorController {

    @FXML
    private TableColumn<Doctor, String> Address;

    @FXML
    private TableColumn<?, ?> DateOfbirth;

    @FXML
    private TableView<Doctor> DoctorTableview;

    @FXML
    private TableColumn<?, ?> EduQualification;

    @FXML
    private TableColumn<?, ?> Email;

    @FXML
    private TableColumn<?, ?> PhoneNum;

    @FXML
    private TableColumn<?, ?> Specialization;

    @FXML
    private TableColumn<?, ?> UserName;

    @FXML
    private Button button_add;

    @FXML
    private Button button_cancel;

    @FXML
    private TableColumn<?, ?> FullName;

    @FXML
    private TextField getUsernameField;






}

