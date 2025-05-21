package com.example.campingk.controllers;

import com.example.campingk.App;
import com.example.campingk.ConnexionBDD;
import com.example.campingk.classes.Planing;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.sql.*;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class PlaningController {

    @FXML Button boutonAnimateurs, boutonLieux, boutonAnimations;
    @FXML ComboBox<String> animation, ajouterAnimateur;
    @FXML TextField textFieldAnimateursPresents, textFieldHeure, textFieldDuree;
    @FXML DatePicker datePickerDate;
    @FXML Spinner<Integer> spinnerNombrePlaces;
    @FXML ListView<Planing> listView;
    @FXML Button ajouter, modifier, supprimer;

    private int idCreneauSelectionne = -1;



    public void allerPageAnimateurs(MouseEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("animateurs.fxml"));
        App.lancerPage(fxmlLoader, boutonAnimateurs);
    }

    public void allerPageLieux(MouseEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("lieux.fxml"));
        App.lancerPage(fxmlLoader, boutonLieux);
    }

    public void allerPageAnimations(MouseEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("animations.fxml"));
        App.lancerPage(fxmlLoader, boutonAnimations);
    }

    public void majListView() {
        try (Connection connection = ConnexionBDD.initialiserConnexion()) {
            String sql = "SELECT creneau.idCreneau, creneau.dateCreneau, creneau.heureCreneau, creneau.dureeCreneau, creneau.placesCreneau, " +
                    "animation.nomAnimation, utilisateur.prenomUtilisateur, utilisateur.nomUtilisateur, " +
                    "lieu.libelleLieu, lieu.numRueLieu, lieu.nomRueLieu, lieu.villeLieu " +
                    "FROM creneau " +
                    "INNER JOIN animation ON creneau.idAnimation = animation.idAnimation " +
                    "INNER JOIN animer ON creneau.idCreneau = animer.idCreneau " +
                    "INNER JOIN utilisateur ON animer.idAnimateur = utilisateur.idUtilisateur " +
                    "INNER JOIN lieu ON creneau.idLieu = lieu.idLieu ORDER BY creneau.dateCreneau";
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            ObservableList<Planing> data = FXCollections.observableArrayList();
            while (rs.next()) {
                Planing p = new Planing(
                        rs.getInt("idCreneau"),
                        rs.getDate("dateCreneau").toLocalDate(),
                        rs.getTime("heureCreneau").toLocalTime(),
                        rs.getTime("dureeCreneau").toLocalTime(),
                        rs.getInt("placesCreneau"),
                        rs.getString("nomAnimation"),
                        rs.getString("prenomUtilisateur"),
                        rs.getString("nomUtilisateur"),
                        rs.getString("libelleLieu"),
                        rs.getString("numRueLieu"),
                        rs.getString("nomRueLieu"),
                        rs.getString("villeLieu")
                );
                data.add(p);
            }
            listView.setItems(data);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void chargerComboBox() {
        try (Connection conn = ConnexionBDD.initialiserConnexion()) {
            List<String> animations = new ArrayList<>();
            List<String> animateurs = new ArrayList<>();

            ResultSet rsAnim = conn.createStatement().executeQuery("SELECT nomAnimation FROM animation");
            while (rsAnim.next()) {
                animations.add(rsAnim.getString("nomAnimation"));
            }

            ResultSet rsAnimtr = conn.createStatement().executeQuery("SELECT prenomUtilisateur, nomUtilisateur FROM utilisateur WHERE role = 'animateur'");
            while (rsAnimtr.next()) {
                animateurs.add(rsAnimtr.getString("prenomUtilisateur") + " " + rsAnimtr.getString("nomUtilisateur"));
            }

            animation.setItems(FXCollections.observableArrayList(animations));
            ajouterAnimateur.setItems(FXCollections.observableArrayList(animateurs));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void remplirFormulaireDepuisSelection() {
        Planing p = listView.getSelectionModel().getSelectedItem();
        if (p == null) return;
        idCreneauSelectionne = p.getIdCreneau();
        datePickerDate.setValue(p.getDateCreneau());
        textFieldHeure.setText(p.getHeureCreneau().toString());
        textFieldDuree.setText(p.getDureeCreneau().toString());
        spinnerNombrePlaces.getValueFactory().setValue(p.getPlacesCreneau());
        animation.setValue(p.getNomAnimation());
        ajouterAnimateur.setValue(p.getPrenomAnimateur() + " " + p.getNomAnimateur());
    }




    @FXML
    public void initialize() {
        majListView();
        chargerComboBox();
        spinnerNombrePlaces.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 100, 10));
        listView.setOnMouseClicked(event -> remplirFormulaireDepuisSelection());
    }


    @FXML
    private void ajouterCreneau() {
        try (Connection conn = ConnexionBDD.initialiserConnexion()) {
            LocalTime heure = LocalTime.parse(textFieldHeure.getText());
            LocalTime duree = LocalTime.parse(textFieldDuree.getText());

            PreparedStatement ps = conn.prepareStatement(
                    "INSERT INTO creneau (dateCreneau, heureCreneau, dureeCreneau, placesCreneau, idAnimation, idLieu) " +
                            "VALUES (?, ?, ?, ?, (SELECT idAnimation FROM animation WHERE nomAnimation = ?), 1)",
                    Statement.RETURN_GENERATED_KEYS);
            ps.setDate(1, Date.valueOf(datePickerDate.getValue()));
            ps.setTime(2, Time.valueOf(heure));
            ps.setTime(3, Time.valueOf(duree));
            ps.setInt(4, spinnerNombrePlaces.getValue());
            ps.setString(5, animation.getValue());
            ps.executeUpdate();

            ResultSet generatedKeys = ps.getGeneratedKeys();
            int idCreneau = -1;
            if (generatedKeys.next()) {
                idCreneau = generatedKeys.getInt(1);
            }

            String[] nomPrenom = ajouterAnimateur.getValue().split(" ");
            PreparedStatement psAnim = conn.prepareStatement(
                    "SELECT idUtilisateur FROM utilisateur WHERE prenomUtilisateur = ? AND nomUtilisateur = ?");
            psAnim.setString(1, nomPrenom[0]);
            psAnim.setString(2, nomPrenom[1]);
            ResultSet rsAnim = psAnim.executeQuery();

            if (rsAnim.next()) {
                int idAnimateur = rsAnim.getInt("idUtilisateur");
                PreparedStatement psAnimer = conn.prepareStatement("INSERT INTO animer (idCreneau, idAnimateur) VALUES (?, ?)");
                psAnimer.setInt(1, idCreneau);
                psAnimer.setInt(2, idAnimateur);
                psAnimer.executeUpdate();
            }

            majListView();
            viderChampsFormulaire();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void modifierCreneau() {
        if (idCreneauSelectionne == -1) return;
        try (Connection conn = ConnexionBDD.initialiserConnexion()) {
            LocalTime heure = LocalTime.parse(textFieldHeure.getText());
            LocalTime duree = LocalTime.parse(textFieldDuree.getText());

            PreparedStatement ps = conn.prepareStatement(
                    "UPDATE creneau SET dateCreneau = ?, heureCreneau = ?, dureeCreneau = ?, placesCreneau = ?, idAnimation = (SELECT idAnimation FROM animation WHERE nomAnimation = ?) WHERE idCreneau = ?");
            ps.setDate(1, Date.valueOf(datePickerDate.getValue()));
            ps.setTime(2, Time.valueOf(heure));
            ps.setTime(3, Time.valueOf(duree));
            ps.setInt(4, spinnerNombrePlaces.getValue());
            ps.setString(5, animation.getValue());
            ps.setInt(6, idCreneauSelectionne);
            ps.executeUpdate();

            String[] nomPrenom = ajouterAnimateur.getValue().split(" ");
            PreparedStatement psAnim = conn.prepareStatement(
                    "SELECT idUtilisateur FROM utilisateur WHERE prenomUtilisateur = ? AND nomUtilisateur = ?");
            psAnim.setString(1, nomPrenom[0]);
            psAnim.setString(2, nomPrenom[1]);
            ResultSet rsAnim = psAnim.executeQuery();

            if (rsAnim.next()) {
                int idAnimateur = rsAnim.getInt("idUtilisateur");
                PreparedStatement psUpdate = conn.prepareStatement("UPDATE animer SET idAnimateur = ? WHERE idCreneau = ?");
                psUpdate.setInt(1, idAnimateur);
                psUpdate.setInt(2, idCreneauSelectionne);
                psUpdate.executeUpdate();
            }

            majListView();
            viderChampsFormulaire();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void supprimerCreneau() {
        if (idCreneauSelectionne == -1) return;
        try (Connection conn = ConnexionBDD.initialiserConnexion()) {
            PreparedStatement ps1 = conn.prepareStatement("DELETE FROM animer WHERE idCreneau = ?");
            ps1.setInt(1, idCreneauSelectionne);
            ps1.executeUpdate();

            PreparedStatement ps2 = conn.prepareStatement("DELETE FROM creneau WHERE idCreneau = ?");
            ps2.setInt(1, idCreneauSelectionne);
            ps2.executeUpdate();

            majListView();
            viderChampsFormulaire();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void viderChampsFormulaire() {
        datePickerDate.setValue(null);
        textFieldHeure.clear();
        textFieldDuree.clear();
        spinnerNombrePlaces.getValueFactory().setValue(10);
        animation.setValue(null);
        ajouterAnimateur.setValue(null);
        idCreneauSelectionne = -1;
    }
}
