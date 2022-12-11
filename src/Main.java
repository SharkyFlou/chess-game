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

        board.initBoard();

        supervisor.addBoardMover(board, mover);

        // Piece test = board[0][1];
        /*
         * for (int i = 0; i < 8; i++) {
         * for (int j = 0; j < 8; j++) {
         * if (test.getTheoricalMvt(0, 1)[i][j])
         * System.out.println("X");
         * else
         * System.out.println(" ");
         * }
         * System.out.println("\n");
         * }
         */
    }
}
