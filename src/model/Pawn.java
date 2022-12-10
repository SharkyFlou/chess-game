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
    // pos Y
    //
    // +
    //

    // <-----pos X------->
    public boolean[][] getTheoricalMvt(int posY, int posX) { // very short and easy for the class

        boolean[][] canGoTo = super.initTabFalse();

        int way = super.getTeam() ? -1 : 1;

        if (posY + (1 * way) < 8 && posY + (1 * way) >= 0) {
            canGoTo[posY + (1 * way)][posX] = true;
        }

        if (!hasItMoved() && (posY + (2 * way) < 8 && posY + (2 * way) >= 0)) {
            canGoTo[posY + (2 * way)][posX] = true;
        }
        return canGoTo;
    }

    public boolean[][] getTheoreticalAttack(int posY, int posX) {
        boolean[][] canAttackCase = super.initTabFalse();

        int way = super.getTeam() ? -1 : 1;

        if (posY + (1 * way) < 8 && posY + (1 * way) >= 0) {
            if (posX - 1 >= 0)
                canAttackCase[posY + (1 * way)][posX - 1] = true;
            if (posX + 1 < 8)
                canAttackCase[posY + (1 * way)][posX + 1] = true;
        }
        return canAttackCase;
    }

}
