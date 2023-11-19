package com.echecs.pieces;

import com.echecs.Position;

import java.util.Vector;

public class Dame extends Piece{

    public Dame(char couleur) {
        super(couleur);
    }

    public boolean peutSeDeplacer(Position pos1, Position pos2, Piece[][] echiquier) {
        if (pos1.estSurLaMemeColonneQue(pos2) || pos1.estSurLaMemeLigneQue(pos2) || pos1.estSurLaMemeDiagonaleQue(pos2)){

            int x = pos1.getColonne() - pos2.getColonne();
            int y = pos1.getLigne() - pos2.getLigne();
            x = x/Math.abs(x);
            y = y/Math.abs(y);
            int xTemp = pos1.getColonne() + x;
            int yTemp = pos1.getLigne() + y;
            while (xTemp != pos2.getColonne() && yTemp != pos2.getLigne() && echiquier[xTemp][yTemp] == null){
                xTemp += x;
                yTemp += y;
            }
            return xTemp == pos2.getColonne() && xTemp == pos2.getLigne();
        }
        return false;
    }
}