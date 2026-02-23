package com.medassi.reservationsrestaurant.model;

/**
 * Classe abstraite représentant un élément générique pouvant être placé dans une salle.
 * Introduit l'abstraction et servira de base à des éléments concrets comme les tables.
 */
public abstract class ElementSalle {
    private String id;
    private double positionX; // Position X sur le plan de la salle
    private double positionY; // Position Y sur le plan de la salle

    public ElementSalle(String id, double positionX, double positionY) {
        this.id = id;
        this.positionX = positionX;
        this.positionY = positionY;
    }

    // Getters
    public String getId() {
        return id;
    }

    public double getPositionX() {
        return positionX;
    }

    public double getPositionY() {
        return positionY;
    }

    // Setters (pour permettre le déplacement en mode admin)
    public void setPositionX(double positionX) {
        this.positionX = positionX;
    }

    public void setPositionY(double positionY) {
        this.positionY = positionY;
    }

    @Override
    public String toString() {
        return "ElementSalle{"
               + "id='" + id + "'" +
               ", positionX=" + positionX +
               ", positionY=" + positionY +
               "}";
    }
}
