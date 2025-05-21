package com.example.campingk.classes;

public class Animateur {
    private int numAnimateur;
    private String nomAnimateur;
    private String prenomAnimateur;
    private String email;

    public Animateur(int num, String nom, String prenom, String mail) {
        numAnimateur = num;
        nomAnimateur = nom;
        prenomAnimateur = prenom;
        email = mail;
    }

    public int getId() {
        return numAnimateur;
    }

    public String getNom() {
        return nomAnimateur;
    }

    public String getPrenom() {
        return prenomAnimateur;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String toString(){
        return numAnimateur + ": " + nomAnimateur + " " + prenomAnimateur + " (" + email + ")";
    }
}
