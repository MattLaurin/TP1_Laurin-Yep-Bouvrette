package echecs.pieces;

import com.echecs.Position;

public class Fou extends Piece{
    public Fou(char couleur) {
        super(couleur);
    }

    @Override
    public boolean peutSeDeplacer(Position pos1, Position pos2, Piece[][] echiquier) {
        if(pos1.estSurLaMemeDiagonaleQue(pos2))
        {
            int x = pos1.getColonne() - pos2.getColonne();
            int y = pos1.getLigne() - pos2.getLigne();
            int xsigne = x/Math.abs(x);
            int ysigne = y/Math.abs(y);
            x = pos1.getColonne();
            y = pos1.getLigne();
            while(x != pos2.getColonne() && y != pos2.getLigne() && echiquier[x][y]==null)
            // null on ne sait pas c<est quoi une case vide
            {
                x += xsigne ;
                y += ysigne ;

            }
            if(x == pos2.getColonne() && y == pos2.getLigne())
            {
                return true;
            }
            else
            {
                return false;
            }
        }
        return false;
    }
}
