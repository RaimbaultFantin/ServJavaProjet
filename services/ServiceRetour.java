package services;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;

import contrat.Document;
import documents.Entite;
import exceptions.EmpruntException;
import exceptions.RetourException;
import personnes.Abonne;
import personnes.Bibliotheque;

public class ServiceRetour implements Runnable {

	private final Socket client;

	public ServiceRetour(Socket socket) {
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

			// envoit des livres que possède le client
			str = new StringBuilder("Documents que vous possédez : ");
			str.append(currentAbonne.getDocumentsEmpruntes());
			str.append(" Documents que vous avez réservez : " + currentAbonne.getDocumentsReserves());
			out.println(str);

			// recuperation du choix du livre
			int noCours = Integer.parseInt(in.readLine());
			Entite entite = Bibliotheque.getEntite(noCours);

			if (entite != null) {
				if (entite.estEmpruntePar(currentAbonne) || entite.estReservePar(currentAbonne)) {
					synchronized (entite) {
						try {
							entite.retour();
							reponse = "Le " + entite.getClass().getSimpleName()
									+ " a bien été rendu à la Bibliothèque !";
						} catch (RetourException e) {
							reponse = e.toString();
						} finally {
							out.println(reponse);
						}
					}
				} else {
					reponse = "Vous ne pouvez pas rendre un " + entite.getClass().getSimpleName()
							+ " que vous n'avez ni réservé ni emprunté";
					out.println(reponse);
				}
			} else {
				reponse = "Aucun document ne correspond";
				out.println(reponse);
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
}
