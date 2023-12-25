package idmc.fr.Controller;

import java.io.IOException;

import idmc.fr.App;
import javafx.fxml.FXML;

public class AccueilController {

    // méthode pour aller vers le choix du jeu
    @FXML
    private void BtnChoixJeu() throws IOException {
        App.setRoot("ChoixJeux");
    }
}
