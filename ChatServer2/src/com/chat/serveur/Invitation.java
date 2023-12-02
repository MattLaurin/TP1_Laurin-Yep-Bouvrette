package com.chat.serveur;

public class Invitation {

    private String hote, invite;

    public static final int ACCEPTEE = 0,
                     DEJA_LA = 1,
                     AJOUTEE = 2;
    public Invitation(String hote, String invite) {
        this.hote = hote;
        this.invite = invite;
    }

    public String getHote() {
        return hote;
    }

    public String getInvite() {
        return invite;
    }
}
