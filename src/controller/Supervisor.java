package controller;

import model.Board;
import model.Mover;

public class Supervisor {
    private Board board;
    private Mover mover;

    public Supervisor(){
        
    }

    public void addBoardMover(Board gaveBoard, Mover gaveMover){
        board = gaveBoard;
        mover = gaveMover;
    }


    public boolean clickedOnSomeCase(int posX, int posY, boolean team){
        return false;
    }
}
