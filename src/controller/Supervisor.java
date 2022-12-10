package controller;

import model.Board;
import model.Mover;

public class Supervisor {
    private Board board;
    private Mover mover;
    private int lastClickedPiecePosY;
    private int lastClickedPiecePosX;

    public Supervisor() {

    }

    public void addBoardMover(Board gaveBoard, Mover gaveMover) {
        board = gaveBoard;
        mover = gaveMover;
    }

    public boolean clickedOnSomeCase(int posX, int posY, boolean team) {
        if(board.getPiece(posY, posX)==null){
            System.out.println("The team "+
                (team ? "white" : "black")+
                " clicked on nothing in "+
                posX+";"+posY);

        }
        else{
            System.out.println("The team "+
                (team ? "white" : "black")+
                " clicked on the "+
                (board.getPiece(posY, posX).getTeam() ? "white " : "black ")+
                board.getPiece(posY, posX).getChessName()+
                " in "+
                posX+";"+posY);
        }


        if(mover.isCasePreviewAtk(posX, posY)){
            mover.emptyPreviews();
            board.destroyPiece(posX, posY);
            board.movePiece(lastClickedPiecePosY, lastClickedPiecePosX, posY, posX);
            return true;
        }

        if(mover.isCasePreviewMvt(posX, posY)){
            mover.emptyPreviews();
            board.movePiece(lastClickedPiecePosY, lastClickedPiecePosX, posY, posX);
            return true;
        }

        mover.emptyPreviews();

        if(board.doesCaseContainPiece(posX, posY) && board.doesCaseContainPieceOfTeam(posX, posY, team)){
            mover.calculateRealAtk(posX, posY);
            mover.calculateRealMvt(posX, posY);
        }
        

        return false;
    }
}