package com.chat.serveur;

import com.chat.serveur.Serveur;

/**
 * Cette classe permet de créer des threads capables d'écouter continuellement sur un objet de type Serveur
 * l'arrivée de nouveaux clients.
 *
 * @author Abdelmoumène Toudeft (Abdelmoumene.Toudeft@etsmtl.ca)
 * @version 1.0
 * @since 2023-09-01
 */
public class ThreadEcouteurDeConnexions extends Thread {

    Serveur serveur;

    /**
     * Construit un thread sur un serveur.
     *
     * @param s Serveur Le serveur sur lequel le thread va écouter.
     */
    public ThreadEcouteurDeConnexions(Serveur s) {
        serveur = s;
    }

    /**
     * Méthode principale du thread. Cette méthode appelle continuellement la méthode attendConnexion() du serveur.
     */
    public void run() {
        while (!interrupted()) {
            serveur.attendConnexion();
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                break;
            }
        }
    }
}
