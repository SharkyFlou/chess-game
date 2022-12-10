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

    public boolean isCasePreviewMvt(int posY, int posX) {
        return casesPreviewMvt[posY][posX];
    }

    public boolean isCasePreviewAtk(int posY, int posX) {
        return casesPreviewAtk[posY][posX];
    }

    // reset les tableaux de retour
    public void emptyPreviews() {
        casesPreviewAtk = initializePreviews();
        casesPreviewMvt = initializePreviews();
        notifyEraseDisplays();
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

    public void calculateRealMvt(int posY, int posX) {
        Piece currentPieceMvt = board.getPiece(posY, posX);

        if (currentPieceMvt.getChessName() == "pawn")
            casesPreviewMvt = calculateMvtAtkPawn(posY, posX, true, currentPieceMvt);
        // true correspond a mvt
        // si reach != 1, alors reach == 0
        else if (currentPieceMvt.getChessName() == "knight")
            casesPreviewMvt = calculateMvtAtkKnight(posY, posX, true, currentPieceMvt);

        else if (currentPieceMvt.getChessName() == "bishop")
            casesPreviewMvt = calculateMvtAtkCross(posY, posX, 0, true, currentPieceMvt);

        else if (currentPieceMvt.getChessName() == "rook")
            casesPreviewMvt = calculateMvtAtkPlus(posY, posX, 0, true, currentPieceMvt);

        // entre ces deux pieces suivantes, seulement le reach change
        else if (currentPieceMvt.getChessName() == "king")
            casesPreviewMvt = calculateMvtAtkPlusCross(posY, posX, 1, true, currentPieceMvt);

        else if (currentPieceMvt.getChessName() == "queen")
            casesPreviewMvt = calculateMvtAtkPlusCross(posY, posX, 0, true, currentPieceMvt);

        notifyDisplayMvt();

    }

    public void calculateRealAtk(int posY, int posX) {
        Piece currentPieceAtk = board.getPiece(posY, posX);
        if (currentPieceAtk.getChessName() == "pawn")
            casesPreviewAtk = calculateMvtAtkPawn(posY, posX, false, currentPieceAtk);

        else if (currentPieceAtk.getChessName() == "knight")
            casesPreviewMvt = calculateMvtAtkKnight(posY, posX, false, currentPieceAtk);

        else if (currentPieceAtk.getChessName() == "bishop")
            casesPreviewMvt = calculateMvtAtkCross(posY, posX, 0, false, currentPieceAtk);

        else if (currentPieceAtk.getChessName() == "rook")
            casesPreviewMvt = calculateMvtAtkPlus(posY, posX, 0, false, currentPieceAtk);

        // entre ces deux pieces suivantes, seulement le reach change
        else if (currentPieceAtk.getChessName() == "king")
            casesPreviewMvt = calculateMvtAtkPlusCross(posY, posX, 1, false, currentPieceAtk);

        else if (currentPieceAtk.getChessName() == "queen")
            casesPreviewMvt = calculateMvtAtkPlusCross(posY, posX, 0, false, currentPieceAtk);

        notifyDisplayAtk();
    }

    // GERE LES MOUVEMENTS

    // boolean mvtAtk = correspond a
    // true = mvt
    // false = atk

    // honestly on peut melanger calculateMvtPawn et calculateAtkPawn (comme pour
    // roi et reine)
    // donc je le fais
    private boolean[][] calculateMvtAtkPawn(int posY, int posX, boolean mvtAtk, Piece piece) {

        Pawn pawn = (Pawn) piece;

        boolean realMvtAtkPawn[][] = initializePreviews();

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                // SI ON CHERCHE LE BOOLEAN[][] MVT
                if (mvtAtk) {
                    // le fait d'avoir bouge est deja verifie dans le mouvement theorique
                    if (pawn.getTheoricalMvt(posY, posX)[i][j]) {
                        // s'il y a pas de piece sur la position theorique mvt
                        if (!board.doesCaseContainPiece(i, j)) {
                            realMvtAtkPawn[i][j] = true;
                        }
                    }
                }
                // SI ON CHERCHE LE BOOLEAN[][] ATK
                else {
                    if (pawn.getTheoreticalAttack(posY, posX)[i][j]) {
                        // s'il y a une piece de l'autre team (!team)
                        if (board.doesCaseContainPieceOfTeam(posY, posX, !pawn.getTeam())) {
                            realMvtAtkPawn[i][j] = true;
                        }
                    }
                }

            }
        }
        return realMvtAtkPawn;
    }

    // chevalier
    private boolean[][] calculateMvtAtkKnight(int posY, int posX, boolean mvtAtk, Piece piece) {
        Knight knight = (Knight) piece;
        // initialise a false
        boolean realMvtAtkKnight[][] = initializePreviews();

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                // regarde chaque position vraie theoriquement (bool=true)
                if (knight.getTheoricalMvt(posY, posX)[i][j]) {

                    // SI ON CHERCHE LE BOOLEAN[][] MVT
                    if (mvtAtk) {
                        // si aucune piece existe a cette position
                        if (!board.doesCaseContainPiece(i, j)) {
                            realMvtAtkKnight[i][j] = true;
                        }
                    }
                    // SI ON CHERCHE LE BOOLEAN[][] ATK
                    else {
                        if (board.doesCaseContainPieceOfTeam(posY, posX, !knight.getTeam())) {
                            realMvtAtkKnight[i][j] = true;
                        }
                    }
                }
            }
        }

        return realMvtAtkKnight;
    }

    // bishop, reine, roi
    private boolean[][] calculateMvtAtkCross(int posY, int posX, int reach, boolean mvtAtk, Piece piece) {
        Bishop bishop = (Bishop) piece;
        boolean realMvtAtkCross[][] = initializePreviews();
        return realMvtAtkCross; // initializer
    }

    // tour, reine, roi
    private boolean[][] calculateMvtAtkPlus(int posY, int posX, int reach, boolean mvtAtk, Piece piece) {
        Rook rook = (Rook) piece;

        boolean realMvtAtkPlus[][] = initializePreviews();
        int n = posY - 1, s = posY + 1, w = posX - 1, e = posX + 1;

        // si on cherche le mouvement
        if (mvtAtk) {
            // si on cherche le roi
            if (reach == 1) {
                realMvtAtkPlus[n][posX] = !board.doesCaseContainPiece(n, posX);
                realMvtAtkPlus[s][posX] = !board.doesCaseContainPiece(s, posX);
                realMvtAtkPlus[posY][w] = !board.doesCaseContainPiece(posY, w);
                realMvtAtkPlus[posY][e] = !board.doesCaseContainPiece(posY, e);
            }
            // si on cherche la reine
            else {
                // on va vers le HAUT (NORD)
                while (n >= 0) {
                    if (board.doesCaseContainPiece(n, posX)) {
                        break;
                    }
                    realMvtAtkPlus[n][posX] = true;
                    n--;
                }
                // on va vers le BAS (SUD)
                while (s < 8) {
                    if (board.doesCaseContainPiece(s, posX)) {
                        break;
                    }
                    realMvtAtkPlus[s][posX] = true;
                    s++;
                }
                // on va vers la GAUCHE (WEST)
                while (w >= 0) {
                    if (board.doesCaseContainPiece(posY, w)) {
                        break;
                    }
                    realMvtAtkPlus[posY][w] = true;
                    w--;
                }
                // on va vers la droite (est)
                while (e < 8) {
                    if (board.doesCaseContainPiece(posY, e)) {
                        break;
                    }
                    realMvtAtkPlus[posY][e] = true;
                    e++;
                }
            }

        }
        // si on cherche l'attaque ATK
        else {
            // on cherche le roi
            if (reach == 1) {
                realMvtAtkPlus[n][posX] = board.doesCaseContainPieceOfTeam(n, posX, !rook.getTeam());
                realMvtAtkPlus[s][posX] = board.doesCaseContainPieceOfTeam(s, posX, !rook.getTeam());
                realMvtAtkPlus[posY][w] = board.doesCaseContainPieceOfTeam(posY, w, !rook.getTeam());
                realMvtAtkPlus[posY][e] = board.doesCaseContainPieceOfTeam(posY, e, !rook.getTeam());
            }
            // si on cherche la reine
            else {
                // on va vers le HAUT (nord)
                while (n >= 0) {
                    // si la case contient une piece de l'autre team, case preview = true + on casse
                    // la boucle
                    if (board.doesCaseContainPieceOfTeam(n, posX, !rook.getTeam())) {
                        realMvtAtkPlus[n][posX] = true;
                        break;
                    }
                    n--;
                }
                // on va vers le BAS (SUD)
                while (s < 8) {
                    if (board.doesCaseContainPieceOfTeam(s, posX, !rook.getTeam())) {
                        realMvtAtkPlus[s][posX] = true;
                        break;
                    }
                    s++;
                }
                // on va vers la GAUCHE (WEST)
                while (w >= 0) {
                    if (board.doesCaseContainPieceOfTeam(posY, w, !rook.getTeam())) {
                        realMvtAtkPlus[posY][w] = true;
                        break;
                    }
                    w--;
                }
                // on va vers la droite (est)
                while (e < 8) {
                    if (board.doesCaseContainPieceOfTeam(posY, w, !rook.getTeam())) {
                        realMvtAtkPlus[posY][e] = true;
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
    private boolean[][] calculateMvtAtkPlusCross(int posY, int posX, int reach, boolean mvtAtk, Piece piece) {
        boolean[][] realMvtCross = initializePreviews();
        boolean[][] realMvtPlus = initializePreviews();

        boolean realMvtAtkCross[][] = initializePreviews();

        // esto no sirve
        if (piece.getChessName() == "king") {
            King king = (King) piece;

            realMvtCross = calculateMvtAtkCross(posY, posX, reach, mvtAtk, piece);
            realMvtPlus = calculateMvtAtkCross(posY, posX, reach, mvtAtk, piece);
        }
        if (piece.getChessName() == "queen") {
            Queen queen = (Queen) piece;

            realMvtCross = calculateMvtAtkCross(posY, posX, reach, mvtAtk, piece);
            realMvtPlus = calculateMvtAtkCross(posY, posX, reach, mvtAtk, piece);
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
