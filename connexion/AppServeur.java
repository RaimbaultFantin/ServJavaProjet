package connexion;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import documents.DVD;
import documents.Entite;
import documents.Livre;
import personnes.Abonne;
import personnes.Bibliotheque;
import serveurs.ServeurEmprunt;
import serveurs.ServeurReservation;
import serveurs.ServeurRetour;
import services.ServiceReservation;

public class AppServeur {

	public static void main(String[] args) {
		
		new Bibliotheque(initEntite(), initAbonne());
		final int PORTRESERVATION = 2500;
		final int PORTEMPRUNT = 2600;
		final int PORTRETOUR = 2700;
		
		try {
			new Thread(new ServeurReservation(PORTRESERVATION)).start();
			System.out.println("Serveur lance sur le port " + PORTRESERVATION);
			new Thread(new ServeurEmprunt(PORTEMPRUNT)).start();
			System.out.println("Serveur lance sur le port " + PORTEMPRUNT);
			new Thread(new ServeurRetour(PORTRETOUR)).start();
			System.out.println("Serveur lance sur le port " + PORTRETOUR);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private static List<Entite> initEntite() {
		List<Entite> livres = new ArrayList<>();
		livres.add(new Livre(1, "Harry Potter"));
		livres.add(new Livre(2, "Petit Prince"));
		livres.add(new Livre(3, "Dofus mag edition 4"));
		livres.add(new DVD(4, "Conjuring", 16));
		livres.add(new DVD(4, "007", 12));
		livres.add(new DVD(5, "Dofus le Film"));
		return livres;
	}

	private static List<Abonne> initAbonne() {
		List<Abonne> abonnes = new ArrayList<Abonne>();
		abonnes.add(new Abonne(1, "Fantin",18));
		abonnes.add(new Abonne(2, "Oliwier",14));
		abonnes.add(new Abonne(3, "Mariam",11));
		return abonnes;
	}

}
