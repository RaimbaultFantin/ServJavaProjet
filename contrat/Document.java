package contrat;

import exceptions.EmpruntException;
import exceptions.RetourException;
import personnes.Abonne;

public interface Document {
	
	/**
	 * 
	 * @return identifiant du livre
	 */
	int numero();
	
	/**
	 * 
	 * @param ab
	 * @throws EmpruntException
	 */
	void reserver(Abonne ab) throws EmpruntException ;
	
	/**
	 * 
	 * @param ab
	 * @throws EmpruntException
	 */
	void emprunter(Abonne ab) throws EmpruntException;
	
	/**
	 * 
	 * @throws RetourException
	 */
	void retour() throws RetourException;
}
