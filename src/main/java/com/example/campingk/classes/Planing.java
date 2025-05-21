package com.example.campingk.classes;

import java.time.LocalDate;
import java.time.LocalTime;

public class Planing {
    private int idCreneau;
    private LocalDate dateCreneau;
    private LocalTime heureCreneau;
    private LocalTime dureeCreneau;
    private int placesCreneau;
    private String nomAnimation;
    private String prenomAnimateur;
    private String nomAnimateur;
    private String libelleLieu;
    private String numRueLieu;
    private String nomRueLieu;
    private String villeLieu;

    public Planing(int idCreneau, LocalDate date, LocalTime heure, LocalTime duree, int places,
                   String animationNom, String animateurPrenom, String animateurNom,
                   String libelle, String numRue, String nomRue, String ville) {
        this.idCreneau = idCreneau;
        this.dateCreneau = date;
        this.heureCreneau = heure;
        this.dureeCreneau = duree;
        this.placesCreneau = places;
        this.nomAnimation = animationNom;
        this.prenomAnimateur = animateurPrenom;
        this.nomAnimateur = animateurNom;
        this.libelleLieu = libelle;
        this.numRueLieu = numRue;
        this.nomRueLieu = nomRue;
        this.villeLieu = ville;
    }

    public int getIdCreneau() {
        return idCreneau;
    }

    public LocalDate getDateCreneau() {
        return dateCreneau;
    }

    public LocalTime getHeureCreneau() {
        return heureCreneau;
    }

    public LocalTime getDureeCreneau() {
        return dureeCreneau;
    }

    public int getPlacesCreneau() {
        return placesCreneau;
    }

    public String getNomAnimation() {
        return nomAnimation;
    }

    public String getPrenomAnimateur() {
        return prenomAnimateur;
    }

    public String getNomAnimateur() {
        return nomAnimateur;
    }

    public String getLibelleLieu() {
        return libelleLieu;
    }

    public String getNumRueLieu() {
        return numRueLieu;
    }

    public String getNomRueLieu() {
        return nomRueLieu;
    }

    public String getVilleLieu() {
        return villeLieu;
    }


    @Override
    public String toString() {
        return nomAnimation + " - dirigé par " + prenomAnimateur + " " + nomAnimateur + " (" + placesCreneau + " places)\n"
                + "le " + dateCreneau + " à " + heureCreneau + " pendant " + dureeCreneau + "\n"
                + libelleLieu + " - " + numRueLieu + " " + nomRueLieu + ", " + villeLieu;
    }
}
