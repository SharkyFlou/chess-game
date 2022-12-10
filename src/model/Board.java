package model;

import java.util.ArrayList;
import java.util.List;

public class Board {
    private Piece board[][] = new Piece[8][8];
    private Manager manager;
    List<BoardObserver> listObs = new ArrayList<BoardObserver>();

    public Board(Manager gaveManager){
        manager=gaveManager;
    }

    public void initBoard() {

        for (int i = 0; i < 8; i++) { // pas beau mais ça marche¯\_(ツ)_/¯
            for (int j = 0; j < 8; j++) {
                if (i > 1 && i < 6) { // toutes les cases vides au centre
                    board[i][j] = null;
                } else if (i == 1) { // pion noir
                    board[i][j] = new Pawn(false);
                } else if (i == 6) { // pion blanc
                    board[i][j] = new Pawn(true);
                } else if (i == 0 && (j == 0 || j == 7)) { // tour noire
                    board[i][j] = new Rook(false);
                } else if (i == 7 && (j == 0 || j == 7)) { // tour blanche
                    board[i][j] = new Rook(true);
                } else if (i == 0 && (j == 1 || j == 6)) { // chevalier noir
                    board[i][j] = new Knight(false);
                } else if (i == 7 && (j == 1 || j == 6)) { // chevalier blanc
                    board[i][j] = new Knight(true);
                } else if (i == 0 && (j == 2 || j == 5)) { // fou noir
                    board[i][j] = new Bishop(false);
                } else if (i == 7 && (j == 2 || j == 5)) { // fou blanc
                    board[i][j] = new Bishop(true);
                } else if (i == 0 && j == 3) { // rien noir
                    board[i][j] = new Queen(false);
                } else if (i == 7 && j == 3) { // rien blanche
                    board[i][j] = new Queen(true);
                } else if (i == 0 && j == 4) { // roi noir
                    board[i][j] = new King(false);
                } else if (i == 7 && j == 4) { // roi blanc
                    board[i][j] = new King(true);
                } else { // ne va pas là, pas forcement utile
                    board[i][j] = null;
                }
            }
        }
        board[5][7] = new Pawn(false);
        notifyMov();
    }

    public Piece getPiece(int posY, int posX) {
        if (posX < 0 || posX > 7 || posY < 0 || posY > 7) {
            System.out.println("Trying to access a piece out of the board : " + posX + ";" + posY);
            return null;
        }
        return board[posY][posX];
    }

    public boolean doesCaseContainPiece(int posY, int posX) {
        return this.getPiece(posY, posX) != null;
    }

    public boolean doesCaseContainPieceOfTeam(int posY, int posX, boolean team) {
        if (!doesCaseContainPiece(posY, posX)) { // pour ne pas planter
            return false;
        }
        return this.getPiece(posY, posX).getTeam() == team;
    }

    public void movePiece(int oldPosY, int oldPosX, int newPosY, int newPosX) {
        if (doesCaseContainPiece(oldPosY, oldPosX)) {
            Piece pieceBougee = getPiece(oldPosY, oldPosX);
            destroyPiece(oldPosY, oldPosX);
            board[newPosY][newPosX] = pieceBougee;

            // gere le premier mouvement
            if (pieceBougee.getChessName() == "pawn" ||
                    pieceBougee.getChessName() == "rook" ||
                    pieceBougee.getChessName() == "king") {
                FirstMovement pieceFirstMovement = (FirstMovement) pieceBougee;
                pieceFirstMovement.mooveIt();
            }
        }
        notifyMov();
    }

    public void destroyPiece(int posY, int posX) {
        if (getPiece(posY, posX) != null) {
            notifyPieceTaken(getPiece(posY, posX));
        } else {
            System.out.println("Trying to delete a non existent piece : " + posY + ";" + posX);
        }

        board[posY][posX] = null;
        notifyMov();
    }

    public void addObs(BoardObserver obs) {
        listObs.add(obs);
    }

    public void notifyMov() {
        for (BoardObserver obs : listObs) {
            obs.displayGame();
        }
    }

    public void notifyPieceTaken(Piece piece) {
        for (BoardObserver obs : listObs) {
            obs.displayPieceTaken(piece);
        }
    }

}
