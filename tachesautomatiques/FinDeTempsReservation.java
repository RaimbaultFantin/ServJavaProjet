package tachesautomatiques;

import java.util.TimerTask;

import documents.Entite;
import documents.Livre;
import exceptions.RetourException;

public class FinDeTempsReservation extends TimerTask {

	private Entite entite;

	/**
	 * 
	 * @param entite
	 */
	public FinDeTempsReservation(Entite entite) {
		this.entite = entite;
	}

	@Override
	public void run() {
		try {
			if (!entite.estEmprunte()) {
				entite.retour();
				System.err.println(
						"Vous avez mis trop de temps à aller chercher votre " + entite.getClass().getSimpleName());
			}
		} catch (RetourException e) {
			e.printStackTrace();
		}
	}

}
