package com.example.campingk.classes;

public class Lieu {
    private int idLieu;
    private String libelleLieu;
    private String numRueLieu;
    private String nomRueLieu;
    private String villeLieu;
    private String paysLieu;
    private String coordonnee; // Added field

    // Constructeur complet (utilisé dans le controller)
    public Lieu(int idLieu, String libelle, String pays, String ville, String rue, String numero, String coordonnee) {
        this.idLieu = idLieu;
        this.libelleLieu = libelle;
        this.paysLieu = pays;
        this.villeLieu = ville;
        this.nomRueLieu = rue;
        this.numRueLieu = numero;
        this.coordonnee = coordonnee; // Initialize the new field
    }

    // Constructeur sans ID (ex: pour ajout d'un nouveau lieu)
    public Lieu(String libelle, String pays, String ville, String rue, String numero, String coordonnee) {
        this.libelleLieu = libelle;
        this.paysLieu = pays;
        this.villeLieu = ville;
        this.nomRueLieu = rue;
        this.numRueLieu = numero;
        this.coordonnee = coordonnee; // Initialize the new field
    }

    // Getters
    public int getIdLieu() { return idLieu; }
    public String getLibelleLieu() { return libelleLieu; }
    public String getPaysLieu() { return paysLieu; }
    public String getVilleLieu() { return villeLieu; }
    public String getNomRueLieu() { return nomRueLieu; }
    public String getNumRueLieu() { return numRueLieu; }
    public String getCoordonnee() { return coordonnee; } // Added getter

    // Setters
    public void setIdLieu(int idLieu) { this.idLieu = idLieu; }
    public void setLibelleLieu(String libelleLieu) { this.libelleLieu = libelleLieu; }
    public void setPaysLieu(String paysLieu) { this.paysLieu = paysLieu; }
    public void setVilleLieu(String villeLieu) { this.villeLieu = villeLieu; }
    public void setNomRueLieu(String nomRueLieu) { this.nomRueLieu = nomRueLieu; }
    public void setNumRueLieu(String numRueLieu) { this.numRueLieu = numRueLieu; }
    public void setCoordonnee(String coordonnee) { this.coordonnee = coordonnee; } // Added setter

    @Override
    public String toString() {
        return libelleLieu + " (" + numRueLieu + " " + nomRueLieu + ", " + villeLieu + ", " + paysLieu + ")";
    }
}
