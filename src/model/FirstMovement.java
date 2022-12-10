package model;

public abstract class FirstMovement extends Piece {
    private boolean hasMoved;

    public FirstMovement(boolean newTeam, String givenName, int pieceValue, String image) {
        super(newTeam, givenName, pieceValue, image);

        hasMoved = false;
    }

    public boolean hasItMoved() {
        return hasMoved;
    }

    public void mooveIt() {
        hasMoved = true;
    }

    public abstract boolean[][] getTheoricalMvt(int posY, int posX);
}
