package com.example.hospitalmanagement;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class AddReceptionistController implements Initializable {

    @FXML
    private TableView<Receptionist> ReceptionistTableview;

    @FXML
    private Button button_add;

    @FXML
    private Button button_cancel;

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
    private TableColumn<Receptionist, String> receptionistUserName;

    public void updateReceptionistTable()throws SQLException {

        receptionistFullName.setCellValueFactory(new PropertyValueFactory<>("fullName"));
        receptionistUserName.setCellValueFactory(new PropertyValueFactory<>("userName"));
        receptionistEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        receptionistPhonenum.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        receptionistDOB.setCellValueFactory(new PropertyValueFactory<>("dateOfBirth"));
        receptionistAddress.setCellValueFactory(new PropertyValueFactory<>("address"));

        try{
            ReceptionistTableview.setItems(getReceptionist());
        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            updateReceptionistTable();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ObservableList<Receptionist> getReceptionist() throws SQLException{
        ObservableList<Receptionist> list = FXCollections.observableArrayList();
        try (Connection connect = HospitalManagementDatabase.connectDB();
             PreparedStatement ps = connect.prepareStatement("SELECT * FROM receptionist WHERE status = 0");
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(new Receptionist(
                        rs.getString("fullname"),
                        rs.getString("username"),
                        rs.getString("email"),
                        rs.getString("password"),
                        rs.getString("phonenumber"),
                        rs.getString("date_of_birth"),
                        rs.getInt("status")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    public void addReceptionist(){
        try {
            Receptionist receptionist = ReceptionistTableview.getSelectionModel().getSelectedItem();
            if (receptionist == null) {
                return;
            }
            Connection connect = HospitalManagementDatabase.connectDB();
            PreparedStatement ps = connect.prepareStatement("UPDATE receptionist SET status = 1 WHERE username = ?");
            ps.setString(1, receptionist.getUserName());
            ps.executeUpdate();
            updateReceptionistTable();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

