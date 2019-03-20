package com.example.bashar.comestibleetsauvage;

/**
 * Classe java classique représentant une fontaine
 */
public class Fontaine {

    private String id;
    private String Nom;
    private String Libelle;
    private String Statut;
    private byte[] Image;

    private String lat;
    private String lon;


    //Constructeur vide inutilisé
    public Fontaine() {

    }

    //Constructeurs
    public Fontaine(String id, String Nom, String Libelle, String Statut, byte[] Image) {

        this.id = id;
        this.Nom = Nom;
        this.Libelle = Libelle;
        this.Statut = Statut;
        this.Image = Image;
    }

    public Fontaine(String Nom, String Libelle, String Statut, byte[] Image, String lat, String lon) {

        this.Nom = Nom;
        this.Libelle = Libelle;
        this.Statut = Statut;
        this.Image = Image;
        this.lat = lat;
        this.lon = lon;
    }

    public Fontaine(String id, String Nom, String Libelle, String Statut, byte[] Image, String lat, String lon) {

        this.id = id;
        this.Nom = Nom;
        this.Libelle = Libelle;
        this.Statut = Statut;
        this.Image = Image;
        this.lat = lat;
        this.lon = lon;
    }

    public Fontaine(String Nom, String Libelle, String Statut, byte[] Image) {

        this.Nom = Nom;
        this.Libelle = Libelle;
        this.Statut = Statut;
        this.Image = Image;
    }

    //Les Getteurs et les Setteurs
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNom() {
        return Nom;
    }

    public void setNom(String nom) {
        this.Nom = nom;
    }

    public String getLibelle() {
        return Libelle;
    }

    public void setLibelle(String libelle) {
        this.Libelle = libelle;
    }

    public String getStatut() {
        return Statut;
    }

    public void setStatut(String statut) {
        this.Statut = statut;
    }

    public byte[] getImage() {
        return Image;
    }

    public void setImage(byte[] image) {
        Image = image;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLon() {
        return lon;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }
}


