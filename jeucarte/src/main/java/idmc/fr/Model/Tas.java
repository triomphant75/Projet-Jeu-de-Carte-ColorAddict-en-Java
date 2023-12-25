package idmc.fr.Model;

import java.util.Stack;

public class Tas {
	int nbCarteTas;
	Stack <Carte> carteTas;
	
	public Tas (int nbCarteTas, Stack <Carte> carteTas) {
		this.carteTas = carteTas;
		this.nbCarteTas = nbCarteTas;
	}

	public int getNbCarteTas() {
		return nbCarteTas;
	}

	public void setNbCarteTas(int nbCarteTas) {
		this.nbCarteTas = nbCarteTas;
	}

	public Stack<Carte> getCarteTas() {
		return carteTas;
	}

	public void setCarteTas(Stack<Carte> carteTas) {
		this.carteTas = carteTas;
	}

	@Override
	public String toString() {
		return "Tas :\nNombre de carte dans le Tas = " + nbCarteTas + "\nCarte dans le Tas : \n" + carteTas;
	}

}
