package com.echecs.pieces;

import com.echecs.Position;

public class Roi extends Piece {
    public Roi(char couleur) {
        super(couleur);
    }
    @Override
    public boolean peutSeDeplacer(Position pos1, Position pos2, Piece[][] echiquier) {
        if (pos1.estVoisineDe(pos2))
            return true;
        //Déplacement pour le roque (c'est la partie d'échecs qui va
        // vérifier si le roi ou la tour a déjà bougé)

        if (pos1.estSurLaMemeLigneQue(pos2)
                && (Math.abs(pos1.getColonne()-pos2.getColonne())==2)
                && (pos1.getLigne()==1 || pos1.getLigne()==8))
            return true;
        return false;
    }
    @Override
    public String toString() {
        return (couleur=='b')?"R":"r";
    }
}