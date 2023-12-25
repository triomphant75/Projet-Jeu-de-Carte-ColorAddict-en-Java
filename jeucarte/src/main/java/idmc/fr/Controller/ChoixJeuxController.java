package idmc.fr.Controller;

import java.io.IOException;

import idmc.fr.App;
import javafx.fxml.FXML;

public class ChoixJeuxController {

    // méthode pour faire action de retour par un  bouton 
    @FXML
    private void BtnRetour() throws IOException {
        App.setRoot("Accueil");
    }

   
    // méthode pour faire le choix du jeu à plusieurs 
    @FXML 
    private void BtnJeuxAdeux() throws IOException {
        App.setRoot("LesJoueurs");
    }

     // méthode pour faire le choix du jeu contre l'ordinateur 
    @FXML
    private void btnContreIA () throws IOException {
        App.setRoot("JeuxAvecOrdinateur");
    }
}