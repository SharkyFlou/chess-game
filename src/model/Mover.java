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
        System.out.println("la");
        Pawn pawn = (Pawn) piece;

        boolean realMvtAtkPawn[][] = initializePreviews();

        boolean theoreticalMvt[][] = pawn.getTheoricalMvt(posY, posX);
        boolean theoreticalAttack[][] = pawn.getTheoreticalAttack(posY, posX);

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                // SI ON CHERCHE LE BOOLEAN[][] MVT
                if (mvtAtk) {
                    // le fait d'avoir bouge est deja verifie dans le mouvement theorique
                    if (theoreticalMvt[i][j]) {
                        // s'il y a pas de piece sur la position theorique mvt
                        realMvtAtkPawn[i][j] = !board.doesCaseContainPiece(i, j);
                    }
                }
                // SI ON CHERCHE LE BOOLEAN[][] ATK
                else {
                    if (theoreticalAttack[i][j]) {
                        // s'il y a une piece de l'autre team (!team)
                        realMvtAtkPawn[i][j] = board.doesCaseContainPieceOfTeam(i, j, !pawn.getTeam());

                    }
                }

            }
        }

        /*
         * for (int i = 0; i < 8; i++) {
         * for (int j = 0; j < 8; j++) {
         * if (realMvtAtkPawn[i][j]) {
         * System.out.print("x");
         * } else {
         * System.out.print(".");
         * }
         * }
         * System.out.print("\n");
         * }
         */

        return realMvtAtkPawn;
    }

    // chevalier
    private boolean[][] calculateMvtAtkKnight(int posY, int posX, boolean mvtAtk, Piece piece) {
        Knight knight = (Knight) piece;
        // initialise a false
        boolean realMvtAtkKnight[][] = initializePreviews();
        boolean[][] theoreticalMvtAtk = knight.getTheoricalMvt(posY, posX);

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                // regarde chaque position vraie theoriquement (bool=true)
                if (theoreticalMvtAtk[i][j]) {
                    // SI ON CHERCHE LE BOOLEAN[][] MVT
                    if (mvtAtk) {
                        // si aucune piece existe a cette position
                        realMvtAtkKnight[i][j] = !board.doesCaseContainPiece(i, j);
                    }
                    // SI ON CHERCHE LE BOOLEAN[][] ATK
                    else {
                        realMvtAtkKnight[i][j] = board.doesCaseContainPieceOfTeam(i, j, !knight.getTeam());
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

        if (mvtAtk) {
            if (reach == 1) {
                if (posY - 1 >= 0 && posX - 1 >= 0)
                    realMvtAtkCross[posY - 1][posX - 1] = true;
                if (posY - 1 >= 0 && posX + 1 < 8)
                    realMvtAtkCross[posY - 1][posX + 1] = true;
                if (posY - 1 >= 0 && posX - 1 >= 0)
                    realMvtAtkCross[posY - 1][posX - 1] = true;
                if (posY - 1 >= 0 && posX + 1 < 8)
                    realMvtAtkCross[posY - 1][posX + 1] = true;
            } else {

            }
        }

        return realMvtAtkCross; // initializer

    }

    // tour, reine, roi
    private boolean[][] calculateMvtAtkPlus(int posY, int posX, int reach, boolean mvtAtk, Piece piece) {
        Rook rook = (Rook) piece;

        boolean realMvtAtkPlus[][] = initializePreviews();

        int n = -1, s = 8, w = -1, e = 8;
        if (posY - 1 >= 0)
            n = posY - 1;
        if (posY + 1 >= 0)
            s = posY + 1;
        if (posX - 1 >= 0)
            w = posX - 1;
        if (posX + 1 >= 0)
            e = posX + 1;

        // si on cherche le mouvement
        if (mvtAtk) {
            // si on cherche le roi
            if (reach == 1) {
                if (posY - 1 >= 0)
                    realMvtAtkPlus[posY - 1][posX] = !board.doesCaseContainPiece(posY - 1, posX);
                if (posY + 1 < 8)
                    realMvtAtkPlus[posY + 1][posX] = !board.doesCaseContainPiece(posY + 1, posX);
                if (posX - 1 >= 0)
                    realMvtAtkPlus[posY][posX - 1] = !board.doesCaseContainPiece(posY, posX - 1);
                if (posX + 1 < 8)
                    realMvtAtkPlus[posY][posX + 1] = !board.doesCaseContainPiece(posY, posX + 1);
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
            // si on cherche le roi
            if (reach == 1) {
                if (posY - 1 >= 0)
                    realMvtAtkPlus[posY - 1][posX] = board.doesCaseContainPieceOfTeam(posY - 1, posX, !rook.getTeam());
                if (posY + 1 < 8)
                    realMvtAtkPlus[posY + 1][posX] = board.doesCaseContainPieceOfTeam(posY + 1, posX, !rook.getTeam());
                if (posX - 1 >= 0)
                    realMvtAtkPlus[posY][posX - 1] = board.doesCaseContainPieceOfTeam(posY, posX - 1, !rook.getTeam());
                if (posX + 1 < 8)
                    realMvtAtkPlus[posY][posX + 1] = board.doesCaseContainPieceOfTeam(posY, posX + 1, !rook.getTeam());
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

        // King king = (King) piece;

        // pour king et queen (le reach est specifique a chacun de toute facon, et passé
        // en param)
        Bishop bishop = new Bishop(piece.getTeam());
        Rook rook = new Rook(piece.getTeam());

        realMvtCross = calculateMvtAtkCross(posY, posX, reach, mvtAtk, bishop);
        realMvtPlus = calculateMvtAtkCross(posY, posX, reach, mvtAtk, rook);

        /*
         * if (piece.getChessName() == "queen") {
         * Queen queen = (Queen) piece;
         * 
         * realMvtCross = calculateMvtAtkCross(posY, posX, reach, mvtAtk, piece);
         * realMvtPlus = calculateMvtAtkCross(posY, posX, reach, mvtAtk, piece);
         * }
         */

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
