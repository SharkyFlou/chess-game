package model;

import java.util.ArrayList;
import java.util.List;

public class Mover {
    private boolean casesPreviewMvt[][];
    private boolean casesPreviewAtk[][];
    private Board board;
    private List<PreviewObserver> listObs = new ArrayList<PreviewObserver>();

    //

    public Mover(Board gaveBoard) {
        board = gaveBoard;
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
        else if (currentPieceMvt.getChessName() == "") {
        }
    }

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
        return realMvtAtkPlus; // initializer
    }

    // bishop, reine, roi
    private boolean[][] calculateMvtAtkCross(int posX, int posY, int reach, boolean mvt) {
        boolean realMvtAtkCross[][];
        return realMvtAtkCross; // initializer
    }

    // reine, roi
    private boolean[][] calculateMvtAtkPlusCross(int posX, int posY, int reach, boolean mvt) {
        boolean realMvtAtkCross[][];
        return realMvtAtkCross; // initializer
    }

    // chevalier
    private boolean[][] calculateMvtAtkKnight(int posX, int posY, int reach, boolean mvt) {
        boolean realMvtAtkKnight[][];
        return realMvtAtkKnight;
    }

    public void addObs(PreviewObserver obs) {
        listObs.add(obs);
    }

    public void notifyEraseDisplays() {
        for (PreviewObserver obs : listObs) {
            obs.erasePreviews();
        }
    }

    public void notifyDisplayAtk(Piece piece) {
        for (PreviewObserver obs : listObs) {
            obs.displayPreviewAtk(casesPreviewAtk);
        }
    }

    public void notifyDisplayMvmt(Piece piece) {
        for (PreviewObserver obs : listObs) {
            obs.displayPreviewMvmt(casesPreviewMvt);
        }
    }
}
