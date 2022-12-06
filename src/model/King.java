package model;
public class King extends FirstMovement{

    public King(boolean newTeam) {
        super(newTeam,
        "king",
        -1,
        "king"
        );
    }

    public Boolean[][] getTheoricalMvmt(int posX, int posY) {
        Boolean[][] canGoTo = super.initTabFalse();
        return canGoTo;
    }

}
