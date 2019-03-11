package com.example.bashar.comestibleetsauvage;

/**
 * Classe java classique représentant une plante
 */
public class Plante
{
    private int id;
    private String Nom;
    private String Libelle;
    private String Statut;
    private byte[] Image;

    private String lat;
    private String lon;


    //Constructeur vide inutilisé
    public Plante()
    {

    }

    //Constructeurs
    public Plante(int id, String Nom, String Libelle, String Statut, byte[] Image)
    {

        this.id = id;
        this.Nom = Nom;
        this.Libelle = Libelle;
        this.Statut = Statut;
        this.Image = Image;
    }

    public Plante(String Nom, String Libelle, String Statut, byte[] Image, String lat, String lon)
    {

        this.id = id;
        this.Nom = Nom;
        this.Libelle = Libelle;
        this.Statut = Statut;
        this.Image = Image;
    }

    public Plante(String Nom, String Libelle, String Statut, byte[] Image)
    {

        this.Nom = Nom;
        this.Libelle = Libelle;
        this.Statut = Statut;
        this.Image = Image;
    }

    //Les Getteurs et les Setteurs
    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public String getNom()
    {
        return Nom;
    }

    public void setNom(String nom)
    {
        Nom = nom;
    }

    public String getLibelle()
    {
        return Libelle;
    }

    public void setLibelle(String libelle)
    {
        Libelle = libelle;
    }

    public String getStatut()
    {
        return Statut;
    }

    public void setStatut(String statut)
    {
        Statut = statut;
    }

    public byte[] getImage()
    {
        return Image;
    }

    public void setImage(byte[] image)
    {
        Image = image;
    }

    public String getLat()
    {
        return lat;
    }

    public void setLat(String lat)
    {
        Nom = lat;
    }

    public String getLon()
    {
        return lon;
    }

    public void setLon(String lon)
    {
        Libelle = lon;
    }
}
