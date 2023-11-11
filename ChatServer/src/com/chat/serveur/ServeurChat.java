package com.chat.serveur;

import com.chat.commun.net.Connexion;

import java.util.ArrayList;

/**
 * Cette classe étend (hérite) la classe abstraite Serveur et y ajoute le nécessaire pour que le
 * serveur soit un serveur de chat.
 *
 * @author Abdelmoumène Toudeft (Abdelmoumene.Toudeft@etsmtl.ca)
 * @version 1.0
 * @since 2023-09-15
 */
public class ServeurChat extends Serveur {

    /**
     * Crée un serveur de chat qui va écouter sur le port spécifié.
     *
     * @param port int Port d'écoute du serveur
     */
    public ServeurChat(int port) {
        super(port);
    }

    @Override
    public synchronized boolean ajouter(Connexion connexion) {
        String hist = this.historique();
        if ("".equals(hist)) {
            connexion.envoyer("OK");
        }
        else {
            connexion.envoyer("HIST " + hist);
        }
        return super.ajouter(connexion);
    }
    /**
     * Valide l'arrivée d'un nouveau client sur le serveur. Cette redéfinition
     * de la méthode héritée de Serveur vérifie si le nouveau client a envoyé
     * un alias composé uniquement des caractères a-z, A-Z, 0-9, - et _.
     *
     * @param connexion Connexion la connexion représentant le client
     * @return boolean true, si le client a validé correctement son arrivée, false, sinon
     */
    @Override
    protected boolean validerConnexion(Connexion connexion) {

        String texte = connexion.getAvailableText().trim();
        char c;
        int taille;
        boolean res = true;
        if ("".equals(texte)) {
            return false;
        }
        taille = texte.length();
        for (int i=0;i<taille;i++) {
            c = texte.charAt(i);
            if ((c<'a' || c>'z') && (c<'A' || c>'Z') && (c<'0' || c>'9')
                    && c!='_' && c!='-') {
                res = false;
                break;
            }
        }
        if (!res)
            return false;
        for (Connexion cnx:connectes) {
            if (texte.equalsIgnoreCase(cnx.getAlias())) { //alias déjà utilisé
                res = false;
                break;
            }
        }
        connexion.setAlias(texte);
        return true;
    }

    /**
     * Retourne la liste des alias des connectés au serveur dans une chaîne de caractères.
     *
     * @return String chaîne de caractères contenant la liste des alias des membres connectés sous la
     * forme alias1:alias2:alias3 ...
     */
    public String list() {
        String s = "";
        for (Connexion cnx:connectes)
            s+=cnx.getAlias()+":";
        return s;
    }
    /**
     * Retourne la liste des messages de l'historique de chat dans une chaîne
     * de caractères.
     *
     * @return String chaîne de caractères contenant la liste des alias des membres connectés sous la
     * forme message1\nmessage2\nmessage3 ...
     */
    public String historique() {
        String s = "";
        return s;
    }
}
