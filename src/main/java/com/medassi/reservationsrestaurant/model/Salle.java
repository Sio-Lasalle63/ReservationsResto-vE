package com.medassi.reservationsrestaurant.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Représente la salle du restaurant avec les tables qu'elle contient.
 */
public class Salle {
    private List<Table> tables; // Liste des tables présentes dans la salle

    public Salle() {
        this.tables = new ArrayList<>();
    }
    
    public Salle( List<Table> tables) {
        this.tables = tables != null ? tables : new ArrayList<>();
    }

  
    public List<Table> getTables() {
        return tables;
    }

   

    public void setTables(List<Table> tables) {
        this.tables = tables;
    }

    /**
     * Ajoute une table à la salle.
     * @param table La table à ajouter.
     */
    public void addTable(Table table) {
        this.tables.add(table);
    }

    /**
     * Supprime une table de la salle par son ID.
     * @param tableId L'ID de la table à supprimer.
     * @return true si la table a été supprimée, false sinon.
     */
    public boolean removeTable(String tableId) {
        return this.tables.removeIf(table -> table.getId().equals(tableId));
    }

    /**
     * Trouve une table par son ID.
     * @param tableId L'ID de la table recherchée.
     * @return L'objet Table si trouvé, null sinon.
     */
    public Table findTableById(String tableId) {
        return this.tables.stream()
                   .filter(table -> table.getId().equals(tableId))
                   .findFirst()
                   .orElse(null);
    }

    @Override
    public String toString() {
        return "Salle{" +
               ", tables=" + tables.size() + " tables" +
               '}';
    }
}
