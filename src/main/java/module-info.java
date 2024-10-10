module com.example.capingk {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.campingk to javafx.fxml;
    exports com.example.campingk;
    exports com.example.campingk.controllers;
    opens com.example.campingk.controllers to javafx.fxml;
}