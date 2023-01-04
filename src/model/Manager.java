package model;

import java.util.*;

public class Manager {
    private int whitePts;
    private int blackPts;
    private List<ScoreObserver> observers;

    public Manager() {
        observers = new ArrayList<ScoreObserver>();
        whitePts = 0;
        blackPts = 0;
    }

    public void addPoints(boolean team, int pts) {
        if (team) {
            whitePts += pts;
        } else {
            blackPts += pts;
        }
        notifyChangeScore((team ? whitePts : blackPts), team);
    }

    public int getPoints(boolean team) {
        if (team) {
            return whitePts;
        } else {
            return blackPts;
        }
    }

    /*
     * public boolean managePromotion(boolean team) {
     * Queen queen = new Queen(team);
     * 
     * return true;
     * }
     */

    public void addObsersver(ScoreObserver obs) {
        observers.add(obs);
    }

    public void notifyChangeScore(int pts, boolean team) {
        for (ScoreObserver obs : observers) {
            System.out.println("Changement de score");
            obs.changeScore(pts, team);
        }
    }


}
