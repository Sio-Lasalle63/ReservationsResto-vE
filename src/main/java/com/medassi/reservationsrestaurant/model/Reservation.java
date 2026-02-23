package com.medassi.reservationsrestaurant.model;

import java.time.LocalDate;
import java.util.Objects;

/**
 * Représente une réservation pour une table donnée à une date et un service spécifiques.
 */
public class Reservation {
    private String idReservation; // Identifiant unique pour la réservation
    private String idTable;       // L'ID de la table réservée
    private LocalDate dateReservation; // Date de la réservation
    private Service service;       // Service (MIDI ou SOIR)
    private int nombrePersonnes;   // Nombre de personnes pour la réservation
    private String nomClient;      // Nom du client (pour le commentaire)
    private String commentaire;    // Champ commentaire

    public Reservation(String idReservation, String idTable, LocalDate dateReservation, Service service, int nombrePersonnes, String nomClient, String commentaire) {
        this.idReservation = idReservation;
        this.idTable = idTable;
        this.dateReservation = dateReservation;
        this.service = service;
        this.nombrePersonnes = nombrePersonnes;
        this.nomClient = nomClient;
        this.commentaire = commentaire;
    }

    // Getters
    public String getIdReservation() {
        return idReservation;
    }

    public String getIdTable() {
        return idTable;
    }

    public LocalDate getDateReservation() {
        return dateReservation;
    }

    public Service getService() {
        return service;
    }

    public int getNombrePersonnes() {
        return nombrePersonnes;
    }

    public String getNomClient() {
        return nomClient;
    }

    public String getCommentaire() {
        return commentaire;
    }

    // Setters (si des modifications sont possibles après la création)
    public void setIdReservation(String idReservation) {
        this.idReservation = idReservation;
    }

    public void setIdTable(String idTable) {
        this.idTable = idTable;
    }

    public void setDateReservation(LocalDate dateReservation) {
        this.dateReservation = dateReservation;
    }

    public void setService(Service service) {
        this.service = service;
    }

    public void setNombrePersonnes(int nombrePersonnes) {
        this.nombrePersonnes = nombrePersonnes;
    }

    public void setNomClient(String nomClient) {
        this.nomClient = nomClient;
    }

    public void setCommentaire(String commentaire) {
        this.commentaire = commentaire;
    }

    @Override
    public String toString() {
        return "Reservation{"
               + "idReservation='" + idReservation + "'"
               + ", idTable='" + idTable + "'"
               + ", dateReservation=" + dateReservation
               + ", service=" + service
               + ", nombrePersonnes=" + nombrePersonnes
               + ", nomClient='" + nomClient + "'"
               + ", commentaire='" + commentaire + "'"
               + "}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Reservation that = (Reservation) o;
        return Objects.equals(idReservation, that.idReservation);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idReservation);
    }
}
