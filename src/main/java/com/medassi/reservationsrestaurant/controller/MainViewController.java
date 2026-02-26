package com.medassi.reservationsrestaurant.controller;

import com.medassi.reservationsrestaurant.model.Reservation;
import com.medassi.reservationsrestaurant.model.Service;
import com.medassi.reservationsrestaurant.model.Salle;
import com.medassi.reservationsrestaurant.model.Table;
import java.net.URL;
import java.time.LocalDate;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.fxml.Initializable;
import javafx.scene.layout.Background;
import javafx.util.Callback;

/**
 *
 * @author Anthony
 */
public class MainViewController implements Initializable {

    @FXML
    private DatePicker datePicker;
    @FXML
    private ComboBox<Service> serviceComboBox;
    @FXML
    private Spinner<Integer> personnesSpinner;
    @FXML
    private Pane sallePane;

    private Salle salle;
    private List<Reservation> allReservations;
    private List<Group> tableViews;

    /**
     * Méthode d'initialisation appelée automatiquement après le chargement du
     * FXML.
     *
     * @param url l'URL du FXML (non utilisé ici)
     * @param rb le ResourceBundle pour l'internationalisation (non utilisé ici)
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        serviceComboBox.setItems(FXCollections.observableArrayList(Service.values()));
        serviceComboBox.getSelectionModel().selectFirst();
        personnesSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 20, 2));
        datePicker.setDayCellFactory((DatePicker param) -> new DateCell() {
            @Override
            public void updateItem(LocalDate ld, boolean bln) {
                super.updateItem(ld, bln);
                LocalDate jourdui = LocalDate.now();
                if (ld.isBefore(jourdui)) {
                    setStyle("-fx-background-color: red;");
                    setDisable(true);
                    
                }
            }

        });

    }

    /**
     * Centre le texte (numéro ou nom) sur la forme graphique d'une table.
     *
     * @param text le node Text à positionner
     * @param table l'objet métier Table contenant les dimensions et la forme
     */
    private void centerTextOnTable(Text text, Table table) {
    }

    /**
     * Dessine ou redessine l'ensemble des tables dans le Pane graphique.
     */
    private void redrawSalle() {
    }

    /**
     * Met à jour l'apparence des tables selon les critères de recherche et les
     * réservations.
     */
    private void handleCheckAvailability() {
    }

    /**
     * Gère le clic sur une table : affiche un message ou ouvre le dialog de
     * réservation.
     */
    private void handleTableClick(MouseEvent event) {
    }

    /**
     * Affiche un dialog modal pour créer une nouvelle réservation.
     *
     * @param table l'objet Table sélectionné pour la réservation
     */
    private void showReservationDialog(Table table) {
    }
}
