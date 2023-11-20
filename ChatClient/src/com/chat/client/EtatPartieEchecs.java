package com.chat.client;

public class EtatPartieEchecs {
    private char[][] etatEchiquier;

    public EtatPartieEchecs()
    {
        this.etatEchiquier = new char[8][8];
        this.etatEchiquier[0][7] = 'T';
        this.etatEchiquier[7][7] = 'T';
        this.etatEchiquier[0][0] = 't';
        this.etatEchiquier[7][0] = 't';
        this.etatEchiquier[1][7] = 'C';
        this.etatEchiquier[6][7] = 'C';
        this.etatEchiquier[1][0] = 'c';
        this.etatEchiquier[6][0] = 'c';
        this.etatEchiquier[2][7] = 'F';
        this.etatEchiquier[5][7] = 'F';
        this.etatEchiquier[2][0] = 'f';
        this.etatEchiquier[5][0] = 'f';
        this.etatEchiquier[3][7] = 'D';
        this.etatEchiquier[4][7] = 'R';
        this.etatEchiquier[3][0] = 'd';
        this.etatEchiquier[4][0] = 'r';
        for(int i=0; i <= 7;++i){
            this.etatEchiquier[i][1] = 'P';
        }
        for(int i=0; i <= 7;++i){
            this.etatEchiquier[i][6] = 'p';
        }
        for (int i = 2; i < 6; i++)
            for (int j = 0; j < 8; j++)
                this.etatEchiquier[j][i] = ' ';
    }

    @Override
    public String toString() {
        String affichage = null;

        for (int i = 0; i < 8; i++) {
            affichage += i + 1 + " ";
            for (int j = 0; j < 8; j++)
                affichage += etatEchiquier[j][i] + " ";
            affichage += "\n";
        }
        affichage += "  ";
        for (int j = 0; j < 8; j++) {
            affichage += 'a'+ j + " ";
        }
        return affichage;
    }

    public char[][] getEtatEchiquier() {
        return etatEchiquier;
    }

    public void setEtatEchiquier(char[][] etatEchiquier) {
        this.etatEchiquier = etatEchiquier;
    }
}
