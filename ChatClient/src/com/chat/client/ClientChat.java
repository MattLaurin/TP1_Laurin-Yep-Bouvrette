package com.chat.client;

import echecs.Position;

/**
 * Cette classe étend la classe Client pour lui ajouter des fonctionnalités
 * spécifiques au chat et au jeu d'échecs en réseau.
 *
 * @author Abdelmoumène Toudeft (Abdelmoumene.Toudeft@etsmtl.ca)
 * @version 1.0
 * @since 2023-09-01
 */
public class ClientChat extends Client {

    private EtatPartieEchecs etatPartieEchecs;
    public EtatPartieEchecs getEtatPartieEchecs() {
        return etatPartieEchecs;
    }
    public void setEtatPartieEchecs(Position pos1, Position pos2) {
        this.etatPartieEchecs.setEtatEchiquier(pos1, pos2);
    }
    public void nouvellePartie(){
        this.etatPartieEchecs = new EtatPartieEchecs();
    }
    //Cette classe est pour le moment vide et sera compléter dans le TP.
}
