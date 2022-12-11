package model;

import java.util.*;  

public class Manager {
    private int whitePts;
    private int blackPts;
    private List<ScoreObserver> observers;

    public Manager(){
        whitePts=0;
        blackPts=0;
    }

    public void addPoints(boolean team, int pts){
        if(team){
            whitePts+=pts;
        }
        else{
            blackPts+=pts;
        }
    }

    public int getPoints(boolean team){
        if(team){
            return whitePts;
        }
        else{
            return blackPts;
        }
    }

    public void addObsersver(ScoreObserver obs){
        observers.add(obs);
    }

}
