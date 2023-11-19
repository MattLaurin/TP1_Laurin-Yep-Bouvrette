package com.chat.serveur;

public class SalonPrive {
    public String hote;
    public String invite;

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
}
