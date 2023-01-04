package controller;
import model.Board;
import model.CheckChecker;
import model.Manager;
import model.Mover;
import view.*;

public class Initialiser {
    public void LaunchStartMenu(){
        StartScreen screen = new StartScreen(this);
    }

    public void LaunchGame(String whiteName, String blackName){
        //instantiation des classes
        Supervisor supervisor = new Supervisor();

        Manager manager = new Manager();

        Board board = new Board();
        

        GameFacade gameFacade = new GameFacade(supervisor);

        LabelScore lblWht = new LabelScore(true, whiteName);
        LabelScore lblBlk = new LabelScore(false, blackName);

        DisplayBoard display = new DisplayBoard(gameFacade, board, lblWht, lblBlk);
        display.setNames(whiteName, blackName);

        CheckChecker checkChecker = new CheckChecker(board);

        Mover mover = new Mover(board, checkChecker);
        
        //ajout de lien entre les classe à supervisor
        supervisor.addLinks(board, mover, manager, checkChecker);

        //ajout des observeurs
        checkChecker.addObsersver(display);

        manager.addObsersver(lblWht);
        manager.addObsersver(lblBlk);

        mover.addObs(display);
        board.addObs(display);

        //creation du board
        board.initBoard();
    }
}
