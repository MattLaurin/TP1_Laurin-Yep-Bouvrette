package com.chat.serveur;

import com.echecs.PartieEchecs;

public class SalonPrive {
    public String hote;
    public String invite;
   private PartieEchecs partieEchecs;

    public SalonPrive(String hote, String invite) {
        this.hote = hote;
        this.invite = invite;
    }

    public void setInvite(String invite) {
        this.invite = invite;
    }

    public void setHote(String hote) {
        this.hote = hote;
    }

    public String getInvite() {
        return invite;
    }

    public String getHote() {
        return hote;
    }

    public PartieEchecs getPartieEchecs() {
        return partieEchecs;
      }

public void setPartieEchecs(PartieEchecs partieEchecs) { this.partieEchecs = partieEchecs; }
}
