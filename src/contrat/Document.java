package contrat;

import exceptions.EmpruntException;
import exceptions.RetourException;
import personnes.Abonne;

public interface Document {
	int numero();
	void reserver(Abonne ab) throws EmpruntException ;
	void emprunter(Abonne ab) throws EmpruntException;
	// retour document ou annulation réservation
	void retour() throws RetourException;
}
