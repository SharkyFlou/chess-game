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
    public Boolean[][] getTheoricalMvmt(int posX, int posY) {
        Boolean[][] canGoTo = super.initTabFalse();
        // int nw = 1, ne = 1, sw = 1, se = 1;

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
            // ERROR, while pas verifie
            // NW
            while (posX >= 0 && posY >= 0) {
                canGoTo[posX - reach][posY - reach] = true;
            }
            // NE
            while (posX >= 0 && posY < 8) {
                canGoTo[posX - reach][posY + reach] = true;
            }
            // SW
            while (posX < 8 && posY >= 0) {
                canGoTo[posX + reach][posY - reach] = true;
            }
            // SE
            while (posX < 8 && posY < 8) {
                canGoTo[posX + reach][posY + reach] = true;
            }
        }

        return canGoTo;
    }

}
