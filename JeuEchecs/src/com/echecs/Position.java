package com.echecs;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;
/**
 * Représente une position sur un échiquier de jeu d'échecs. Les lignes de
 * l'échiquier sont numérotées de 8 à 1 et les colonnes de a à h.
 * Cette classe fournit quelques méthodes de comparaison de 2 positions :
 * sont-elles voisines ? sur la même ligne ? colonne ? diagonale ?
 *
 * @author Abdelmoumène Toudeft (Abdelmoumene.Toudeft@etsmtl.ca)
 * @version 1.0
 * @since 2023-09-01
 */
public class Position {
    private char colonne;  //a à h
    private byte ligne;    //0 à 7

    /**
     * Crée une position correspondant à une case d'un échiquier de jeu d'échecs.
     *
     * @param colonne char Colonne a à h de la case.
     * @param ligne byte Ligne 8 à 1 de la case.
     */
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
        throw new NotImplementedException();
    }
    /**
     * Indique si 2 positions sont sur la même ligne sur un échiquier.
     *
     * @param p Position La position à comparer avec this.
     * @return boolean true si les 2 positions sont sur la même ligne, false sinon.
     */
    public boolean estSurLaMemeLigneQue(Position p) {
        throw new NotImplementedException();
    }
    /**
     * Indique si 2 positions sont sur la même colonne sur un échiquier.
     *
     * @param p Position La position à comparer avec this.
     * @return boolean true si les 2 positions sont sur la même colonne, false sinon.
     */
    public boolean estSurLaMemeColonneQue(Position p) {
        throw new NotImplementedException();
    }
    /**
     * Indique si 2 positions sont sur la même diagonale sur un échiquier.
     *
     * @param p Position La position à comparer avec this.
     * @return boolean true si les 2 positions sont sur la même diagonale, false sinon.
     */
    public boolean estSurLaMemeDiagonaleQue(Position p) {
        throw new NotImplementedException();
    }
}
