package idmc.fr;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

import idmc.fr.Controller.LesJoueursController;
import idmc.fr.Model.Carte;
import idmc.fr.Model.Joueur;
import idmc.fr.Model.MainJoueur;
import idmc.fr.Model.PiocheJoueur;
import idmc.fr.Model.ReglesJeu;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML("Accueil"), 578, 450);
        //scene.setDefaultCloseOperation(Scene.EXIT_ON_CLOSE);
        stage.setScene(scene);
        stage.show();
        
    }

    public static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();

        
    }

    public static void main(String[] args) {
       launch();

       
       // Initialisation de ReglesJeu
        ReglesJeu reglesJeu = new ReglesJeu();

        // Création du contrôleur LesJoueursController avec l'injection de dépendances
        LesJoueursController joueursController = new LesJoueursController(reglesJeu);
    }

   /*  public static void setRoot(String string, int nbJoueurs) {
         launch();
    }*/

    private static ArrayList<Joueur> nomsJoueurs;
    private static MainJoueur mainJoueur;
    private static PiocheJoueur piocheJoueur;
    

    private static ArrayList<Carte> pioche;
    private static int nbCartesJoueur;
    private static ArrayList<Joueur> joueursList;


    // ... Autres éléments de la classe App

    public void setNomsJoueurs(ArrayList<Joueur> joueurs) {
        nomsJoueurs = joueurs;
    }

    public static ArrayList<Joueur> getNomsJoueurs() {
        return nomsJoueurs;
    }
    public static void setMainJoueur(MainJoueur main) {
        mainJoueur = main;
    }

    public static MainJoueur getMainJoueur() {
        return mainJoueur;
    }

    public static void setPioche(ArrayList<Carte> cartes) {
        pioche = cartes;
    }

    public static ArrayList<Carte> getPioche() {
        return pioche;
    }

    public static void setNbCartesJoueur(int nbCartes) {
        nbCartesJoueur = nbCartes;
    }

    public static int getNbCartesJoueur() {
        return nbCartesJoueur;
    }

    public static void setJoueursList(ArrayList<Joueur> joueurs) {
        joueursList = joueurs;
    }
    public static ArrayList<Joueur> getJoueursList() {
        return joueursList;
    }
    public static PiocheJoueur getPiocheJoueur() {
        return piocheJoueur;
    }

    public static void setPiocheJoueur(PiocheJoueur piocheJoueur) {
        App.piocheJoueur = piocheJoueur;
    }

  




    

}