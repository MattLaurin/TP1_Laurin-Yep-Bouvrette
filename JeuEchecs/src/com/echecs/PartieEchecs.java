package com.echecs;

import com.echecs.pieces.*;
import com.echecs.util.EchecsUtil;

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

    private boolean roqueDroitBlanc = true;
    private boolean roqueGaucheBlanc = true;
    private boolean roqueDroitNoir = true;
    private boolean roqueGaucheNoir = true;

    private boolean enEchec = false;

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
        echiquier[0][0] = new Tour('n');
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
            return false;
        if (echiquier[initiale.getColonne()][initiale.getLigne()] == null)
            return false;
        if (echiquier[initiale.getColonne()][initiale.getLigne()].getCouleur() != getTour())
            return false;
        if (echiquier[finale.getColonne()][finale.getLigne()].getCouleur() == getTour())
            return false;
        if (initiale.getColonne() == finale.getColonne() && initiale.getLigne() == finale.getLigne())
            return false;

        if (echiquier[initiale.getColonne()][initiale.getLigne()] instanceof Roi && Math.abs(initiale.getColonne() - finale.getColonne()) == 2) { // veut faire un roque
            int directionRoque = finale.getColonne() - initiale.getColonne();
            int x = initiale.getColonne();
            char enEchec = 0;
            if (getTour() == 'b') {
                if ((directionRoque == -2 && roqueGaucheBlanc) || (directionRoque == 2 && roqueDroitBlanc)){
                    directionRoque = directionRoque/2;
                    x += directionRoque;
                    while (x != finale.getColonne() + directionRoque && echiquier[x][1] == null && enEchec == getTour()) {
                        echiquier[x][initiale.getLigne()] = echiquier[initiale.getColonne()][initiale.getLigne()];
                        enEchec = estEnEchec();
                        echiquier[x][initiale.getLigne()] = null;
                        x += directionRoque;
                    }
                    peutDeplacer = x == finale.getColonne();
                } else
                    return false;

            } else {
                if ((directionRoque == -2 && roqueGaucheNoir) || (directionRoque == 2 && roqueDroitNoir)){
                    directionRoque = directionRoque/2;
                    x += directionRoque;
                    while (x != finale.getColonne() + directionRoque && echiquier[x][8] == null && enEchec == getTour()) {
                        echiquier[x][initiale.getLigne()] = echiquier[initiale.getColonne()][initiale.getLigne()];
                        enEchec = estEnEchec();
                        echiquier[x][initiale.getLigne()] = null;
                        x += directionRoque;
                    }
                    peutDeplacer = x == finale.getColonne();
                } else
                    return false;
            }
        }

        if (peutDeplacer)   // si piece peut se deplace
            peutDeplacer = echiquier[initiale.getColonne()][initiale.getLigne()].peutSeDeplacer(initiale, finale, echiquier);
        if (estEnEchec() == getTour()) {
            this.enEchec = true;
            peutDeplacer = false;
        }

        if (initiale.getColonne() == 'a' && initiale.getLigne() == 1 && peutDeplacer && roqueGaucheBlanc)   // tour deja deplace
            this.roqueGaucheBlanc = false;
        if (initiale.getColonne() == 'h' && initiale.getLigne() == 1 && peutDeplacer && roqueDroitBlanc)
            this.roqueDroitBlanc = false;
        if (initiale.getColonne() == 'a' && initiale.getLigne() == 8 && peutDeplacer && roqueGaucheNoir)
            this.roqueGaucheNoir = false;
        if (initiale.getColonne() == 'h' && initiale.getLigne() == 8 && peutDeplacer && roqueDroitNoir)
            this.roqueDroitNoir = false;

        if (initiale.getColonne() == 'e' && initiale.getLigne() == 1 && peutDeplacer && (roqueGaucheBlanc || roqueDroitBlanc)) { // roit deja deplace
            this.roqueGaucheBlanc = false;
            this.roqueDroitBlanc = false;
        }
        if (initiale.getColonne() == 'e' && initiale.getLigne() == 8 && peutDeplacer && (roqueGaucheNoir || roqueDroitNoir)) {
            this.roqueGaucheNoir = false;
            this.roqueDroitNoir = false;
        }

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
        char couleur1 = 0;
        char couleur2 = 0;
        Position roi1 = null;
        Position roi2 = null;
        // trouver rois
        for (int i = 0; i < echiquier.length; i++)
            for (int j = 0; j < echiquier[0].length; j++)
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
        // trouver piece qui peut se deplacer sur roi
        for (int i = 0; i < echiquier.length; i++)
            for (int j = 0; j < echiquier[0].length; j++)
                if (echiquier[i][j] != null){
                    if (echiquier[i][j].getCouleur() != couleur1){
                        if (echiquier[i][j].peutSeDeplacer(EchecsUtil.getPosition((byte)j, (byte)i), roi1, echiquier)){
                            return couleur1;
                        }
                    } else {
                        if (echiquier[i][j].peutSeDeplacer(EchecsUtil.getPosition((byte)j, (byte)i), roi2, echiquier)) {
                            return couleur2;
                        }
                    }
                }
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

    //donne des couleur alleatoire aux joueurs
    public void setCouleur(){
        int couleur = Math.round((float) Math.random());
        if (couleur == 0){
            this.couleurJoueur1 = 'n';
            this.couleurJoueur2 = 'b';
        } else {
            this.couleurJoueur1 = 'b';
            this.couleurJoueur2 = 'n';
        }
    }

    //retourne si en echec
    public Boolean getEnEchec(){
        return this.enEchec;
    }
    public void plusEnEchec(){
        this.enEchec = false;
    }

}