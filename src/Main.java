import controller.GameFacade;
import controller.Supervisor;
import model.Board;
import model.Knight;
import view.*;

public class Main {
    public static void main(String[] args) {
        
        Board board = new Board();

        

        DisplayGame display = new DisplayGame(supervisor, gameFacade, board);
        board.addObs(display);

        board.initBoard();
        display.resetChessBoardColor();

        Supervisor supervisor = new Supervisor();
        GameFacade gameFacade = new GameFacade();

        // Piece test = board[0][1];
        /*
         * for (int i = 0; i < 8; i++) {
         * for (int j = 0; j < 8; j++) {
         * if (test.getTheoricalMvmt(0, 1)[i][j])
         * System.out.println("X");
         * else
         * System.out.println(" ");
         * }
         * System.out.println("\n");
         * }
         */
    }
}
