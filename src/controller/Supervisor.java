package controller;

import model.Board;
import model.CheckChecker;
import model.Mover;
import model.PointsManager;
import view.EndScreen;
import view.PromotionWindow;

public class Supervisor {
    private Board board;
    private Mover mover;
    private PointsManager manager;
    private CheckChecker checkChecker;
    private int lastClickedPiecePosY = 0;
    private int lastClickedPiecePosX = 0;

    // promotion
    public String pieceProm = "";
    public boolean hasClicked = false;
    public boolean finishedPromotion = true;

    public String name1 = "";
    public String name2 = "";
    public Initialiser initialiser;

    // on donne au supervisor le Mover, le Board, le manager, et le checkChecker
    public void addLinks(Board gaveBoard, Mover gaveMover, PointsManager gaveManager, CheckChecker gaveCheckChecker) {
        board = gaveBoard;
        mover = gaveMover;
        manager = gaveManager;
        checkChecker = gaveCheckChecker;
    }

    //

    // est appelé par GameFacade, fait les test pour savoir comment réagire
    public boolean clickedOnSomeCase(int posY, int posX, boolean team) {
        if (!finishedPromotion) {
            return false;
        }

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

        // si le joueur veut manger une piece adverse ATK
        if (mover.isCasePreviewAtk(posY, posX)) {
            // vide les preview APRES avoir testé si le joueur à cliqué dessus
            mover.emptyPreviews();

            System.out.println("Deplacement de : " + lastClickedPiecePosY + ";" + lastClickedPiecePosX + " vers " + posY
                    + ";" + posX);

            // vérifie si ce n'est pas en prise en passant
            if (board.doesCaseContainPiece(posY, posX)) {
                System.out.println("Pas prise en passant");
                // ajout des points normalement
                manager.addPoints(team, board.getPiece(posY, posX).getValue());

                // promotion while Atk
                // check si la piece qui vient d'arriver est possiblement promoted
                if (board.getPiece(lastClickedPiecePosY, lastClickedPiecePosX).getChessName() == "pawn"
                        && board.canGetPromoted(lastClickedPiecePosY, lastClickedPiecePosX, posY, posX)) {
                    // c'est ici qu'on devra gérer la pièce choisie

                    // on cree la promotion
                    PromotionWindow prom = new PromotionWindow(this);
                    // board.addObsProm(prom);
                    prom.displayPromotion(posY, posX,
                            board.getPiece(lastClickedPiecePosY, lastClickedPiecePosX).getTeam());
                    finishedPromotion = false;

                    // @@@@@@@@@@@@@ on doit bloquer les pieces

                }
                // sinon on gère normalement la piece qui vient de bouger
                else {
                    board.destroyPiece(posY, posX, false);
                    // deplace la piece
                    board.movePiece(lastClickedPiecePosY, lastClickedPiecePosX, posY, posX, false);

                }

            } else {
                // c'est une prise en passant
                // si l'équipe est blanche (true) la case tué se situe en dessous, dans le cas
                // contraire au dessus
                System.out.println("Prise en passant en " + (posY + (team ? +1 : -1)) + ";" + posX);
                manager.addPoints(team, board.getPiece(posY + (team ? +1 : -1), posX).getValue());

                // mange la piece ennmie
                // le board doit lui meme faire la vérification de la prise en passant afin que
                // les simulation pour les test d'echec fonctionne
                board.destroyPiece(posY + (team ? +1 : -1), posX, false);
                // deplace la piece
                board.movePiece(lastClickedPiecePosY, lastClickedPiecePosX, posY, posX, false);

            }

            // verifie si le prochain joueur est en echec, en pat, ou en echec et mat
            // selon peut deja lock des pieces, finir la partie, mettre en fluo le roi
            doTheChecks(!team);

            return true;
        }

        // si le joueur veut deplacer sa piece
        if (mover.isCasePreviewMvt(posY, posX)) {
            // vide les preview APRES avoir testé si le joueur à cliqué dessus
            mover.emptyPreviews();

            System.out.println("Deplacement de : " + lastClickedPiecePosY + ";" + lastClickedPiecePosX + " vers " + posY
                    + ";" + posX);

            // promotion while Mvt
            // check si la piece qui vient d'arriver est possiblement promoted
            if (board.getPiece(lastClickedPiecePosY, lastClickedPiecePosX).getChessName() == "pawn"
                    && board.canGetPromoted(lastClickedPiecePosY, lastClickedPiecePosX, posY, posX)) {
                // c'est ici qu'on devra gérer la pièce choisie

                // on cree la promotion
                PromotionWindow prom = new PromotionWindow(this);
                // board.addObsProm(prom);
                prom.displayPromotion(posY, posX, board.getPiece(lastClickedPiecePosY, lastClickedPiecePosX).getTeam());
                finishedPromotion = false;
                // @@@@@@@@@@@@@@@@@@@@@@@ ici on doit bloquer la piece

            } // si on fait un mouvement normal, on ne fait que deplacer la piece
            else {
                board.movePiece(lastClickedPiecePosY, lastClickedPiecePosX, posY, posX, false);
            }

            // verifie si le prochain joueur est en echec, en pat, ou en echec et mat
            // selon peut deja lock des pieces, finir la partie, mettre en fluo le roi
            doTheChecks(!team);

            return true;
        }

        // vide les preview APRES avoir testé si le joueur à cliqué dessus
        mover.emptyPreviews();

        // si la pièce est bloqué, fin
        if (checkChecker.isLocked(posY, posX)) {
            return false;
        }

        // si le joueur clique sur une de ses pièces
        if (board.doesCaseContainPiece(posY, posX) && board.doesCaseContainPieceOfTeam(posY, posX, team)) {
            // calcul les mouvements et attaque possible de la piece
            // et filtre les mouvement reelement possible ne mettant plus le roi en echec
            mover.calculateRealMvt(posY, posX, true);
            mover.calculateRealAtk(posY, posX, true);

            System.out.println("Enregistrement dernière piece : " + posY + ";" + posX);
            // enregistre la position de la pièce
            lastClickedPiecePosX = posX;
            lastClickedPiecePosY = posY;
        }

        System.out.println("---------------------------------------------------");
        return false;
    }

    //

    //
    public void sendPromotion(int posY, int posX, boolean team) {
        if (hasClicked) {
            if (pieceProm != "") {
                board.changePiecePromotion(posY,
                        posX,
                        board.getPiece(lastClickedPiecePosY, lastClickedPiecePosX).getTeam(),
                        pieceProm);
                board.destroyPiece(lastClickedPiecePosY, lastClickedPiecePosX, true);

                pieceProm = "";
                hasClicked = false;

                // verifie si la nouvelle piece cause un echec
                finishedPromotion = true;
                doTheChecks(!team);
            }
        }
    }

    private void doTheChecks(boolean currentTeam) {
        if (!checkChecker.isCheck(currentTeam)) { // si pas d'echec
            if (checkChecker.isPat(currentTeam, false)) { // sans preview des lockedpiece
                System.out.println("Pat !");
            } else {
                System.out.println("Rien !");
            }
        } else { // si echec
            if (checkChecker.isPat(currentTeam, true)) { // + preview des lockedpieces
                EndScreen end = new EndScreen(initialiser);

                // if(currentTeam)== si white en mat
                // on envoie black comme gagnant
                if (currentTeam) {
                    end.fillEndScreen(name2,
                            manager.getPoints(!currentTeam),
                            name1,
                            manager.getPoints(currentTeam),
                            !currentTeam);

                } // on envoie white comme gagnant
                else {
                    end.fillEndScreen(name1,
                            manager.getPoints(!currentTeam),
                            name2,
                            manager.getPoints(currentTeam),
                            !currentTeam);

                }

                // puis on doit desactiver displayBoard
                board.notifyCheckmate();
                // setActive(false);
                System.out.println("Echec et mat !");
            }
            // met en surbrillance le roi
            else {
                checkChecker.highlightKing(currentTeam);
                System.out.println("Echec !");
            }
        }
    }
}