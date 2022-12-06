package model;
public class Queen extends Piece{

    public Queen(boolean newTeam) {
        super(newTeam,
        "queen",
        9,
        "queen");
    }
    public Boolean[][] getTheoricalMvmt(int posX, int posY) {
        Boolean[][] canGoTo = super.initTabFalse();
        return canGoTo;
    }
    
}
