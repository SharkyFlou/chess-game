package model;

public class Pawn extends FirstMovement {

    public Pawn(boolean newTeam) {
        super(newTeam,
                "pawn",
                1,
                "pawn");

    }

    //
    // -
    //
    // pos x
    //
    // +
    //

    // <-----pos Y------->
    public Boolean[][] getTheoricalMvmt(int posX, int posY) { // very short and easy for the class

        Boolean[][] canGoTo = super.initTabFalse();

        int way = super.getTeam() ? 1 : -1;

        if (posX + (1 * way) < 8 && posX + (1 * way) >= 0) {
            canGoTo[posX + (1 * way)][posY] = true;
        }

        if (!hasItMoved() && (posX + (2 * way) < 8 && posX + (2 * way) >= 0)) {
            canGoTo[posX + (2 * way)][posY] = true;
        }

        return canGoTo;
    }

    public Boolean[][] getTheoreticalAttack(int posX, int posY) {
        Boolean[][] canAttackCase = super.initTabFalse();

        int way = super.getTeam() ? 1 : -1;

        if (posX + (1 * way) < 8 && posX + (1 * way) >= 0) {
            if (posY - 1 >= 0) // A
                canAttackCase[posX + (1 * way)][posY - 1] = true;
            if (posY + 1 < 8)
                canAttackCase[posX + (1 * way)][posY + 1] = true;
        }
        return canAttackCase;
    }

}
