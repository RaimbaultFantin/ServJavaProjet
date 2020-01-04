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
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");  
	    Date currentDate = new Date();  
	    Calendar c = Calendar.getInstance();
        c.setTime(currentDate);
        c.add(Calendar.DATE, 14);
        Date currentDatePlusOne = c.getTime();
        if(currentDate.before(currentDatePlusOne))
        	System.out.println("hello");
	}

}
