package idmc.fr.Model;
import java.util.ArrayList;
import java.util.Collections;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.Stack;

public class ReglesJeu {
	
	
		
	public ReglesJeu( Joueur joueur) {
		
	}
	
	public ReglesJeu() {
	}


	//mélanger le jeu de carte qui vient d'être créé
	public static List <Carte> MelangeCarte(List <Carte> jeuDeCarte){
		Collections.shuffle(jeuDeCarte);
		return jeuDeCarte;
	}
	
	
	//le nombre de carte dans une pioche en fonction du nombre de joueur dans la partie.
	//en fonction du nombre de joueur, je n'utilise pas toutes les cartes créés pour garder une égalité entre les joueurs.
	//si il y a moins de 2 joueurs ou plus de 6 joueurs, le programme se termine et un message est envoyé.
	public static void lesJoueurs() {
		boolean valide = false;
		while(!valide) {
			try {
				Scanner sc = new Scanner(System.in);
				System.out.println("Combien il y a t'il de joueur ? ");
				AppData.nbJoueur = sc.nextInt();
				valide = true;
			} catch (InputMismatchException e) {
				System.out.println("Vous devez mettre un chiffre entre 2 et 6 joueurs.");
			}
		}
		if (AppData.nbJoueur<2) {
			System.out.println("Il n'y a pas assez de joueur pour lancer une nouvelle partie ! ");
			System.exit(0);
		}
		if (AppData.nbJoueur==2) {
			AppData.nbCartePioche=25;
			System.out.println("Une nouvelle partie à deux va commencer ! ");
			AppData.getListJoueur().add(AppData.joueur1 = AppData.infoJoueur(null));
			AppData.getListJoueur().add(AppData.joueur2 = AppData.infoJoueur(null));
			}
		if (AppData.nbJoueur==3) {
			AppData.nbCartePioche=15;
			System.out.println("Une nouvelle partie à 3 va commencer ! ");
			AppData.getListJoueur().add(AppData.joueur1 = AppData.infoJoueur(null));
			AppData.getListJoueur().add(AppData.joueur2 = AppData.infoJoueur(null));
			AppData.getListJoueur().add(AppData.joueur3 = AppData.infoJoueur(null));
			}
		if (AppData.nbJoueur==4) {
			AppData.nbCartePioche=11;
			System.out.println("Une nouvelle partie à 4 va commencer ! ");
			AppData.getListJoueur().add(AppData.joueur1 = AppData.infoJoueur(null));
			AppData.getListJoueur().add(AppData.joueur2 = AppData.infoJoueur(null));
			AppData.getListJoueur().add(AppData.joueur3 = AppData.infoJoueur(null));
			AppData.getListJoueur().add(AppData.joueur4 = AppData.infoJoueur(null));
			}
		if (AppData.nbJoueur==5) {
			AppData.nbCartePioche=8;
			System.out.println("Une nouvelle partie à 5 va commencer ! ");
			AppData.getListJoueur().add(AppData.joueur1 = AppData.infoJoueur(null));
			AppData.getListJoueur().add(AppData.joueur2 = AppData.infoJoueur(null));
			AppData.getListJoueur().add(AppData.joueur3 = AppData.infoJoueur(null));
			AppData.getListJoueur().add(AppData.joueur4 = AppData.infoJoueur(null));
			AppData.getListJoueur().add(AppData.joueur5 = AppData.infoJoueur(null));
			}
		if (AppData.nbJoueur==6) {
			AppData.nbCartePioche=6;
			System.out.println("Une nouvelle partie à 6 va commencer ! ");
			AppData.getListJoueur().add(AppData.joueur1 = AppData.infoJoueur(null));
			AppData.getListJoueur().add(AppData.joueur2 = AppData.infoJoueur(null));
			AppData.getListJoueur().add(AppData.joueur3 = AppData.infoJoueur(null));
			AppData.getListJoueur().add(AppData.joueur4 = AppData.infoJoueur(null));
			AppData.getListJoueur().add(AppData.joueur5 = AppData.infoJoueur(null));
			AppData.getListJoueur().add(AppData.joueur6 = AppData.infoJoueur(null));
			}
		if (AppData.nbJoueur>6) {
			System.out.println("Il y a trop de joueurs pour lancer une nouvelle partie ! ");
			System.exit(0);
		}
	}
	
	//débuter une partie
	public static void CommencerJeu() {
		AppData.setJeuDeCarte(AppData.GenererCarte());
		MelangeCarte(AppData.getJeuDeCarte());
		
		//début de la partie, on met une carte au milieu du jeu et on crée les joueurs
		Stack <Carte> stackTas = new Stack<Carte>();
		Tas tas = new Tas (0,stackTas);
		lesJoueurs();
		tas.carteTas.add(AppData.getJeuDeCarte().get(0));
		AppData.getJeuDeCarte().remove(0);
		tas.nbCarteTas=tas.nbCarteTas+1;
		System.out.println(tas);
		Carte carteJ = new CarteJoker();
		if(tas.carteTas.get(0).equals(carteJ)) {
			tas=ChoisirCouleur(tas);
		}
		Boolean fin = false;
		while(!fin) {
			for(int i=0;i<AppData.nbJoueur;i++) {
				Jouer(AppData.getListJoueur().get(i),tas);
			}
		}
	}
	
	public static void Jouer(Joueur joueur,Tas tas) {
		Boolean trouve=false;
		String newPartie;
		while(!trouve) {
			JouerAuto(joueur, tas);
			System.out.println(tas);
			trouve=Gagne(joueur);
		}
		
	}
	
	//pour comparer deux cartes
	public static boolean ComparerCarte(Carte cartemain, Carte carte) {
		Boolean trouve=false;
		if (cartemain.couleurCarte==carte.couleurCarte||cartemain.couleurCarte==carte.nomCarte||cartemain.nomCarte==carte.couleurCarte||cartemain.nomCarte==carte.nomCarte  ) {	
			trouve=true;
		}
		
		return trouve;
	}
	
	public static Tas ChoisirCouleur(Tas tas) {
        boolean valide = false;
        Scanner sc = new Scanner(System.in);
        while (!valide) {
            System.out.println("Choisissez une couleur parmi : Bleu, Jaune, Rouge, Vert, Orange, Rose et Noir : ");
            String couleur = sc.nextLine().toLowerCase();
            switch (couleur) {
                case "bleu":
                    tas.carteTas.push(new CarteCouleur(Couleur.bleu, Couleur.bleu));
                    valide = true;
                    break;
                case "jaune":
                    tas.carteTas.push(new CarteCouleur(Couleur.jaune, Couleur.jaune));
                    valide = true;
                    break;
                case "rouge":
                    tas.carteTas.push(new CarteCouleur(Couleur.rouge, Couleur.rouge));
                    valide = true;
                    break;
                case "vert":
                    tas.carteTas.push(new CarteCouleur(Couleur.vert, Couleur.vert));
                    valide = true;
                    break;
                case "orange":
                    tas.carteTas.push(new CarteCouleur(Couleur.orange, Couleur.orange));
                    valide = true;
                    break;
                case "rose":
                    tas.carteTas.push(new CarteCouleur(Couleur.rose, Couleur.rose));
                    valide = true;
                    break;
                case "noir":
                    tas.carteTas.push(new CarteCouleur(Couleur.noir, Couleur.noir));
                    valide = true;
                    break;
                default:
                    valide = false;
                    System.err.println("Couleur invalide, veuillez réessayer.");
            }
        }
        return tas;
    }
	
	//l'ordinateur joue
	public static void JouerAuto(Joueur joueur, Tas tas) {
		Carte carte;
		carte = tas.carteTas.peek();
		Carte carteJ = new CarteJoker();
		for (int i=0; i<joueur.mainJoueur.carteMain.size();i++) {
			if (ComparerCarte(joueur.mainJoueur.carteMain.get(i),carte) ) {
				tas.carteTas.push(joueur.mainJoueur.carteMain.remove(i));
				tas.nbCarteTas= tas.nbCarteTas+1;
				joueur.mainJoueur.nbCarteMain=joueur.mainJoueur.nbCarteMain-1;
				return;
			}
			if(joueur.mainJoueur.carteMain.get(i).couleurCarte.equals(carteJ.couleurCarte)) {
				tas.carteTas.push(joueur.mainJoueur.carteMain.remove(i));
				tas.nbCarteTas= tas.nbCarteTas+1;
				joueur.mainJoueur.nbCarteMain=joueur.mainJoueur.nbCarteMain-1;
				tas=ChoisirCouleur(tas);
				return;
			}
		}
		Boolean trouve=false;
		while(!trouve) {
			trouve=Gagne(joueur);
			joueur.mainJoueur.carteMain.add(joueur.piocheJoueur.cartePioche.pop());
			joueur.mainJoueur.nbCarteMain=joueur.mainJoueur.nbCarteMain+1;
			joueur.piocheJoueur.nbCartePioche=joueur.piocheJoueur.nbCartePioche-1;
			for (int i=0; i<joueur.mainJoueur.carteMain.size();i++) {
				if (ComparerCarte(joueur.mainJoueur.carteMain.get(i),carte) ) {
					tas.carteTas.push(joueur.mainJoueur.carteMain.remove(i));
					tas.nbCarteTas= tas.nbCarteTas+1;
					joueur.mainJoueur.nbCarteMain=joueur.mainJoueur.nbCarteMain-1;
					trouve=false;
					return;
				}
			}
			trouve=Gagne(joueur);
		}
	}
	//on vérifie si un joueur à gagngé
	//un joueur gagne si il n'a plus de carte dans sa main ou si il n'a plus de carte dans sa pioche
	public static boolean Gagne(Joueur joueur) {
		Boolean trouve=false;
		if(joueur.mainJoueur.nbCarteMain==0) {
			System.out.println(joueur.nomJoueur+" a gagné la partie car il n'y a plus de carte dans sa main.");
			System.out.println(joueur);
			trouve=true;
			System.exit(0);
		}
		if(joueur.piocheJoueur.nbCartePioche==0) {
			System.out.println(joueur.nomJoueur+" a gagné la partie car il n'y a plus de crate dans sa pioche.");
			System.out.println(joueur);
			trouve=true;
			System.exit(0);
		}
		return trouve;
	}

}
