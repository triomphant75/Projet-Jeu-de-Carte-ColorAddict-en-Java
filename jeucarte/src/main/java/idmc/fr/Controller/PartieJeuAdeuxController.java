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
import idmc.fr.Model.MainJoueur;
import idmc.fr.Model.PiocheJoueur;
import idmc.fr.Model.ReglesJeu;
import idmc.fr.Model.Tas;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.Dialog;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.HBox;

import javafx.scene.text.Text;

public class PartieJeuAdeuxController {

    private int IndexJoueurActuel = 0;

    // id Interface utilisateur 
    @FXML 
    private Button idPiocheJeu;
    @FXML
    private Button idTas;
    @FXML 
    private Text idJoueurGame; 
    @FXML
    private Text idConfirmationTxt;

    @FXML
    private HBox idMainJoueur; // Conteneur pour les cartes de la main du joueur

    private ArrayList<Button> idCarteMain; // Liste des boutons représentant les cartes du joueur
    private ArrayList<Joueur> joueurs;


    //Vue à l'initialisation du jeu 
    @FXML
    public void initialize() {
        joueurs = App.getJoueursList();

        ArrayList<Joueur> nomsJoueurs = App.getNomsJoueurs(); // On récupère les noms des joueurs depuis App
        MainJoueur mainJoueur = App.getMainJoueur();
        PiocheJoueur pioche = App.getPiocheJoueur();
        ArrayList<Carte> genererCartes = AppData.GenererCarte();
        AppData.getListJoueur();

        //récupérations jeu de carte 
        ArrayList<Carte> jeuDeCarte = AppData.getJeuDeCarte();

        //injection de dépendance 
        ReglesJeu reglesJeu = new ReglesJeu();
        // Mélange du jeu de carte au début du jeu
        reglesJeu.MelangeCarte(jeuDeCarte);
        
        // nom des joueurs sur l'interface
       if (nomsJoueurs != null && !nomsJoueurs.isEmpty()) {
            String nomPremierJoueur = nomsJoueurs.get(0).getNomJoueur();
            idJoueurGame.setText(nomPremierJoueur); 
    }


    //Affichage sur l'interface à l'initialisation 

    //main joueur actuel
    
    Joueur joueurActuel = App.getJoueursList().get(IndexJoueurActuel);
    ArrayList<Carte> mainDuJoueur = joueurActuel.getMainJoueur().getCarteMain();
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
    private void afficherDonneeJoueurActuel(){
        Joueur joueurActuel = App.getJoueursList().get(IndexJoueurActuel);
        //Affiche le nom du joueur 
        idJoueurGame.setText(joueurActuel.getNomJoueur());
        //Affiche la main du joueur actuel
        ArrayList<Carte> mainDuJoueur = joueurActuel.getMainJoueur().getCarteMain();
        afficherCartesJoueur(mainDuJoueur);


         // appel de la méthode déterminer un vainqueur 
        determinerVainqueur();
        // appel de la méthode match nul
        determinerMatchNul();



    }


    @FXML
    //Méthode pour afficher les cartes dans la main du joueur
    
     private void afficherCartesJoueur (ArrayList<Carte> mainJoueur) {
         Collections.shuffle(mainJoueur); 
 
        AppData.setJeuDeCarte(AppData.GenererCarte());
        // Nettoyage les boutons précédents s'ils existent
        idMainJoueur.getChildren().clear();
        idCarteMain = new ArrayList<>();
        mainJoueur.get(0);
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
        determinerVainqueur();
        // appel de la méthode match nul
        determinerMatchNul();

        
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
            Joueur joueurActuel = App.getJoueursList().get(IndexJoueurActuel);
            joueurActuel.getMainJoueur().getCarteMain().remove(carte);
    
            // Mets à jour la main du joueur avec la nouvelle carte (la carte modifiée)
            joueurActuel.getMainJoueur().getCarteMain().add(nouvelleCarte);
    
            // Affiche la nouvelle main du joueur
            afficherCartesJoueur(joueurActuel.getMainJoueur().getCarteMain());
    
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
    ArrayList<Joueur> joueursList = AppData.getListJoueur();
    ArrayList<Carte> jeuDeCarte = AppData.getJeuDeCarte();
    Alert alert = new Alert(AlertType.INFORMATION);
    int limiteCartesMain = 5; // Limite de cartes en main
    int limitePiochesAutorisees = 5; // Limite de pioches autorisées par joueur

    int indexJoueurActuel = IndexJoueurActuel;
    Joueur joueurActuel = joueursList.get(indexJoueurActuel);
    PiocheJoueur piocheJoueur = App.getPiocheJoueur();

    // On vérifie si le joueur a encore des pioches disponibles
    if (joueurActuel.getNbPioche() >= limitePiochesAutorisees) {
        alert.setTitle("Attention !");
        alert.setHeaderText(null);
        alert.setContentText(joueurActuel.getNomJoueur() + "  vous n'avez plus de carte dans votre pioche .");
        alert.showAndWait();
        return;
    }

    // On Vérifie si la main du joueur a atteint la limite de cartes
    if (joueurActuel.getMainJoueur().getCarteMain().size() >= limiteCartesMain) {
        alert.setTitle("Attention !");
        alert.setHeaderText(null);
        alert.setContentText("Limite de cartes en main atteinte pour " + joueurActuel.getNomJoueur() + ". Défaussez une ou plusieurs cartes pour piocher.");
        alert.showAndWait();
        return;
    }

    // On Vérifie si la pioche est vide
    if (piocheJoueur.getCartePioche().isEmpty()) {
        // Et on alimente la pioche en fonction du nombre de joueurs
        int limiteCartesPioche;
        if (joueursList.size() == 2) {
            limiteCartesPioche = 3; // 3 cartes pour 2 joueurs
        } else if (joueursList.size() == 3) {
            limiteCartesPioche = 2; // 2 cartes pour 3 joueurs
        } else {
            // Normalement on peut définir jusqu'à 6 joueurs 
            limiteCartesPioche = 4; // Modifier cette valeur selon vos besoins
        }
        
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
        joueurActuel.getMainJoueur().getCarteMain().add(cartePiochee);
        afficherCartesJoueur(joueurActuel.getMainJoueur().getCarteMain());

        // Incrémente le nombre de pioches effectuées par le joueur
        joueurActuel.setNbPioche(joueurActuel.getNbPioche() + 1);

       
    }

    afficherDonneeJoueurActuel();
    // appel de la méthode déterminer un vainqueur 
    determinerVainqueur();
    // appel de la méthode match nul
    determinerMatchNul();


   
}



    //Méthode pour le bouton passer son tour 
    @FXML 
    private void btnTour(){
        passerAuJoueurSuivant();
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
            Joueur joueurActuel = App.getJoueursList().get(IndexJoueurActuel);
            joueurActuel.getMainJoueur().getCarteMain().remove(carte);
            ArrayList<Carte> nouvelleMain = joueurActuel.getMainJoueur().getCarteMain();
            afficherCartesJoueur(nouvelleMain);
            afficherDonneeJoueurActuel();
            passerAuJoueurSuivant();
            //appel de la méthode déterminer un vainqueur 
            determinerVainqueur();
            // appel de la méthode match nul
            determinerMatchNul();
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


    // Méthode pour passer au joueur Suivant 
    @FXML
    private void passerAuJoueurSuivant() {

        IndexJoueurActuel++;
    if (IndexJoueurActuel >= App.getJoueursList().size()) {
        IndexJoueurActuel = 0; // Revenir au premier joueur si c'était le dernier
    }

        // Mettre à jour l'interface pour afficher les données du joueur suivant
            afficherDonneeJoueurActuel();
        }


    
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
    
   


    // Méthode pour mettre retour à l'accueil
    @FXML
    private void BtnMettreFinP() throws IOException {
        App.setRoot("ChoixJeux");
    }
}

    
    

