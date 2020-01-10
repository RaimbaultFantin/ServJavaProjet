package documents;

import java.util.Timer;

import exceptions.EmpruntException;
import personnes.Abonne;
import tachesautomatiques.FinDeTempsReservation;

public class Livre extends Entite {

	private final int DeuxHeures = 200000;

	public Livre(int numero, String titre) {
		super(numero, titre);
	}

	@Override
	public String toString() {
		return "[ Titre : " + titre + " numero : " + numero + " ]";
	}

}
