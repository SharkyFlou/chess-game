package controller;

public class GameFacade {
    private boolean nextToPlay;
    private Supervisor supervisor;

    public GameFacade(Supervisor gaveSupervisor){
        nextToPlay=true; //les blancs commencent
        supervisor=gaveSupervisor;
    }

    public void clickedOnSomeCase(int posY, int posX){
        if(supervisor.clickedOnSomeCase(posY, posX, nextToPlay)){ //si qlq chose c'est passé : deplacement / attaque
            nextToPlay=!nextToPlay; //au prochain de jouer
        }
    }
}
