package model;

public abstract class Piece {
    private String chessName;
    private int value;
    private boolean team;
    private String imageLink;

    public Piece() {
        team = true; // white
        chessName = "Piece";
        value = -1;
        imageLink = "default";
    }

    public Piece(boolean newTeam, String givenName, int pieceValue, String image) {
        team = newTeam;
        chessName = givenName;
        value = pieceValue;
        imageLink = (newTeam ? "w" : "b") + image;
    }

    public boolean getTeam() {
        return team;
    }

    public int getValue() {
        return value;
    }

    public String getChessName() {
        return chessName;
    }

    public String getImageLink() {
        return imageLink;
    }

    public abstract boolean[][] getTheoricalMvt(int posY, int posX);

    protected boolean[][] initTabFalse() {
        boolean[][] tab = new boolean[8][8];
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                tab[i][j] = false;
            }
        }
        return tab;
    }
}
