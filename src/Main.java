import controller.GameFacade;
import controller.Supervisor;
import model.Board;
import model.CheckChecker;
import model.Manager;
import model.Mover;
import view.*;

public class Main {
    public static void main(String[] args) {
        //instantiation des classes
        Supervisor supervisor = new Supervisor();

        Manager manager = new Manager();

        Board board = new Board();

        GameFacade gameFacade = new GameFacade(supervisor);

        LabelScore lblWht = new LabelScore(true);
        LabelScore lblBlk = new LabelScore(false);

        DisplayBoard display = new DisplayBoard(gameFacade, board, lblWht, lblBlk);

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
