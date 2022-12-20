package model;

import javax.swing.border.Border;

public class CheckChecker {
    private Board board;

    public CheckChecker(Board gaveBoard){
        board=gaveBoard;
    }

    public boolean isCheck(boolean team){
        Board boardTemp = copyBoard();
        return isCheck(team, boardTemp);
    }


    //est ce que l'équipde "team" est en echec
    public boolean isCheck(boolean team , Board gaveBoard){
        Board boardTemp=gaveBoard;
        Mover moverTemp = new Mover(boardTemp);


        int[] coordsKing = findKing(team,gaveBoard);
        //parcours le plateau
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                //si la piece est une piece "ennemi"
                if(boardTemp.doesCaseContainPiece(i, j) && boardTemp.getPiece(i, j).getTeam() != team){
                    //calcul de l'attaque de la piece
                    moverTemp.calculateRealAtk(i, j);
                    //si le roi est "contenu" dans l'attaque de la piece
                    if(isPosInTab(coordsKing[0],coordsKing[1], moverTemp.getAtk())){
                        return true;
                    }
                }
            }            
        }


        return false;
    }


    public boolean isPat(boolean team){
        Board tempBoard = copyBoard();
        Mover moverTemp = new Mover(tempBoard);
        //int[] coordsKing = findKing(team);

        //parcours le plateau
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                //si la piece est une piece "allié"
                if(tempBoard.doesCaseContainPiece(i, j) && tempBoard.getPiece(i, j).getTeam() == team){
                    //calcul de l'attaque de la piece
                    moverTemp.calculateRealMvt(i, j);
                    boolean[][] tabMvt = moverTemp.getMvt();
                    //calcul de les mouvements de la piece
                    moverTemp.calculateRealAtk(i, j);
                    boolean[][] tabAtk = moverTemp.getAtk();
                    //fusionne pour tout les deplacements possible de la piece
                    boolean[][] tabMoves = mergeTab(tabMvt,tabAtk);
                    System.out.println("Mvmt possible de la piece en "+i+";"+j);
                    for(int k = 0; k < 8; k ++){
                        for(int l = 0; l < 8 ; l ++){
                            if(tabMoves[k][l]){
                                System.out.print("X");
                            }
                            else{
                                System.out.print("_");
                            }
                            
                        }
                        System.out.print("\n");
                    }



                    //si le roi n'est PAS toujours en echec quel que soit le mouvement de la piece
                    if(!browseTabCheckCheck(tabMoves, moverTemp, i, j, team)){
                        return false;
                    }
                }
            }            
        }
        return true;
    }

    //parcours tout les deplacements possible d'une piece, et regarde si le roi est toujours en echec
    public boolean browseTabCheckCheck(boolean[][] tab, Mover mover, int posY, int posX, boolean team){

        Board boardToMove;
        for(int k = 0; k < 8; k ++){
            for(int l = 0; l < 8 ; l ++){
                boardToMove = copyBoard();
                if(tab[k][l]){
                    boardToMove.movePiece(posY, posX, k, l);
                    if(!isCheck(team,boardToMove)){
                        return false;
                    }
                }
            }
        }

        //le roi est tout le temps en echec
        return true;
    }


    public boolean[][] mergeTab(boolean[][] tab1, boolean[][] tab2){
        boolean[][] newTab = new boolean[8][8];
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                newTab[i][j]=tab1[i][j] || tab2[i][j];
            }
        }

        return newTab;
    }

    public boolean isPosInTab(int posY, int PosX, boolean[][] tab){
        return tab[posY][PosX];
    }

    public boolean[][] giveMvtWithoutCheck(){

        return null;
    }

    public boolean[][] giveAtkWithoutCheck(){

        return null;
    }

    public Board copyBoard(){
        Board newBoard = new Board();

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                newBoard.setPiece(board.getPiece(i, j), i, j); //copie le board
            }            
        }

        return newBoard;
    }

    public int[] findKing(boolean team, Board gaveBoard){
        int[] coords = new int[2];
        coords[0]=-1;
        coords[1]=-1;

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if(gaveBoard.doesCaseContainPiece(i, j) && gaveBoard.getPiece(i, j).getChessName()=="king" && gaveBoard.getPiece(i, j).getTeam() == team){
                    coords[0]=i;
                    coords[1]=j;
                }
            }            
        }

        return coords;
    }
}
