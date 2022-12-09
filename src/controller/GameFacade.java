package controller;

public class GameFacade {
    private boolean nextToPlay;
    private Supervisor supervisor;

    public GameFacade(Supervisor gaveSupervisor){
        nextToPlay=true; //les blancs commencent
        supervisor=gaveSupervisor;
    }

    public void clickedOnSomeCase(int posX, int posY){
        if(supervisor.clickedOnSomeCase(posX, posY, nextToPlay)){
            nextToPlay=!nextToPlay;
        }
    }
}
