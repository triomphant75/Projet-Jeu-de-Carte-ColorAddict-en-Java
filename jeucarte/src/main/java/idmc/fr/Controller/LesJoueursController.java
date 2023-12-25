package idmc.fr.Controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Stack;

import idmc.fr.App;
import idmc.fr.Model.AppData;
import idmc.fr.Model.Joueur;
import idmc.fr.Model.MainJoueur;
import idmc.fr.Model.PiocheJoueur;
import idmc.fr.Model.ReglesJeu;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class LesJoueursController {

     private ReglesJeu reglesJeu; // Champ pour stocker la référence à ReglesJeu

    public LesJoueursController(ReglesJeu reglesJeu) {
        this.reglesJeu = reglesJeu; // Injection de dépendance lors de la création du contrôleur
    }
    

    public LesJoueursController() {
    }

    @FXML
    private TextField inputNbJoueurs;

    @FXML
    private VBox namesContainer;

    @FXML
    private Button idcommencerButton; 

    @FXML
    private Text confirmationText; // Texte de confirmation
    @FXML 
    private Button idboutonDemarrerPartie; 
    @FXML 
    private Button idEnregistre; 

    @FXML 
    private Label confirmationTextV;
    @FXML
    private boolean nombreJoueursSaisi = false;
 
    @FXML
    void initialize() {
    
    // Désactive le bouton au démarrage (tant que tous les noms ne sont pas saisis)
    idboutonDemarrerPartie.setDisable(true);
    idEnregistre.setDisable(true);
    }

    @FXML
    private void commencer(ActionEvent event) throws IOException {
        
        try {
            int nbJoueurs = Integer.parseInt(inputNbJoueurs.getText());
           AppData.nbJoueur = nbJoueurs; // le nombre de joueurs dans la classe ReglesJeu
    
            if (nbJoueurs >= 2 && nbJoueurs <= 6) {
                // Ici on va créer dynamiquement les champs de texte pour les noms des joueurs
                for (int i = 1; i <= nbJoueurs; i++) {
                    TextField textField = new TextField();
                    textField.setPromptText("Nom du joueur " + i);
                    namesContainer.getChildren().add(textField);
                }
                
                // Active le bouton pour enregistrer les noms
                idEnregistre.setDisable(false);
                inputNbJoueurs.setDisable(true);
                nombreJoueursSaisi = true;
                idcommencerButton.setDisable(nombreJoueursSaisi);

            } else {
                confirmationText.setText("Vous n'avez pas saisi un chiffre entre 2 et 6");
                inputNbJoueurs.clear();
            }
        } catch (NumberFormatException e) {
            confirmationText.setText("Vous n'avez pas saisi un chiffre entre 2 et 6");
            inputNbJoueurs.clear();
            idboutonDemarrerPartie.setDisable(true);
            e.printStackTrace();
        } catch (Exception e) {
            confirmationText.setText("Vous n'avez saisie une chaine ou un caractère");
        }

        
    }

    @FXML
private void enregistrerNoms(ActionEvent event) {
    // Supprime les anciens joueurs (au cas où le bouton est utilisé plusieurs fois)
    AppData.getListJoueur().clear();
    
    // Récupére les noms des joueurs à partir des champs de texte et les enregistrer dans la liste
    for (int i = 0; i < namesContainer.getChildren().size(); i++) {
        TextField textField = (TextField) namesContainer.getChildren().get(i);
        String nomJoueur = textField.getText().trim();
        confirmationTextV.setText("Les joueurs ont été enregistré.");
        
       if (!nomJoueur.isEmpty()) {

            //génere le jeu de carte 
            AppData.setJeuDeCarte(AppData.GenererCarte());
            // Distribue une main au joueur nouvellement créé
            MainJoueur mainJoueur = new MainJoueur (0,new ArrayList<>()); // Crée une nouvelle main
            AppData.DistribuerMain(AppData.getJeuDeCarte(),mainJoueur); // Distribue la main au joueur
            // Distribue une pioche au joueur nouvellement créé
            PiocheJoueur piocheJoueur = new PiocheJoueur (0,new Stack<>()); // Crée une nouvelle pioche
            AppData.DistribuerPioche(AppData.getJeuDeCarte(),piocheJoueur); // Distribue la pioche au joueur
            

            AppData.joueur = new Joueur(nomJoueur, mainJoueur, piocheJoueur); 

            //ajout à la liste des joueurs 
            AppData.getListJoueur().add(AppData.joueur);
            //
            
            AppData.joueur.setNomJoueur(nomJoueur); 
            AppData.joueur.setMainJoueur(mainJoueur);
            AppData.joueur.setPiocheJoueur(piocheJoueur);
            AppData.setNbCartePioche(i);
             
        }
    }

     App.setJoueursList(AppData.getListJoueur());
        App app = new App();
        app.setNomsJoueurs(AppData.getListJoueur());
        app.setMainJoueur(AppData.getJoueur().getMainJoueur());
        app.setPiocheJoueur(AppData.getJoueur().getPiocheJoueur());
        app.setNbCartesJoueur(AppData.getJoueur().getMainJoueur().getCarteMain().size()); 
   
    
    // Désactive le bouton après avoir enregistré les noms
    idboutonDemarrerPartie.setDisable(false);
}
  

//Méthode pour repartir à l'accueil 
@FXML 
private void BtnAccueil () throws IOException {
    App.setRoot("ChoixJeux");

}



// Méthode pour commencer la partie 

    @FXML
private void commencerPartie() throws IOException {
    boolean tousLesNomsSaisis = true;

    // Vérifie si tous les noms des joueurs sont saisis
    for (int i = 0; i < AppData.getListJoueur().size(); i++) {
        String nomJoueur = AppData.getListJoueur().get(i).getNomJoueur();

        // Vérifier si le nom du joueur est vide
        if (nomJoueur == null || nomJoueur.trim().isEmpty()) {
            tousLesNomsSaisis = false;
            confirmationText.setText("Le nom du joueur " + (i + 1) + " n'a pas été saisi.");
            break; 
        }
    }

   if (tousLesNomsSaisis && AppData.getListJoueur().size() == AppData.nbJoueur) {
       
        App.setRoot("PartieJeuAdeux");
       
         // Passer les données des joueurs à PartieJeuAdeuxController
         //PartieJeuAdeuxController controller = App.getScene().getController();
         //controller.initData(AppData.getListJoueur());
       
    } else {
        confirmationText.setText("Inscrivez d'abord les joueurs.");
    }
}
        
    }

    