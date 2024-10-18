package com.example.campingk.controllers;

import com.example.campingk.App;
import com.example.campingk.ConnexionBDD;
import com.example.campingk.classes.Animateur;
import com.example.campingk.classes.Animation;
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
import java.util.List;

public class AnimateursController {

    // Pages
    @FXML
    Button boutonPlaning;

    @FXML
    Button boutonLieux;

    @FXML
    Button boutonAnimations;


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
            String sql = "SELECT numAnimateur, nomAnimateur, prenomAnimateur, email FROM animateur";
            ResultSet resultSet = statement.executeQuery(sql);

            // Effacer les éléments précédents dans la ListView
            listView.getItems().clear();

            // Parcours des résultats et ajout à l'ObservableList
            ObservableList<Animateur> data = FXCollections.observableArrayList();
            while (resultSet.next()) {
                Animateur a = new Animateur(
                        resultSet.getInt("numAnimateur"),
                        resultSet.getString("nomAnimateur"),
                        resultSet.getString("prenomAnimateur"),
                        resultSet.getString("email"));
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


    public void clicked(MouseEvent mouseEvent) {
        Object e = listView.getSelectionModel().getSelectedItem();
        System.out.println(e.toString());
        /*textFieldPrenom;
        textFieldPays;
        textFieldVille;
        textFieldNumRue;
        textFieldNomRue;tony    Qsdfghjklm555*
        textFieldEmail;*/
    }
}
