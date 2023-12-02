package com.echecs.pieces;

import com.echecs.Position;
import com.echecs.util.EchecsUtil;

public class Pion extends Piece{

    public Pion(char couleur) {
        super(couleur);
    }
    @Override
    public boolean peutSeDeplacer(Position pos1, Position pos2, Piece[][] echiquier) {
        int iFinal = EchecsUtil.indiceLigne(pos2),
                jFinal = EchecsUtil.indiceColonne(pos2);
        if (pos1.estSurLaMemeColonneQue(pos2)) {
            if (echiquier[iFinal][jFinal] != null)
                return false;
            if (couleur=='b' && pos2.getLigne() - pos1.getLigne() == 1
            || couleur=='n' && pos1.getLigne() - pos2.getLigne() == 1) {
                return true;
            }
            else if (couleur=='b' && pos1.getLigne()==2 && pos2.getLigne() - pos1.getLigne() == 2
                    && echiquier[5][jFinal]==null
                    || couleur=='n'  && pos1.getLigne()==7 && pos1.getLigne() - pos2.getLigne() == 2
                    && echiquier[2][jFinal]==null) {
                return true;
            }
            return false;
        }
        if (Math.abs(pos1.getColonne()-pos2.getColonne())==1) {
            if (couleur=='b' && pos2.getLigne()-pos1.getLigne()==1) {
                if (echiquier[iFinal][jFinal]!=null
                        && echiquier[iFinal][jFinal].getCouleur()=='n')
                    return true;
            }
            if (couleur=='n' && pos1.getLigne()-pos2.getLigne()==1) {
                if (echiquier[iFinal][jFinal]!=null
                        && echiquier[iFinal][jFinal].getCouleur()=='b')
                    return true;
            }
        }
        return false;
    }
    @Override
    public String toString() {
        return (couleur=='b')?"P":"p";
    }
}
