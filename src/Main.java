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
        Board board = new Board(manager);

        Mover mover = new Mover(board);

        DisplayBoard display = new DisplayBoard(gameFacade, board);
        board.addObs(display);
        mover.addObs(display);

        supervisor.addBoardMover(board, mover);

        board.initBoard();
    }
}
