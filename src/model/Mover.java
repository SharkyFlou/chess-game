package model;

import java.util.ArrayList;
import java.util.List;

public class Mover {
    private boolean casesPreviewMvt[][];
    private boolean casesPreviewAtk[][];
    private Board board;
    private boolean team; // a remplir
    private List<PreviewObserver> listObs = new ArrayList<PreviewObserver>();

    //

    public Mover(Board gaveBoard) {
        board = gaveBoard;
        emptyPreviews();
    }

    public boolean isCasePreviewMvt(int posX, int posY) {
        return casesPreviewMvt[posY][posX];
    }

    public boolean isCasePreviewAtk(int posX, int posY) {
        return casesPreviewAtk[posY][posX];
    }

    // reset les tableaux de retour
    public void emptyPreviews() {
        casesPreviewAtk=initializePreviews();
        casesPreviewMvt=initializePreviews();
    }

    // initialise les tableaux de retour a 0
    // dans le cas ou vraiment on retourne rien
    public boolean[][] initializePreviews() {
        boolean[][] initializer = new boolean[8][8];
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                initializer[i][j] = false;
            }
        }
        return initializer;
    }

    public void calculateRealMvt(int posX, int posY) {
        Piece currentPieceMvt = board.getPiece(posX, posY);
        if (currentPieceMvt.getChessName() == "pawn")
            casesPreviewMvt = calculateMvtAtkPawn(posX, posY, true);
        // true correspond a mvt
        // si reach != 1, alors reach == 0
        else if (currentPieceMvt.getChessName() == "knight")
            casesPreviewMvt = calculateMvtAtkKnight(posX, posY, true);

        else if (currentPieceMvt.getChessName() == "bishop")
            casesPreviewMvt = calculateMvtAtkCross(posX, posY, 0, true);

        else if (currentPieceMvt.getChessName() == "rook")
            casesPreviewMvt = calculateMvtAtkPlus(posX, posY, 0, true);

        // entre ces deux pieces suivantes, seulement le reach change
        else if (currentPieceMvt.getChessName() == "king")
            casesPreviewMvt = calculateMvtAtkPlusCross(posX, posY, 1, true);

        else if (currentPieceMvt.getChessName() == "queen")
            casesPreviewMvt = calculateMvtAtkPlusCross(posX, posY, 0, true);

        notifyDisplayMvt();

    }

    public void calculateRealAtk(int posX, int posY) {
        Piece currentPieceAtk = board.getPiece(posX, posY);
        if (currentPieceAtk.getChessName() == "pawn")
            casesPreviewAtk = calculateMvtAtkPawn(posX, posY, false);

        else if (currentPieceAtk.getChessName() == "knight")
            casesPreviewMvt = calculateMvtAtkKnight(posX, posY, false);

        else if (currentPieceAtk.getChessName() == "bishop")
            casesPreviewMvt = calculateMvtAtkCross(posX, posY, 0, false);

        else if (currentPieceAtk.getChessName() == "rook")
            casesPreviewMvt = calculateMvtAtkPlus(posX, posY, 0, false);

        // entre ces deux pieces suivantes, seulement le reach change
        else if (currentPieceAtk.getChessName() == "king")
            casesPreviewMvt = calculateMvtAtkPlusCross(posX, posY, 1, false);

        else if (currentPieceAtk.getChessName() == "queen")
            casesPreviewMvt = calculateMvtAtkPlusCross(posX, posY, 0, false);
        
        notifyDisplayAtk();
    }

    // GERE LES MOUVEMENTS

    // boolean mvtAtk = correspond a
    // true = mvt
    // false = atk

    // honestly on peut melanger calculateMvtPawn et calculateAtkPawn (comme pour
    // roi et reine)
    // donc je le fais
    private boolean[][] calculateMvtAtkPawn(int posX, int posY, boolean mvtAtk) {
        Pawn pawn = new Pawn(team);

        boolean realMvtAtkPawn[][] = initializePreviews();

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                // SI ON CHERCHE LE BOOLEAN[][] MVT
                if (mvtAtk) {
                    // le fait d'avoir bouge est deja verifie dans le mouvement theorique
                    if (pawn.getTheoricalMvt(posX, posY)[i][j]) {
                        // s'il y a pas de piece sur la position theorique mvt
                        if (!board.doesCaseContainPiece(i, j)) {
                            realMvtAtkPawn[i][j] = true;
                        }
                    }
                }
                // SI ON CHERCHE LE BOOLEAN[][] ATK
                else {
                    if (pawn.getTheoreticalAttack(posX, posY)[i][j]) {
                        // s'il y a une piece de l'autre team (!team)
                        if (board.doesCaseContainPieceOfTeam(posX, posY, !team)) {
                            realMvtAtkPawn[i][j] = true;
                        }
                    }
                }

            }
        }
        return realMvtAtkPawn;
    }

    // chevalier
    private boolean[][] calculateMvtAtkKnight(int posX, int posY, boolean mvtAtk) {
        Knight knight = new Knight(team);
        // initialise a false
        boolean realMvtAtkKnight[][] = initializePreviews();

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                // regarde chaque position vraie theoriquement (bool=true)
                if (knight.getTheoricalMvt(posX, posY)[i][j]) {

                    // SI ON CHERCHE LE BOOLEAN[][] MVT
                    if (mvtAtk) {
                        // si aucune piece existe a cette position
                        if (!board.doesCaseContainPiece(i, j)) {
                            realMvtAtkKnight[i][j] = true;
                        }
                    }
                    // SI ON CHERCHE LE BOOLEAN[][] ATK
                    else {
                        if (board.doesCaseContainPieceOfTeam(posX, posY, !team)) {
                            realMvtAtkKnight[i][j] = true;
                        }
                    }
                }
            }
        }

        return realMvtAtkKnight;
    }

    // bishop, reine, roi
    // des que tu lis ça charly appelle moi car c'est une catastrophe
    private boolean[][] calculateMvtAtkCross(int posX, int posY, int reach, boolean mvtAtk) {
        Bishop bishop = new Bishop(team);
        boolean realMvtAtkCross[][] = initializePreviews();
        return realMvtAtkCross; // initializer
    }

    // tour, reine, roi
    private boolean[][] calculateMvtAtkPlus(int posX, int posY, int reach, boolean mvtAtk) {
        Rook rook = new Rook(true);

        boolean realMvtAtkPlus[][] = initializePreviews();
        // je ne sais pas si c'est la version la plus optimisee mais
        if (mvtAtk) {
            // for()
        }

        return realMvtAtkPlus; // initializer
    }

    // reine, roi
    // ceci appelle les resultats de plus + cross
    private boolean[][] calculateMvtAtkPlusCross(int posX, int posY, int reach, boolean mvtAtk) {
        boolean[][] realMvtCross = calculateMvtAtkCross(posX, posY, reach, mvtAtk);
        boolean[][] realMvtPlus = calculateMvtAtkCross(posX, posY, reach, mvtAtk);

        boolean realMvtAtkCross[][] = initializePreviews();

        // si les tableaux de booleens de plus et cross sont true, on les melange
        // dans un autre tableau
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (realMvtCross[i][j] || realMvtPlus[i][j])
                    realMvtAtkCross[i][j] = true;
            }
        }
        return realMvtAtkCross; // initializer
    }

    public void addObs(PreviewObserver obs) {
        listObs.add(obs);
    }

    public void notifyEraseDisplays() {
        for (PreviewObserver obs : listObs) {
            obs.erasePreviews();
        }
    }

    public void notifyDisplayAtk() {
        for (PreviewObserver obs : listObs) {
            obs.displayPreviewAtk(casesPreviewAtk);
        }
    }

    public void notifyDisplayMvt() {
        for (PreviewObserver obs : listObs) {
            obs.displayPreviewMvt(casesPreviewMvt);
        }
    }
}
