package com.example.campingk.controllers;

import com.example.campingk.App;
import com.example.campingk.ConnexionBDD;
import com.example.campingk.classes.Animation;
import com.example.campingk.classes.Planing;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class PlaningController {

    // Pages
    @FXML
    Button boutonAnimateurs;

    @FXML
    Button boutonLieux;

    @FXML
    Button boutonAnimations;


    // Modération
    @FXML
    ListView listView;

    @FXML
    public void initialize() {
        majListView();
    }

    public void allerPageAnimateurs(MouseEvent mouseEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("animateurs.fxml"));
        App.lancerPage(fxmlLoader, boutonAnimateurs);
    }

    public void allerPageLieux(MouseEvent mouseEvent) throws IOException {
        App.lancerPage(new FXMLLoader(App.class.getResource("lieux.fxml")), boutonLieux);
    }

    public void allerPageAnimations(MouseEvent mouseEvent) {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("animations.fxml"));
        App.lancerPage(fxmlLoader, boutonAnimations);
    }

    public void majListView() {
        try {
            Connection connection = ConnexionBDD.initialiserConnexion();
            Statement statement = connection.createStatement();

            // Exécution de la requête SQL
            String sql = "SELECT\n" +
                    "creneau.dateCreneau, creneau.heureCreneau, creneau.dureeCreneau, creneau.placesCreneau,\n" +
                    "animation.nomAnimation,\n" +
                    "animateur.prenomAnimateur, animateur.nomAnimateur,\n" +
                    "lieu.libelleLieu, lieu.numRueLieu, lieu.nomRueLieu, lieu.villeLieu\n" +
                    "\n" +
                    "FROM `creneau` \n" +
                    "INNER JOIN animation ON creneau.idAnimation = animation.idAnimation\n" +
                    "INNER JOIN animer ON creneau.idCreneau = animer.idCreneau \n" +
                    "INNER JOIN animateur ON animer.numAnimateur = animateur.numAnimateur \n" +
                    "INNER JOIN lieu ON creneau.idLieu = lieu.idLieu;";
            ResultSet resultSet = statement.executeQuery(sql);

            // Effacer les éléments précédents dans la ListView
            listView.getItems().clear();

            // Parcours des résultats et ajout à l'ObservableList
            ObservableList<Planing> data = FXCollections.observableArrayList();
            while (resultSet.next()) {
                Planing p = new Planing(
                        resultSet.getDate("dateCreneau"),
                        resultSet.getTime("heureCreneau"),
                        resultSet.getTime("dureeCreneau"),
                        resultSet.getInt("placesCreneau"),
                        resultSet.getString("nomAnimation"),
                        resultSet.getString("prenomAnimateur"),
                        resultSet.getString("nomAnimateur"),
                        resultSet.getString("libelleLieu"),
                        resultSet.getString("numRueLieu"),
                        resultSet.getString("nomRueLieu"),
                        resultSet.getString("villeLieu"));
                data.add(p);
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
}
