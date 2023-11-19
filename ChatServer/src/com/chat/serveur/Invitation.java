package com.chat.serveur;

public class Invitation {
    private String hote;
    private String invite;

    public Invitation(String hote, String invite){
        this.hote = hote;
        this.invite = invite;

    }
    public String getHote() {
        return hote;
    }

    public void setHote(String hote) {
        this.hote = hote;
    }

    public String getInvite() {
        return invite;
    }

    public void setInvite(String invite) {
        this.invite = invite;
    }

}
