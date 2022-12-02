import controller.GameFacade;
import controller.Supervisor;
import view.*;


public class Main {
    public static void main(String[] args) {
        Supervisor supervisor = new Supervisor();
        GameFacade gameFacade = new GameFacade();
        DisplayGame display = new DisplayGame(supervisor,gameFacade);


        

    }
}
