package model;

public class Bishop extends Piece {

    private int reach;

    public Bishop(boolean newTeam) {
        super(newTeam,
                "bishop",
                3,
                "bishop");
    }

    //
    // -
    //
    // pos x
    //
    // +
    //

    // <-----pos Y------->
    public boolean[][] getTheoricalMvt(int posY, int posX) {
        boolean[][] canGoTo = super.initTabFalse();
        // int nw = 1, ne = 1, sw = 1, se = 1;

        // onn utilise pas le mvt theorique vu qu'il faut calculer l'interception
        // progressive de pieces entre le bishop et les pieces du board
        // on le fait dans mover

        // 8 positions possibles = KING
        // vers le haut
        if (reach == 1) {
            // vers le bas
            if (posX + reach >= 0 && posX + reach < 8) {
                if (posY - reach >= 0)
                    canGoTo[posX + reach][posY - reach] = true;
                if (posY + reach < 8)
                    canGoTo[posX + reach][posY + reach] = true;
            }

            // vers le haut
            if (posX - reach >= 0 && posX - reach < 8) {
                if (posY - reach >= 0)
                    canGoTo[posX - reach][posY - reach] = true;
                if (posY + reach < 8)
                    canGoTo[posX - reach][posY + reach] = true;
            }
        }
        // RANGEES = BISHOP, REINE
        else {
            int posXVariable = posX, posYVariable = posY;
            // NW
            while (posXVariable >= 0 && posYVariable >= 0) {
                canGoTo[posXVariable - 1][posYVariable - 1] = true;
            }
            posXVariable = posX;
            posYVariable = posY;

            // NE
            while (posXVariable >= 0 && posYVariable < 8) {
                canGoTo[posXVariable - 1][posYVariable + 1] = true;
            }
            posXVariable = posX;
            posYVariable = posY;

            // SW
            while (posXVariable < 8 && posYVariable >= 0) {
                canGoTo[posXVariable + 1][posYVariable - 1] = true;
            }
            posXVariable = posX;
            posYVariable = posY;

            // SE
            while (posXVariable < 8 && posYVariable < 8) {
                canGoTo[posXVariable + 1][posYVariable + 1] = true;
            }
            posXVariable = posX;
            posYVariable = posY;
        }
        return canGoTo;
    }

}
