package tachesautomatiques;

import java.util.TimerTask;

import personnes.Abonne;

public class InterdictionDeBibliotheque extends TimerTask {

	private Abonne ab;

	public InterdictionDeBibliotheque(Abonne ab) {
		this.ab = ab;
	}

	@Override
	public void run() {
		ab.setEstInterdit(false);
	}

}
