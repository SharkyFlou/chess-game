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
    public boolean[][] getTheoricalMvt(int posY, int posX) {
        boolean[][] canGoTo = super.initTabFalse();
        // 8 positions possibles

        // on utilise pas le mvt theorique vu que le mvt du roi est composé du mvt du
        // bishop et du rook
        // on le calcule dans mover

        // both sides
        if (posY - 1 >= 0)
            canGoTo[posX][posY - 1] = true;
        if (posY + 1 < 8)
            canGoTo[posX][posY + 1] = true;

        // vers le bas
        if (posX + 1 >= 0 && posX + 1 < 8) {
            canGoTo[posX + 1][posY] = true;
            if (posY - 1 >= 0)
                canGoTo[posX + 1][posY - 1] = true;
            if (posY + 1 < 8)
                canGoTo[posX + 1][posY + 1] = true;
        }

        // vers le haut
        if (posX - 1 >= 0 && posX - 1 < 8) {
            canGoTo[posX - 1][posY] = true;
            if (posY - 1 >= 0)
                canGoTo[posX - 1][posY - 1] = true;
            if (posY + 1 < 8)
                canGoTo[posX - 1][posY + 1] = true;
        }

        return canGoTo;
    }

}
