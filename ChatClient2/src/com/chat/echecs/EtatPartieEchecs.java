package com.chat.echecs;

import observer.Observable;
import com.echecs.Position;
import com.echecs.util.EchecsUtil;

/**
 *
 * @author Abdelmoumène Toudeft (Abdelmoumene.Toudeft@etsmtl.ca)
 * @version 1.0
 * @since 2023-10-01
 */
public class EtatPartieEchecs extends Observable {
    private char[][] etatEchiquier = new char[8][8];
    public EtatPartieEchecs() {
        //Les pions :
        for (int j=0;j<8;j++) {
            etatEchiquier[1][j] = 'p';
            etatEchiquier[6][j] = 'P';
        }
        //Cases vide :
        for (int j=0;j<8;j++)
            for (int i=2;i<6;i++) {
                etatEchiquier[i][j] = ' ';
            }

        //Tours :
        etatEchiquier[0][0] = 't';
        etatEchiquier[0][7] = 't';
        etatEchiquier[7][0] = 'T';
        etatEchiquier[7][7] = 'T';

        //Cavaliers :
        etatEchiquier[0][1] = 'c';
        etatEchiquier[0][6] = 'c';
        etatEchiquier[7][1] = 'C';
        etatEchiquier[7][6] = 'C';

        //Fous :
        etatEchiquier[0][2] = 'f';
        etatEchiquier[0][5] = 'f';
        etatEchiquier[7][2] = 'F';
        etatEchiquier[7][5] = 'F';

        //Dames :
        etatEchiquier[0][3] = 'd';
        etatEchiquier[7][3] = 'D';

        //Rois :
        etatEchiquier[0][4] = 'r';
        etatEchiquier[7][4] = 'R';
    }
    public boolean move(String deplacement) {   // completer
        boolean res = false;
        if (deplacement.charAt(2) == '-' && deplacement.length() == 5 || deplacement.charAt(2) == ' ' && deplacement.length() == 5
                || deplacement.length() == 4){
            char column = deplacement.charAt(0);
            byte ligne = (byte)Character.getNumericValue(deplacement.charAt(1));
            Position posInitiale = new Position(column, ligne);

            column = deplacement.charAt(deplacement.length() - 2);
            ligne = (byte)Character.getNumericValue(deplacement.charAt(deplacement.length() - 1));
            Position posFinale = new Position(column, ligne);

            if(posInitiale.getColonne() >= 'a' || posInitiale.getColonne() <= 'h' || posInitiale.getLigne() >= 1 || posInitiale.getLigne() <= 8 ||
                    posFinale.getColonne() >= 'a' || posFinale.getColonne() <= 'h' || posFinale.getLigne() >= 1 || posFinale.getLigne() <= 8) {
                res = true;
                byte ligneFinale = EchecsUtil.indiceLigne(posFinale);
                byte colonneFinale = EchecsUtil.indiceColonne(posFinale);
                etatEchiquier[ligneFinale][colonneFinale] =
                        etatEchiquier[EchecsUtil.indiceLigne(posInitiale)][EchecsUtil.indiceColonne(posInitiale)];
                etatEchiquier[EchecsUtil.indiceLigne(posInitiale)][EchecsUtil.indiceColonne(posInitiale)] = ' ';

                if (etatEchiquier[ligneFinale][colonneFinale] == 'p' && ligneFinale == 7)
                    etatEchiquier[ligneFinale][colonneFinale] = 'd';
                if (etatEchiquier[ligneFinale][colonneFinale] == 'P' && ligneFinale == 0)
                    etatEchiquier[ligneFinale][colonneFinale] = 'D';

                notifierObservateurs();
            }
        }
        return res;
    }

    @Override
    public String toString() {
        String s = "";
        for (byte i=0;i<8;i++) {
            s+=(byte)(8-i)+" ";
            for (int j=0;j<8;j++)
                s+=((etatEchiquier[i][j]==' ')?".":etatEchiquier[i][j])+" ";
            s+="\n";
        }
        s+="  ";
        for (char j='a';j<='h';j++)
            s+=j+" ";
        return s;
    }

    public char[][] getEtatEchiquier() {
        return etatEchiquier;
    }

    public void setEtatEchiquier(char[][] etatEchiquier) {
        this.etatEchiquier = etatEchiquier;
    }
}
