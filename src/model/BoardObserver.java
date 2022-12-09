package model;

public interface BoardObserver {
    public void displayGame();

    public void displayPromotion(int posX, int posY);

    public void displayPieceTaken(Piece piece);
    
}
