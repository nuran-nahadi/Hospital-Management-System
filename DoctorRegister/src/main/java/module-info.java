module com.example.doctorregister {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.dlsc.formsfx;

    opens com.example.doctorregister to javafx.fxml;
    exports com.example.doctorregister;
}