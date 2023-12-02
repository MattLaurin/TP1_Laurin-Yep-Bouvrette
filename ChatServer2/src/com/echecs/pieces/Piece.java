package com.echecs.pieces;

import com.echecs.Position;

public abstract class Piece {
    protected char couleur; //b ou n
    public Piece(char couleur) {
        this.couleur = couleur;
    }

    public char getCouleur() {
        return couleur;
    }

    /**
     * Vérifie si la piece peut se déplacer d'une position à une autre.
     *
     * @param pos1 Position La position initiale
     * @param pos2 Position La position finale
     *
     * @return boolean true, si la pièce peut se déplacer de la position pos1 à la position pos2, false sinon
     */
    public abstract boolean peutSeDeplacer(Position pos1, Position pos2, Piece echiquier[][]);
}