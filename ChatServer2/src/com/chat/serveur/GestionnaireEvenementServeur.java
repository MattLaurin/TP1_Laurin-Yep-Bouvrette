package com.chat.serveur;

import com.chat.commun.evenement.Evenement;
import com.chat.commun.evenement.GestionnaireEvenement;
import com.chat.commun.net.Connexion;
import com.echecs.PartieEchecs;
import com.echecs.Position;

/**
 * Cette classe représente un gestionnaire d'événement d'un serveur. Lorsqu'un serveur reçoit un texte d'un client,
 * il crée un événement à partir du texte reçu et alerte ce gestionnaire qui réagit en gérant l'événement.
 *
 * @author Abdelmoumène Toudeft (Abdelmoumene.Toudeft@etsmtl.ca)
 * @version 1.0
 * @since 2023-09-01
 */
public class GestionnaireEvenementServeur implements GestionnaireEvenement {
    private Serveur serveur;

    /**
     * Construit un gestionnaire d'événements pour un serveur.
     *
     * @param serveur Serveur Le serveur pour lequel ce gestionnaire gère des événements
     */
    public GestionnaireEvenementServeur(Serveur serveur) {
        this.serveur = serveur;
    }

    /**
     * Méthode de gestion d'événements. Cette méthode contiendra le code qui gère les réponses obtenues d'un client.
     *
     * @param evenement L'événement à gérer.
     */
    @Override
    public void traiter(Evenement evenement) {
        Object source = evenement.getSource();
        Connexion cnx, cnx2;
        String msg, typeEvenement, aliasExpediteur, aliasDestinataire, str, s1, s2;
        String hote, invite;
        ServeurChat serveurChat = (ServeurChat) this.serveur;
        SalonPrive sp;
        PartieEchecs partie;
        Position pos1, pos2;

        if (source instanceof Connexion) {
            cnx = (Connexion) source;
            System.out.println("SERVEUR-Recu de "+cnx.getAlias()+" : " + evenement.getType() + " " + evenement.getArgument());
            typeEvenement = evenement.getType();
            switch (typeEvenement) {
    /******************* COMMANDES GÉNÉRALES *******************/
                case "EXIT": //Ferme la connexion avec le client qui a envoyé "EXIT":
                    cnx.envoyer("END");
                    aliasExpediteur = cnx.getAlias();
                    serveurChat.envoyerATousSauf("EXIT "+aliasExpediteur, aliasExpediteur);
                    serveurChat.enlever(cnx);
                    cnx.close();
                    break;
                case "LIST": //Envoie la liste des alias des personnes connectées :
                    cnx.envoyer("LIST " + serveurChat.list());
                    break;
    /******************* CHAT PUBLIC *******************/
                case "MSG": //Ajoute le message recu à l'historique du salon public
                    //et l'envoie à tous les connectés sauf l'expéditeur :
                    aliasExpediteur = cnx.getAlias();
                    msg = aliasExpediteur + ">>" + evenement.getArgument();
                    serveurChat.envoyerATousSauf("MSG "+msg, aliasExpediteur);
                    serveurChat.ajouterHistorique(msg);
                    break;
                //Ajoutez ici d’autres case pour gérer d’autres commandes.
    /******************* CHAT PRIVÉ *******************/
                case "JOIN" : //Recoit une invitation ou acceptation d'invitation
                    hote = cnx.getAlias();
                    invite = evenement.getArgument().trim();
                    if ("".equals(invite))  //invité non indiqué.
                        break;
                    cnx2 = serveurChat.getConnexionParAlias(invite);
                    if (cnx2==null) //l'invité n'est pas connecté.
                        break;
                    if (serveurChat.enPrive(hote,invite)) //déjà en chat privé.
                        break;
                    int result = serveurChat.traiterInvitation(hote,invite);
                    if (result==Invitation.ACCEPTEE) { //créer salon privé
                        serveurChat.ajouter(new SalonPrive(hote,invite));
                        serveurChat.supprimeInvitation(hote,invite);
                        cnx.envoyer("JOINOK "+invite);
                        cnx2.envoyer("JOINOK "+hote);
                    }
                    else if (result==Invitation.AJOUTEE) { //informer l'invité
                        cnx2.envoyer("JOIN "+hote);
                    }
                    break;
                case "DECLINE" :  //refuse une invitation à un chat ou à un jeu d'échecs
                    aliasExpediteur = cnx.getAlias();
                    aliasDestinataire = evenement.getArgument().trim();
                    if ("".equals(aliasDestinataire))  //destinataire non indiqué.
                        break;
                    cnx2 = serveurChat.getConnexionParAlias(aliasDestinataire);
                    if (cnx2==null) //le destinataire n'est pas connecté.
                        break;
                    if (serveurChat.supprimeInvitation(aliasDestinataire,aliasExpediteur)) {
                        cnx2.envoyer("DECLINE "+aliasExpediteur);
                    }
                    else if (serveurChat.supprimeInvitationEchecs(aliasDestinataire,aliasExpediteur))
                        cnx2.envoyer("DECLINE_CHESS "+aliasExpediteur);
                    break;
                case "INV" :  //envoyer liste des invitations reçues
                    cnx.envoyer("INV " + serveurChat.listInvitations(cnx.getAlias()));
                    break;
                case "PRV" :  //envoyer un message privé
                    msg = evenement.getArgument();
                    int i = msg.indexOf(' ');
                    if (i == -1) //message vide
                        break;
                    else {
                        aliasExpediteur = cnx.getAlias();
                        aliasDestinataire = msg.substring(0, i);
                        if (!serveurChat.enPrive(aliasExpediteur,aliasDestinataire))
                            break;
                        cnx2 = serveurChat.getConnexionParAlias(aliasDestinataire);
                        if (cnx2!=null)
                            cnx2.envoyer("PRV "+aliasExpediteur+" "+msg.substring(i).trim());
                    }
                    break;
                case "QUIT" : //quitter un chat privé
                    aliasExpediteur = cnx.getAlias();
                    aliasDestinataire = evenement.getArgument().trim();
                    if ("".equals(aliasDestinataire))  //hote non indiqué.
                        break;
                    if (serveurChat.supprimeSalonPrive(aliasExpediteur,aliasDestinataire)) {
                        cnx2 = serveurChat.getConnexionParAlias(aliasDestinataire);
                        if (cnx2==null) //l'hote n'est pas connecté.
                            break;
                        cnx.envoyer("QUIT "+aliasDestinataire);
                        cnx2.envoyer("QUIT "+aliasExpediteur);
                    }
                    break;
    /******************* JEU D'ÉCHECS EN RÉSEAU *******************/
                case "CHESS" : //Réception d'une invitation à jouer ou acceptation d'invitation
                    aliasExpediteur = cnx.getAlias();
                    aliasDestinataire = evenement.getArgument().trim();
                    if ("".equals(aliasDestinataire))  //hote non indiqué.
                        break;
                    //Vérifier que les 2 ne sont pas déjà dans une partie d'échecs:
                    if (serveurChat.getPartieDe(aliasExpediteur)!=null || serveurChat.getPartieDe(aliasDestinataire)!=null)
                        break;
                    if (serveurChat.enPrive(aliasExpediteur,aliasDestinataire)) {  //les 2 sont en privé
                        sp = serveurChat.getSalonPrive(aliasExpediteur, aliasDestinataire);  //on accède au salon privé
                        if (sp!=null) { //Le salon privé avec les 2 personnes existe
                            partie = sp.addJoueur(aliasExpediteur);
                            if (partie!=null) { //c'est le 2e joueur qui est ajouté:on démarre une partie
                                if (aliasExpediteur.equals(partie.getAliasJoueur1())) {
                                    cnx.envoyer("CHESSOK "+aliasDestinataire+" "+partie.getCouleurJoueur1());
                                    cnx2 = serveurChat.getConnexionParAlias(aliasDestinataire);
                                    cnx2.envoyer("CHESSOK "+aliasExpediteur+" "+partie.getCouleurJoueur2());
                                    sp.supprimeInvitationAuxEchecs();
                                }
                                else {
                                    cnx.envoyer("CHESSOK "+aliasDestinataire+" "+partie.getCouleurJoueur2());
                                    cnx2 = serveurChat.getConnexionParAlias(aliasDestinataire);
                                    cnx2.envoyer("CHESSOK "+aliasExpediteur+" "+partie.getCouleurJoueur1());
                                }
                            }
                            else {
                                cnx2 = serveurChat.getConnexionParAlias(aliasDestinataire);
                                cnx2.envoyer("CHESS "+aliasExpediteur);
                            }
                        }
                    }
                    break;
                case "MOVE" :
                    aliasExpediteur = cnx.getAlias();
                    partie = serveurChat.getPartieDe(aliasExpediteur);
                    if (partie!=null) {
                        aliasDestinataire = (partie.getAliasJoueur1().equals(aliasExpediteur))?
                                partie.getAliasJoueur2():partie.getAliasJoueur1();
                        str = evenement.getArgument().trim();
                        if (str.length()<4)
                            cnx.envoyer("INVALID mauvais format");
                        else {
                            s1 = str.substring(0,2);
                            s2 = str.substring(str.length()-2);
                            pos1 = new Position(s1.charAt(0), (byte)Character.getNumericValue(s1.charAt(1)));
                            pos2 = new Position(s2.charAt(0), (byte)Character.getNumericValue(s2.charAt(1)));
                            if (!partie.estLeTourDe(cnx.getAlias())) {
                                cnx.envoyer("INVALID Ce n'est pas votre tour");
                                break;
                            }
                            if (partie.deplace(pos1,pos2)) {
                                cnx.envoyer("MOVE "+s1+"-"+s2);
                                cnx2 = serveurChat.getConnexionParAlias(aliasDestinataire);
                                if (cnx2!=null) //être sûr que l'autre joueur n'est pas parti.
                                    cnx2.envoyer("MOVE "+s1+"-"+s2);
                                if (partie.echecEtMat(partie.getTour())) {
                                    cnx.envoyer("MAT "+cnx.getAlias());
                                    if (cnx2!=null)
                                        cnx2.envoyer("MAT "+cnx.getAlias());
                                }
                                System.out.println(partie);
                            }
                            else
                                cnx.envoyer("INVALID Déplacement non permis");
                        }
                    }
                    break;
                case "ABANDON":
                    aliasExpediteur = cnx.getAlias();
                    partie = serveurChat.getPartieDe(aliasExpediteur);
                    if (partie!=null) {
                        aliasDestinataire = (partie.getAliasJoueur1().equals(aliasExpediteur))?
                                partie.getAliasJoueur2():partie.getAliasJoueur1();
                        cnx2 = serveurChat.getConnexionParAlias(aliasDestinataire);
                        if (cnx2!=null) //être sûr que l'autre joueur n'est pas parti.
                            cnx2.envoyer("ABANDON "+aliasExpediteur);
                        cnx.envoyer("Vous avez abandonné la partie. "+aliasDestinataire+" a gagné.");
                        sp = serveurChat.getSalonPrive(aliasExpediteur,aliasDestinataire);
                        if (sp!=null)
                            sp.setPartieEchecs(null);//on détruit la partie d'échecs.
                    }
                    break;
    /******************* TRAITEMENT PAR DÉFAUT *******************/
                default: //Renvoyer le texte recu convertit en majuscules :
                    msg = (evenement.getType() + " " + evenement.getArgument()).toUpperCase();
                    cnx.envoyer(msg);
            }
        }
    }
}