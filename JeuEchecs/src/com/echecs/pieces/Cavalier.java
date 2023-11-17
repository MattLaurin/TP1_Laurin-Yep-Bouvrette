package com.echecs.pieces;

import com.echecs.Position;

public class Cavalier extends Piece{

    public Cavalier(char couleur) {
        super(couleur);
    }

    @Override
    public boolean peutSeDeplacer(Position pos1, Position pos2, Piece[][] echiquier) {
        int deplacementX = Math.abs(pos1.getColonne() - pos2.getColonne());
        int deplacementY = Math.abs(pos1.getLigne() - pos2.getLigne());
        if ((deplacementX == 1 && deplacementY == 3) || (deplacementX == 3 && deplacementY == 1)){ //&&(this.getCouleur() != echiquier[pos2.getColonne()][pos2.getLigne()].getCouleur())){
            return true;
        }
        else {
            return false;
        }
    }
}
