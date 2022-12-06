package model;
public class Rook extends FirstMovement{

    public Rook(boolean newTeam) {
        super(newTeam,
        "rook",
        5,
        "rook");
        
    }

    public Boolean[][] getTheoricalMvmt(int posX, int posY) {
        Boolean[][] canGoTo = super.initTabFalse();
        return canGoTo;
    }

}
