package idmc.fr.Model;
import java.util.Stack;

public class PiocheJoueur {
	int nbCartePioche;
	Stack <Carte> cartePioche;
	
	public PiocheJoueur (int nbCartePioche, Stack <Carte> cartePioche) {
		this.cartePioche = cartePioche;
		this.nbCartePioche = nbCartePioche;
	}

	public int getNbCartePioche() {
		return nbCartePioche;
	}

	public void setNbCartePioche(int nbCartePioche) {
		this.nbCartePioche = nbCartePioche;
	}

	public Stack<Carte> getCartePioche() {
		return cartePioche;
	}

	public void setCartePioche(Stack<Carte> cartePioche) {
		this.cartePioche = cartePioche;
	}

	@Override
	public String toString() {
		return "Nombre de carte = " + nbCartePioche + "\nLes carte de sa pioche : \n " + cartePioche;
	}
	
}
