package com.example.campingk.classes;

public class Lieu {
    String libelleLieu;
    String numRueLieu;
    String nomRueLieu;
    String villeLieu;

    public Lieu(String libelle, String numRue, String nomRue, String ville) {
        libelleLieu = libelle;
        numRueLieu = numRue;
        nomRueLieu = nomRue;
        villeLieu = ville;
    }

    @Override
    public String toString() {
        return libelleLieu + " (" + numRueLieu + " " + nomRueLieu + ", " + villeLieu + ")";
    }
}
