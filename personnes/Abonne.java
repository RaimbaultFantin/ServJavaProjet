package personnes;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Timer;

import documents.Entite;
import tachesautomatiques.FinDeTempsReservation;
import tachesautomatiques.InterdictionDeBibliotheque;

public class Abonne {
	private final long UNMOIS = 262000000;
	
	private int numero;
	private String prenom;
	private int age;
	private List<Entite> documentsReserves;
	private List<Entite> documentsEmpruntes;
	private boolean estInterdit;
	
	public Abonne(int numero, String prenom, int age) {
		this.numero = numero;
		this.prenom = prenom;
		this.age = age;
		this.documentsEmpruntes = new ArrayList<Entite>();
		this.documentsReserves = new ArrayList<Entite>();
		this.estInterdit = false;
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
	
	public void deleteReservation(Entite entite) {
		this.documentsReserves.remove(entite);
	}
	
	public void deleteEmprunt(Entite entite) {
		this.documentsEmpruntes.remove(entite);
	}
	
	public void sanctionner() {
		this.estInterdit = true;
		Calendar c = Calendar.getInstance();
		c.setTime(new Date());
		c.add(Calendar.MONTH, 1);
		Date currentDatePlusOneMonth = c.getTime();
		Timer t = new Timer();
		t.schedule(new InterdictionDeBibliotheque(this), currentDatePlusOneMonth);
	}
	
	@Override
	public String toString() {
		return "Prénom : " + prenom + " numéro : " + numero;
	}
	
}
