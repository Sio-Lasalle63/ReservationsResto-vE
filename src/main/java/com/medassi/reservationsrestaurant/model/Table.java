package com.medassi.reservationsrestaurant.model;

/**
 * Représente une table du restaurant, héritant de ElementSalle.
 */
public class Table extends ElementSalle {
    private int numero;
    private int capacite; // Nombre de personnes que la table peut accueillir
    private double largeur; // Largeur de la table pour l'affichage graphique
    private double hauteur; // Hauteur de la table pour l'affichage graphique
    private Forme forme; // La forme de la table (RECTANGLE ou ROND)

    public Table(String id, double positionX, double positionY, int numero, int capacite, double largeur, double hauteur, Forme forme) {
        super(id, positionX, positionY);
        this.numero = numero;
        this.capacite = capacite;
        this.largeur = largeur;
        this.hauteur = hauteur;
        this.forme = forme;
    }

    // Getters
    public int getNumero() {
        return numero;
    }

    public int getCapacite() {
        return capacite;
    }

    public double getLargeur() {
        return largeur;
    }

    public double getHauteur() {
        return hauteur;
    }

    public Forme getForme() {
        return forme;
    }

    // Setters
    public void setNumero(int numero) {
        this.numero = numero;
    }

    public void setCapacite(int capacite) {
        this.capacite = capacite;
    }

    public void setLargeur(double largeur) {
        this.largeur = largeur;
    }

    public void setHauteur(double hauteur) {
        this.hauteur = hauteur;
    }
    
    public void setForme(Forme forme) {
        this.forme = forme;
    }

    @Override
    public String toString() {
        return "Table{"
               + "id='" + getId() + "'" + 
               ", numero=" + numero +
               ", capacite=" + capacite +
               ", positionX=" + getPositionX() +
               ", positionY=" + getPositionY() +
               ", largeur=" + largeur +
               ", hauteur=" + hauteur +
               ", forme=" + forme +
               '}'
               ;
    }
}
