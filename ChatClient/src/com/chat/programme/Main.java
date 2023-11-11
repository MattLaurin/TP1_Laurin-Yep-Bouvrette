package com.chat.programme;

import com.chat.client.Client;
import com.chat.client.ClientChat;

import java.util.Scanner;

/**
 * Programme simple de démonstration d'un client. Le programme utilise un
 * client pour se connecter à un serveur et lui envoie les textes/commandes
 * saisis par l'utilisateur jusqu'à ce que celui-ci saisisse le texte EXIT.
 *
 * @author Abdelmoumène Toudeft (Abdelmoumene.Toudeft@etsmtl.ca)
 * @version 1.0
 * @since 2023-09-01
 */
public class Main {

	/**
	 * Méthode principale du programme.
	 *
	 * @param args Arguments du programme
	 */
	public static void main(String[] args) {

		Scanner clavier = new Scanner(System.in);
		Client client = new ClientChat();
		String saisie;

		if (!client.connecter()) {
			System.out.println("Serveur introuvable a l'adresse " + client.getAdrServeur()
					+ " sur le port " + client.getPortServeur());
			return;
		}
		System.out.println("Vous etes connectes au serveur à l'adresse " + client.getAdrServeur()
				+ " sur le port " + client.getPortServeur());

		System.out.println("Saisissez vos textes (EXIT pour quitter) :");
		do {
			saisie = clavier.nextLine();
			client.envoyer(saisie);
		}while (!"EXIT".equals(saisie));
		//Lorsque le client envoie "EXIT", le serveur répond "END." et le gestionnaire d'événement déconnecte le client

		//client.deconnecter();
	}
}
