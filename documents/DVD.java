package documents;

import exceptions.EmpruntException;
import personnes.Abonne;

public class DVD extends Entite {

	private int age;

	public DVD(int numero, String titre, int age) {
		super(numero, titre);
		this.age = age;
	}

	public DVD(int numero, String titre) {
		super(numero, titre);
		this.age = 0;
	}

	@Override
	public void reserver(Abonne ab) throws EmpruntException {
		if (ab.getAge() < age)
			throw new EmpruntException("Vous n'avez pas l'age pour reserver ce DVD !");
		super.reserver(ab);
	}

	@Override
	public void emprunter(Abonne ab) throws EmpruntException {
		if (ab.getAge() < age)
			throw new EmpruntException("Vous n'avez pas l'age pour emprunter ce DVD !");
		super.emprunter(ab);
	}

	@Override
	public String toString() {
		return "[ Titre : " + titre + " numero : " + numero + " Age minimum : " + age + " ]";
	}
}
