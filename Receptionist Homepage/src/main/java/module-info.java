module com.example.receptionisthomepage {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.almasb.fxgl.all;

    opens com.example.receptionisthomepage to javafx.fxml;
    exports com.example.receptionisthomepage;
}