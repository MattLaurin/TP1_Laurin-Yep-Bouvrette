package com.echecs.util;

import com.echecs.Position;

/**
 * Classe utilitaire pour le jeu d'échecs.
 *
 * @author Abdelmoumène Toudeft (Abdelmoumene.Toudeft@etsmtl.ca)
 * @since 2023-10-01
 * @version 1.0
 */
public class EchecsUtil {
    /**
     * Constructeur privé pour empêcher l'instanciation inutile de la classe.
     */
    private EchecsUtil() {
    }
    /**
     * Convertit les indices d'une case de l'échiquier en objet Position
     *
     * @param i byte indice de la ligne d'une case (0 à 7)
     * @param j byte indice de la colonne d'une case (0 à 7)
     * @return Position position de la case
     */
    public static Position getPosition(byte i, byte j) {
        byte  ligne = (byte)(8-i);
        char colonne = (char)('a'+j);
        return new Position(colonne,ligne);
    }
    /**
     * Retourne le numéro d'une ligne de l'échiquier (8 à 1) à partir de son indice (0 à 7).
     *
     * @param i int indice d'une ligne entre 0 et 7
     * @return byte numéro de la ligne entre 8 et 1
     */
    public static byte getLigne(byte i) {
        return (byte)(8-i);
    }

    /**
     * Retourne la colonne de l'échiquier (a à h) à partir de son indice (0 à 7).
     *
     * @param j int indice d'une colonne entre 0 et 7
     * @return char caractère de la colonne entre a et h
     */
    public static char getColonne(byte j) {
        return (char)('a'+j);
    }
    public static byte indiceLigne(byte ligne) {
        return (byte)(8-ligne);
    }
    public static byte indiceLigne(Position p) {
        return (byte)(8-p.getLigne());
    }
    public static byte indiceColonne(char colonne) {
        return (byte)(colonne-'a');
    }
    public static byte indiceColonne(Position p) {
        return (byte)(p.getColonne()-'a');
    }
    /**
     * Indique si une position est valide sur un échiquier (ligne entre 1
     * et 8 et colonne entre a et h).
     *
     * @param p Position position à valider
     * @return true, si p est une position valide de l'échiquier, false sinon
     */
    public static boolean positionValide(Position p) {
        return p.getLigne()>=1 && p.getLigne()<=8
                && p.getColonne()>='a' && p.getColonne()<='h';
    }
}