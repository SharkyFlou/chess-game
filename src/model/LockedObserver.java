package model;

public interface LockedObserver {
    public void erasePreviewsLock();

    public void displayLocked(boolean[][] cases);

    public void displayKing(int posY, int posX);
}
