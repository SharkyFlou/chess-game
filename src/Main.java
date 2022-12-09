import controller.GameFacade;
import controller.Supervisor;
import model.Board;
import model.Mover;
import view.*;

public class Main {
    public static void main(String[] args) {
        Supervisor supervisor = new Supervisor();
        GameFacade gameFacade = new GameFacade(supervisor);
        Board board = new Board();

        Mover mover = new Mover(board);

        DisplayGame display = new DisplayGame(supervisor, gameFacade, board);
        board.addObs(display);
        mover.addObs(display);

        board.initBoard();
        display.resetChessBoardColor();

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
