package model;

import java.util.ArrayList;
import java.util.List;

public class Mover {
    private boolean casesPreviewMvt[][];
    private boolean casesPreviewAtk[][];
    private Board board;
    // private boolean team; // a remplir
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
        casesPreviewAtk = initializePreviews();
        casesPreviewMvt = initializePreviews();
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
            casesPreviewMvt = calculateMvtAtkPawn(posX, posY, true, currentPieceMvt);
        // true correspond a mvt
        // si reach != 1, alors reach == 0
        else if (currentPieceMvt.getChessName() == "knight")
            casesPreviewMvt = calculateMvtAtkKnight(posX, posY, true, currentPieceMvt);

        else if (currentPieceMvt.getChessName() == "bishop")
            casesPreviewMvt = calculateMvtAtkCross(posX, posY, 0, true, currentPieceMvt);

        else if (currentPieceMvt.getChessName() == "rook")
            casesPreviewMvt = calculateMvtAtkPlus(posX, posY, 0, true, currentPieceMvt);

        // entre ces deux pieces suivantes, seulement le reach change
        else if (currentPieceMvt.getChessName() == "king")
            casesPreviewMvt = calculateMvtAtkPlusCross(posX, posY, 1, true, currentPieceMvt);

        else if (currentPieceMvt.getChessName() == "queen")
            casesPreviewMvt = calculateMvtAtkPlusCross(posX, posY, 0, true, currentPieceMvt);

        notifyDisplayMvt();

    }

    public void calculateRealAtk(int posX, int posY) {
        Piece currentPieceAtk = board.getPiece(posX, posY);
        if (currentPieceAtk.getChessName() == "pawn")
            casesPreviewAtk = calculateMvtAtkPawn(posX, posY, false, currentPieceAtk);

        else if (currentPieceAtk.getChessName() == "knight")
            casesPreviewMvt = calculateMvtAtkKnight(posX, posY, false, currentPieceAtk);

        else if (currentPieceAtk.getChessName() == "bishop")
            casesPreviewMvt = calculateMvtAtkCross(posX, posY, 0, false, currentPieceAtk);

        else if (currentPieceAtk.getChessName() == "rook")
            casesPreviewMvt = calculateMvtAtkPlus(posX, posY, 0, false, currentPieceAtk);

        // entre ces deux pieces suivantes, seulement le reach change
        else if (currentPieceAtk.getChessName() == "king")
            casesPreviewMvt = calculateMvtAtkPlusCross(posX, posY, 1, false, currentPieceAtk);

        else if (currentPieceAtk.getChessName() == "queen")
            casesPreviewMvt = calculateMvtAtkPlusCross(posX, posY, 0, false, currentPieceAtk);

        notifyDisplayAtk();
    }

    // GERE LES MOUVEMENTS

    // boolean mvtAtk = correspond a
    // true = mvt
    // false = atk

    // honestly on peut melanger calculateMvtPawn et calculateAtkPawn (comme pour
    // roi et reine)
    // donc je le fais
    private boolean[][] calculateMvtAtkPawn(int posX, int posY, boolean mvtAtk, Piece piece) {

        Pawn pawn = (Pawn) piece;

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
                        if (board.doesCaseContainPieceOfTeam(posX, posY, !pawn.getTeam())) {
                            realMvtAtkPawn[i][j] = true;
                        }
                    }
                }

            }
        }
        return realMvtAtkPawn;
    }

    // chevalier
    private boolean[][] calculateMvtAtkKnight(int posX, int posY, boolean mvtAtk, Piece piece) {
        Knight knight = (Knight) piece;
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
                        if (board.doesCaseContainPieceOfTeam(posX, posY, !knight.getTeam())) {
                            realMvtAtkKnight[i][j] = true;
                        }
                    }
                }
            }
        }

        return realMvtAtkKnight;
    }

    // bishop, reine, roi
    private boolean[][] calculateMvtAtkCross(int posX, int posY, int reach, boolean mvtAtk, Piece piece) {
        Bishop bishop = (Bishop) piece;
        boolean realMvtAtkCross[][] = initializePreviews();
        return realMvtAtkCross; // initializer
    }

    // tour, reine, roi
    private boolean[][] calculateMvtAtkPlus(int posX, int posY, int reach, boolean mvtAtk, Piece piece) {
        Rook rook = (Rook) piece;

        boolean realMvtAtkPlus[][] = initializePreviews();
        int n = posX - 1, s = posX + 1, w = posY - 1, e = posY + 1;

        // si on cherche le mouvement
        if (mvtAtk) {
            // si on cherche le roi
            if (reach == 1) {
                realMvtAtkPlus[n][posY] = !board.doesCaseContainPiece(n, posY);
                realMvtAtkPlus[s][posY] = !board.doesCaseContainPiece(s, posY);
                realMvtAtkPlus[posX][w] = !board.doesCaseContainPiece(posX, w);
                realMvtAtkPlus[posX][e] = !board.doesCaseContainPiece(posX, e);
            }
            // si on cherche la reine
            else {
                // on va vers le HAUT (NORD)
                while (n >= 0) {
                    if (board.doesCaseContainPiece(n, posY)) {
                        break;
                    }
                    realMvtAtkPlus[n][posY] = true;
                    n--;
                }
                // on va vers le BAS (SUD)
                while (s < 8) {
                    if (board.doesCaseContainPiece(s, posY)) {
                        break;
                    }
                    realMvtAtkPlus[s][posY] = true;
                    s++;
                }
                // on va vers la GAUCHE (WEST)
                while (w >= 0) {
                    if (board.doesCaseContainPiece(posX, w)) {
                        break;
                    }
                    realMvtAtkPlus[posX][w] = true;
                    w--;
                }
                // on va vers la droite (est)
                while (e < 8) {
                    if (board.doesCaseContainPiece(posX, e)) {
                        break;
                    }
                    realMvtAtkPlus[posX][e] = true;
                    e++;
                }
            }

        }
        // si on cherche l'attaque ATK
        else {
            // on cherche le roi
            if (reach == 1) {
                realMvtAtkPlus[n][posY] = board.doesCaseContainPieceOfTeam(n, posY, !rook.getTeam());
                realMvtAtkPlus[s][posY] = board.doesCaseContainPieceOfTeam(s, posY, !rook.getTeam());
                realMvtAtkPlus[posX][w] = board.doesCaseContainPieceOfTeam(posX, w, !rook.getTeam());
                realMvtAtkPlus[posX][e] = board.doesCaseContainPieceOfTeam(posX, e, !rook.getTeam());
            }
            // si on cherche la reine
            else {
                // on va vers le HAUT (nord)
                while (n >= 0) {
                    // si la case contient une piece de l'autre team, case preview = true + on casse
                    // la boucle
                    if (board.doesCaseContainPieceOfTeam(n, posY, !rook.getTeam())) {
                        realMvtAtkPlus[n][posY] = true;
                        break;
                    }
                    n--;
                }
                // on va vers le BAS (SUD)
                while (s < 8) {
                    if (board.doesCaseContainPieceOfTeam(s, posY, !rook.getTeam())) {
                        realMvtAtkPlus[s][posY] = true;
                        break;
                    }
                    s++;
                }
                // on va vers la GAUCHE (WEST)
                while (w >= 0) {
                    if (board.doesCaseContainPieceOfTeam(posX, w, !rook.getTeam())) {
                        realMvtAtkPlus[posX][w] = true;
                        break;
                    }
                    w--;
                }
                // on va vers la droite (est)
                while (e < 8) {
                    if (board.doesCaseContainPieceOfTeam(posX, w, !rook.getTeam())) {
                        realMvtAtkPlus[posX][e] = true;
                        break;
                    }
                    e--;
                }
            }

        }

        return realMvtAtkPlus; // initializer
    }

    // reine, roi
    // ceci appelle les resultats de plus + cross
    private boolean[][] calculateMvtAtkPlusCross(int posX, int posY, int reach, boolean mvtAtk, Piece piece) {
        boolean[][] realMvtCross = initializePreviews();
        boolean[][] realMvtPlus = initializePreviews();

        boolean realMvtAtkCross[][] = initializePreviews();

        // esto no sirve
        if (piece.getChessName() == "king") {
            King king = (King) piece;

            realMvtCross = calculateMvtAtkCross(posX, posY, reach, mvtAtk, piece);
            realMvtPlus = calculateMvtAtkCross(posX, posY, reach, mvtAtk, piece);
        }
        if (piece.getChessName() == "queen") {
            Queen queen = (Queen) piece;

            realMvtCross = calculateMvtAtkCross(posX, posY, reach, mvtAtk, piece);
            realMvtPlus = calculateMvtAtkCross(posX, posY, reach, mvtAtk, piece);
        }

        // si les tableaux de booleens de plus et cross sont true, on les melange
        // dans un autre tableau
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                realMvtAtkCross[i][j] = realMvtCross[i][j] || realMvtPlus[i][j];
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
