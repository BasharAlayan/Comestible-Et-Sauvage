package com.example.bashar.comestibleetsauvage;

public class Plante {

    private int id;
    private String Nom;
    private String Libelle;
    private String Statut;
    private String Image;

    public Plante(){

    }



    public Plante(int id, String Nom, String Libelle, String Statut, String Image){

        this.id=id;
        this.Nom=Nom;
        this.Libelle=Libelle;
        this.Statut=Statut;
        this.Image=Image;
    }

    public Plante(String Nom,String Libelle,String Statut,String Image){

        this.Nom=Nom;
        this.Libelle=Libelle;
        this.Statut=Statut;
        this.Image=Image;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return Nom;
    }

    public void setNom(String nom) {
        Nom = nom;
    }

    public String getLibelle() {
        return Libelle;
    }

    public void setLibelle(String libelle) {
        Libelle = libelle;
    }

    public String getStatut() {
        return Statut;
    }

    public void setStatut(String statut) {
        Statut = statut;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }
}
