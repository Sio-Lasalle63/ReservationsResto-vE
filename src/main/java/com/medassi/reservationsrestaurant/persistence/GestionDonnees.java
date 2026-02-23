package com.medassi.reservationsrestaurant.persistence;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.medassi.reservationsrestaurant.model.Reservation;
import com.medassi.reservationsrestaurant.model.Salle;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Gère la persistance des données (Salle et Réservations) en utilisant des fichiers JSON.
 */
public class GestionDonnees {

    private static final String DATA_DIR = "data";
    private static final String SALLE_FILE = DATA_DIR + "/plan_salle.json";
    private static final String RESERVATIONS_FILE = DATA_DIR + "/reservations.json";

    private static final Gson gson = new GsonBuilder()
            .setPrettyPrinting() // Pour un JSON lisible
            .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
            .create();

    private static final Logger LOGGER = Logger.getLogger(GestionDonnees.class.getName());

    /**
     * Charge le plan de la salle depuis le fichier JSON.
     * @return L'objet Salle chargé, ou une nouvelle Salle par défaut si le fichier n'existe pas ou est vide.
     */
    public static Salle chargerPlanSalle() {
        Path filePath = Paths.get(SALLE_FILE);
        if (Files.notExists(filePath) || Files.isReadable(filePath) && Paths.get(SALLE_FILE).toFile().length() == 0) {
            LOGGER.log(Level.INFO, "Fichier plan_salle.json non trouvé ou vide. Création d'une salle par défaut.");
            return new Salle(); // Salle par défaut
        }

        try (FileReader reader = new FileReader(SALLE_FILE)) {
            Salle loadedSalle = gson.fromJson(reader, Salle.class);
            return loadedSalle != null ? loadedSalle : new Salle();
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Erreur lors de la lecture du fichier plan_salle.json", e);
            return new Salle(); // Retourne une salle par défaut en cas d'erreur
        }
    }

    /**
     * Sauvegarde le plan de la salle dans le fichier JSON.
     * @param salle L'objet Salle à sauvegarder.
     */
    public static void sauvegarderPlanSalle(Salle salle) {
        creerRepertoireDataSiNecessaire();
        try (FileWriter writer = new FileWriter(SALLE_FILE)) {
            gson.toJson(salle, writer);
            LOGGER.log(Level.INFO, "Plan de la salle sauvegardé avec succès.");
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Erreur lors de la sauvegarde du fichier plan_salle.json", e);
        }
    }

    /**
     * Charge la liste des réservations depuis le fichier JSON.
     * @return La liste des réservations chargée, ou une liste vide si le fichier n'existe pas ou est vide.
     */
    public static List<Reservation> chargerReservations() {
        Path filePath = Paths.get(RESERVATIONS_FILE);
        if (Files.notExists(filePath) || Files.isReadable(filePath) && Paths.get(RESERVATIONS_FILE).toFile().length() == 0) {
            LOGGER.log(Level.INFO, "Fichier reservations.json non trouvé ou vide. Retourne une liste de réservations vide.");
            return new ArrayList<>();
        }

        try (FileReader reader = new FileReader(RESERVATIONS_FILE)) {
            Type listType = new TypeToken<ArrayList<Reservation>>(){}.getType();
            List<Reservation> reservations = gson.fromJson(reader, listType);
            return reservations != null ? reservations : new ArrayList<>();
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Erreur lors de la lecture du fichier reservations.json", e);
            return new ArrayList<>(); // Retourne une liste vide en cas d'erreur
        }
    }

    /**
     * Sauvegarde la liste des réservations dans le fichier JSON.
     * @param reservations La liste des réservations à sauvegarder.
     */
    public static void sauvegarderReservations(List<Reservation> reservations) {
        creerRepertoireDataSiNecessaire();
        try (FileWriter writer = new FileWriter(RESERVATIONS_FILE)) {
            gson.toJson(reservations, writer);
            LOGGER.log(Level.INFO, "Réservations sauvegardées avec succès.");
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Erreur lors de la sauvegarde du fichier reservations.json", e);
        }
    }

    /**
     * Crée le répertoire 'data' si celui-ci n'existe pas.
     */
    private static void creerRepertoireDataSiNecessaire() {
        Path dataPath = Paths.get(DATA_DIR);
        if (Files.notExists(dataPath)) {
            try {
                Files.createDirectories(dataPath);
                LOGGER.log(Level.INFO, "Répertoire 'data' créé : " + dataPath.toAbsolutePath());
            } catch (IOException e) {
                LOGGER.log(Level.SEVERE, "Erreur lors de la création du répertoire 'data'", e);
            }
        }
    }
}
