package model;

public class Rook extends FirstMovement {

    private int reach;

    public Rook(boolean newTeam) {
        super(newTeam,
                "rook",
                5,
                "rook");

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

        // on utilise pas le mvt theorique vu qu'il faut calculer l'interception
        // progressive de pieces entre le rook et les pieces du board
        // on le fait dans mover

        // KING
        if (reach == 1) {
            if (posY - 1 >= 0)
                canGoTo[posX][posY - 1] = true;
            if (posY + 1 < 8)
                canGoTo[posX][posY + 1] = true;
            if (posX - 1 >= 0)
                canGoTo[posX - 1][posY] = true;
            if (posX + 1 < 8)
                canGoTo[posX + 1][posY] = true;
        }
        // ROOK
        else {
            for (int i = 0; i < 8; i++) {
                canGoTo[posX][i] = true;
                canGoTo[i][posY] = true;
            }
            canGoTo[posX][posY] = false;

        }

        return canGoTo;
    }

}
