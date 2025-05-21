package com.example.campingk.classes;

public class Animation {
    private String nomAnimation;
    private String libelleAnimation;
    private String imageAnimation; // Added field for image

    // Constructor
    public Animation(String nom, String libelle, String image) {
        this.nomAnimation = nom;
        this.libelleAnimation = libelle;
        this.imageAnimation = image;
    }

    // Constructor without image (for backward compatibility)
    public Animation(String nom, String libelle) {
        this(nom, libelle, ""); // Default empty string for image
    }

    // Getters
    public String getNomAnimation() {
        return nomAnimation;
    }

    public String getLibelleAnimation() {
        return libelleAnimation;
    }

    public String getImageAnimation() {
        return imageAnimation;
    }

    // Setters
    public void setNomAnimation(String nomAnimation) {
        this.nomAnimation = nomAnimation;
    }

    public void setLibelleAnimation(String libelleAnimation) {
        this.libelleAnimation = libelleAnimation;
    }

    public void setImageAnimation(String imageAnimation) {
        this.imageAnimation = imageAnimation;
    }

    @Override
    public String toString() {
        return nomAnimation + " - " + libelleAnimation;
    }
}
