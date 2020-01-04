package services;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import contrat.Document;
import documents.Entite;
import exceptions.EmpruntException;
import personnes.Abonne;
import personnes.Bibliotheque;

public class ServiceReservation implements Runnable {
	
	private final Socket client;
	
	public ServiceReservation(Socket socket) {
		this.client = socket;
	}

	@Override
	public void run() {
		String reponse = null;
		StringBuilder str = null;
		try {
			// lecteur du client : in
			BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
			// envoyeur du client : out
			PrintWriter out = new PrintWriter(client.getOutputStream(), true);

			// envoit liste Abonnes
			str = new StringBuilder("Liste des Abonnés : ");
			for (Abonne abonne : Bibliotheque.getAbonnes()) {
				str.append(" " + abonne.toString());
			}
			out.println(str);
			
			// recuperation du client
			int numeroAb = Integer.parseInt(in.readLine());
			Abonne currentAbonne = Bibliotheque.getAbonne(numeroAb);
			
			// envoit des livres disponnibles au client
			str = new StringBuilder("Documents disponibles : ");
			List<Entite> livresDisponibles = Bibliotheque.getEntitesDisponibles();
			for (Entite l : livresDisponibles) {
				str.append(" " + l.toString());
			}
			out.println(str);

			// recuperation du choix du livre
			int noCours = Integer.parseInt(in.readLine());
			Document livre = Bibliotheque.getEntite(noCours);

			if (livre != null) {
				synchronized (livre) {
					try {
						livre.reserver(currentAbonne);
						reponse = "Reservation du document: " + livre.toString() + " réussi!";
					} catch (EmpruntException e) {
						reponse = e.toString();
					} finally {
						out.println(reponse);
					}
				}
			} else {
				System.out.println("Aucun document ne correspond");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		try {
			client.close();
		} catch (IOException e2) {
			e2.printStackTrace();
		}

	}
	
	protected void finalize() throws Throwable {
		client.close();
	}

}
