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
	
	public static List<Entite> getEntitesDisponibles() {
		List<Entite> livresDispo = new ArrayList<>();
		for (Entite l : entites) {
			if(l.estDisponible())
				livresDispo.add(l);
		}
			
		return livresDispo;
	}
	
	public static Abonne getAbonne(int numeroAb) {
		for (Abonne a : abonnes)
			if (a.getNumero() == numeroAb)
				return a;
		return null;
	}
	
	public static Entite getEntite(int numero) {
		for (Entite l : entites)
			if (l.numero() == numero)
				return l;
		return null;
	}
}
