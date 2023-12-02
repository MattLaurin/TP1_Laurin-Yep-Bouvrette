package com.echecs.pieces;

import com.echecs.Position;

public class Dame extends Piece {

    public Dame(char couleur) {
        super(couleur);
    }

    @Override
    public boolean peutSeDeplacer(Position pos1, Position pos2, Piece[][] echiquier) {
        Tour t = new Tour(' ');
        if (t.peutSeDeplacer(pos1,pos2,echiquier))
            return true;
        Fou f = new Fou(' ');
        if (f.peutSeDeplacer(pos1,pos2,echiquier))
            return true;
        return false;
    }
    @Override
    public String toString() {
        return (couleur=='b')?"D":"d";
    }
}
