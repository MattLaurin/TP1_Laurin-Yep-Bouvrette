package com.chat.serveur;

import com.echecs.PartieEchecs;

import java.util.Objects;

public class SalonPrive {
    private String hote, invite;
    private PartieEchecs partieEchecs;
    private String aliasPremierJoueur;

    public SalonPrive(String hote, String invite) {
        this.hote = hote;
        this.invite = invite;
    }

    public PartieEchecs getPartieEchecs() {
        return partieEchecs;
    }

    public void setPartieEchecs(PartieEchecs partieEchecs) {
        this.partieEchecs = partieEchecs;
    }

    public String getHote() {
        return hote;
    }

    public String getInvite() {
        return invite;
    }

    /**
     * Definit l'egalite entre 2 salons prives. Deux salons sont egaux s'ils
     * contiennent les memes personnes, peu importe leurs roles.
     *
     * @param obj Object avec lequel on compare this.
     * @return boolean true, si les 2 salons ont les memes personnes, false, sinon.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        SalonPrive that = (SalonPrive) obj;
        return hote.equals(that.hote) && invite.equals(that.invite)
            || hote.equals(that.invite) && invite.equals(that.hote);
    }

    @Override
    public int hashCode() {
        return Objects.hash(hote, invite);
    }

    public PartieEchecs addJoueur(String alias) {
        if (aliasPremierJoueur==null) {
            //on mémorise le 1er joueur mais on n'a pas encore de partie d'échecs :
            aliasPremierJoueur = alias;
            return null;
        }
        else {
            //On a le 2e joueur donc on démarre la partie :
            partieEchecs = new PartieEchecs();
            partieEchecs.setAliasJoueur1(aliasPremierJoueur);
            partieEchecs.setAliasJoueur2(alias);
            return partieEchecs;
        }
    }
    public boolean supprimeInvitationAuxEchecs() {
        if (aliasPremierJoueur!=null) {
            this.aliasPremierJoueur = null;
            return true;
        }
        return false;
    }
}