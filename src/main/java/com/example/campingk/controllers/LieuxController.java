package com.example.campingk.controllers;

import com.example.campingk.App;
import com.example.campingk.ConnexionBDD;
import com.example.campingk.classes.Animateur;
import com.example.campingk.classes.Lieu;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class LieuxController {

    // Pages
    @FXML
    Button btnPlaning;

    @FXML
    Button btnAnimateurs;

    @FXML
    Button btnAnimations;

    @FXML
    Button ajouter;

    @FXML
    Button supprimer;

    @FXML
    Button modifier;

    // Modération
    @FXML
    ListView listView;

    @FXML private TextField textFieldLibelle;
    @FXML private TextField textFieldNumRue;
    @FXML private TextField textFieldNomRue;
    @FXML private TextField textFieldVille;
    @FXML private TextField textFieldPays;
    @FXML private TextField textFieldCoordonnees;


    @FXML
    public void initialize() {
        majListView();
    }


    public void allerPagePlaning(MouseEvent mouseEvent) throws IOException {
        App.lancerPage(new FXMLLoader(App.class.getResource("planing.fxml")), btnPlaning);
    }

    public void allerPageAnimateurs(MouseEvent mouseEvent) throws IOException  {
        App.lancerPage(new FXMLLoader(App.class.getResource("animateurs.fxml")), btnAnimateurs);
    }

    public void allerPageAnimations(MouseEvent mouseEvent) throws IOException  {
        App.lancerPage(new FXMLLoader(App.class.getResource("animations.fxml")), btnAnimations);
    }


    public void majListView() {
        String sql = "SELECT idLieu, libelleLieu, coordonnee, paysLieu, villeLieu, nomRueLieu, numRueLieu FROM lieu";

        try (Connection connection = ConnexionBDD.initialiserConnexion();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            ObservableList<Lieu> data = FXCollections.observableArrayList();

            while (resultSet.next()) {
                Lieu l = new Lieu(
                        resultSet.getString("libelleLieu"),
                        resultSet.getString("paysLieu"),
                        resultSet.getString("villeLieu"),
                        resultSet.getString("nomRueLieu"),
                        resultSet.getString("numRueLieu"),
                        resultSet.getString("coordonnee") // Include the coordonnee field
                );
                data.add(l);
            }

            listView.setItems(data);

        } catch (Exception e) {
            e.printStackTrace();
            // Consider logging the error or showing an error message to the user
        }
    }



    @FXML
    public void clicked(MouseEvent event) {
        Lieu selectedLieu = (Lieu) listView.getSelectionModel().getSelectedItem();
        if (selectedLieu == null) return;

        textFieldLibelle.setText(selectedLieu.getLibelleLieu());
        textFieldNumRue.setText(selectedLieu.getNumRueLieu());
        textFieldNomRue.setText(selectedLieu.getNomRueLieu());
        textFieldVille.setText(selectedLieu.getVilleLieu());
        textFieldPays.setText(selectedLieu.getPaysLieu());
        textFieldCoordonnees.setText(selectedLieu.getCoordonnee());
    }


    @FXML
    public void ajouterLieu() {
        String sql = "INSERT INTO lieu (libelleLieu, coordonnee, paysLieu, villeLieu, nomRueLieu, numRueLieu) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection connection = ConnexionBDD.initialiserConnexion();
             var ps = connection.prepareStatement(sql)) {

            ps.setString(1, textFieldLibelle.getText());
            ps.setString(2, textFieldCoordonnees.getText());
            ps.setString(3, textFieldPays.getText());
            ps.setString(4, textFieldVille.getText());
            ps.setString(5, textFieldNomRue.getText());
            ps.setString(6, textFieldNumRue.getText());

            ps.executeUpdate();
            majListView();
            clearFields();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @FXML
    public void modifierLieu() {
        Lieu selectedLieu = (Lieu) listView.getSelectionModel().getSelectedItem();
        if (selectedLieu == null) return;

        String sql = "UPDATE lieu SET libelleLieu = ?, coordonnee = ?, paysLieu = ?, villeLieu = ?, nomRueLieu = ?, numRueLieu = ? WHERE libelleLieu = ? AND villeLieu = ?";

        try (Connection connection = ConnexionBDD.initialiserConnexion();
             var ps = connection.prepareStatement(sql)) {

            ps.setString(1, textFieldLibelle.getText());
            ps.setString(2, textFieldCoordonnees.getText());
            ps.setString(3, textFieldPays.getText());
            ps.setString(4, textFieldVille.getText());
            ps.setString(5, textFieldNomRue.getText());
            ps.setString(6, textFieldNumRue.getText());
            ps.setString(7, selectedLieu.getLibelleLieu());
            ps.setString(8, selectedLieu.getVilleLieu());

            ps.executeUpdate();
            majListView();
            clearFields();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @FXML
    public void supprimerLieu() {
        Lieu selectedLieu = (Lieu) listView.getSelectionModel().getSelectedItem();
        if (selectedLieu == null) return;

        String sql = "DELETE FROM lieu WHERE libelleLieu = ? AND villeLieu = ?";

        try (Connection connection = ConnexionBDD.initialiserConnexion();
             var ps = connection.prepareStatement(sql)) {

            ps.setString(1, selectedLieu.getLibelleLieu());
            ps.setString(2, selectedLieu.getVilleLieu());

            ps.executeUpdate();
            majListView();
            clearFields();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void clearFields() {
        textFieldLibelle.clear();
        textFieldPays.clear();
        textFieldVille.clear();
        textFieldNomRue.clear();
        textFieldNumRue.clear();
        textFieldCoordonnees.clear();
    }
}
