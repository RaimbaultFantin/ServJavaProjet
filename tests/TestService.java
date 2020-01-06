package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.junit.jupiter.api.Test;

import documents.Entite;
import documents.Livre;
import exceptions.EmpruntException;
import exceptions.RetourException;
import personnes.Abonne;

class TestService {

	@Test
	void test() {
		Entite livre = new Livre(1, "Harry Pooter");
		Abonne ab = new Abonne(1, "Fantin", 18);
		try {
			livre.emprunter(ab);
		} catch (EmpruntException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
