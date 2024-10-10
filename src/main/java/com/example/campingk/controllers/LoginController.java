package com.example.campingk.controllers;

import com.example.campingk.App;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.io.IOException;

public class LoginController {

    @FXML
    Button connecter;

    @FXML
    Button creerCompte;

    @FXML
    TextField login;

    @FXML
    PasswordField mdp;

    public void allerPagePlaning(MouseEvent mouseEvent) throws IOException {
        if (login.getText().equals("admin") && mdp.getText().equals("0550002D")) {
            FXMLLoader page = new FXMLLoader(App.class.getResource("planing.fxml"));
            App.lancerPage(page, connecter);
        } else {
            login.setText("");
            mdp.setText("");
        }
    }

    public void allerPageSignin(MouseEvent mouseEvent) {
        FXMLLoader page = new FXMLLoader(App.class.getResource("signin.fxml"));
        App.lancerPage(page, creerCompte);
    }
}
