package idmc.fr.Model;
import java.util.ArrayList;

public class MainJoueur {
	int nbCarteMain;
	ArrayList <Carte> carteMain;
	
	public MainJoueur (int nbCarteMain, ArrayList <Carte> carteMain) {
		this.carteMain = carteMain;
		this.nbCarteMain = nbCarteMain;
	}

	public int getNbCarteMain() {
		return nbCarteMain;
	}

	public void setNbCarteMain(int nbCarteMain) {
		this.nbCarteMain = nbCarteMain;
	}

	public ArrayList<Carte> getCarteMain() {
		return carteMain;
	}

	public void setCarteMain(ArrayList<Carte> carteMain) {
		this.carteMain = carteMain;
	}

	@Override
	public String toString() {
		return "Nombre de carte en main = " + nbCarteMain + "\nCarte en main : \n" + carteMain;
	}
}
