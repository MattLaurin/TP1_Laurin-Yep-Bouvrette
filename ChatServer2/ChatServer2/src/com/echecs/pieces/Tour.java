package com.echecs.pieces;

import com.echecs.Position;
import com.echecs.util.EchecsUtil;

public class Tour extends Piece{

    public Tour(char couleur) {
        super(couleur);
    }

    @Override
    public boolean peutSeDeplacer(Position pos1, Position pos2, Piece[][] echiquier) {
        int     d,f,temp,x;
        if (pos1.estSurLaMemeLigneQue(pos2)) {
            x = EchecsUtil.indiceLigne(pos1);
            d = EchecsUtil.indiceColonne(pos1);
            f = EchecsUtil.indiceColonne(pos2);
            if (d>f) {
                temp = d;
                d = f;
                f = temp;
            }
            for (int i=d+1; i<f;i++)
                if (echiquier[x][i]!=null)
                    return false;
        }
        else if (pos1.estSurLaMemeColonneQue(pos2)) {
            x = EchecsUtil.indiceColonne(pos1);
            d = EchecsUtil.indiceLigne(pos1);
            f = EchecsUtil.indiceLigne(pos2);
            if (d>f) {
                temp = d;
                d = f;
                f = temp;
            }
            for (int i=d+1; i<f;i++)
                if (echiquier[i][x]!=null)
                    return false;
        }
        else
            return false;
        return true;
    }
    @Override
    public String toString() {
        return (couleur=='b')?"T":"t";
    }
}