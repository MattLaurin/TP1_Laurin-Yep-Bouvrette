package com.echecs.pieces;

import com.echecs.Position;
import com.echecs.util.EchecsUtil;

public class Fou extends Piece{

    public Fou(char couleur) {
        super(couleur);
    }

    @Override
    public boolean peutSeDeplacer(Position pos1, Position pos2, Piece[][] echiquier) {
        int     yi,yf,temp,xi,xf, dx, dy, i, j;
        if (pos1.estSurLaMemeDiagonaleQue(pos2)) {
            xi = EchecsUtil.indiceLigne(pos1);
            yi = EchecsUtil.indiceColonne(pos1);
            xf = EchecsUtil.indiceLigne(pos2);
            yf = EchecsUtil.indiceColonne(pos2);

            dx = (int)Math.signum(xf-xi);
            dy = (int)Math.signum(yf-yi);

            xi+=dx;
            yi+=dy;
            while (xi!=xf) {
                if (echiquier[xi][yi]!=null)
                    return false;
                xi+=dx;
                yi+=dy;
            }
            return true;
        }
        return false;
    }
    @Override
    public String toString() {
        return (couleur=='b')?"F":"f";
    }
}
