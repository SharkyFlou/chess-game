package model;

public interface BoardObserver {
    public void displayGame();

    // public void displayPromotion(int posY, int posX);

    public void displayPieceTaken(Piece piece);

    public void displayTeamToPlay(boolean team);

    public void closeApp();
}
