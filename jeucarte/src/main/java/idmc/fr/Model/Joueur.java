package idmc.fr.Model;
public class Joueur {
	String nomJoueur;
	MainJoueur mainJoueur;
	PiocheJoueur piocheJoueur;
	int NbPioche;
	
	

	public Joueur(String nomJoueur) {
        this.nomJoueur = nomJoueur;
    }

    public Joueur(MainJoueur mainJoueur, PiocheJoueur piocheJoueur) {
        this.mainJoueur = mainJoueur;
        this.piocheJoueur = piocheJoueur;
    }

    public Joueur(String nomJoueur, MainJoueur mainJoueur) {
		this.nomJoueur = nomJoueur;
		this.mainJoueur = mainJoueur;
	}

	public Joueur(String nomJoueur, MainJoueur mainJoueur, PiocheJoueur piocheJoueur) {
		this.nomJoueur = nomJoueur;
		this.mainJoueur = mainJoueur;
		this.piocheJoueur = piocheJoueur;
	}

	public String getNomJoueur() {
		return nomJoueur;
	}

	public void setNomJoueur(String nomJoueur) {
		this.nomJoueur = nomJoueur;
	}

	public MainJoueur getMainJoueur() {
		return mainJoueur;
	}

	public void setMainJoueur(MainJoueur mainJoueur) {
		this.mainJoueur = mainJoueur;
	}

	public PiocheJoueur getPiocheJoueur() {
		return piocheJoueur;
	}

	public void setPiocheJoueur(PiocheJoueur piocheJoueur) {
		this.piocheJoueur = piocheJoueur;
	}

	public int getNbPioche() {
		return NbPioche;
	}

	public void setNbPioche(int nbPioche) {
		NbPioche = nbPioche;
	}

	@Override
	public String toString() {
		return "Nom du joueur : \n" + nomJoueur + "\nMain du joueur : \n" + mainJoueur + "\nPioche du joueur : \n" + piocheJoueur;
	}
	
}
