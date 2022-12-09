package model;

public class King extends FirstMovement {

    public King(boolean newTeam) {
        super(newTeam,
                "king",
                -1,
                "king");
    }

    //
    //
    //
    // pos x
    //
    //
    //

    // <-----pos Y------->
    public Boolean[][] getTheoricalMvmt(int posX, int posY) {
        Boolean[][] canGoTo = super.initTabFalse();
        // 8 positions possibles

        /*
         * // both sides
         * if (posY - 1 >= 0)
         * canGoTo[posX][posY - 1] = true;
         * if (posY + 1 < 8)
         * canGoTo[posX][posY + 1] = true;
         * 
         * // vers le bas
         * if (posX + 1 >= 0 && posX + 1 < 8) {
         * canGoTo[posX + 1][posY] = true;
         * if (posY - 1 >= 0)
         * canGoTo[posX + 1][posY - 1] = true;
         * if (posY + 1 < 8)
         * canGoTo[posX + 1][posY + 1] = true;
         * }
         * 
         * // vers le haut
         * if (posX - 1 >= 0 && posX - 1 < 8) {
         * canGoTo[posX - 1][posY] = true;
         * if (posY - 1 >= 0)
         * canGoTo[posX - 1][posY - 1] = true;
         * if (posY + 1 < 8)
         * canGoTo[posX - 1][posY + 1] = true;
         * }
         */
        return canGoTo;
    }

}
