package model;

public class Rook extends FirstMovement {

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
    public Boolean[][] getTheoricalMvmt(int posX, int posY) {
        Boolean[][] canGoTo = super.initTabFalse();

        for (int i = 0; i < 8; i++) {
            canGoTo[posX][i] = true;
            canGoTo[i][posY] = true;
        }
        canGoTo[posX][posY] = false;

        return canGoTo;
    }

}
