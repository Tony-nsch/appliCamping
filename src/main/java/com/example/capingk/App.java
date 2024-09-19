package com.example.capingk;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

public class App extends Application {



    @Override
    public void start(Stage stage) throws IOException {


        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("events.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1456, 816);
        stage.resizableProperty().setValue(Boolean.FALSE);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();





    }

    public static void main(String[] args) {
        launch();
    }
}