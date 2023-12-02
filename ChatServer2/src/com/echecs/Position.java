package com.echecs;

import java.util.Objects;

public class Position {
    private char colonne;  //a à h
    private byte ligne;    //0 à 7

    public Position(char colonne, byte ligne) {
        this.colonne = colonne;
        this.ligne = ligne;
    }

    public char getColonne() {
        return colonne;
    }

    public byte getLigne() {
        return ligne;
    }

    /**
     * Indique si 2 positions sont voisines sur un échiquier.
     *
     * @param p Position La position à comparer avec this.
     * @return boolean true si les 2 positions sont voisines, false sinon.
     */
    public boolean estVoisineDe(Position p) {
        return Math.abs(this.ligne-p.ligne)<=1 && Math.abs(this.colonne-p.colonne)<=1;
    }
    /**
     * Indique si 2 positions sont sur la même ligne sur un échiquier.
     *
     * @param p Position La position à comparer avec this.
     * @return boolean true si les 2 positions sont sur la même ligne, false sinon.
     */
    public boolean estSurLaMemeLigneQue(Position p) {
        return this.ligne == p.ligne;
    }
    /**
     * Indique si 2 positions sont sur la même colonne sur un échiquier.
     *
     * @param p Position La position à comparer avec this.
     * @return boolean true si les 2 positions sont sur la même colonne, false sinon.
     */
    public boolean estSurLaMemeColonneQue(Position p) {
        return this.colonne == p.colonne;
    }
    /**
     * Indique si 2 positions sont sur la même diagonale sur un échiquier.
     *
     * @param p Position La position à comparer avec this.
     * @return boolean true si les 2 positions sont sur la même diagonale, false sinon.
     */
    public boolean estSurLaMemeDiagonaleQue(Position p) {
        return Math.abs(this.ligne-p.ligne)==Math.abs(this.colonne-p.colonne);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Position position = (Position) o;
        return colonne == position.colonne && ligne == position.ligne;
    }

    @Override
    public int hashCode() {
        return Objects.hash(colonne, ligne);
    }

    @Override
    public String toString() {
        return "" + colonne + ligne;
    }
}
