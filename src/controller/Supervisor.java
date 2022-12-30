package controller;

import model.Board;
import model.CheckChecker;
import model.Mover;
import model.Manager;

public class Supervisor {
    private Board board;
    private Mover mover;
    private Manager manager;
    private CheckChecker checkChecker;
    private int lastClickedPiecePosY=0;
    private int lastClickedPiecePosX=0;

    //on donne au supervisor le Mover, le Board, le manager, et le checkChecker
    public void addLinks(Board gaveBoard, Mover gaveMover, Manager gaveManager, CheckChecker gaveCheckChecker) {
        board = gaveBoard;
        mover = gaveMover;
        manager=gaveManager;
        checkChecker = gaveCheckChecker;
    }

    //est appelé par GameFacade, fait les test pour savoir comment réagire
    public boolean clickedOnSomeCase(int posY, int posX, boolean team) {

        //les prints ci dessous sont ici pour le debug : temporaire
        if(board.getPiece(posY, posX)==null){
            System.out.println("The team "+
                (team ? "white" : "black")+
                " clicked on nothing in "+
                posY+";"+posX);

        }
        else{
            System.out.println("The team "+
                (team ? "white" : "black")+
                " clicked on the "+
                (board.getPiece(posY, posX).getTeam() ? "white " : "black ")+
                board.getPiece(posY, posX).getChessName()+
                " in "+
                posY+";"+posX);
        }

       

        //si le joueur veut manger une piece adverse
        if(mover.isCasePreviewAtk(posY, posX)){ 
            //vide les preview APRES avoir testé si le joueur à cliqué dessus
            mover.emptyPreviews();

            System.out.println("Deplacement de : "+lastClickedPiecePosY+";"+lastClickedPiecePosX+" vers "+posY+";"+posX);

            //rajoute les points
            manager.addPoints(team, board.getPiece(posY, posX).getValue());

            //mange la piece ennmie
            board.destroyPiece(posY, posX, false);

            //deplace la piece
            board.movePiece(lastClickedPiecePosY, lastClickedPiecePosX, posY, posX, false);

            //verifie si le prochain joueur est en echec, en pat, ou en echec et mat
            //selon peut deja lock des pieces, finir la partie, mettre en fluo le roi
            doTheChecks(!team);

            return true;
        }

        //si le joueur veut deplacer sa piece 
        if(mover.isCasePreviewMvt(posY, posX)){ 
            //vide les preview APRES avoir testé si le joueur à cliqué dessus
            mover.emptyPreviews();

            System.out.println("Deplacement de : "+lastClickedPiecePosY+";"+lastClickedPiecePosX+" vers "+posY+";"+posX);

            //deplace la piece
            board.movePiece(lastClickedPiecePosY, lastClickedPiecePosX, posY, posX, false);

            //verifie si le prochain joueur est en echec, en pat, ou en echec et mat
            //selon peut deja lock des pieces, finir la partie, mettre en fluo le roi
            doTheChecks(!team);
            
            return true;
        }

        //vide les preview APRES avoir testé si le joueur à cliqué dessus
        mover.emptyPreviews();

        //si la pièce est bloqué, fin
        if(checkChecker.isLocked(posY, posX)){
            return false;
        }

        //si le joueur clique sur une de ses pièces
        if(board.doesCaseContainPiece(posY, posX) && board.doesCaseContainPieceOfTeam(posY, posX, team)){
            //calcul les mouvements et attaque possible de la piece
            //et filtre les mouvement reelement possible ne mettant plus le roi en echec
            mover.calculateRealMvt(posY, posX, true);
            mover.calculateRealAtk(posY, posX, true);
            
            System.out.println("Enregistrement dernière piece : "+posY+";"+posX);
            //enregistre la position de la pièce
            lastClickedPiecePosX= posX;
            lastClickedPiecePosY = posY;
        }
        
        System.out.println("---------------------------------------------------");
        return false;
    }

    private void doTheChecks(boolean currentTeam){
        if(!checkChecker.isCheck(currentTeam)){ //si pas d'echec
            if(checkChecker.isPat(currentTeam, false)){ //sans preview des lockedpiece
                System.out.println("Pat !");
            }
            else{
                System.out.println("Rien !");
            }
            }
        else{ //si echec
            if(checkChecker.isPat(currentTeam, true)){ //+ preview des lockedpieces
                System.out.println("Echec et mat !");
            }
            else{
                checkChecker.highlightKing(currentTeam);
                System.out.println("Echec !");
            }
            //met en surbrillance le roi
        }
    }
}