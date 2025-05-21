package com.example.campingk.controllers;

import com.example.campingk.App;
import com.example.campingk.ConnexionBDD;
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

public class AnimationController extends App {

    // Pages
    @FXML
    Button boutonPlaning;

    @FXML
    Button boutonAnimateurs;

    @FXML
    Button boutonLieux;


    // Modération
    @FXML
    ListView listView;

    @FXML
    Button modifier;

    @FXML
    Button ajouter;

    @FXML Button supprimer;

    @FXML
    private TextField textFieldNom;

    @FXML
    private TextField textFieldLibelle;

    @FXML
    private TextField textFieldImage;

    @FXML
    public void initialize() {
        majListView();
        listView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                Animation selectedAnimation = (Animation) newValue;
                textFieldNom.setText(selectedAnimation.getNomAnimation());
                textFieldLibelle.setText(selectedAnimation.getLibelleAnimation());
                // textFieldImage.setText(selectedAnimation.getImageAnimation());
            }
        });
    }

    public void allerPageAnimateurs(MouseEvent mouseEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("animateurs.fxml"));
        App.lancerPage(fxmlLoader, boutonAnimateurs);
    }

    public void allerPageLieux(MouseEvent mouseEvent) throws IOException {
        App.lancerPage(new FXMLLoader(App.class.getResource("lieux.fxml")), boutonLieux);
    }

    public void allerPagePlaning(MouseEvent mouseEvent) {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("planing.fxml"));
        App.lancerPage(fxmlLoader, boutonPlaning);
    }

    public void majListView() {
        try {
            Connection connection = ConnexionBDD.initialiserConnexion();
            Statement statement = connection.createStatement();

            // Exécution de la requête SQL
            String sql = "SELECT idAnimation, nomAnimation, libelleAnimation FROM animation";
            ResultSet resultSet = statement.executeQuery(sql);

            // Effacer les éléments précédents dans la ListView
            listView.getItems().clear();

            // Parcours des résultats et ajout à l'ObservableList
            ObservableList<Animation> data = FXCollections.observableArrayList();
            while (resultSet.next()) {
                Animation a = new Animation(resultSet.getString("nomAnimation"), resultSet.getString("libelleAnimation"));
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

    @FXML
    public void ajouterAnimation() {
        String sql = "INSERT INTO animation (nomAnimation, libelleAnimation, imageAnimation) VALUES (?, ?, ?)";

        try (Connection connection = ConnexionBDD.initialiserConnexion();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, textFieldNom.getText());
            ps.setString(2, textFieldLibelle.getText());
            ps.setString(3, textFieldImage.getText());

            ps.executeUpdate();
            majListView();
            clearFields();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void modifierAnimation() {
        Animation selectedAnimation = (Animation) listView.getSelectionModel().getSelectedItem();
        if (selectedAnimation == null) return;

        String sql = "UPDATE animation SET nomAnimation = ?, libelleAnimation = ?, imageAnimation = ? WHERE nomAnimation = ?";

        try (Connection connection = ConnexionBDD.initialiserConnexion();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, textFieldNom.getText());
            ps.setString(2, textFieldLibelle.getText());
            ps.setString(3, textFieldImage.getText());
            ps.setString(4, selectedAnimation.getNomAnimation());

            ps.executeUpdate();
            majListView();
            clearFields();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void supprimerAnimation() {
        Animation selectedAnimation = (Animation) listView.getSelectionModel().getSelectedItem();
        if (selectedAnimation == null) return;

        String sql = "DELETE FROM animation WHERE nomAnimation = ?";

        try (Connection connection = ConnexionBDD.initialiserConnexion();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, selectedAnimation.getNomAnimation());

            ps.executeUpdate();
            majListView();
            clearFields();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void handleListViewSelection(MouseEvent mouseEvent) {
        Animation selectedAnimation = (Animation) listView.getSelectionModel().getSelectedItem();
        if (selectedAnimation != null) {
            textFieldNom.setText(selectedAnimation.getNomAnimation());
            textFieldLibelle.setText(selectedAnimation.getLibelleAnimation());
        }
    }

    public void clearFields() {
        textFieldNom.clear();
        textFieldLibelle.clear();
        textFieldImage.clear();
    }
}
