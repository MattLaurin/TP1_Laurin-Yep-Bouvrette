package com.echecs.pieces;

import com.echecs.Position;

import java.util.Vector;

public class Roi extends Piece{

    public Roi(char couleur) {
        super(couleur);
    }

    public boolean peutSeDeplacer(Position pos1, Position pos2, Piece[][] echiquier) { //castle
        return pos1.estVoisineDe(pos2);
    }
}
