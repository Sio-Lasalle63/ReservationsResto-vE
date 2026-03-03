package com.medassi.reservationsrestaurant.controller;

import com.medassi.reservationsrestaurant.model.Forme;
import com.medassi.reservationsrestaurant.model.Reservation;
import com.medassi.reservationsrestaurant.model.Service;
import com.medassi.reservationsrestaurant.model.Salle;
import com.medassi.reservationsrestaurant.model.Table;
import com.medassi.reservationsrestaurant.persistence.GestionDonnees;
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
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

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
        datePicker.setValue(LocalDate.now());
        datePicker.valueProperty().addListener((o, oldV, newV) -> handleCheckAvailability());
        serviceComboBox.valueProperty().addListener((o, oldV, newV) -> handleCheckAvailability());
        personnesSpinner.valueProperty().addListener((o, oldV, newV) -> handleCheckAvailability());
        this.salle = GestionDonnees.chargerPlanSalle();
        this.allReservations = GestionDonnees.chargerReservations();
        redrawSalle();
    }

    /**
     * Centre le texte (numéro ou nom) sur la forme graphique d'une table.
     *
     * @param text le node Text à positionner
     * @param table l'objet métier Table contenant les dimensions et la forme
     */
    private void centerTextOnTable(Text text, Table table) {
        if (table.getForme() == Forme.ROND) {
            text.setTranslateX(-text.getBoundsInLocal().getWidth() / 2);
            text.setTranslateY(text.getBoundsInLocal().getHeight() / 2);
        }else{
            text.setTranslateX(table.getLargeur()/2);
            text.setTranslateY(table.getHauteur()/2);
        }
    }

    /**
     * Dessine ou redessine l'ensemble des tables dans le Pane graphique.
     */
    private void redrawSalle() {
        for (Table uneTable : this.salle.getTables()) {
            Group gTable = new Group();
            Shape shapeTable = (uneTable.getForme() == Forme.RECTANGLE)
                    ? new Rectangle(uneTable.getLargeur(), uneTable.getHauteur())
                    : new Circle(uneTable.getHauteur() / 2);
            shapeTable.setFill(Paint.valueOf("LEMONCHIFFON"));
            shapeTable.setStroke(Paint.valueOf("VIOLET"));
            Text textTable = new Text(uneTable.getCapacite() + " p.");
            centerTextOnTable(textTable, uneTable);
            gTable.getChildren().add(shapeTable);
            gTable.getChildren().add(textTable);
            gTable.setLayoutX(uneTable.getPositionX());
            gTable.setLayoutY(uneTable.getPositionY());
            this.sallePane.getChildren().add(gTable);
            gTable.setUserData(uneTable);
            gTable.setOnMouseEntered((event) -> shapeTable.setStroke(Paint.valueOf("BLACK")) );
            gTable.setOnMouseExited((event) -> shapeTable.setStroke(Paint.valueOf("VIOLET")) );
            gTable.setOnMouseClicked((event) -> handleTableClick(event));
           
        }
        handleCheckAvailability();
    }

    /**
     * Met à jour l'apparence des tables selon les critères de recherche et les
     * réservations.
     */
    private void handleCheckAvailability() {
        System.out.println("CheckAvailability");
        for( Node node : this.sallePane.getChildren()){
            Group leGroupe = (Group)node ;
            Table laTable=  (Table) leGroupe.getUserData() ;
            Shape laShape = (Shape) leGroupe.getChildren().get(0) ;
            Text leText = (Text) leGroupe.getChildren().get(1) ;
            if( laTable.getCapacite() < personnesSpinner.getValue() ){
                laShape.setFill(Color.CRIMSON);
            }
        }
        
    }

    /**
     * Gère le clic sur une table : affiche un message ou ouvre le dialog de
     * réservation.
     */
    private void handleTableClick(MouseEvent event) {
        Group groupClicked = (Group) event.getSource() ;
        Table laTable = (Table) groupClicked.getUserData();
        System.out.println("Clique sur table :" + laTable.getNumero());
    }

    /**
     * Affiche un dialog modal pour créer une nouvelle réservation.
     *
     * @param table l'objet Table sélectionné pour la réservation
     */
    private void showReservationDialog(Table table) {
    }
}
