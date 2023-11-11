package com.chat.programme;

import com.chat.serveur.Config;
import com.chat.serveur.ServeurChat;
import com.chat.serveur.Serveur;

import java.util.Scanner;

/**
 * Programme simple de démonstration d'un serveur. Le programme démarre un serveur qui se met à écouter
 * l'arrivée de connexions.
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
        String saisie;

        Serveur serveur = new ServeurChat(Config.PORT_SERVEUR);
        if (serveur.demarrer()) {
            System.out.println("Serveur a l'ecoute sur le port " + serveur.getPort());
        }

        System.out.println("Saisissez EXIT pour arreter le serveur.");
        saisie = clavier.nextLine();
        while (!"EXIT".equals(saisie)) {
            System.out.println("??? Saisissez EXIT pour arreter le serveur.");
            saisie = clavier.nextLine();
        }
        serveur.arreter();
    }
}