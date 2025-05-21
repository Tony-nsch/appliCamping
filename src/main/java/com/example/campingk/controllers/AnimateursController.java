package com.example.campingk.controllers;

import com.example.campingk.App;
import com.example.campingk.ConnexionBDD;
import com.example.campingk.classes.Animateur;
import com.example.campingk.classes.Animation;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;

public class AnimateursController {

    // Pages
    @FXML
    Button boutonPlaning;

    @FXML
    Button boutonLieux;

    @FXML
    Button boutonAnimations;

    @FXML
    Button ajouter;

    @FXML
    Button supprimer;

    @FXML
    Button modifier;


    // Modération
    @FXML
    ListView listView;

    @FXML
    TextField textFieldNom;

    @FXML
    TextField textFieldPrenom;

    @FXML
    TextField textFieldPays;

    @FXML
    TextField textFieldVille;

    @FXML
    TextField textFieldNumRue;

    @FXML
    TextField textFieldNomRue;

    @FXML
    TextField textFieldEmail;

    @FXML
    TextField textFieldMdp;



    @FXML
    public void initialize() {
        majListView();
    }




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





    public void majListView() {
        try {
            Connection connection = ConnexionBDD.initialiserConnexion();
            Statement statement = connection.createStatement();

            // Exécution de la requête SQL
            String sql = "SELECT idUtilisateur, emailUtilisateur, mdpUtilisateur, nomUtilisateur, prenomUtilisateur, paysUtilisateur, villeUtilisateur, nomRueUtilisateur, numRueUtilisateur, role FROM utilisateur WHERE role = 'animateur'";
            ResultSet resultSet = statement.executeQuery(sql);

            // Effacer les éléments précédents dans la ListView
            listView.getItems().clear();

            // Parcours des résultats et ajout à l'ObservableList
            ObservableList<Animateur> data = FXCollections.observableArrayList();
            while (resultSet.next()) {
                Animateur a = new Animateur(
                        resultSet.getInt("idUtilisateur"),
                        resultSet.getString("nomUtilisateur"),
                        resultSet.getString("prenomUtilisateur"),
                        resultSet.getString("emailUtilisateur"));
                data.add(a);
            }

            // Mise à jour de la ListView avec les nouvelles données
            listView.setItems(data);

            // Fermeture des ressources
            resultSet.close();
            statement.close();
            connection.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void clearFields() {
        textFieldNom.clear();
        textFieldPrenom.clear();
        textFieldEmail.clear();
        textFieldMdp.clear();
        textFieldPays.clear();
        textFieldVille.clear();
        textFieldNomRue.clear();
        textFieldNumRue.clear();
    }



    @FXML
    public void ajouterAnimateur(ActionEvent actionEvent) {
        String nom = textFieldNom.getText();
        String prenom = textFieldPrenom.getText();
        String email = textFieldEmail.getText();
        String mdp = textFieldMdp.getText();
        String pays = textFieldPays.getText();
        String ville = textFieldVille.getText();
        String numRue = textFieldNumRue.getText();
        String nomRue = textFieldNomRue.getText();

        if (nom.isEmpty() || prenom.isEmpty() || email.isEmpty()) return;

        String pseudo = prenom;

        String sql = "INSERT INTO utilisateur (emailUtilisateur, mdpUtilisateur, nomUtilisateur, prenomUtilisateur, paysUtilisateur, villeUtilisateur, nomRueUtilisateur, numRueUtilisateur, role, pseudo) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, 'animateur', ?)";

        try (Connection connection = ConnexionBDD.initialiserConnexion();
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setString(1, email);
            stmt.setString(2, mdp);
            stmt.setString(3, nom);
            stmt.setString(4, prenom);
            stmt.setString(5, pays);
            stmt.setString(6, ville);
            stmt.setString(7, nomRue);
            stmt.setString(8, numRue);
            stmt.setString(9, pseudo);

            stmt.executeUpdate();
            majListView();
            clearFields();


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void supprimerAnimateur() {
        Animateur a = (Animateur) listView.getSelectionModel().getSelectedItem();

        if (a == null) return;

        try {
            Connection connection = ConnexionBDD.initialiserConnexion();

            String sql = "DELETE FROM utilisateur WHERE idUtilisateur = ?";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, a.getId());

            stmt.executeUpdate();

            stmt.close();
            connection.close();

            majListView();
            clearFields();


        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @FXML
    public void modifierAnimateur() {
        Animateur a = (Animateur) listView.getSelectionModel().getSelectedItem();

        if (a == null) return;

        try {
            Connection connection = ConnexionBDD.initialiserConnexion();

            String sql = "UPDATE utilisateur SET emailUtilisateur=?, nomUtilisateur=?, prenomUtilisateur=?, paysUtilisateur=?, villeUtilisateur=?, nomRueUtilisateur=?, numRueUtilisateur=? WHERE idUtilisateur=?";

            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, textFieldEmail.getText());
            stmt.setString(2, textFieldNom.getText());
            stmt.setString(3, textFieldPrenom.getText());
            stmt.setString(4, textFieldPays.getText());
            stmt.setString(5, textFieldVille.getText());
            stmt.setString(6, textFieldNomRue.getText());
            stmt.setString(7, textFieldNumRue.getText());
            stmt.setInt(8, a.getId());

            stmt.executeUpdate();

            stmt.close();
            connection.close();

            majListView();
            clearFields();


        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    public void clicked(MouseEvent mouseEvent) {
        Animateur a = (Animateur) listView.getSelectionModel().getSelectedItem();
        if (a == null) return;

        textFieldNom.setText(a.getNom());
        textFieldPrenom.setText(a.getPrenom());
        textFieldEmail.setText(a.getEmail());

        try {
            Connection connection = ConnexionBDD.initialiserConnexion();
            String sql = "SELECT * FROM utilisateur WHERE idUtilisateur = ?";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, a.getId());
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                textFieldPays.setText(rs.getString("paysUtilisateur"));
                textFieldVille.setText(rs.getString("villeUtilisateur"));
                textFieldNomRue.setText(rs.getString("nomRueUtilisateur"));
                textFieldNumRue.setText(rs.getString("numRueUtilisateur"));
                textFieldMdp.setText(rs.getString("mdpUtilisateur"));
            }

            rs.close();
            stmt.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
