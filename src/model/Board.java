package model;
import java.util.ArrayList;
import java.util.List;

public class Board {
    private Piece board[][] = new Piece[8][8];
    List<BoardObserver> listObs = new ArrayList<BoardObserver>();


    public void initBoard() {

        for (int i = 0; i < 8; i++) { // not pretty, but works pretty well ¯\_(ツ)_/¯
            for (int j = 0; j < 8; j++) {
                if (i > 1 && i < 6) { // all the empy cases in the middle
                    board[i][j] = null;
                } else if (i == 1) { // black pawns
                    board[i][j] = new Pawn(true);
                } else if (i == 6) { // white pawns
                    board[i][j] = new Pawn(false);
                } else if (i == 0 && (j == 0 || j == 7)) { // black rooks
                    board[i][j] = new Rook(true);
                } else if (i == 7 && (j == 0 || j == 7)) { // white rooks
                    board[i][j] = new Rook(false);
                } else if (i == 0 && (j == 1 || j == 6)) { // black knights
                    board[i][j] = new Knight(true);
                } else if (i == 7 && (j == 1 || j == 6)) { // white knights
                    board[i][j] = new Knight(false);
                } else if (i == 0 && (j == 2 || j == 5)) { // black bishops
                    board[i][j] = new Bishop(true);
                } else if (i == 7 && (j == 2 || j == 5)) { // white bishops
                    board[i][j] = new Bishop(false);
                } else if (i == 0 && j == 3) { // black queen
                    board[i][j] = new Queen(true);
                } else if (i == 7 && j == 3) { // white queen
                    board[i][j] = new Queen(false);
                } else if (i == 0 && j == 4) { // black king
                    board[i][j] = new King(true);
                } else if (i == 7 && j == 4) { // white king
                    board[i][j] = new King(false);
                } else { // dont go in it
                    board[i][j] = null;
                }
            }
        }
        notifyMov();
    }


    public Piece getPiece(int posY,int posX){
        if(posX < 0 || posX > 7 || posY < 0 || posY > 7){
            System.out.println("Trying to access a piece out of the board : "+posX+";"+posY);
            return null;
        }
        return board[posY][posX];
    }

    public boolean doesCaseContainPiece(int posX, int posY) {
        if (this.getPiece(posX, posY) != null)
            return true;
        return false;
    }



    public boolean doesCaseContainPieceOfTeam(int posX, int posY, boolean team) {
        if (this.getPiece(posX, posY).getTeam() == team) {
            return true;
        }
        return false;
    }

    public void movePiece(int oldPosX, int oldPosY, int newPosX, int newPosY) {
        if (doesCaseContainPiece(oldPosX, oldPosY)) {
            Piece pieceBougee = getPiece(oldPosX, oldPosY);
            destroyPiece(oldPosX, oldPosY);
            board[newPosX][newPosY] = pieceBougee;
            // gere le premier mouvement
            if (pieceBougee.getChessName() == "pawn" ||
                    pieceBougee.getChessName() == "rook" ||
                    pieceBougee.getChessName() == "king") {
                /*
                 * if (!pieceBougee.hasItMoved())
                 * (FirstMovement)pieceBougee.hasMoved = true;
                 */
            }
        }
    }

    public void destroyPiece(int posX, int posY) {
        if(board[posX][posY]!=null){
            notifyPieceTaken(board[posX][posY]);
        }
        else{
            System.out.println("Trying to delete a non existent piece : "+posX+";"+posY);
        }
        
        board[posX][posY] = null;
    }

    public void addObs(BoardObserver obs){
        listObs.add(obs);
    }

    public void notifyMov(){
        for (BoardObserver obs : listObs) {
            obs.displayGame();
        }
    }

    public void notifyPieceTaken(Piece piece){
        for (BoardObserver obs : listObs) {
            obs.displayPieceTaken(piece);
        }
    }

}
