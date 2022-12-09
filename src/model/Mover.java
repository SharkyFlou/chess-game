package model;

public class Mover {
    private boolean casesPreviewMvt[][];
    private boolean casesPreviewAtk[][];

    public boolean isCasePreviewMvt(int posX, int posY) {
        return true;
    }

    public boolean isCasePreviewAtk(int posX, int posY) {
        return true;
    }

    public void emptyPreviews() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                casesPreviewMvt[i][j] = false;
                casesPreviewAtk[i][j] = false;
            }
        }
    }

    public void calculateRealMvt(int posX, int posY) {

    }

    public void calculateRealAtk(int posX, int posY) {

    }

    public boolean[][] calculateMvtPawn(int posX, int posY) {
        boolean realMvtPawn[][];
        return realMvtPawn; // initializer
    }

    public boolean[][] calculateAtkPawn(int posX, int posY) {
        boolean realAtkPawn[][];
        return realAtkPawn; // initializer
    }

    public boolean[][] calculateMvtAtkPlus(int posX, int posY, int reach) {
        boolean realMvtAtkPlus[][];
        return realMvtPawn; // initializer
    }

}
