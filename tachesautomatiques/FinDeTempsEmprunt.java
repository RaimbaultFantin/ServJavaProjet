package tachesautomatiques;

import java.util.TimerTask;

import personnes.Abonne;

public class FinDeTempsEmprunt extends TimerTask {
	
	private Abonne ab;
	
	public FinDeTempsEmprunt(Abonne ab) {
		this.ab=ab;
	}

	@Override
	public void run() {
		ab.sanctionner();
	}

}
