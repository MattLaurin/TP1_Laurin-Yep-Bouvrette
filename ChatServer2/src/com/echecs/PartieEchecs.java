package com.echecs;

import com.echecs.pieces.*;
import com.echecs.util.EchecsUtil;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import javax.swing.*;

public class PartieEchecs {
    /**
     * Grille du jeu d'échecs. La ligne 0 de la grille correspond à la ligne
     * 8 de l'échiquier. La colonne 0 de la grille correspond à la colonne a
     * de l'échiquier.
     */
    private Piece[][] echiquier;
    private String aliasJoueur1, aliasJoueur2;
    private char couleurJoueur1, couleurJoueur2;

    /**
     * La couleur de celui à qui c'est le tour de jouer (n ou b).
     */
    private char tour = 'b'; //Les blancs commencent toujours
    //Attributs pour vérifier le roque :
    private boolean roiBlancABouge = false,
            roiNoirABouge = false,
            tourBlancheColAABouge = false,
            tourNoireColAABouge = false,
            tourBlancheColHABouge = false,
            tourNoireColHABouge = false,
            roqueBlancImpossible = false,
            roqueNoirImpossible = false;

    /**
     * Crée un échiquier de jeu d'échecs avec les pièces dans leurs positions
     * initiales de début de partie.
     * Répartit au hasard les couleurs n et b entre les 2 joueurs.
     */
    public PartieEchecs() {
        int i,j;
        echiquier = new Piece[8][8];
        //Placement des pièces :
        //Pions :
        for (char col='a'; col<='h';col++) {
            placer(col,(byte)2,new Pion('b'));
            placer(col,(byte)7,new Pion('n'));
        }

        //Tours :
        placer('a',(byte)1,new Tour('b'));
        placer('h',(byte)1,new Tour('b'));
        placer('a',(byte)8,new Tour('n'));
        placer('h',(byte)8,new Tour('n'));

        //Cavaliers :
        placer('b',(byte)1,new Cavalier('b'));
        placer('g',(byte)1,new Cavalier('b'));
        placer('b',(byte)8,new Cavalier('n'));
        placer('g',(byte)8,new Cavalier('n'));

        //Fous :
        placer('c',(byte)1,new Fou('b'));
        placer('f',(byte)1,new Fou('b'));
        placer('c',(byte)8,new Fou('n'));
        placer('f',(byte)8,new Fou('n'));

        //Dames :
        placer('d',(byte)1,new Dame('b'));
        placer('d',(byte)8,new Dame('n'));

        //Rois :
        placer('e',(byte)1,new Roi('b'));
        placer('e',(byte)8,new Roi('n'));

        this.tour = 'b';
        if (Math.random()<0.5) {
            this.couleurJoueur1 = 'b';
            this.couleurJoueur2 = 'n';
        }
        else {
            this.couleurJoueur1 = 'n';
            this.couleurJoueur2 = 'b';
        }
    }
    /**
     * Place une pièce à une position valide. Ne fait rien, si la position
     * n'est pas valide.
     * @param colonne char Colonne de la position.
     * @param ligne byte Ligne de la position.
     * @param p Piece Piece à placer.
     */
    public void placer(char colonne, byte ligne, Piece p) {
        if (!EchecsUtil.positionValide(new Position(colonne,ligne)))
            return;
        int i = EchecsUtil.indiceLigne(ligne),
            j = EchecsUtil.indiceColonne(colonne);
        echiquier[i][j] = p;
    }
    /**
     * Change la main du jeu (de n à b ou de b à n).
     */
    public void changerTour() {
        if (tour=='b')
            tour = 'n';
        else
            tour = 'b';
    }
    /**
     * Tente de déplacer une pièce d'une position à une autre sur l'échiquier.
     * Le déplacement peut échouer pour plusieurs raisons, selon les règles du
     * jeu d'échecs. Par exemples :
     *  Une des positions n'existe pas;
     *  Il n'y a pas de pièce à la position initiale;
     *  La pièce de la position initiale ne peut pas faire le mouvement;
     *  Le déplacement met en échec le roi de la même couleur que la pièce.
     *
     * @param initiale Position la position initiale
     * @param finale Position la position finale
     *
     * @return boolean true, si le déplacement a été effectué avec succès, false sinon
     */
    public boolean deplace(Position initiale, Position finale) {
        if (!EchecsUtil.positionValide(initiale)|| !EchecsUtil.positionValide(finale))
            return false;
        if (initiale.equals(finale))
            return false;
        int     i1 = EchecsUtil.indiceLigne(initiale),
                j1 = EchecsUtil.indiceColonne(initiale),
                i2 = EchecsUtil.indiceLigne(finale),
                j2 = EchecsUtil.indiceColonne(finale);
        Piece pieceADeplacer = this.echiquier[i1][j1];

        if (pieceADeplacer==null)
            return false;
        //Vérifier que la pièce à déplacer appartient bien au jour qui a la main:
        if (pieceADeplacer.getCouleur()!=this.tour)
            return false;
        //Vérifier qu'il n'y a pas une pièce de même couleur à la position finale:
        if (this.echiquier[i2][j2] !=null
           && this.echiquier[i2][j2].getCouleur()==pieceADeplacer.getCouleur())
            return false;

        if (!pieceADeplacer.peutSeDeplacer(initiale,finale, this.echiquier))
            return false;

        //Traiter le cas du roque :
        if (pieceADeplacer instanceof Roi) {
//            if (pieceADeplacer.getCouleur()=='b') {
//                this.roiBlancABouge = true;
//                this.roqueBlancImpossible = true;
//            }
//            else {
//                this.roiNoirABouge = true;
//                this.roqueNoirImpossible = true;
//            }
//            if (Math.abs(initiale.getColonne()-finale.getColonne())==2) {
//                if (pieceADeplacer.getCouleur() == 'b' && this.roiBlancABouge)
//                     return false;
//                if (pieceADeplacer.getCouleur() == 'n' && this.roiNoirABouge)
//                     return false;
//            }
            //à suivre
        }

        //Vérifier si le joueur ne met pas son roi en échec.
        //Effectuer temporairement le déplacement :
        Piece prise = this.echiquier[i2][j2];//mémorise la pièce prise éventuelle
        this.echiquier[i2][j2] = this.echiquier[i1][j1];
        this.echiquier[i1][j1] = null;
        //Vérifier si le joueur ne met pas son roi en échec.
        char couleur = estEnEchec();
        if (couleur==this.tour) {
            //défaire le déplacement :
            this.echiquier[i1][j1] = this.echiquier[i2][j2];
            this.echiquier[i2][j2] = prise;
            return false;
        }
        //Promotion numérique :
        if (pieceADeplacer instanceof Pion) {
            if (pieceADeplacer.getCouleur()=='b' && i2==0
            || pieceADeplacer.getCouleur()=='n' && i2==7) {
                this.echiquier[i2][j2] = new Dame(pieceADeplacer.getCouleur());
                //Pas moyen d'informer les clients de la promotion. Donc,
                //EtatPartieEchecs doit le détecter du coté client (sinon la
                //nouvelle dame va continuer à apparaitre comme un pion malgré
                //ses nouveaux pouvoirs :)
            }
        }

        this.changerTour();
        return true;
    }

    /**
     * Vérifie si un roi est en échec et, si oui, retourne sa couleur sous forme
     * d'un caractère n ou b.
     * Si la couleur du roi en échec est la même que celle de la dernière pièce
     * déplacée, le dernier déplacement doit être annulé.
     * Les 2 rois peuvent être en échec en même temps. Dans ce cas, la méthode doit
     * retourner la couleur de la pièce qui a été déplacée en dernier car ce
     * déplacement doit être annulé.
     *
     * @return char Le caractère n, si le roi noir est en échec, le caractère b,
     * si le roi blanc est en échec, tout autre caractère, sinon.
     */
    public char estEnEchec() {
        Position posBlanc = null;
        Position posNoir = null;
        int cpt = 0;

        //Déterminer les positions des 2 rois :
        for (int i=0;i<8 && cpt<2;i++) {
            for (int j=0;j<8 && cpt<2;j++) {
                if (echiquier[i][j] instanceof Roi) {
                    if (echiquier[i][j].getCouleur()=='b') {
                        posBlanc = new Position(EchecsUtil.getColonne((byte) j), EchecsUtil.getLigne((byte) i));
                        cpt++;
                    }
                    else {
                        posNoir = new Position(EchecsUtil.getColonne((byte) j), EchecsUtil.getLigne((byte) i));
                        cpt++;
                    }
                }
            }
        }
        System.out.println("Positions - Roi blanc : "+posBlanc+"\nRoi noir : "+posNoir);
        //Vérifier si un des rois est en échec :
        for (int i=0;i<8;i++) {
            for (int j=0;j<8;j++) {
                if (echiquier[i][j]!=null) {
                    if (echiquier[i][j].getCouleur() == 'n') {
                        if (echiquier[i][j].peutSeDeplacer(new Position(EchecsUtil.getColonne((byte) j), EchecsUtil.getLigne((byte) i)), posBlanc, echiquier)) {
                            System.out.println("Roi blanc en echec");
                            return 'b';
                        }
                    } else {
                        if (echiquier[i][j].peutSeDeplacer(new Position(EchecsUtil.getColonne((byte) j), EchecsUtil.getLigne((byte) i)), posNoir, echiquier)) {
                            System.out.println("Roi noir en echec");
                            return 'n';
                        }
                    }
                }
            }
        }
        return ' ';
    }

    /**
     * Indique si le roi d'une couleur est en échec et mat.
     *
     * @param couleur la couleur dsu roi à vérifier
     * @return true, si le roi de la couleur spécifée est en échec et mat, false sinon.
     */
    public boolean echecEtMat(char couleur) {
        //à suivre
        return false;
    }
    /**
     * Retourne la couleur n ou b du joueur qui a la main.
     *
     * @return char la couleur du joueur à qui c'est le tour de jouer.
     */
    public char getTour() {
        return tour;
    }
    /**
     * Retourne l'alias du premier joueur.
     * @return String alias du premier joueur.
     */
    public String getAliasJoueur1() {
        return aliasJoueur1;
    }
    /**
     * Modifie l'alias du premier joueur.
     * @param aliasJoueur1 String nouvel alias du premier joueur.
     */
    public void setAliasJoueur1(String aliasJoueur1) {
        this.aliasJoueur1 = aliasJoueur1;
    }
    /**
     * Retourne l'alias du deuxième joueur.
     * @return String alias du deuxième joueur.
     */
    public String getAliasJoueur2() {
        return aliasJoueur2;
    }
    /**
     * Modifie l'alias du deuxième joueur.
     * @param aliasJoueur2 String nouvel alias du deuxième joueur.
     */
    public void setAliasJoueur2(String aliasJoueur2) {
        this.aliasJoueur2 = aliasJoueur2;
    }
    /**
     * Retourne la couleur n ou b du premier joueur.
     * @return char couleur du premier joueur.
     */
    public char getCouleurJoueur1() {
        return couleurJoueur1;
    }
    /**
     * Retourne la couleur n ou b du deuxième joueur.
     * @return char couleur du deuxième joueur.
     */
    public char getCouleurJoueur2() {
        return couleurJoueur2;
    }

    @Override
    public String toString() {
        String s = "";
        for (byte i=0;i<8;i++) {
            s+=EchecsUtil.getLigne(i)+" ";
            for (int j=0;j<8;j++)
                s+=((echiquier[i][j]==null)?".":echiquier[i][j])+" ";
            s+="\n";
        }
        s+="  ";
        for (byte j=0;j<8;j++)
            s+=EchecsUtil.getColonne(j)+" ";
        return s;
    }
    public boolean estLeTourDe(String alias) {
        if (alias.equals(aliasJoueur1))
            return tour == couleurJoueur1;
        else
            return tour == couleurJoueur2;
    }
}