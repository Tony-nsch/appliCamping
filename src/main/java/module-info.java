module com.example.capingk {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.campingk to javafx.fxml;
    exports com.example.campingk;
}