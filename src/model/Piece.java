package model;

public abstract class Piece {
    private String chessName;
    private int value;
    private boolean team;
    private String imageLink;

    public Piece(){
        team = true; //white
        chessName="Piece";
        value = -1;
        imageLink = "default";
    }

    public Piece(boolean newTeam, String givenName, int pieceValue, String image){
        team = newTeam;
        chessName=givenName;
        value = pieceValue;
        imageLink = (newTeam ? "b" : "w" )+ image;
    }
    public boolean getTeam(){
        return team;
    }
    public int getValue(){
        return value;
    }
    public String getChessName(){
        return chessName;
    }
    public String getImageLink(){
        return imageLink;
    }
}
