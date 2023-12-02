package com.chat.client;

import com.chat.echecs.EtatPartieEchecs;

/**
 * Cette classe étend la classe Client pour lui ajouter des fonctionnalités
 * spécifiques au chat et au jeu d'échecs en réseau.
 *
 * @author Abdelmoumène Toudeft (Abdelmoumene.Toudeft@etsmtl.ca)
 * @version 1.0
 * @since 2023-09-01
 */
public class ClientChat extends Client {
    //Cette classe est pour le moment vide et sera compléter dans le TP.
    private EtatPartieEchecs etatPartieEchecs;
    private String alias;

    public EtatPartieEchecs getEtatPartieEchecs() {
        return etatPartieEchecs;
    }
    public void nouvellePartie() {
            this.etatPartieEchecs = new EtatPartieEchecs();
    }
    public void setEtatPartieEchecs(EtatPartieEchecs etatPartieEchecs) {
        this.etatPartieEchecs = etatPartieEchecs;
    }
    public String getAlias() {
        return this.alias;
    }
    public void setAlias(String alias) {
        this.alias = alias;
    }
}