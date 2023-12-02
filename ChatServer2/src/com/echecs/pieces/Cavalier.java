package com.echecs.pieces;

import com.echecs.Position;
import com.echecs.util.EchecsUtil;

public class Cavalier extends Piece{

    public Cavalier(char couleur) {
        super(couleur);
    }

    @Override
    public boolean peutSeDeplacer(Position pos1, Position pos2, Piece[][] echiquier) {
        int     d1,d2,x1,x2,y1,y2;

        x1 = EchecsUtil.indiceLigne(pos1);
        x2 = EchecsUtil.indiceLigne(pos2);
        y1 = EchecsUtil.indiceColonne(pos1);
        y2 = EchecsUtil.indiceColonne(pos2);

        d1 = Math.abs(x2-x1);
        d2 = Math.abs(y2-y1);

        return d1>0 && d2>0 && d1+d2==3;
    }

    @Override
    public String toString() {
        return (couleur=='b')?"C":"c";
    }
}
