module com.example.register {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.almasb.fxgl.all;

    opens com.example.register to javafx.fxml;
    exports com.example.register;
}