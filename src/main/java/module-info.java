module com.example.hospitalmanagement {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires mysql.connector.j;


    opens com.example.hospitalmanagement to javafx.fxml;
    exports com.example.hospitalmanagement;
}