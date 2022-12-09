package model;

public class Mover {
    private boolean casesPreviewMvt[][];
    private boolean casesPreviewAtk[][];

    // Board board = new Board();

    public Mover(Board board) {

    }

    public boolean isCasePreviewMvt(int posX, int posY) {
        return true;
    }

    public boolean isCasePreviewAtk(int posX, int posY) {
        return true;
    }

    public void emptyPreviews() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                casesPreviewMvt[i][j] = false;
                casesPreviewAtk[i][j] = false;
            }
        }
    }

    public void calculateRealMvt(int posX, int posY) {
        Piece currentPieceMvt = board.getPiece(posX, posY);
        if (currentPieceMvt.getChessName() == "pawn")
            casesPreviewMvt = calculateMvtPawn(posX, posY);
        else if(currentPieceMvt.getChessName() == "")

    public void calculateRealAtk(int posX, int posY) {
        Piece currentPieceAtk = board.getPiece(posX, posY);
        if (currentPieceAtk.getChessName() == "pawn")
            casesPreviewAtk = calculateAtkPawn(posX, posY);
    }

    private boolean[][] calculateMvtPawn(int posX, int posY) {
        boolean realMvtPawn[][];
        return realMvtPawn; // initializer
    }

    private boolean[][] calculateAtkPawn(int posX, int posY) {
        boolean realAtkPawn[][];
        return realAtkPawn; // initializer
    }

    // GERE LES MOUVEMENTS

    // Boolean mvt = correspond a
    // true = mvt
    // false = atk

    // tour, reine, roi
    private boolean[][] calculateMvtAtkPlus(int posX, int posY, int reach, boolean mvt) {
        Rook rook = new Rook(true);

        boolean realMvtAtkPlus[][];
        return realMvtPawn; // initializer
    }

    // bishop, reine, roi
    private boolean[][] calculateMvtAtkCross(int posX, int posY, int reach, boolean mvt) {
        boolean realMvtAtkCross[][];
        return realMvtPawn; // initializer
    }

    // chevalier
    private boolean[][] calculateMvtAtkKnight(int posX, int posY, int reach, boolean mvt) {
        boolean realMvtAtkKnight[][];
        return realMvtPawn; // initializer
    }

    // reine, roi
    private boolean[][] calculateMvtAtkPlusCross(int posX, int posY, int reach, boolean mvt) {
        boolean realMvtAtkCross[][];
        return realMvtPawn; // initializer
    }

}
