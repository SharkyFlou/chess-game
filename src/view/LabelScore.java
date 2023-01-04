package view;

import model.ScoreObserver;

import java.awt.Dimension;

import javax.swing.*;

public class LabelScore extends JLabel implements ScoreObserver {
    private boolean team;
    private String name;

    public LabelScore(boolean gaveTeam, String gaveName){
        name = gaveName;
        team = gaveTeam;
        new JLabel("Score de "+ name + " : 0",SwingConstants.LEFT);
        setAlignmentY(BOTTOM_ALIGNMENT);
        setPreferredSize(new Dimension(150,50));
    }

    public void changeScore(int pts, boolean xTeam){
        if(xTeam == team){
            setText("Score de "+ name + " : "+pts);
            System.out.println("Changement pts pour "+ name +" de "+pts);
        }
    }
}
