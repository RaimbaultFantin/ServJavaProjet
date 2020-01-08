package personnes;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import documents.Entite;
import tachesautomatiques.FinDeTempsReservation;
import tachesautomatiques.FinInterdictionDeBibliotheque;

public class Abonne {
	private final long UNMOIS = 262000000;
	
	private int numero;
	private String prenom;
	private int age;
	private List<Entite> documentsReserves;
	private List<Entite> documentsEmpruntes;
	private boolean estInterdit;
	private TimerTask interdictionBibliotheque;
	
	public Abonne(int numero, String prenom, int age) {
		this.numero = numero;
		this.prenom = prenom;
		this.age = age;
		this.documentsEmpruntes = new ArrayList<Entite>();
		this.documentsReserves = new ArrayList<Entite>();
		this.estInterdit = false;
		this.interdictionBibliotheque = null;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Abonne other = (Abonne) obj;
		if (numero != other.numero)
			return false;
		return true;
	}

	public int getNumero() {
		return this.numero;
	}
	
	public List getDocumentsReserves() {
		return this.documentsReserves;
	}
	
	public List getDocumentsEmpruntes() {
		return this.documentsEmpruntes;
	}

	public int getAge() {
		return age;
	}

	public void ajouterEmprunt(Entite livre) {
		this.documentsEmpruntes.add(livre);
	}

	public void setEstInterdit(boolean estInterdit) {
		this.estInterdit = estInterdit;
	}

	public void ajouterReservation(Entite entite) {
		this.documentsReserves.add(entite);
	}

	public void setInterdictionBibliotheque(TimerTask interdictionBibliotheque) {
		this.interdictionBibliotheque = interdictionBibliotheque;
	}

	public void deleteReservation(Entite entite) {
		this.documentsReserves.remove(entite);
	}
	
	public void deleteEmprunt(Entite entite) {
		this.documentsEmpruntes.remove(entite);
	}
	
	public void sanctionner() {
		if(this.interdictionBibliotheque != null)
			this.interdictionBibliotheque.cancel();
		this.estInterdit = true;
		Calendar c = Calendar.getInstance();
		c.setTime(new Date());
		c.add(Calendar.MONTH, 1);
		Date currentDatePlusOneMonth = c.getTime();
		Timer t = new Timer();
		this.interdictionBibliotheque = new FinInterdictionDeBibliotheque(this);
		t.schedule(this.interdictionBibliotheque, currentDatePlusOneMonth);
	}
	
	public boolean estInterdit() {
		return estInterdit;
	}
	
	@Override
	public String toString() {
		return "Prénom : " + prenom + " numéro : " + numero;
	}
	
}
