package com.echecs.pieces;

import com.echecs.Position;

import java.util.Vector;

public class Pion extends Piece{

    public Pion(char couleur) {
        super(couleur);
    }

    public boolean peutSeDeplacer(Position pos1, Position pos2, Piece[][] echiquier) {
        int deplacementX = pos2.getColonne() - pos1.getColonne();
        int deplacementY = pos2.getLigne() - pos1.getLigne();
        boolean peutDeplacer = false;
        if (this.couleur == 'b'){ //blanc
              if (deplacementY == -1 && deplacementX == 0)
                  return true;
              if (deplacementY == -1 && (deplacementX == 1 || deplacementX == -1))
                  if (echiquier[pos2.getColonne()][pos2.getLigne()].getCouleur() == 'n')
                      peutDeplacer = true;
              if (pos1.getLigne() == 7)
                  if (deplacementY == -2 && deplacementX == 0)
                      peutDeplacer = true;
        }
        if (this.couleur == 'n'){ //noir
            if (deplacementY == 1 && deplacementX == 0)
                return true;
            if (deplacementY == 1 && (deplacementX == 1 || deplacementX == -1))
                if (echiquier[pos2.getColonne()][pos2.getLigne()].getCouleur() == 'b')
                    peutDeplacer = true;
            if (pos1.getLigne() == 2)
                if (deplacementY == 2 && deplacementX == 0)
                    peutDeplacer = true;
        }
        return peutDeplacer;
    }
}