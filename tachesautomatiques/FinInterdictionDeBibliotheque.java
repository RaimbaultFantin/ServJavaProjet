package tachesautomatiques;

import java.util.TimerTask;

import personnes.Abonne;

public class FinInterdictionDeBibliotheque extends TimerTask {

	private Abonne ab;

	public FinInterdictionDeBibliotheque(Abonne ab) {
		this.ab = ab;
	}

	@Override
	public void run() {
		ab.setEstInterdit(false);
		ab.setInterdictionBibliotheque(null);
	}

}
