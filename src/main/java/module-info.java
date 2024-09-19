module com.example.capingk {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.capingk to javafx.fxml;
    exports com.example.capingk;
}