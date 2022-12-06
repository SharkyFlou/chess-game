package model;
public class Knight extends Piece{

    public Knight(boolean newTeam) {
        super(newTeam,
        "knight",
        3,
        "knight");
    }

    public Boolean[][] getTheoricalMvmt(int posX, int posY) {
        Boolean[][] canGoTo = super.initTabFalse();
        return canGoTo;
    }

}
