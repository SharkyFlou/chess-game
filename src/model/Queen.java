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
        // on utilise pas le mvt theorique vu que le mvt du roi est composé du mvt du
        // bishop et du rook
        // on le calcule dans mover

        // cette fonction n'a pas ete codee car on a décidé de faire cela avant de coder
        // cette fonction
        return canGoTo;
    }

}
