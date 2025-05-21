package com.example.campingk.controllers;

import com.example.campingk.App;
import com.example.campingk.ConnexionBDD;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginController {

    @FXML
    Button connecter;

    @FXML
    Button creerCompte;

    @FXML
    TextField emailUtilisateur;

    @FXML
    PasswordField mdpUtilisateur;

    public void allerPagePlaning(MouseEvent mouseEvent) throws IOException {
        Connection conn = ConnexionBDD.initialiserConnexion();
        if (conn != null) {
            try {
                String sql = "SELECT emailUtilisateur, mdpUtilisateur FROM utilisateur WHERE emailUtilisateur = ? AND mdpUtilisateur = ?";
                PreparedStatement statement = conn.prepareStatement(sql);
                statement.setString(1, emailUtilisateur.getText());
                statement.setString(2, mdpUtilisateur.getText());

                ResultSet resultSet = statement.executeQuery();

                if (resultSet.next()) {
                    FXMLLoader page = new FXMLLoader(App.class.getResource("planing.fxml"));
                    App.lancerPage(page, connecter);
                } else {
                    emailUtilisateur.setText("");
                    mdpUtilisateur.setText("");
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Erreur Insertion");
                    alert.setContentText("email ou mot de passe incorrect");
                    alert.showAndWait();
                }
            } catch (SQLException ex) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur SQL");
                alert.setContentText("Impossible de vérifier les informations : " + ex.getMessage());
                alert.showAndWait();
            } finally {
                try {
                    conn.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }


    public void allerPageSignin(MouseEvent mouseEvent) {
        FXMLLoader page = new FXMLLoader(App.class.getResource("signin.fxml"));
        App.lancerPage(page, creerCompte);
    }
}
