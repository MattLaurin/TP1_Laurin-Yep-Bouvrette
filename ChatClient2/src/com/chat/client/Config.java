package com.chat.client;

/**
 * Informations sur un serveur, utilisées par défaut par un client.
 */
public interface Config {
    /**
     * Adresse IP du serveur.
     */
    String ADRESSE_SERVEUR = "127.0.0.1";
    /**
     * Port d'écoute du serveur.
     */
    int PORT_SERVEUR = 8888;
}
