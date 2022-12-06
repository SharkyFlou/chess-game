package model;
public class Pawn extends FirstMovement{

    public Pawn(boolean newTeam) {
        super(newTeam,
        "pawn",
        1,
        "pawn"
        );
        
    }

    public Boolean[][] getTheoricalMvmt(int posX, int posY) { //very short and easy for the class

        Boolean[][] canGoTo = super.initTabFalse();

        int way = super.getTeam() ? 1 : -1;

        if(posY+(1*way)<8 && posY+(1*way)>=0){
            canGoTo[posY+(1*way)][posX]=true;
        }

        if(!hasItMoved()){
            canGoTo[posY+(2*way)][posX]=true;
        }

        return canGoTo;
    }

}
