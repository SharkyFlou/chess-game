import controller.GameFacade;
import controller.Supervisor;
import model.Board;
import view.*;


public class Main {
    public static void main(String[] args) {
        Supervisor supervisor = new Supervisor();
        GameFacade gameFacade = new GameFacade();
        Board board = new Board();
        board.initBoard();
        DisplayGame display = new DisplayGame(supervisor,gameFacade,board);
        display.resetChessBoardColor();
        
 
        
    }
}
