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


    // Modération
    @FXML
    ListView listView;

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
        try {
            Connection connection = ConnexionBDD.initialiserConnexion();
            Statement statement = connection.createStatement();

            // Exécution de la requête SQL
            String sql = "SELECT libelleLieu, numRueLieu, nomRueLieu, villeLieu FROM lieu";
            ResultSet resultSet = statement.executeQuery(sql);

            // Effacer les éléments précédents dans la ListView
            listView.getItems().clear();

            // Parcours des résultats et ajout à l'ObservableList
            ObservableList<Lieu> data = FXCollections.observableArrayList();
            while (resultSet.next()) {
                Lieu l = new Lieu(resultSet.getString("libelleLieu"), resultSet.getString("numRueLieu"), resultSet.getString("nomRueLieu"), resultSet.getString("villeLieu"));
                data.add(l);
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
