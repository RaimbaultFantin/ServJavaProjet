package personnes;

import java.util.ArrayList;
import java.util.List;
import documents.Entite;

public class Bibliotheque {
	private static List<Entite> entites;
	private static List<Abonne> abonnes;
	
	public Bibliotheque(List<Entite> entites, List<Abonne> abonnes) {
		this.entites = entites;
		this.abonnes = abonnes;
	}
	
	public static List<Abonne> getAbonnes() {
		return abonnes;
	}
	
	/**
	 * 
	 * @return liste entite d'une bibliothèque
	 */
	public static List<Entite> getEntitesDisponibles() {
		List<Entite> livresDispo = new ArrayList<>();
		for (Entite l : entites) {
			if(l.estDisponible())
				livresDispo.add(l);
		}
			
		return livresDispo;
	}
	
	/**
	 * 
	 * @param numeroAb
	 * @return abonne ou null si id invalide
	 */
	public static Abonne getAbonne(int numeroAb) {
		for (Abonne a : abonnes)
			if (a.getNumero() == numeroAb)
				return a;
		return null;
	}
	
	/**
	 * 
	 * @param numero
	 * @return entite ou null si id invalide
	 */
	public static Entite getEntite(int numero) {
		for (Entite l : entites)
			if (l.numero() == numero)
				return l;
		return null;
	}
}
