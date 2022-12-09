package model;
import java.util.ArrayList;
import java.util.List;

public class Mover {
    private Boolean casesPreviewMvmt[][];
    private Boolean casesPreviewAtk[][];
    private List<PreviewObserver> listObs = new ArrayList<PreviewObserver>();
    private Board board;


    public Mover(Board gaveBoard){
        board=gaveBoard;
    }



    public void addObs(PreviewObserver obs){
        listObs.add(obs);
    }

    public void notifyEraseDisplays(){
        for (PreviewObserver obs : listObs) {
            obs.erasePreviews();
        }
    }

    public void notifyDisplayAtk(Piece piece){
        for (PreviewObserver obs : listObs) {
            obs.displayPreviewAtk(casesPreviewAtk);
        }
    }

    public void notifyDisplayMvmt(Piece piece){
        for (PreviewObserver obs : listObs) {
            obs.displayPreviewMvmt(casesPreviewMvmt);
        }
    }

}
