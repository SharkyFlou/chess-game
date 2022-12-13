package view;

import model.ScoreObserver;
import javax.swing.*;

public class LabelScore extends JLabel implements ScoreObserver {
    private boolean team;

    public LabelScore(boolean gaveTeam){
        team = gaveTeam;
        new JLabel("Score des "+ (team ? "blancs": "noirs")+ " : 0");
    }

    public void changeScore(int pts, boolean xTeam){
        if(xTeam == team){
            setText("Score des "+ (team ? "blancs": "noirs")+ " : "+pts);
            System.out.println("Changement pts pour "+(xTeam ? "blanc" : "noir")+" de "+pts);
        }
    }
}
