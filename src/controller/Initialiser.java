package controller;

import model.Board;
import model.CheckChecker;
import model.PointsManager;
import model.Mover;
import view.*;

public class Initialiser {
    public void LaunchStartMenu() {
        StartScreen screen = new StartScreen(this);
    }

    public void LaunchGame(String whiteName, String blackName) {
        // instantiation des classes
        Supervisor supervisor = new Supervisor();
        supervisor.initialiser = this;
        PointsManager ptsManager = new PointsManager();

        Board board = new Board();

        GameFacade gameFacade = new GameFacade(supervisor);

        LabelScore lblWht = new LabelScore(true, whiteName);
        LabelScore lblBlk = new LabelScore(false, blackName);

        DisplayBoard display = new DisplayBoard(gameFacade, board, lblWht, lblBlk);
        // vu que Supervisor n'a aucune communication avec DisplayBoard, on peut de même
        // stocker les noms des joueurs
        // pour apres pouvoir appeler l'ecran de fin depuis cette première classe
        supervisor.name1 = whiteName;
        supervisor.name2 = blackName;
        display.setNames(whiteName, blackName);

        CheckChecker checkChecker = new CheckChecker(board);

        Mover mover = new Mover(board, checkChecker);

        // ajout de lien entre les classe à supervisor
        supervisor.addLinks(board, mover, ptsManager, checkChecker);

        // ajout des observeurs
        checkChecker.addObsersver(display);

        ptsManager.addObsersver(lblWht);
        ptsManager.addObsersver(lblBlk);

        mover.addObs(display);
        board.addObs(display);

        // creation du board
        board.initBoard();
    }
}
