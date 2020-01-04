package documents;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;

import contrat.Disponibilite;
import contrat.Document;
import exceptions.EmpruntException;
import exceptions.RetourException;
import personnes.Abonne;
import tachesautomatiques.FinDeTempsReservation;

public abstract class Entite implements Document, Disponibilite {
	
	private static final int DeuxHeures = 7200000;
	protected static int TempsEmprunt = 7;
	protected static int TempsMaxRetour = 14;
	
	protected int numero;
	protected String titre;
	protected Abonne reserveur;
	protected Abonne emprunteur;
	protected Date dateretour;
	
	public Entite(int numero, String titre) {
		this.numero=numero;
		this.titre=titre;
		this.reserveur = null;
		this.emprunteur = null;
		this.dateretour = null;
	}

	@Override
	public int numero() {
		return this.numero;
	}

	@Override
	public void reserver(Abonne ab) throws EmpruntException {
		if (ab.equals(emprunteur))
			throw new EmpruntException("Vous êtes déjà en possession du livre, vous ne pouvez pas le réserver !");
		if (reserveur != null)
			throw new EmpruntException("Le livre est réservé !");
		if (emprunteur != null)
			throw new EmpruntException("Le livre est emprunté, vous ne pouvez pas le réserver!");
		this.reserveur = ab;
		ab.ajouterReservation(this);
		Timer t = new Timer();
		t.schedule(new FinDeTempsReservation(this), DeuxHeures);
	}

	public void setReserveur(Abonne reserveur) {
		this.reserveur = reserveur;
	}

	public void setEmprunteur(Abonne emprunteur) {
		this.emprunteur = emprunteur;
	}

	@Override
	public void emprunter(Abonne ab) throws EmpruntException {
		if (ab.equals(emprunteur))
			throw new EmpruntException("Vous êtes déjà en possession du livre, vous ne pouvez pas l'emprunter !");
		if (reserveur != null && !ab.equals(reserveur))
			throw new EmpruntException("Le livre est réservé !");
		if (emprunteur != null)
			throw new EmpruntException("Le livre est emprunté !");
		this.emprunteur = ab;
		ab.ajouterEmprunt(this);
		this.dateretour = creerDateRetour();
		if(ab.equals(reserveur))
			ab.deleteReservation(this);
	}

	@Override
	public void retour() throws RetourException {
		if (reserveur == null && emprunteur == null)
			throw new RetourException("Le livre n'est ni emprunté, ni retourné, vous ne pouvez pas le retourner");
		reset();
		if(reserveur != null)
			reserveur.deleteReservation(this);
		if(emprunteur != null) {
			emprunteur.deleteEmprunt(this);
			Calendar c = Calendar.getInstance();
			c.setTime(dateretour);
			c.add(Calendar.DATE, TempsMaxRetour);
			Date currentDatePlusTwoWeeks = c.getTime();
			if(currentDatePlusTwoWeeks.before(new Date()));
				emprunteur.sanctionner();
		}
	}
	
	@Override
	public boolean estDisponible() {
		return this.reserveur == null && this.emprunteur == null;
	}
	
	public boolean estEmprunte() {
		return this.emprunteur != null;
	}
	
	public boolean estEmpruntePar(Abonne a) {
		if(emprunteur != null)
			return emprunteur.equals(a);
		return false;
	}
	
	public boolean estReservePar(Abonne a) {
		if(reserveur != null)
			return reserveur.equals(a);
		return false;
	}
	
	private static Date creerDateRetour() {
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");  
	    Date currentDate = new Date();
	    Calendar c = Calendar.getInstance();
        c.setTime(currentDate);
        c.add(Calendar.DATE, TempsEmprunt);
        Date currentDatePlusOne = c.getTime();
        return currentDatePlusOne;
	}
	
	private void reset() {
		this.emprunteur = null;
		this.reserveur = null;
	}
	
}
