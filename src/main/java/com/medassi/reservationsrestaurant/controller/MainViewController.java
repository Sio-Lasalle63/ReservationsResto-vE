package com.medassi.reservationsrestaurant.controller;

import com.medassi.reservationsrestaurant.model.Forme;
import com.medassi.reservationsrestaurant.model.Reservation;
import com.medassi.reservationsrestaurant.model.Service;
import com.medassi.reservationsrestaurant.model.Salle;
import com.medassi.reservationsrestaurant.model.Table;
import com.medassi.reservationsrestaurant.persistence.GestionDonnees;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.UUID;
import javafx.collections.FXCollections;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.layout.Background;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javax.swing.text.DateFormatter;

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
        serviceComboBox.setTooltip(new Tooltip("Service du midi ou du soir"));
        redrawSalle();
    }

    /**
     * Centre le texte (numéro ou nom) sur la forme graphique d'une table.
     *
     * @param text le node Text à positionner
     * @param table l'objet métier Table contenant les dimensions et la forme
     */
    private void centerTextOnTable(Text text, Table table) {
        text.setTextOrigin(VPos.CENTER);
        if (table.getForme() == Forme.ROND) {
            text.setX(0 - text.getLayoutBounds().getWidth() / 2);
            text.setY(0);
        } else {
            text.setX(table.getLargeur() / 2 - text.getLayoutBounds().getWidth() / 2);
            text.setY(table.getHauteur() / 2);
        }
    }

    /**
     * Dessine ou redessine l'ensemble des tables dans le Pane graphique.
     */
    private void redrawSalle() {
        sallePane.getChildren().clear();
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
            gTable.setOnMouseEntered((event) -> shapeTable.setStroke(Paint.valueOf("BLACK")));
            gTable.setOnMouseExited((event) -> shapeTable.setStroke(Paint.valueOf("VIOLET")));
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
        for (Node node : this.sallePane.getChildren()) {
            Group leGroupe = (Group) node;
            Table laTable = (Table) leGroupe.getUserData();
            Shape laShape = (Shape) leGroupe.getChildren().get(0);
            Text leText = (Text) leGroupe.getChildren().get(1);
            int diff = laTable.getCapacite() - personnesSpinner.getValue();
            //si elle est reservé -> idTable== et laDate== et service==
            boolean isReserved = false;
            String nomClient = "";
            for (Reservation r : allReservations) {
                if (r.getIdTable().equals(laTable.getId())
                        && r.getDateReservation().isEqual(datePicker.getValue())
                        && r.getService() == serviceComboBox.getValue()) {
                    isReserved = true;
                    nomClient = r.getNomClient();
                }
            }
            if (!isReserved) {
                if (diff < 0) {
                    laShape.setFill(Color.CRIMSON);
                } else if (diff == 0) {
                    laShape.setFill(Color.LIGHTGREEN);
                } else if (diff == 1) {
                    laShape.setFill(Color.LIGHTSALMON);
                } else if (diff == 2) {
                    laShape.setFill(Color.LIGHTGOLDENRODYELLOW);
                } else {
                    laShape.setFill(Color.LIGHTBLUE);
                }
                leText.setText(laTable.getCapacite() + " p.");
                leText.setFill(Color.BLACK);
                centerTextOnTable(leText, laTable);
            } else {
                // J'affiche le nom du client
                // Je met une couleur noir en fill
                // Je met une couleur blanc pour le texte
                leText.setText(nomClient);
                centerTextOnTable(leText, laTable);
                laShape.setFill(Color.DARKGREY);
                leText.setFill(Color.WHITE);
            }
        }
    }

    /**
     * Gère le clic sur une table : affiche un message ou ouvre le dialog de
     * réservation.
     */
    private void handleTableClick(MouseEvent event) {
        Group groupClicked = (Group) event.getSource();
        Table laTable = (Table) groupClicked.getUserData();
        Shape laShape = (Shape) groupClicked.getChildren().get(0);
        Text leText = (Text) groupClicked.getChildren().get(1);
        Color c = (Color) laShape.getFill();

        int diff = laTable.getCapacite() - personnesSpinner.getValue();
        if (diff < 0) {
            Alert a = new Alert(Alert.AlertType.NONE);
            a.setTitle("Réservation d'une table");
            a.setAlertType(Alert.AlertType.ERROR);
            a.setContentText("Cette table de " + laTable.getCapacite() + " personnes est trop petite!");
            a.showAndWait();
        } else {
            if (c != Color.DARKGREY) {
                //Si elle n'est pas reserver alors
                showReservationDialog(laTable);
            }
        }

    }

    /**
     * Affiche un dialog modal pour créer une nouvelle réservation.
     *
     * @param table l'objet Table sélectionné pour la réservation
     */
    private void showReservationDialog(Table table) {
        Dialog<Reservation> dialog = new Dialog<>();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd MM yyyy");
        dialog.setTitle("Réservation");
        dialog.setHeaderText("Saisir la réservation pour " + table.getCapacite() + " p.");
        Label labelDate = new Label("Le " + dtf.format(datePicker.getValue()));
        Label labelNumTable = new Label("Table : " + table.getNumero());
        Label labelService = new Label("Service du " + serviceComboBox.getValue());
        TextField tfNomClient = new TextField();
        tfNomClient.setPromptText("Nom du client");
        TextField tfCommentaire = new TextField();
        tfCommentaire.setPromptText("Commentaire éventuel");
        VBox vbox = new VBox(labelNumTable, labelDate, labelService, tfNomClient, tfCommentaire);
        vbox.setSpacing(10);
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.FINISH, ButtonType.CANCEL);
        dialog.getDialogPane().setContent(vbox);
        dialog.setResultConverter(button -> {
            Reservation resARetourner = null;
            if (button == ButtonType.FINISH) {
                if (tfNomClient.getText().strip().isBlank()) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("Le nom du client est obligatoire !!!");
                    alert.showAndWait();
                } else {
                    String numRes = "RES-" + UUID.randomUUID().toString();
                    resARetourner = new Reservation(
                            numRes,
                            table.getId(),
                            datePicker.getValue(),
                            serviceComboBox.getValue(),
                            personnesSpinner.getValue(),
                            tfNomClient.getText().strip(),
                            tfCommentaire.getText().strip());

                }
            } else {
                return null;
            }
            return resARetourner;
        });
        Optional<Reservation> optReservation = dialog.showAndWait();
        if (optReservation.isPresent()) {
            Reservation r = optReservation.get();
            allReservations.add(r);
            GestionDonnees.sauvegarderReservations(allReservations);
            handleCheckAvailability();
        }

    }
}
