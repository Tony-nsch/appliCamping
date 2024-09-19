package com.example.capingk;

import javafx.fxml.FXML;
import javafx.scene.layout.Pane;

import java.io.IOException;

public class Controller {
    @FXML
    Pane pane;

    @FXML
    public void initialize() throws IOException {
        pane.setStyle("-fx-background-color: rgba(0, 100, 100, 50); -fx-background-radius: 10;");
    }


}