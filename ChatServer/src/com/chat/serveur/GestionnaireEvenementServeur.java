package com.chat.serveur;

import com.chat.commun.evenement.Evenement;
import com.chat.commun.evenement.GestionnaireEvenement;
import com.chat.commun.net.Connexion;

/**
 * Cette classe repr�sente un gestionnaire d'�v�nement d'un serveur. Lorsqu'un serveur re�oit un texte d'un client,
 * il cr�e un �v�nement � partir du texte re�u et alerte ce gestionnaire qui r�agit en g�rant l'�v�nement.
 *
 * @author Abdelmoum�ne Toudeft (Abdelmoumene.Toudeft@etsmtl.ca)
 * @version 1.0
 * @since 2023-09-01
 */
public class GestionnaireEvenementServeur implements GestionnaireEvenement {
    private Serveur serveur;

    /**
     * Construit un gestionnaire d'�v�nements pour un serveur.
     *
     * @param serveur Serveur Le serveur pour lequel ce gestionnaire g�re des �v�nements
     */
    public GestionnaireEvenementServeur(Serveur serveur) {
        this.serveur = serveur;
    }

    /**
     * M�thode de gestion d'�v�nements. Cette m�thode contiendra le code qui g�re les r�ponses obtenues d'un client.
     *
     * @param evenement L'�v�nement � g�rer.
     */

    @Override
    public void traiter(Evenement evenement) {
        Object source = evenement.getSource();
        Connexion cnx;
        String msg, typeEvenement, aliasExpediteur;
        ServeurChat serveur = (ServeurChat) this.serveur;

        if (source instanceof Connexion) {
            cnx = (Connexion) source;
            System.out.println("SERVEUR-Recu : " + evenement.getType() + " " + evenement.getArgument());
            typeEvenement = evenement.getType();
            switch (typeEvenement) {
                case "EXIT": //Ferme la connexion avec le client qui a envoy� "EXIT":
                    cnx.envoyer("END");
                    serveur.enlever(cnx);
                    cnx.close();
                    break;
                case "LIST": //Envoie la liste des alias des personnes connect�es :
                    cnx.envoyer("LIST " + serveur.list());
                    break;

                //Ajoutez ici d�autres case pour g�rer d�autres commandes.
                case "MSG":
                    cnx.envoyer("MSG" + " ");
                    msg= (evenement.getArgument());
                    serveur.envoyerATousSauf(msg, cnx.getAlias());
                    break;


                case "JOIN":
                   String hote = cnx.getAlias();
                   String invite = evenement.getArgument();
                   serveur.commandeJoin(hote,invite);
                   break;
                case "DECLINE":
                    hote = cnx.getAlias();
                    invite = evenement.getArgument();
                    serveur.commandeDecline(hote,invite);
                    break;

                case "INV":
                    invite = cnx.getAlias();
                    serveur.commandeINV(invite);
                    break;
                case "PRV":
                    String[] mess = evenement.getArgument().split(" ",2); // Marche seulement si l'utilisateur met un espace entre chaque 'element'

                    String alias1 = cnx.getAlias();
                    String alias2 = mess[0];
                    msg = mess[1];
                    serveur.commandePRV(alias1,alias2,msg);
                    break;
                case "QUIT":
                    alias1 = cnx.getAlias();
                    alias2 = evenement.getArgument();
                    serveur.commandeQuit(alias1,alias2);
                    break;
                case "CHESS":
                    alias1 = cnx.getAlias();
                    alias2 = evenement.getArgument();
                    serveur.commandeChess(alias1, alias2);
                    break;
                default: //Renvoyer le texte recu convertit en majuscules :
                    msg = (evenement.getType() + " " + evenement.getArgument()).toUpperCase();
                    cnx.envoyer(msg);
            }
        }
    }
}