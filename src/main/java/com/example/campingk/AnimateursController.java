package com.example.campingk;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;

import java.io.IOException;

public class AnimateursController {

    @FXML
    Button boutonPlaning;

    @FXML
    Button boutonLieux;

    @FXML
    Button boutonAnimations;

    public void allerPagePlaning(MouseEvent mouseEvent) throws IOException {
        FXMLLoader page = new FXMLLoader(App.class.getResource("planing.fxml"));
        App.lancerPage(page, boutonPlaning);
    }

    public void allerPageLieux(MouseEvent mouseEvent) throws IOException {
        FXMLLoader page = new FXMLLoader(App.class.getResource("lieux.fxml"));
        App.lancerPage(page, boutonLieux);
    }

    public void allerPageAnimations(MouseEvent mouseEvent) throws IOException {
        FXMLLoader page = new FXMLLoader(App.class.getResource("animations.fxml"));
        App.lancerPage(page, boutonAnimations);
    }
}
