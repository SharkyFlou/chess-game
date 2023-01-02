package controller;

import model.Board;
import model.Mover;
import model.Manager;

public class Supervisor {
    private Board board;
    private Mover mover;
    private Manager manager;
    private int lastClickedPiecePosY = 0;
    private int lastClickedPiecePosX = 0;

    public Supervisor() {

    }

    // on donne au supervisor le Mover et le Board
    public void addBoardMover(Board gaveBoard, Mover gaveMover, Manager gaveManager) {
        board = gaveBoard;
        mover = gaveMover;
        manager = gaveManager;
    }

    // est appelé par GameFacade, fait les test pour savoir comment réagit
    public boolean clickedOnSomeCase(int posY, int posX, boolean team) {

        // les prints ci dessous sont ici pour le debug : temporaire
        if (board.getPiece(posY, posX) == null) {
            System.out.println("The team " +
                    (team ? "white" : "black") +
                    " clicked on nothing in " +
                    posY + ";" + posX);

        } else {
            System.out.println("The team " +
                    (team ? "white" : "black") +
                    " clicked on the " +
                    (board.getPiece(posY, posX).getTeam() ? "white " : "black ") +
                    board.getPiece(posY, posX).getChessName() +
                    " in " +
                    posY + ";" + posX);
        }

        // si le joueur veut manger une piece adverse
        if (mover.isCasePreviewAtk(posY, posX)) {
            mover.emptyPreviews();
            System.out.println("Deplacement de : " + lastClickedPiecePosY + ";" + lastClickedPiecePosX + " vers " + posY
                    + ";" + posX);
            manager.addPoints(team, board.getPiece(posY, posX).getValue());

            // si on vient de bouger un pawn noir à la posY==7 || un pawn blanc à la posY==0
            // on passe au manager qui va gérer la promotion

            if (board.getPiece(lastClickedPiecePosY, lastClickedPiecePosX).getChessName() == "pawn"
                    && board.canGetPromoted(lastClickedPiecePosY, lastClickedPiecePosX, posY, posX)) {
                // c'est ici qu'on devra gérer la pièce choisie
                // Queen queen = new Queen(board.getPiece(lastClickedPiecePosY,
                // lastClickedPiecePosX).getTeam())
                board.changePiecePromotion(posY, posX,
                        board.getPiece(lastClickedPiecePosY, lastClickedPiecePosX).getTeam());
                board.destroyPiece(lastClickedPiecePosY, lastClickedPiecePosX, true);
            }
            // sinon on gère normalement la piece qui vient de bouger
            else {
                board.destroyPiece(posY, posX, false);
                board.movePiece(lastClickedPiecePosY, lastClickedPiecePosX, posY, posX);
            }
            return true;
        }

        // si le joueur veut deplacer sa piece
        if (mover.isCasePreviewMvt(posY, posX)) {
            mover.emptyPreviews();
            System.out.println("Deplacement de : " + lastClickedPiecePosY + ";" + lastClickedPiecePosX + " vers " + posY
                    + ";" + posX);
            if (board.getPiece(lastClickedPiecePosY, lastClickedPiecePosX).getChessName() == "pawn"
                    && board.canGetPromoted(lastClickedPiecePosY, lastClickedPiecePosX, posY, posX)) {
                // c'est ici qu'on devra gérer la pièce choisie
                // Queen queen = new Queen(board.getPiece(lastClickedPiecePosY,
                // lastClickedPiecePosX).getTeam())
                board.changePiecePromotion(posY, posX,
                        board.getPiece(lastClickedPiecePosY, lastClickedPiecePosX).getTeam());
                board.destroyPiece(lastClickedPiecePosY, lastClickedPiecePosX, true);
            } // si on fait un mouvement normal
            else {
                board.movePiece(lastClickedPiecePosY, lastClickedPiecePosX, posY, posX);
            }

            return true;
        }

        mover.emptyPreviews();

        // si le joueur clique sur une de ses pièces
        if (board.doesCaseContainPiece(posY, posX) && board.doesCaseContainPieceOfTeam(posY, posX, team)) {
            mover.calculateRealMvt(posY, posX);
            mover.calculateRealAtk(posY, posX);
            System.out.println("Enregistrement dernière piece : " + posY + ";" + posX);
            // enregistre la position de la pièce
            lastClickedPiecePosX = posX;
            lastClickedPiecePosY = posY;
        }

        System.out.println("---------------------------------------------------");
        return false;
    }
}