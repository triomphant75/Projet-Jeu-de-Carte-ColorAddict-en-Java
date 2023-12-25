package idmc.fr.Model;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.Stack;

public class AppData {

    public static ArrayList <Joueur> listJoueur = new ArrayList <Joueur>();
    private static MainJoueur mainJoueur;
    private static ArrayList<Carte> jeuDeCarte;
    public static Joueur joueur;
	public static Joueur joueur1;
	public static Joueur joueur2;
	public static Joueur joueur3;
	public static Joueur joueur4;
	public static Joueur joueur5;
	public static Joueur joueur6;
	public static int nbCartePioche;
	public static int nbJoueur;

    //créer les carte du jeu
	public static ArrayList<Carte> GenererCarte(){
		setJeuDeCarte(new ArrayList<>());
        for (Couleur couleurCarte : Couleur.values()){
        	for(Couleur nomCarte : Couleur.values()) {
        		if(couleurCarte != Couleur.multicolore && nomCarte != Couleur.multicolore){
        			getJeuDeCarte().add(new CarteCouleur(nomCarte, couleurCarte));
        		}
        	}
        }
        for(int i=0;i<8;i++) {
        	getJeuDeCarte().add(new CarteJoker());
        }
        return getJeuDeCarte();
	}

    //distribuer cartes MainJoueeur
	public static void DistribuerMain(ArrayList <Carte> jeuDeCarte, MainJoueur mainjoueur) {
		for (int i=0; i<3 ;i++ ) {
			mainjoueur.carteMain.add(jeuDeCarte.get(i));
			jeuDeCarte.remove(i);
		}
		mainjoueur.nbCarteMain=3;
	}
	//distribuer cartes PiocheJoueeur
	public static void DistribuerPioche(ArrayList <Carte> jeuDeCarte, PiocheJoueur piochejoueur) {
		int a=0;
		for (int i=0; i<nbCartePioche;i++ ) {
			piochejoueur.cartePioche.add(jeuDeCarte.get(a));
			jeuDeCarte.remove(a);
		}
		piochejoueur.nbCartePioche=nbCartePioche;
	}

    PiocheJoueur piocheJoueur = new PiocheJoueur (0,new Stack<>()); // Créer une nouvelle pioche
    MainJoueur mainjoueur = new MainJoueur (0,new ArrayList<>()); // Créer une nouvelle main
    

    //créer un joueur
	public static Joueur infoJoueur(String nom) {
		ArrayList <Carte> set = new ArrayList <Carte>();
		MainJoueur mainJoueur = new MainJoueur (0,set);
		DistribuerMain(getJeuDeCarte(),mainJoueur);
		Stack <Carte> stack= new Stack<Carte>();
		PiocheJoueur piocheJoueur = new PiocheJoueur (0,stack);
		DistribuerPioche(getJeuDeCarte(),piocheJoueur);
		System.out.println("Nom du joueur : ");
		Joueur joueur = new Joueur(mainJoueur, piocheJoueur);
        joueur.setNomJoueur(nom);
		return joueur;
	}
	

    



    public static ArrayList<Joueur> getListJoueur() {
        return listJoueur;
    }
    public static void setListJoueur(ArrayList<Joueur> listJoueur) {
        AppData.listJoueur = listJoueur;
    }
    public static MainJoueur getMainJoueur() {
        return mainJoueur;
    }
    public static void setMainJoueur(MainJoueur mainJoueur) {
        AppData.mainJoueur = mainJoueur;
    }
    public static ArrayList<Carte> getJeuDeCarte() {
        return jeuDeCarte;
    }
    public static void setJeuDeCarte(ArrayList<Carte> jeuDeCarte) {
        AppData.jeuDeCarte = jeuDeCarte;
    }
    public static Joueur getJoueur() {
        return joueur;
    }
    public static void setJoueur(Joueur joueur) {
        AppData.joueur = joueur;
    }
    public static Joueur getJoueur1() {
        return joueur1;
    }
    public static void setJoueur1(Joueur joueur1) {
        AppData.joueur1 = joueur1;
    }
    public static Joueur getJoueur2() {
        return joueur2;
    }
    public static void setJoueur2(Joueur joueur2) {
        AppData.joueur2 = joueur2;
    }
    public static Joueur getJoueur3() {
        return joueur3;
    }
    public static void setJoueur3(Joueur joueur3) {
        AppData.joueur3 = joueur3;
    }
    public static Joueur getJoueur4() {
        return joueur4;
    }
    public static void setJoueur4(Joueur joueur4) {
        AppData.joueur4 = joueur4;
    }
    public static Joueur getJoueur5() {
        return joueur5;
    }
    public static void setJoueur5(Joueur joueur5) {
        AppData.joueur5 = joueur5;
    }
    public static Joueur getJoueur6() {
        return joueur6;
    }
    public static void setJoueur6(Joueur joueur6) {
        AppData.joueur6 = joueur6;
    }
    public static int getNbCartePioche() {
        return nbCartePioche;
    }
    public static void setNbCartePioche(int nbCartePioche) {
        AppData.nbCartePioche = nbCartePioche;
    }
    public static int getNbJoueur() {
        return nbJoueur;
    }
    public static void setNbJoueur(int nbJoueur) {
        AppData.nbJoueur = nbJoueur;
    }

    public PiocheJoueur getPiocheJoueur() {
        return piocheJoueur;
    }

    public void setPiocheJoueur(PiocheJoueur piocheJoueur) {
        this.piocheJoueur = piocheJoueur;
    }

    
    public MainJoueur getMainjoueur() {
        return mainjoueur;
    }

    public void setMainjoueur(MainJoueur mainjoueur) {
        this.mainjoueur = mainjoueur;
    }

    

    
}
