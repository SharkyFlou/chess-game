package model;

public class Knight extends Piece {

    public Knight(boolean newTeam) {
        super(newTeam,
                "knight",
                3,
                "knight");
    }

    //
    //
    //
    // pos x
    //
    //
    //

    // <-----pos Y------->
    public boolean[][] getTheoricalMvt(int posY, int posX) {
        boolean[][] canGoTo = super.initTabFalse();

        // 8 positions possibles
        // vers le bas
        if (posY + 2 >= 0 && posY + 2 < 8) {
            if (posX - 1 >= 0)
                canGoTo[posY + 2][posX - 1] = true;
            if (posX + 1 < 8)
                canGoTo[posY + 2][posX + 1] = true;
        }
        if (posY + 1 >= 0 && posY + 1 < 8) {
            if (posX - 2 >= 0)
                canGoTo[posY + 1][posX - 2] = true;
            if (posX + 2 < 8)
                canGoTo[posY + 1][posX + 2] = true;
        }
        // vers le haut
        if (posY - 1 >= 0 && posY - 1 < 8) {
            if (posX - 2 >= 0)
                canGoTo[posY - 1][posX - 2] = true;
            if (posX + 2 < 8)
                canGoTo[posY - 1][posX + 2] = true;
        }
        if (posY - 2 >= 0 && posY - 2 < 8) {
            if (posX - 1 >= 0)
                canGoTo[posY - 2][posX - 1] = true;
            if (posX + 1 < 8)
                canGoTo[posY - 2][posX + 1] = true;
        }

        return canGoTo;
    }

}
