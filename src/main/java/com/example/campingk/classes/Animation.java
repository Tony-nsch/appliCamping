package com.example.campingk.classes;

public class Animation {
    String nomAnimation;
    String libelleAnimation;

    public Animation(String nom, String libelle) {
        nomAnimation = nom;
        libelleAnimation = libelle;
    }

    @Override
    public String toString() {
        return nomAnimation + " - " + libelleAnimation;
    }
}
