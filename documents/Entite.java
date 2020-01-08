package documents;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import contrat.Document;
import exceptions.EmpruntException;
import exceptions.RetourException;
import personnes.Abonne;
import tachesautomatiques.FinDeTempsEmprunt;
import tachesautomatiques.FinDeTempsReservation;

public abstract class Entite implements Document {

	protected static final int DEUXHEURES = 7200000;
	protected static int TempsEmprunt = 7;
	protected static int TempsMaxRetour = 14;
	protected static final double DEUXCHANCESURTROIS = 0.66;

	protected int numero;
	protected String titre;
	protected Abonne reserveur;
	protected Abonne emprunteur;
	protected TimerTask finTempsEmprunt;
	protected TimerTask finTempsReservation;
	
	public Entite(int numero, String titre) {
		this.numero = numero;
		this.titre = titre;
		this.reserveur = null;
		this.emprunteur = null;
		this.finTempsEmprunt = null;
		this.finTempsReservation = null;
	}

	@Override
	public int numero() {
		return this.numero;
	}

	@Override
	public void reserver(Abonne ab) throws EmpruntException {
		if (ab.estInterdit())
			throw new EmpruntException("Vous �tes interdit de Biblioth�que.");
		if (ab.equals(emprunteur))
			throw new EmpruntException("Vous �tes d�j� en possession du livre, vous ne pouvez pas le r�server !");
		if (reserveur != null)
			throw new EmpruntException("Le livre est r�serv� !");
		if (emprunteur != null)
			throw new EmpruntException("Le livre est emprunt�, vous ne pouvez pas le r�server!");
		this.reserveur = ab;
		ab.ajouterReservation(this);
		Timer t = new Timer();
		this.finTempsReservation = new FinDeTempsReservation(this);
		t.schedule(this.finTempsReservation, DEUXHEURES);
	}

	public void setReserveur(Abonne reserveur) {
		this.reserveur = reserveur;
	}

	public void setEmprunteur(Abonne emprunteur) {
		this.emprunteur = emprunteur;
	}

	@Override
	public void emprunter(Abonne ab) throws EmpruntException {
		if (ab.estInterdit())
			throw new EmpruntException("Vous �tes interdit de Biblioth�que.");
		if (ab.equals(emprunteur))
			throw new EmpruntException("Vous �tes d�j� en possession du livre, vous ne pouvez pas l'emprunter !");
		if (reserveur != null && !ab.equals(reserveur))
			throw new EmpruntException("Le livre est r�serv� !");
		if (emprunteur != null)
			throw new EmpruntException("Le livre est emprunt� !");
		this.emprunteur = ab;
		ab.ajouterEmprunt(this);
		Timer t = new Timer();
		this.finTempsEmprunt = new FinDeTempsEmprunt(emprunteur);
		t.schedule(this.finTempsEmprunt, this.dateRetourPlusDeuxSemaines());
		if (ab.equals(reserveur)) {
			ab.deleteReservation(this);
			this.finTempsReservation.cancel();
		}
	}

	@Override
	public void retour() throws RetourException {
		if (reserveur == null && emprunteur == null)
			throw new RetourException("Le livre n'est ni emprunt�, ni r�serv�, vous ne pouvez pas le retourner");
		if (reserveur != null)
			reserveur.deleteReservation(this);
		if (emprunteur != null) {
			emprunteur.deleteEmprunt(this);
			this.finTempsEmprunt.cancel();
		}
		double chanceDegradation = Math.random();
		System.out.println(chanceDegradation);
		if(chanceDegradation > DEUXCHANCESURTROIS) {
			emprunteur.sanctionner();
			reset();
			throw new RetourException("Le livre a bien �t� rendu mais vous �copez d'un mois d'interdiction � la biblioth�que pour d�gradation");
		}
		reset();
	}

	public boolean estDisponible() {
		return this.reserveur == null && this.emprunteur == null;
	}

	public boolean estEmprunte() {
		return this.emprunteur != null;
	}

	public boolean estEmpruntePar(Abonne a) {
		if (emprunteur != null)
			return emprunteur.equals(a);
		return false;
	}

	public boolean estReservePar(Abonne a) {
		if (reserveur != null)
			return reserveur.equals(a);
		return false;
	}

	private Date dateRetourPlusDeuxSemaines() {
		Calendar c = Calendar.getInstance();
		c.setTime(new Date());
		c.add(Calendar.DATE, TempsEmprunt + TempsMaxRetour); // arbitrairement le d�lais d'emprunt d'un livre est de 7 jours
		return c.getTime();
	}

	private void reset() {
		this.emprunteur = null;
		this.reserveur = null;
		this.finTempsEmprunt = null;
		this.finTempsReservation = null;
	}

}
