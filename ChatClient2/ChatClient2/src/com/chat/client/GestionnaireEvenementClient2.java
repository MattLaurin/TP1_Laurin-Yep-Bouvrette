package com.chat.client;

import com.chat.commun.evenement.Evenement;
import com.chat.commun.evenement.GestionnaireEvenement;
import com.chat.commun.net.Connexion;
import com.chat.echecs.EtatPartieEchecs;
import com.chat.programme.MainFrame;
import controleur.EcouteurJeuEchecs;
import vue.FenetreEchecs;
import vue.PanneauEchiquier;
import vue.PanneauPrincipal;

import javax.swing.*;

public class GestionnaireEvenementClient2 implements GestionnaireEvenement {
    private ClientChat client;
    private PanneauPrincipal panneauPrincipal;

    /**
     * Construit un gestionnaire d'événements pour un client.
     *
     * @param client Client Le client pour lequel ce gestionnaire gère des événements
     */
    public GestionnaireEvenementClient2(ClientChat client, PanneauPrincipal panneauPrincipal) {

        this.client = client;
        this.panneauPrincipal = panneauPrincipal;
        this.client.setGestionnaireEvenementClient(this);
    }
    @Override
    public void traiter(Evenement evenement) {
        Object source = evenement.getSource();
        Connexion cnx;
        String typeEvenement, arg, str, alias;
        int i;
        String[] membres, invAlias;
        EtatPartieEchecs etat;
        MainFrame fenetre;
        FenetreEchecs fenetreEchecs = null;

        if (source instanceof Connexion) {
            cnx = (Connexion) source;
            typeEvenement = evenement.getType();
            switch (typeEvenement) {
                /******************* COMMANDES GÉNÉRALES *******************/
                case "END": //Le serveur demande de fermer la connexion
                    client.deconnecter(); //On ferme la connexion
                    panneauPrincipal.setVisible(false);
                    System.out.println("Vider panneau principal");
                    panneauPrincipal.vider();
                    break;
                case "WAIT_FOR": //Le serveur demande de fermer la connexion
                    boolean termine = false;
                    while (!termine) {
                        fenetre = (MainFrame)panneauPrincipal.getTopLevelAncestor();
                        alias = JOptionPane.showInputDialog(fenetre,"Votre alias :");
                        if (alias != null) {
                            if (!"".equals(alias.trim())) {
                                client.envoyer(alias);
                                client.setAlias(alias);
                                termine = true;
                            }
                        } else {
                            client.deconnecter();
                            termine = true;
                        }
                    }
                    break;
                case "NEW": //Un client s'est connecté
                    alias = evenement.getArgument();
                    panneauPrincipal.ajouterConnectes(alias);
                    break;
                case "EXIT": //Un client s'est déconnecté
                    alias = evenement.getArgument();
                    System.out.println(alias+" a quitté le serveur");
                    panneauPrincipal.retirerConnecte(alias);
                    break;
                case "LIST": //Le serveur a renvoyé la liste des connectés
                    arg = evenement.getArgument();
                    panneauPrincipal.ajouterConnectes(arg.trim());
                    break;
                /******************* CHAT PUBLIC *******************/
                case "HIST": //Le serveur a renvoyé l'historique des messages du chat public
                    panneauPrincipal.setVisible(true);
                    fenetre = (MainFrame)panneauPrincipal.getTopLevelAncestor();
                    fenetre.setTitle(MainFrame.TITRE+" - "+client.getAlias());
                    cnx.envoyer("LIST");
                    arg = evenement.getArgument();
                    panneauPrincipal.ajouterMessage(arg);
                    break;
                case "OK":
                    panneauPrincipal.setVisible(true);
                    fenetre = (MainFrame)panneauPrincipal.getTopLevelAncestor();
                    fenetre.setTitle(MainFrame.TITRE+" - "+client.getAlias());
                    cnx.envoyer("LIST");
                    break;
                case "MSG":
                    panneauPrincipal.ajouterMessage(evenement.getArgument());
                    break;
                /******************* CHAT PRIVÉ *******************/
                case "JOIN":
                    alias = evenement.getArgument();
                    panneauPrincipal.ajouterInvitationRecue(alias);
                    break;
                case "JOINOK":
                    arg = evenement.getArgument();
                    panneauPrincipal.creerFenetreSalonPrive(arg);
                    System.out.println(arg + " Vous êtes en chat privé avec "+arg+" (PRV alias msg pour lui envoyer " +
                            "un message en privé)");
                    break;
                case "PRV":
                    arg = evenement.getArgument();
                    i = arg.indexOf(' ');
                    if (i == -1) //message vide. Ne devrait pas arriver.
                        break;
                    else {
                        alias = arg.substring(0, i);
                        String msg = arg.substring(i).trim();
                        panneauPrincipal.ajouterMessagePrive(alias,msg);
                    }
                    break;
                case "DECLINE":
                    arg = evenement.getArgument();
                    panneauPrincipal.retirerInvitationRecue(arg);
                    break;
                case "INV": //Le serveur a renvoyé la liste des invitations reçues
                    arg = evenement.getArgument();
                    invAlias = arg.split(":");
                    System.out.println("\t\tInvitations reçues :");
                    for (String s:invAlias)
                        System.out.println("\t\t\t- "+s);
                    break;
                case "QUIT" :
                    arg = evenement.getArgument();
                    panneauPrincipal.retirerSalonPrive(arg);
                    System.out.println(arg +" a quitté le salon privé.");
                    break;
                /******************* JEU D'ÉCHECS EN RÉSEAU *******************/
                case "CHESS":
                    alias = evenement.getArgument();
                    panneauPrincipal.inviteEchecs(alias);
                    break;
                case "DECLINE_CHESS":
                    alias = evenement.getArgument();
                    panneauPrincipal.annuleInviteEchecs(alias);
                    break;
                case "CHESSOK":
                    arg = evenement.getArgument();
                    str = arg.substring(arg.indexOf(" ")+1);
                    arg = arg.substring(0,arg.indexOf(" "));
                    client.nouvellePartie();
                    System.out.println("Partie d'échecs démarrée avec "+arg+". Votre couleur est : "+str);
                    System.out.println(client.getEtatPartieEchecs());
                    PanneauEchiquier panneauEchiquier = new PanneauEchiquier(client.getEtatPartieEchecs());
                    //à compléter

                    panneauPrincipal.setFenetreEchecs(arg,fenetreEchecs);
                    break;
                case "INVALID":
                    System.out.println(evenement.getArgument());
                    fenetreEchecs=panneauPrincipal.getFenetreEchecs();
                    JOptionPane.showMessageDialog(fenetreEchecs,evenement.getArgument());
                    break;
                case "MOVE":
                    etat = ((ClientChat)client).getEtatPartieEchecs();
                    arg = evenement.getArgument();
                    if (etat.move(arg)) {
                        System.out.println(etat);
                    }
                    break;
                case "ABANDON":
                    arg = evenement.getArgument();
                    fenetre = (MainFrame)panneauPrincipal.getTopLevelAncestor();
                    JOptionPane.showMessageDialog(fenetre,arg+" a abandonné la partie d'échecs");
                    System.out.println(evenement.getArgument());
                    ((ClientChat)client).setEtatPartieEchecs(null);
                    //On détruit la fenêtre de jeu d'échecs :
                    panneauPrincipal.setFenetreEchecs(arg,null);
                    break;
                /******************* TRAITEMENT PAR DÉFAUT *******************/
                default:
                    System.out.println("RECU : "+evenement.getType()+" "+evenement.getArgument());
            }
        }
    }
}