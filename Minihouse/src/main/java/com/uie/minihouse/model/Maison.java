package com.uie.minihouse.model;

public class Maison {
    private int id;
    private String nom;
    private String adresse;
    private String type;
    private double prix;
    private double surface;
    private int nbPieces;
    private String statut;

    public Maison() {}

    public Maison(int id, String nom, String adresse, String type, double prix, double surface, int nbPieces, String statut) {
        this.id = id;
        this.nom = nom;
        this.adresse = adresse;
        this.type = type;
        this.prix = prix;
        this.surface = surface;
        this.nbPieces = nbPieces;
        this.statut = statut;
    }

    public Maison(String nom, String adresse, String type, double prix, double surface, int nbPieces, String statut) {
        this.nom = nom;
        this.adresse = adresse;
        this.type = type;
        this.prix = prix;
        this.surface = surface;
        this.nbPieces = nbPieces;
        this.statut = statut;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }

    public String getAdresse() { return adresse; }
    public void setAdresse(String adresse) { this.adresse = adresse; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public double getPrix() { return prix; }
    public void setPrix(double prix) { this.prix = prix; }

    public double getSurface() { return surface; }
    public void setSurface(double surface) { this.surface = surface; }

    public int getNbPieces() { return nbPieces; }
    public void setNbPieces(int nbPieces) { this.nbPieces = nbPieces; }

    public String getStatut() { return statut; }
    public void setStatut(String statut) { this.statut = statut; }

    @Override
    public String toString() {
        return "Maison{id=" + id + ", nom='" + nom + "', adresse='" + adresse + 
               "', type='" + type + "', prix=" + prix + ", surface=" + surface + 
               ", nbPieces=" + nbPieces + ", statut='" + statut + "'}";
    }
}
