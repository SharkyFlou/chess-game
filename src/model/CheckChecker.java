package model;

import java.util.ArrayList;
import java.util.List;

public class CheckChecker {
    private Board board;
    private boolean[][] lockedPieces = new boolean[8][8];
    private List<LockedObserver> listObs = new ArrayList<LockedObserver>();

    private boolean[][][][] giantFilter = new boolean[8][8][8][8];

    public CheckChecker(Board gaveBoard) {
        board = gaveBoard;
        resetGiantFilter();
    }

    // fonction écran du "vrai" isCheck, accès de l'exterieur
    public boolean isCheck(boolean team) {
        Board boardTemp = copyBoard(board);
        return isCheck(team, boardTemp);
    }

    // est ce que l'équipe "team" est en echec
    private boolean isCheck(boolean team, Board gaveBoard) {
        Board boardTemp = gaveBoard;
        Mover moverTemp = new Mover(boardTemp, null);

        int[] coordsKing = findKing(team, gaveBoard);
        // parcours le plateau
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                // si la piece est une piece "ennemi"
                if (boardTemp.doesCaseContainPiece(i, j) && boardTemp.getPiece(i, j).getTeam() != team) {
                    // calcul de l'attaque de la piece
                    moverTemp.calculateRealAtk(i, j, false);
                    // si le roi est "contenu" dans l'attaque de la piece
                    if (isPosInTab(coordsKing[0], coordsKing[1], moverTemp.getAtk())) {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    private void resetGiantFilter() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                for (int k = 0; k < 8; k++) {
                    for (int l = 0; l < 8; l++) {
                        giantFilter[i][j][k][l] = true;
                    }
                }
            }
        }
    }

    public boolean isPat(boolean team, boolean lockPieces) {
        notifyReset();
        resetGiantFilter();
        Board tempBoard = copyBoard(board);
        Mover moverTemp = new Mover(tempBoard, null);
        boolean finalReturn = true;
        lockedPieces = initializePreviews();
        // parcours le plateau
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                // si la piece est une piece "allié"
                if (tempBoard.doesCaseContainPiece(i, j) && tempBoard.getPiece(i, j).getTeam() == team) {
                    // calcul de l'attaque de la piece
                    moverTemp.calculateRealMvt(i, j, false);
                    boolean[][] tabMvt = moverTemp.getMvt();
                    // calcul de les mouvements de la piece
                    moverTemp.calculateRealAtk(i, j, false);
                    boolean[][] tabAtk = moverTemp.getAtk();
                    // fusionne pour tout les deplacements possible de la piece
                    boolean[][] tabMoves = mergeTab(tabMvt, tabAtk);

                    // si le roi n'est PAS toujours en echec quel que soit le mouvement de la piece
                    // ne sort pas directement car il faut pouvoir "lock" les pieces, et leur
                    // empecher d'effectuer des mouvement mettant / laissant le roi en echec
                    if (!browseTabCheckCheck(tabMoves, moverTemp, i, j, team)) {
                        finalReturn = false;
                    } else if (lockPieces) {
                        lockedPieces[i][j] = true;
                    }

                    // test approfondi si les rock sont possible
                    // si c'est le roi et qu'il est en position X 4
                    if (tempBoard.getPiece(i, j).getChessName() == "king" && j == 4) {
                        // si le roi est deja en echec
                        if (lockPieces) {
                            disableCastle(i, true, true);
                            continue;
                        }
                        // si grand roque possible
                        if (tabMoves[i][2]) {
                            // vérification si le mouvement intérmédiaire met en echec le roi
                            Board testBoard = copyBoard(tempBoard);
                            testBoard.movePiece(i, j, i, j - 1, true);
                            if (isCheck(team, testBoard)) {
                                disableCastle(i, true, false);
                            }
                        }
                        // si petit roque possible
                        if (tabMoves[i][6]) {
                            // vérification si le mouvement intérmédiaire met en echec le roi
                            Board testBoard = copyBoard(tempBoard);
                            testBoard.movePiece(i, j, i, j + 1, true);
                            if (isCheck(team, testBoard)) {
                                disableCastle(i, false, true);
                            }
                        }

                    }
                }
            }
        }

        // si le roi est en echec, on affiche les piece ne pouvant faire aucun mouvement
        // / aucun mouvement debloquant le roi de son echec
        if (lockPieces) {
            notifyChangeLocked();
        }
        return finalReturn;
    }

    private void disableCastle(int posY, boolean queenside, boolean kingsinde) {
        if (queenside) {
            giantFilter[posY][4][posY][2] = false;
        }
        if (kingsinde) {
            giantFilter[posY][4][posY][6] = false;
        }
    }

    // parcours tout les deplacements possible d'une piece, et regarde si le roi est
    // toujours en echec
    private boolean browseTabCheckCheck(boolean[][] tab, Mover mover, int posY, int posX, boolean team) {

        boolean returnValue = true;
        Board boardToMove;
        // parcours tout les déplacement possible de la piece
        for (int k = 0; k < 8; k++) {
            for (int l = 0; l < 8; l++) {
                boardToMove = copyBoard(board);
                if (tab[k][l]) {
                    boardToMove.movePiece(posY, posX, k, l, true);
                    if (!isCheck(team, boardToMove)) {
                        returnValue = false;
                    } else {
                        giantFilter[posY][posX][k][l] = false;
                    }
                }
            }
        }

        // le roi est tout le temps en echec
        return returnValue;
    }

    // fisionne deux tableau de booléen
    private boolean[][] mergeTab(boolean[][] tab1, boolean[][] tab2) {
        boolean[][] newTab = new boolean[8][8];
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                newTab[i][j] = tab1[i][j] || tab2[i][j];
            }
        }

        return newTab;
    }

    private boolean isPosInTab(int posY, int PosX, boolean[][] tab) {
        return tab[posY][PosX];
    }

    // permet de copier le board, afin de modifer un autre board temporaire
    private Board copyBoard(Board toCopy) {
        Board newBoard = new Board();

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                newBoard.setPiece(toCopy.getPiece(i, j), i, j); // copie le board
            }
        }

        return newBoard;
    }

    // renvoie les coordonnées du roi de l'équipe "team" demandé
    private int[] findKing(boolean team, Board gaveBoard) {
        int[] coords = new int[2];
        coords[0] = -1;
        coords[1] = -1;

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (gaveBoard.doesCaseContainPiece(i, j) && gaveBoard.getPiece(i, j).getChessName() == "king"
                        && gaveBoard.getPiece(i, j).getTeam() == team) {
                    coords[0] = i;
                    coords[1] = j;
                }
            }
        }

        return coords;
    }

    // met en surbrillance le roi
    public void highlightKing(boolean team) {
        int[] coordsKing = findKing(team, board);
        notifyKingCheck(coordsKing[0], coordsKing[1]);
    }

    public void addObsersver(LockedObserver obs) {
        listObs.add(obs);
    }

    private void notifyChangeLocked() {
        for (LockedObserver obs : listObs) {
            obs.displayLocked(lockedPieces);
        }
    }

    private void notifyKingCheck(int posY, int posX) {
        for (LockedObserver obs : listObs) {
            obs.displayKing(posY, posX);
        }
    }

    // supprime les preview des pièces bloqués, et du roi en echec
    private void notifyReset() {
        for (LockedObserver obs : listObs) {
            obs.erasePreviewsLock();
        }
    }

    // initialise les tableaux de retour a true
    private boolean[][] initializePreviews() {
        boolean[][] initializer = new boolean[8][8];
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                initializer[i][j] = false;
            }
        }
        return initializer;
    }

    public boolean isLocked(int posY, int posX) {
        return lockedPieces[posY][posX];
    }

    public boolean[][] filterOutImpossibleMoves(int posY, int posX, boolean[][] moves) {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                moves[i][j] = moves[i][j] && giantFilter[posY][posX][i][j];
            }
        }
        return moves;
    }

}
