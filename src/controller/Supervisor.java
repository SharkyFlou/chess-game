package controller;

import model.Board;
import model.Mover;

public class Supervisor {
    private Board board;
    private Mover mover;
    private int lastClickedPiecePosY=0;
    private int lastClickedPiecePosX=0;

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


        if(mover.isCasePreviewAtk(posY, posX)){
            mover.emptyPreviews();
            System.out.println("Deplacement de : "+lastClickedPiecePosY+";"+lastClickedPiecePosX+" vers "+posY+";"+posX);
            board.destroyPiece(posY, posX);
            board.movePiece(lastClickedPiecePosY, lastClickedPiecePosX, posY, posX);
            return true;
        }

        if(mover.isCasePreviewMvt(posY, posX)){
            mover.emptyPreviews();
            System.out.println("Deplacement de : "+lastClickedPiecePosY+";"+lastClickedPiecePosX+" vers "+posY+";"+posX);
            board.movePiece(lastClickedPiecePosY, lastClickedPiecePosX, posY, posX);
            return true;
        }

        mover.emptyPreviews();

        if(board.doesCaseContainPiece(posY, posX) && board.doesCaseContainPieceOfTeam(posY, posX, team)){
            mover.calculateRealMvt(posY, posX);
            mover.calculateRealAtk(posY, posX);
            System.out.println("Enregistrement dernière piece : "+posY+";"+posX);
            lastClickedPiecePosX= posX;
            lastClickedPiecePosY = posY;
        }
        
        System.out.println("---------------------------------------------------");
        return false;
    }
}