package com.example.campingk.controllers;

import com.example.campingk.App;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;

import java.io.IOException;

public class LieuxController {

    @FXML
    Button btnPlaning;

    @FXML
    Button btnAnimateurs;

    @FXML
    Button btnAnimations;

    public void allerPagePlaning(MouseEvent mouseEvent) throws IOException {
        App.lancerPage(new FXMLLoader(App.class.getResource("planing.fxml")), btnPlaning);
    }

    public void allerPageAnimateurs(MouseEvent mouseEvent) throws IOException  {
        App.lancerPage(new FXMLLoader(App.class.getResource("animateurs.fxml")), btnAnimateurs);
    }

    public void allerPageAnimations(MouseEvent mouseEvent) throws IOException  {
        App.lancerPage(new FXMLLoader(App.class.getResource("animations.fxml")), btnAnimations);
    }

    public static class PlaningController {

        @FXML
        Button btnAnimateurs;

        @FXML
        Button btnLieux;

        @FXML
        Button btnAnimations;


        public void allerPageAnimateurs(MouseEvent mouseEvent) throws IOException {
            App.lancerPage(new FXMLLoader(App.class.getResource("animateurs.fxml")), btnAnimateurs);
        }

        public void allerPageLieux(MouseEvent mouseEvent) throws IOException  {
            App.lancerPage(new FXMLLoader(App.class.getResource("lieux.fxml")), btnLieux);
        }

        public void allerPageAnimations(MouseEvent mouseEvent) throws IOException  {
            App.lancerPage(new FXMLLoader(App.class.getResource("animations.fxml")), btnAnimations);
        }
    }
}
