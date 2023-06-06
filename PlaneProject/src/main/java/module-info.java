module com.example.planeproject {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires com.fasterxml.jackson.databind;

    opens com.example.planeproject;
    exports com.example.planeproject;
}