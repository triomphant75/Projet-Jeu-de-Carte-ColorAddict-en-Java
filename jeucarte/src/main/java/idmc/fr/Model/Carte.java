package idmc.fr.Model;
public class Carte {
	Couleur nomCarte;
	Couleur couleurCarte; 
	
//	
	public Carte (Couleur nomCarte, Couleur couleurCarte) {
		this.couleurCarte=couleurCarte;
		this.nomCarte=nomCarte;
	}

	
	public Couleur getNomCarte() {
		return nomCarte;
	}

	public Couleur getCouleurCarte() {
		return couleurCarte;
	}

	public boolean estMulticolore() {
		// Votre logique pour déterminer si la carte est multicolore en fonction du nom et de la couleur
		return nomCarte.equals("Multicolor") && couleurCarte.equals(Couleur.multicolore);
	}
	



	@Override
	public String toString() {
		return "Carte{nomCarte=" + nomCarte + ",couleurCarte=" + couleurCarte + "}\n";
	}


	public void setNomCarte(Couleur nomCarte) {
		this.nomCarte = nomCarte;
	}


	public void setCouleurCarte(Couleur couleurCarte) {
		this.couleurCarte = couleurCarte;
	}

}
