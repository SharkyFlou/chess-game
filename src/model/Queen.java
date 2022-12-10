package model;

public class Queen extends Piece {

    public Queen(boolean newTeam) {
        super(newTeam,
                "queen",
                9,
                "queen");
    }

    public boolean[][] getTheoricalMvt(int posY, int posX) {
        boolean[][] canGoTo = super.initTabFalse();
        return canGoTo;
    }

}
