package com.example.campingk.controllers;

import com.example.campingk.ConnexionBDD;
import com.example.campingk.util.PasswordUtils;
import javafx.scene.control.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.example.campingk.App;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.input.MouseEvent;
import org.w3c.dom.Text;

import java.io.IOException;

public class SigninController {

    @FXML
    Button creerCompte;

    @FXML
    Button retour;

    @FXML
    TextField emailUtilisateur;

    @FXML
    PasswordField mdpUtilisateur;

    @FXML
    PasswordField confMdp;

    @FXML
    TextField nomUtilisateur;
    @FXML
    TextField prenomUtilisateur;
    @FXML
    TextField paysUtilisateur;
    @FXML
    TextField villeUtilisateur;
    @FXML
    TextField nomRueUtilisateur;
    @FXML
    TextField numRueUtilisateur;
    @FXML
    private ComboBox<String> role;

    @FXML
    public void initialize() {
        role.getItems().addAll("Admin", "Utilisateur", "Invité");
    }




    public void seConnecter(MouseEvent mouseEvent) throws IOException {
        try {
            String mdpText = mdpUtilisateur.getText();
            String[] mdpSplit = mdpText.split("");

            boolean nbCarac = false;
            boolean min = false;
            boolean maj = false;
            boolean chiffre = false;
            boolean caracSpe = false;

            if(mdpSplit.length >= 12) nbCarac = true;

            for (String s : mdpSplit) {
                char c = s.charAt(0);
                if (Character.isLowerCase(c)) min = true;
                if (Character.isUpperCase(c)) maj = true;
                if (Character.isDigit(c)) chiffre = true;
                if (!Character.isLetterOrDigit(c)) caracSpe = true;
            }

            if(mdpUtilisateur.getText().equals(confMdp.getText()) && !emailUtilisateur.getText().isEmpty() && nbCarac && min && maj && chiffre && caracSpe) {

                Connection conn = ConnexionBDD.initialiserConnexion();

                if (conn != null) {
                    System.out.println("Email: " + emailUtilisateur.getText());
                    System.out.println("Mot de passe: " + mdpUtilisateur.getText());
                    System.out.println("Nom: " + nomUtilisateur.getText());
                    System.out.println("Prenom: " + prenomUtilisateur.getText());
                    System.out.println("Pays: " + paysUtilisateur.getText());
                    System.out.println("Ville: " + villeUtilisateur.getText());
                    System.out.println("Rue: " + nomRueUtilisateur.getText());
                    System.out.println("Numéro de rue: " + numRueUtilisateur.getText());
                    System.out.println("Rôle: " + role.getValue());

                    try {
                        // Hacher le mot de passe
                        String hashedPassword = PasswordUtils.hashPassword(mdpUtilisateur.getText());

                        // Préparer la requête d'insertion
                        String sql = "INSERT INTO utilisateur (emailUtilisateur, mdpUtilisateur, nomUtilisateur, prenomUtilisateur, paysUtilisateur, villeUtilisateur, nomRueUtilisateur, numRueUtilisateur, role) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
                        PreparedStatement statement = conn.prepareStatement(sql);
                        statement.setString(1, emailUtilisateur.getText());
                        statement.setString(2, hashedPassword); // Stocker le mot de passe haché
                        statement.setString(3, nomUtilisateur.getText());
                        statement.setString(4, prenomUtilisateur.getText());
                        statement.setString(5, paysUtilisateur.getText());
                        statement.setString(6, villeUtilisateur.getText());
                        statement.setString(7, nomRueUtilisateur.getText());
                        statement.setString(8, numRueUtilisateur.getText());
                        statement.setString(9, role.getValue());

                        // Exécuter la requête
                        int rowsInserted = statement.executeUpdate();
                        if (rowsInserted > 0) {
                            // Redirection vers la page de connexion si l'insertion a réussi
                            App.lancerPage(new FXMLLoader(App.class.getResource("login.fxml")), creerCompte);
                        }
                    } catch (SQLException ex) {
                        // Gérer les erreurs SQL
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Erreur SQL");
                        alert.setContentText("Impossible de créer le compte 0 : " + ex.getMessage());
                        alert.showAndWait();
                    } finally {
                        try {
                            conn.close();
                        } catch (SQLException ex) {
                            ex.printStackTrace();
                        }
                    }
                } else {
                    emailUtilisateur.setText("");
                    mdpUtilisateur.setText("");
                    confMdp.setText("");
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Erreur");
                    alert.setContentText("Impossible de créer le compte 1");
                    alert.showAndWait();
                }
            } else {
                emailUtilisateur.setText("");
                mdpUtilisateur.setText("");
                confMdp.setText("");
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur");
                alert.setContentText("Impossible de créer le compte 2");
                alert.showAndWait();
            }

        } catch (Exception ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setContentText("MDP incorrect");
            alert.showAndWait();
        }
    }


    public void allerPageLogin(MouseEvent mouseEvent) throws IOException {
        FXMLLoader page = new FXMLLoader(App.class.getResource("login.fxml"));
        App.lancerPage(page, retour);
    }
}
