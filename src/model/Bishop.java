package model;

public class Bishop extends Piece {

    public Bishop(boolean newTeam) {
        super(newTeam,
                "bishop",
                3,
                "bishop");
    }

    //
    // -
    //
    // pos x
    //
    // +
    //

    // <-----pos Y------->
    public Boolean[][] getTheoricalMvmt(int posX, int posY) {
        Boolean[][] canGoTo = super.initTabFalse();

        // 8 positions possibles
        // vers le haut
        while (posX >= 0) {
            canGoTo[posX - 1][posY] = true; // a changer
            // diagonale haut-gauche
            // if(posY)
        }
        return canGoTo;
    }

}
