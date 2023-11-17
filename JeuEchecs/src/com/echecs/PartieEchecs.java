package com.echecs;

import com.echecs.pieces.*;
import com.echecs.util.EchecsUtil;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 * Représente une partie de jeu d'échecs. Orcheste le déroulement d'une partie :
 * déplacement des pièces, vérification d'échec, d'échec et mat,...
 *
 * @author Abdelmoumène Toudeft (Abdelmoumene.Toudeft@etsmtl.ca)
 * @version 1.0
 * @since 2023-09-01
 */
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
    /**
     * Crée un échiquier de jeu d'échecs avec les pièces dans leurs positions
     * initiales de début de partie.
     * Répartit au hasard les couleurs n et b entre les 2 joueurs.
     */
    public PartieEchecs() {
        echiquier = new Piece[8][8];

        //piece noir
        echiquier[0][0] = new Tour('n');    //**soit 7 ou 8 pour position**
        echiquier[1][0] = new Cavalier('n');
        echiquier[2][0] = new Fou('n');
        echiquier[3][0] = new Dame('n');
        echiquier[4][0] = new Roi('n');
        echiquier[5][0] = new Fou('n');
        echiquier[6][0] = new Cavalier('n');
        echiquier[7][0] = new Tour('n');
        for (int i = 0; i < 8; i++)
            echiquier[i][6] = new Pion('n') {
            };

        //piece blanche
        echiquier[0][7] = new Tour('b');
        echiquier[1][7] = new Cavalier('b');
        echiquier[2][7] = new Fou('b');
        echiquier[3][7] = new Dame('b');
        echiquier[4][7] = new Roi('b');
        echiquier[5][7] = new Fou('b');
        echiquier[6][7] = new Cavalier('b');
        echiquier[7][7] = new Tour('b');
        for (int i = 0; i < 8; i++)
            echiquier[i][1] = new Pion('n') {
            };

        //Placement des pièces :

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
        boolean peutDeplacer = true;
        if (!EchecsUtil.positionValide(initiale) || !EchecsUtil.positionValide(finale))
            peutDeplacer = false;
        if (echiquier[initiale.getColonne()][initiale.getLigne()] == null)
            peutDeplacer = false;
        if (echiquier[initiale.getColonne()][initiale.getLigne()].getCouleur() != getTour())
            peutDeplacer = false;
        if (echiquier[finale.getColonne()][finale.getLigne()].getCouleur() == getTour())
            peutDeplacer = false;

        //castle
        if (peutDeplacer) // necessaire??
            echiquier[initiale.getColonne()][initiale.getLigne()].peutSeDeplacer(initiale, finale, echiquier);

        return peutDeplacer;
        //throw new NotImplementedException();
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
        char enEchec = '.';
        char couleur1;
        char couleur2;
        Position roi1 = null;
        Position roi2 = null;
        // trouver rois
        for (int i = 0; i < echiquier.length; i++)
            for (int j = 0; j < echiquier.length; j++)
                if (echiquier[i][j] instanceof Roi ) {
                    if (roi1 != null) {
                        roi2 = EchecsUtil.getPosition((byte) j, (byte) i);
                        couleur2 = echiquier[i][j].getCouleur();
                    }
                    else {
                        roi1 = EchecsUtil.getPosition((byte) j, (byte) i);
                        couleur1 = echiquier[i][j].getCouleur();
                    }
                }
        // regarder echec

        return enEchec;
        //throw new NotImplementedException();
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
}