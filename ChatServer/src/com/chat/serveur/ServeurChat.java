package com.chat.serveur;


import com.chat.commun.net.Connexion;
import com.echecs.PartieEchecs;
import com.echecs.Position;


import java.sql.Array;
import java.util.ArrayList;
import java.util.ListIterator;
import java.util.Random;
import java.util.Vector;

/**
 * Cette classe étend (hérite) la classe abstraite Serveur et y ajoute le nécessaire pour que le
 * serveur soit un serveur de chat.
 *
 * @author Abdelmoumène Toudeft (Abdelmoumene.Toudeft@etsmtl.ca)
 * @version 1.0
 * @since 2023-09-15
 */
public class ServeurChat extends Serveur {


    // Création du Vector de String historique :
    private Vector<String> historique = new Vector<>();
    private ArrayList<Invitation> invitations = new ArrayList<>();
    private ArrayList<SalonPrive> salonPrives = new ArrayList<>();


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
        ListIterator<String> iterateur = historique.listIterator();
        while (iterateur.hasNext()) {
            s += iterateur.next() + "\n";
        }
        return s;
    }

    public void commandeJoin(String hote, String invite) {
            if(!hote.equals(invite)){
                if (invitationExistante(hote, invite) != null && salonExistant(hote,invite) == null) {
                    SalonPrive salon = new SalonPrive(hote, invite);
                    salonPrives.add(salon);
                    informerSalon(hote, invite, salon);
                    invitations.remove(invitationExistante(hote, invite));
                }else if(invitationExistante(hote,invite) == null && salonExistant(hote,invite) == null){
                        Invitation newInvitation = new Invitation(hote, invite);
                        invitations.add(newInvitation);
                        informerInvitation(hote, invite);
                }
                else if(salonExistant(hote,invite) != null){
                    for(Connexion cnx : connectes){
                        if(cnx.getAlias().equals(hote))
                            cnx.envoyer("Il existe déjà un salon entre " +invite + " et vous ! ");
                    }
                }
            }else {
                for(Connexion cnx : connectes){
                    if(cnx.getAlias().equals(hote))
                        cnx.envoyer("Vous ne pouvez pas vous invitez vous-même ! ");
                }
            }

    }

    public void commandeDecline(String hote, String invite){
        String pasInvite = ("Pas d'invitation");
        Invitation invitation = invitationExistante(hote,invite);
        if (invitation != null) {
            invitations.remove(invitation);
            informerRefus(hote, invite);
        }
        else{
            for(Connexion cnx : connectes){
                if (cnx.getAlias().equals(hote)){
                    cnx.envoyer(pasInvite);
                }
            }
        }
    }

    public void commandeINV(String invite){
        ArrayList<String> invRecu = getInvitations(invite);

        if(!invRecu.isEmpty()){
            String message = "Liste des invitations reçu : ";
            for (String hote: invRecu){
                message += hote + ",";
            }
            for(Connexion cnx : connectes){
                if (cnx.getAlias().equals(invite)){
                    cnx.envoyer(message);
                }
            }
        }
        else{
            for (Connexion cnx : connectes)
                if (cnx.getAlias().equals(invite)){
                    cnx.envoyer("Pas d'invitation");
                }
        }
    }

    public void commandePRV(String alias1, String alias2, String msg){
        SalonPrive salon = salonExistant(alias1,alias2);
        String message = "Vous n'avez pas de salon privé avec " + alias2;

        if (salon != null){
            msgPriv(salon,alias1,alias2,msg);
        }
        else{
            for(Connexion cnx : connectes){
                if (cnx.getAlias().equals(alias1)){
                    cnx.envoyer(message);
                }
            }
        }
    }

    public void commandeQuit(String alias1, String alias2){
        SalonPrive salon = salonExistant(alias1,alias2);
        String message = "Il n'y a pas de salon privé avec " + alias2;

        if (salon != null){
            salonPrives.remove(salon);
            informerQuit(alias1,alias2);
        }
        else{
            for(Connexion cnx : connectes){
                if (cnx.getAlias().equals(alias1)){
                    cnx.envoyer(message);
                }
            }
        }
    }
    public void commandeChess(String alias1, String alias2){
        // Pour vérifier s'ils sont dans un salon privé
        Boolean estEntrainJouer = false;

        if(salonExistant(alias1,alias2)!=null){
            estEntrainJouer = true;
            SalonPrive salon = salonExistant(alias1,alias2);
            salon.setPartieEchecs(new PartieEchecs());
            salon.getPartieEchecs().setAliasJoueur1(alias1);
            salon.getPartieEchecs().setAliasJoueur2(alias2);
            salon.getPartieEchecs().setCouleur();

            for(Connexion cnx : connectes){
                if (cnx.getAlias().equals(alias2)){
                    cnx.envoyer("CHESSOK "+ salon.getPartieEchecs().getCouleurJoueur2());
                }
            }
            for(Connexion cnx : connectes){
                if (cnx.getAlias().equals(alias1)){
                    cnx.envoyer("CHESSOK "+ salon.getPartieEchecs().getCouleurJoueur1());
                }
            }
        }
    }


    public void commandeMove(String alias1, String posInit, String posFinale) {
        boolean vrai = true;

        for (SalonPrive s : salonPrives) {
            if (s.getHote().equals(alias1)){
                SalonPrive salon = s;
                String alias_1 = salon.getHote();
                String alias_2 = salon.getInvite();
                PartieEchecs partieJouer = salon.getPartieEchecs();

                //Création des positions :
                char column = posInit.charAt(0);
                char ligne = posInit.charAt(1);
                Position posInitiale = new Position(column,Byte.valueOf(String.valueOf(ligne)));



                char column1 = posFinale.charAt(0);
                char ligne1 = posFinale.charAt(1);
                Position posFinale1 = new Position(column1,Byte.valueOf(String.valueOf(ligne1)));


                if (partieJouer.deplace(posInitiale,posFinale1)){           // Il y a une erreur dans cette boucle.
                    for(Connexion cnx : connectes){
                        if (cnx.getAlias().equals(alias_1) || cnx.getAlias().equals(alias_2) ){
                            cnx.envoyer("MOVEOK "  + posInitiale +" " + posFinale1);
                        }
                    }
                }
                else if(partieJouer.getEnEchec()){
                    for(Connexion cnx : connectes){
                        if (cnx.getAlias().equals(alias_1) || cnx.getAlias().equals(alias_2) ){
                            cnx.envoyer("ECHEC" + "alias_en_echec");
                            partieJouer.plusEnEchec();
                        }
                    }
                } else if (partieJouer.estMat()) {
                    for(Connexion cnx : connectes){
                        if (cnx.getAlias().equals(alias_1) || cnx.getAlias().equals(alias_2) ){
                            char couleur = partieJouer.estEnEchec();
                            if(partieJouer.getCouleurJoueur1() == couleur)
                                cnx.envoyer("MAT" + partieJouer.getAliasJoueur1());
                            else{
                                cnx.envoyer("MAT" + partieJouer.getAliasJoueur2());
                            }
                            salon.setPartieEchecs(null); // Met la partie a null (supprime)
                            salonPrives.remove(salon); // Supprime le salon;
                        }
                    }

                }


            }
        }
    }

    public void commandeAbandon(String alias1){
        for (SalonPrive s : salonPrives){
            if (s.getHote().equals(alias1)){
                SalonPrive salon = s;
                boolean fini = false;

                for (Connexion cnx : connectes){
                    if(cnx.getAlias().equals(salon.getHote())){
                        cnx.envoyer(alias1 + " a abandonné la partie." +"\n");
                        fini = true;
                    }
                    else if(cnx.getAlias().equals(salon.getInvite())){
                        cnx.envoyer(salon.getInvite() + " a abandonné la partie." +"\n");
                        fini = true;
                    }
                    else{
                        fini = false;
                    }
                }

                if (fini){
                    salon.setPartieEchecs(null); // Met la partie a null (supprime)
                    salonPrives.remove(salon); // Supprime le salon;
                }
            }
        }
    }

    public void informerQuit(String alias1, String alias2){
        String message = alias1 + " A quitter le salon privé avec " + alias2;
        for(Connexion cnx : connectes){
            if (cnx.getAlias().equals(alias1)){
                cnx.envoyer(message);
            }
        }
        for(Connexion cnx : connectes){
            if (cnx.getAlias().equals(alias2)){
                cnx.envoyer(message);
            }
        }
    }


    private void msgPriv(SalonPrive salon, String alias1, String alias2, String msg){
        String message = String.format("PRIVÉ" + alias1 +":" + msg);

        for(Connexion cnx : connectes){
            if (cnx.getAlias().equals(alias1) || cnx.getAlias().equals(alias2)){
                cnx.envoyer(message);
            }
        }
    }

    public ArrayList<String> getInvitations(String invite){
        ArrayList<String> invites = new ArrayList<>();

        for (Invitation i : invitations){
            if (i.getInvite().equals(invite)){
                invites.add(i.getHote());
            }
        }
        return invites;
    }

    public void informerRefus(String hote, String invite) {
        String messageRefus = ("L'invitation a été refuser" + invite);
        for(Connexion cnx : connectes){
            if (cnx.getAlias().equals(invite)){
                cnx.envoyer(messageRefus);
            }
        }
    }
    public void informerInvitation(String hote, String invite){
        String message = ("Vous avez reçu une invitation de " + hote);
        for(Connexion cnx : connectes){
            if (cnx.getAlias().equals(invite)){
                cnx.envoyer(message);
            }
        }
    }

    public void informerSalon(String hote, String invite, SalonPrive salon){
        String messageInvite =("Vous avez rejoin un salon avec " + hote);
        String messageHote = ("Vous avez rejoin un salon avec " + invite);

        for(Connexion cnx : connectes){
            if (cnx.getAlias().equals(hote)){
                cnx.envoyer(messageHote);
            }
        }

        for(Connexion cnx : connectes){
            if (cnx.getAlias().equals(invite)){
                cnx.envoyer(messageInvite);
            }
        }
    }

    public Invitation invitationExistante(String hote, String invite){
        for(Invitation invitation : invitations)
            if ((invitation.getHote().equals(hote) && invitation.getInvite().equals(invite)) || invitation.getHote().equals(invite) && invitation.getInvite().equals(hote))
                return invitation;

        return null;
    }

    public SalonPrive salonExistant(String hote, String invite){
        for (SalonPrive s : salonPrives){
            if (s.getHote().equals(hote) && s.getInvite().equals(invite) || s.getHote().equals(invite) && s.getInvite().equals(hote))
                return s;
        }
        return null;
    }

     /**
     * Ajoute le message à l'historique.
     */
     public void ajouterHistorique(String message){
         historique.add(message);
     }


    /**
     * Envoyer le message envoyer par un utilisateur à tous les
     * autres utilisateurs sauf lui même
     *
     * return String message envoyer de base
     * forme Alias>> message
     */
    public void envoyerATousSauf(String str, String aliasExpediteur){
        String messageRecu = aliasExpediteur + ">>" + " " + str;
        ajouterHistorique(messageRecu);
        for(Connexion cnx : connectes){
            if (!cnx.getAlias().equals(aliasExpediteur)){
                cnx.envoyer(messageRecu);
            }
        }
    }
}
