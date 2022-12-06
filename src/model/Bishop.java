package model;

public class Bishop extends Piece{

    public Bishop(boolean newTeam) {
        super(newTeam,
        "bishop",
        3,
        "bishop"
        );
    }

    public Boolean[][] getTheoricalMvmt(int posX, int posY) {
        Boolean[][] canGoTo = super.initTabFalse();
        return canGoTo;
    }

}
