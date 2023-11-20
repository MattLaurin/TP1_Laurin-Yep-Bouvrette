package com.chat.client;

import com.chat.commun.evenement.Evenement;
import com.chat.commun.evenement.GestionnaireEvenement;
import com.chat.commun.net.Connexion;

/**
 * Cette classe représente un gestionnaire d'événement d'un client. Lorsqu'un client reçoit un texte d'un serveur,
 * il crée un événement à partir du texte reçu et alerte ce gestionnaire qui réagit en gérant l'événement.
 *
 * @author Abdelmoumène Toudeft (Abdelmoumene.Toudeft@etsmtl.ca)
 * @version 1.0
 * @since 2023-09-01
 */
public class GestionnaireEvenementClient implements GestionnaireEvenement {
    private Client client;

    /**
     * Construit un gestionnaire d'événements pour un client.
     *
     * @param client Client Le client pour lequel ce gestionnaire gère des événements
     */
    public GestionnaireEvenementClient(Client client) {
        this.client = client;
    }
    /**
     * Méthode de gestion d'événements. Cette méthode contiendra le code qui gère les réponses obtenues d'un serveur.
     *
     * @param evenement L'événement à gérer.
     */
    @Override
    public void traiter(Evenement evenement) {
        Object source = evenement.getSource();
        Connexion cnx;
        String typeEvenement, arg;
        String[] membres;

        if (source instanceof Connexion) {
            cnx = (Connexion) source;
            typeEvenement = evenement.getType();
            switch (typeEvenement) {
                case "END" : //Le serveur demande de fermer la connexion
                    client.deconnecter(); //On ferme la connexion
                    break;
                case "LIST" : //Le serveur a renvoyé la liste des connectés
                    arg = evenement.getArgument();
                    membres = arg.split(":");
                    System.out.println("\t\t"+membres.length+" personnes dans le salon :");
                    for (String s:membres)
                        System.out.println("\t\t\t- "+s);
                    break;
                case "HIST":
                    arg = evenement.getArgument();
                    String[] msg = arg.split("\n");
                    for (String string : msg)
                        System.out.println("\t\t\t"+"."+string);
                    break;
                case "CHESSOK" :
                    arg = evenement.getArgument();
                    if (arg.charAt(0) == 'b'){
                        System.out.println("Vous etes les blancs\n");
                    }else if (arg.charAt(0) == 'n'){
                        System.out.println("Vous etes les NOIRS\n");
                    }
                    ClientChat client = new ClientChat();
                    client.nouvellePartie();
                    System.out.println("Voici l'echiquier : ");
                    System.out.println(client.getEtatPartieEchecs().toString());
                    System.out.println("Quel est votre prochain movement ? (Veuillez respecter la syntaxe suivante : MOVE x#-x# // MOVE x# x# // MOVE x#x# ");
                    break;
                case "MOVE":
                    arg = evenement.getArgument();

                default: //Afficher le texte recu :
                    System.out.println("\t\t\t."+evenement.getType()+" "+evenement.getArgument());
            }
        }
    }
}
