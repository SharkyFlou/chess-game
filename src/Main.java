import controller.GameFacade;
import controller.Supervisor;
import model.Board;
import model.Manager;
import model.Mover;
import view.*;

public class Main {
    public static void main(String[] args) {
        Supervisor supervisor = new Supervisor();
        GameFacade gameFacade = new GameFacade(supervisor);
        Manager manager = new Manager();
        Board board = new Board();

        Mover mover = new Mover(board);

        LabelScore lblWht = new LabelScore(true);
        LabelScore lblBlk = new LabelScore(false);
        DisplayBoard display = new DisplayBoard(gameFacade, board, lblWht, lblBlk);
        board.addObs(display);
        mover.addObs(display);

        supervisor.addBoardMover(board, mover, manager);

        manager.addObsersver(lblWht);
        manager.addObsersver(lblBlk);

        board.initBoard();
    }
}
