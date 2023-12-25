package idmc.fr.Controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Stack;

import idmc.fr.App;
import idmc.fr.Model.AppData;
import idmc.fr.Model.Carte;
import idmc.fr.Model.Couleur;
import idmc.fr.Model.Joueur;
import idmc.fr.Model.Main;
import idmc.fr.Model.MainJoueur;
import idmc.fr.Model.PiocheJoueur;
import idmc.fr.Model.ReglesJeu;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

public class PartieAvecOrdinateurController {
private int IndexJoueurActuel = 1;

    // id Interface utilisateur 
    @FXML 
    private Button idPiocheJeu;
    @FXML
    private Button idTas;
    @FXML 
    private Text idJoueurGame; 

    @FXML
    private HBox idMainJoueur; // Conteneur pour les cartes de la main du joueur

    private ArrayList<Button> idCarteMain; // Liste des boutons représentant les cartes du joueur
    private Joueur joueur1;


    //Vue à l'initialisation du jeu 
    @FXML
    public void initialize() {

         //injection de dépendance 
        ReglesJeu reglesJeu = new ReglesJeu();

        ArrayList<Carte> jeuDeCarte = AppData.GenererCarte();
        AppData.setJeuDeCarte(jeuDeCarte);

        

        // Distribution de la main et de la pioche pour le joueur 1
        MainJoueur mainJoueur = new MainJoueur (0,new ArrayList<>()); 
        AppData.DistribuerMain(jeuDeCarte, mainJoueur);
         PiocheJoueur piocheJoueur = new PiocheJoueur (0,new Stack<>());
        AppData.DistribuerPioche(jeuDeCarte, piocheJoueur);

             // Initialisation du joueur 1
         joueur1 = new Joueur(mainJoueur, piocheJoueur);
        AppData.setJoueur1(joueur1); 
    


       
        // Mélange du jeu de carte au début du jeu
        reglesJeu.MelangeCarte(jeuDeCarte);
        
        ArrayList<Carte> mainDuJoueur = joueur1.getMainJoueur().getCarteMain();
        afficherCartesJoueur(mainDuJoueur);

    //Tas
    AfficherTas();
        
    }

    //Méthode pour affihcer le Tas 
    @FXML
    public void AfficherTas(){
        ArrayList<Carte> jeuDeCarte = AppData.getJeuDeCarte();
        ArrayList<Carte> cartesNonMulticolores = new ArrayList<>();

    // Filtre des cartes multicolores
    for (Carte carte : jeuDeCarte) {
        if (!carte.estMulticolore()) {
            cartesNonMulticolores.add(carte);
        }
    }

    if (!cartesNonMulticolores.isEmpty()) {
        int indexCarteInitiale = (int) (Math.random() * cartesNonMulticolores.size());
        Carte carteInitiale = cartesNonMulticolores.get(indexCarteInitiale);
        
        // On pose la carte initiale sur le tas 
        poserCarteSurTas(carteInitiale);
        
        // Puis on mets à jour le jeu de cartes dans AppData
        AppData.setJeuDeCarte(jeuDeCarte);
    }
}


    // Méthode pour poser une carte sur le Tas 
    @FXML void poserCarteSurTas( Carte carte){
        
        idTas.setText(carte.getNomCarte() + " " + carte.getCouleurCarte());
        idTas.setStyle("-fx-font-size: 12px; -fx-min-width: 100px; -fx-min-height: 130px;");
        // Stocke l'objet Carte dans l'élément idTas
        idTas.setUserData(carte);
    }
   

    // Méthode pour afficher Les Données des joueurs 
    @FXML
private void afficherDonneeJoueurActuel() {
    
    if (joueur1 != null) {
        // Récupère la main du joueur actuel
        ArrayList<Carte> mainDuJoueur = joueur1.getMainJoueur().getCarteMain();
        // Affiche les cartes dans la main du joueur
        afficherCartesJoueur(mainDuJoueur);
        //determinerVainqueur();
        //determinerMatchNul();
    } 
      
}


    @FXML
    //Méthode pour afficher les cartes dans la main du joueur
    
     private void afficherCartesJoueur (ArrayList<Carte> mainJoueur) {
         Collections.shuffle(mainJoueur); 
 
        AppData.setJeuDeCarte(AppData.GenererCarte());
        // Nettoyage les boutons précédents s'ils existent
        idMainJoueur.getChildren().clear();
        idCarteMain = new ArrayList<>();
        ArrayList<Carte> nouvellesCartes = new ArrayList<>();

        // Création d'un bouton pour chaque carte dans la main du joueur
        for (Carte carte : mainJoueur) {
            Button carteButton = new Button(); 
         
            String text = carte.getNomCarte()+" "+carte.getCouleurCarte();
            carteButton.setText(text); // le texte  (ou l'image si y'a possibilité de mettre )  correspondant à chaque carte
            carteButton.setStyle("-fx-font-size: 12px; -fx-min-width: 100px; -fx-min-height: 130px;"); // style des boutons
            
            if (carte.getCouleurCarte() == Couleur.multicolore) {
                carteButton.setOnAction(e -> {
                    Carte nouvelleCarte = (Carte) choisirCouleurPourCarteMulticolore(carte);
                    if (nouvelleCarte != null) {
                        nouvellesCartes.add(nouvelleCarte);
                    }
                });
            } else {
                carteButton.setOnAction(e -> jouerCarte(carte));
            }

            idCarteMain.add(carteButton);
            idMainJoueur.getChildren().add(carteButton);
        }

        mainJoueur.removeAll(nouvellesCartes);

        // appel de la méthode déterminer un vainqueur 
        //determinerVainqueur();
        // appel de la méthode match nul
        //determinerMatchNul();

        
    }

    private Object choisirCouleurPourCarteMulticolore(Carte carte) {
        List<String> couleurs = List.of("Rouge", "Bleu", "Vert", "Jaune", "Orange", "Rose", "Noir"); // Liste des couleurs possibles
        ChoiceDialog<String> dialog = new ChoiceDialog<>(null, couleurs);
        dialog.setTitle("Choisir une couleur");
        dialog.setHeaderText("Choisissez une couleur pour la carte");
    
        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()) {
            String couleurChoisie = result.get();
            // Crée une nouvelle carte avec le nom de la carte actuelle et la couleur choisie
            Couleur couleurCarte;
            switch (couleurChoisie) {
                case "Rouge":
                    couleurCarte = Couleur.rouge;
                    break;
                case "Bleu":
                    couleurCarte = Couleur.bleu;
                    break;
                case "Vert":
                    couleurCarte = Couleur.vert;
                    break;
                case "Jaune":
                    couleurCarte = Couleur.jaune;
                    break;
                case "Orange":
                    couleurCarte = Couleur.orange;
                case "Rose":
                    couleurCarte = Couleur.rose;
                case "Noir":
                    couleurCarte = Couleur.noir;
                default:
                    couleurCarte = Couleur.orange;
        
                    break;
            }
            // Crée une nouvelle carte avec le nom de la carte existante et la couleur choisie
            Carte nouvelleCarte = new Carte(carte.getNomCarte(), couleurCarte);
    
            // Retire la carte multicolore d'origine de la main du joueur
        
            joueur1.getMainJoueur().getCarteMain().remove(carte);
    
            // Mets à jour la main du joueur avec la nouvelle carte (la carte modifiée)
            joueur1.getMainJoueur().getCarteMain().add(nouvelleCarte);
    
            // Affiche la nouvelle main du joueur
            afficherCartesJoueur(joueur1.getMainJoueur().getCarteMain());
    
            // la nouvelle carte avec la nouvelle couleur sur le tas
            //poserCarteSurTas(nouvelleCarte);
    
            // Retourner la nouvelle carte (la carte modifiée)
            return nouvelleCarte;

            
        } else {
            // Si aucune couleur n'est choisie, on retourne la carte d'origine sans modification
            return carte;
        }
        
    }
    
    

    
//Méthode pour gérer la pioche 

@FXML
private void pioche() {

    ArrayList<Carte> jeuDeCarte = AppData.getJeuDeCarte();
    Alert alert = new Alert(AlertType.INFORMATION);
    int limiteCartesMain = 5; // Limite de cartes en main
    int limitePiochesAutorisees = 3; // Limite de pioches autorisées par joueur
    PiocheJoueur piocheJoueur = joueur1.getPiocheJoueur();

    // On vérifie si le joueur a encore des pioches disponibles
    if (joueur1.getNbPioche() >= limitePiochesAutorisees) {
        alert.setTitle("Attention !");
        alert.setHeaderText(null);
        alert.setContentText(joueur1.getNomJoueur() + "  vous n'avez plus de carte dans votre pioche .");
        alert.showAndWait();
        return;
    }

    // On Vérifie si la main du joueur a atteint la limite de cartes
    if (joueur1.getMainJoueur().getCarteMain().size() >= limiteCartesMain) {
        alert.setTitle("Attention !");
        alert.setHeaderText(null);
        alert.setContentText("Limite de cartes en main atteinte pour " + joueur1.getNomJoueur() + ". Défaussez une ou plusieurs cartes pour piocher.");
        alert.showAndWait();
        return;
    }

    // On Vérifie si la pioche est vide
    if (piocheJoueur.getCartePioche().isEmpty()) {
        // Et on alimente la pioche en fonction du nombre de joueurs
        int limiteCartesPioche=5;
        
        int cartesRestantesPioche = limiteCartesPioche - piocheJoueur.getCartePioche().size();

        if (cartesRestantesPioche > 0 && jeuDeCarte.size() >= cartesRestantesPioche) {
            for (int i = 0; i < cartesRestantesPioche; i++) {
                Collections.shuffle(jeuDeCarte);
                Carte carteAjoutee = jeuDeCarte.remove(0);
                piocheJoueur.getCartePioche().add(carteAjoutee);
            }
        } else {
            alert.setTitle("Attention !");
            alert.setHeaderText(null);
            alert.setContentText("Impossible d'ajouter plus de cartes à la pioche.");
            alert.showAndWait();
            return;
        }
    }

    // on peut donc piocher  si la pioche n'est pas vide
    Carte cartePiochee = piocheJoueur.getCartePioche().pop();

    // Ajout de  la carte piochée à la main du joueur
    if (cartePiochee != null) {
        joueur1.getMainJoueur().getCarteMain().add(cartePiochee);
        afficherCartesJoueur(joueur1.getMainJoueur().getCarteMain());

        // Incrémente le nombre de pioches effectuées par le joueur
        joueur1.setNbPioche(joueur1.getNbPioche() + 1);

       
    }

    afficherDonneeJoueurActuel();
    // appel de la méthode déterminer un vainqueur 
   // determinerVainqueur();
    // appel de la méthode match nul
    //determinerMatchNul();


   
}

    
    
    //Méthode pour joueur une carte
    @FXML
    private void jouerCarte(Carte carte) {
        ReglesJeu reglesJeu = new ReglesJeu();

        Carte carteSurTas = (Carte) idTas.getUserData(); // la description de la carte sur le tas


        // Compare la carte sélectionnée dans la main du joueur avec celle sur le tas
        if (reglesJeu.ComparerCarte(carte, carteSurTas)) {
            poserCarteSurTas(carte); // Pose la carte sur le tas visuellement
        
    
            // la logique pour retirer la carte de la main du joueur

            joueur1.getMainJoueur().getCarteMain().remove(carte);
            ArrayList<Carte> nouvelleMain = joueur1.getMainJoueur().getCarteMain();
             afficherDonneeJoueurActuel();
            afficherCartesJoueur(nouvelleMain);
           
            //appel de la méthode déterminer un vainqueur 
            //determinerVainqueur();
            // appel de la méthode match nul
            //determinerMatchNul();
        }
    else {
        // Affiche un message indiquant que la carte ne peut pas être jouée sur le tas actuel
        Alert alert = new Alert(AlertType.WARNING);
        alert.setTitle("Carte invalide");
        alert.setHeaderText(null);
        alert.setContentText("La carte sélectionnée ne peut pas être jouée sur le tas actuel.");
        alert.showAndWait();
    }
        
    }



    /* 
        private void determinerVainqueur() {
            Joueur vainqueur = null;
            for (Joueur joueur : App.getJoueursList()) {
                if (joueur.getNbPioche() >= 3 && joueur.getMainJoueur().getCarteMain().isEmpty()) {
                    vainqueur = joueur;
                    break;
                }
            }
        
            if (vainqueur != null) {
                // Affiche le message indiquant le vainqueur
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Partie terminée");
                alert.setHeaderText(null);
                alert.setContentText("Le vainqueur est " + vainqueur.getNomJoueur() + " !");
                alert.showAndWait();
        
                // On désactive tous les mouvements ou actions du jeu
                idPiocheJeu.setDisable(true);
                idTas.setDisable(true);
                idMainJoueur.setDisable(true);


                
            }
        }

        */
        

     /*   
        
    // Méthode pour déterminer un match null 

    private void determinerMatchNul() {
        boolean matchNul = true;
    
        // Vérifie si chaque joueur a atteint sa limite de pioche et ne peut pas jouer de carte sur le tas
        for (Joueur joueur : App.getJoueursList()) {
            if (joueur.getNbPioche() < 3 || peutJouerCarte(joueur)) {
                // Si le joueur a encore des pioches ou peut jouer une carte, ce n'est pas un match nul
                matchNul = false;
                break;
            }
        }
    
        if (matchNul) {
            // Affiche un message indiquant qu'il y a un match nul
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Match nul");
            alert.setHeaderText(null);
            alert.setContentText("La partie est terminée. Match nul !");
            alert.showAndWait();
    
            // Désactive tous les mouvements ou actions du jeu si nécessaire
            idPiocheJeu.setDisable(true);
            idTas.setDisable(true);
            idMainJoueur.setDisable(true);
        }
    }
    
    // Méthode pour vérifier si un joueur peut jouer une carte sur le tas actuel
    private boolean peutJouerCarte(Joueur joueur) {
        Carte carteSurTas = (Carte) idTas.getUserData();
    
        for (Carte carte : joueur.getMainJoueur().getCarteMain()) {
            if (carte.estMulticolore() || new ReglesJeu().ComparerCarte(carte, carteSurTas)) {
                // Si le joueur peut jouer une carte sur le tas actuel, retourne true
                return true;
            }
        }
    
        // Retourne false si le joueur ne peut pas jouer de carte sur le tas actuel
        return false;
    }

     */
    






    
    //Méthode pour repartir à l'accueil 
@FXML 
private void BtnAccueil () throws IOException {
    App.setRoot("ChoixJeux");

}
    
}
