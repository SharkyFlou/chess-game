package model;

import java.util.ArrayList;
import java.util.List;

public class Board {
    private Piece board[][] = new Piece[8][8];

    // la raison par laquelle on a deux observers differents (au lieu d'avoir un
    // seul observer) est car la nouvelle fenetre promotion n'a besoin que d'une
    // fonction displayOptions et ne doit pas implementer les fonctions du
    // BoardObserver
    private List<BoardObserver> listObs = new ArrayList<BoardObserver>();
    // private List<PromotionObserver> listObsProm = new
    // ArrayList<PromotionObserver>();

    private int[] coordsPawnBigJump = new int[2];

    public Board() {
        coordsPawnBigJump[0] = -1;
        coordsPawnBigJump[1] = -1;
    }

    // initialise le baord avec les pièces aux bon endroits
    public void initBoard() {

        for (int i = 0; i < 8; i++) { // pas beau mais ça marche¯\_(ツ)_/¯
            for (int j = 0; j < 8; j++) {
                if (i > 1 && i < 6) { // toutes les cases vides au centre
                    board[i][j] = null;
                } else if (i == 1) { // pion noir
                    board[i][j] = new Pawn(false);
                } else if (i == 6) { // pion blanc
                    board[i][j] = new Pawn(true);
                } else if (i == 0 && (j == 0 || j == 7)) { // tour noire
                    board[i][j] = new Rook(false);
                } else if (i == 7 && (j == 0 || j == 7)) { // tour blanche
                    board[i][j] = new Rook(true);
                } else if (i == 0 && (j == 1 || j == 6)) { // chevalier noir
                    board[i][j] = new Knight(false);
                } else if (i == 7 && (j == 1 || j == 6)) { // chevalier blanc
                    board[i][j] = new Knight(true);
                } else if (i == 0 && (j == 2 || j == 5)) { // fou noir
                    board[i][j] = new Bishop(false);
                } else if (i == 7 && (j == 2 || j == 5)) { // fou blanc
                    board[i][j] = new Bishop(true);
                } else if (i == 0 && j == 3) { // rien noir
                    board[i][j] = new Queen(false);
                } else if (i == 7 && j == 3) { // rien blanche
                    board[i][j] = new Queen(true);
                } else if (i == 0 && j == 4) { // roi noir
                    board[i][j] = new King(false);
                } else if (i == 7 && j == 4) { // roi blanc
                    board[i][j] = new King(true);
                } else { // ne va pas là, pas forcement utile
                    board[i][j] = null;
                }
            }
        }
        notifyMov();
    }

    // retourne la piece du X et Y demandé
    public Piece getPiece(int posY, int posX) {
        if (posX < 0 || posX > 7 || posY < 0 || posY > 7) {
            System.out.println("Trying to access a piece out of the board : " + posX +
                    ";" + posY);
            return null;
        }
        return board[posY][posX];
    }



    // retourne vrai si la case contient une pièce, faux sinon
    public boolean doesCaseContainPiece(int posY, int posX) {
        return this.getPiece(posY, posX) != null;
    }

    // retourne vrai si la case contient une pièce de l'équipe, faux sinon et si
    // vide
    public boolean doesCaseContainPieceOfTeam(int posY, int posX, boolean team) {
        if (!doesCaseContainPiece(posY, posX)) { // pour ne pas planter
            return false;
        }
        return this.getPiece(posY, posX).getTeam() == team;
    }

    // deplace la piece à partir de ses anciennes et noubelles positions
    // si calc est true, c'est que movePiece est appellé depuis ChechChecker, et que
    // le first Mvmt ne doit pas être appele
    // s'occupe maintenant aussi du roque
    public void movePiece(int oldPosY, int oldPosX, int newPosY, int newPosX, boolean isCalc) {
        if (doesCaseContainPiece(oldPosY, oldPosX)) {
            Piece pieceBougee = getPiece(oldPosY, oldPosX);
            destroyPiece(oldPosY, oldPosX, true);
            board[newPosY][newPosX] = pieceBougee;

            // gere le premier mouvement
            if (!isCalc && (pieceBougee.getChessName() == "pawn" ||
                    pieceBougee.getChessName() == "rook" ||
                    pieceBougee.getChessName() == "king")) {
                FirstMovement pieceFirstMovement = (FirstMovement) pieceBougee;
                pieceFirstMovement.mooveIt();
            }
        }
        notifyMov();

        // si la piece ayant bougé est un roi s'étant deplacé en X d'au moins 2 cases,
        // c'est un roque !
        if (getPiece(newPosY, newPosX).getChessName() == "king" && (oldPosX - newPosX > 1 || oldPosX - newPosX < -1)) {
            // grand roque
            if (newPosX == 2) {
                movePiece(newPosY, 0, newPosY, 3, isCalc);
            }
            // petit roque
            else {
                movePiece(newPosY, 7, newPosY, 5, isCalc);
            }
        }

        // si c'est un gros saut de pawn
        if (getPiece(newPosY, newPosX).getChessName() == "pawn" && (oldPosY - newPosY > 1 || oldPosY - newPosY < -1)) {
            coordsPawnBigJump[0] = newPosY;
            coordsPawnBigJump[1] = newPosX;
        } else {
            coordsPawnBigJump[0] = -1;
            coordsPawnBigJump[1] = -1;
        }
    }

    public int[] getCoordsPawnBigJump() {
        return coordsPawnBigJump;
    }

    // supprime la pièce aux coordonnées envoyées
    // le boolean toMoveIt, permet de savoir si c'est pour "prendre" une piece, ou
    // seulement en deplacer une autre
    // besoin d'avoir l'équipe afin de réagir correctement à la prise en passant
    public void destroyPiece(int posY, int posX, boolean toMoveIt) {
        if (doesCaseContainPiece(posY, posX)) {
            boolean teamPiece = getPiece(posY, posX).getTeam();
            if (toMoveIt) { // si c'est pour deplacer, on appelle la notif changement d'équipe
                notifyChangeTeam(!teamPiece);
            } else {
                notifyPieceTaken(getPiece(posY, posX));
            }
            board[posY][posX] = null;
        } else {
            System.out.println("Destruction d'une piece non existante en : " + posY + ";" + posX);
        }

        notifyMov();
    }

    public void changePiecePromotion(int posY, int posX, boolean team, String pieceACreer) {
        // on ecrase la piece
        if (pieceACreer == "queen") {
            Queen queen = new Queen(team);
            board[posY][posX] = queen;
        } else if (pieceACreer == "rook") {
            Rook rook = new Rook(team);
            board[posY][posX] = rook;
        } else if (pieceACreer == "bishop") {
            Bishop bishop = new Bishop(team);
            board[posY][posX] = bishop;
        } else if (pieceACreer == "knight") {
            Knight knight = new Knight(team);
            board[posY][posX] = knight;
        }
    }

    public boolean canGetPromoted(int lastClickedPiecePosY, int lastClickedPiecePosX, int posY, int posX) {
        if ((getPiece(lastClickedPiecePosY, lastClickedPiecePosX).getTeam() && posY == 0)
                || (!getPiece(lastClickedPiecePosY, lastClickedPiecePosX).getTeam() && posY == 7)) {
            return true;
        }
        return false;
    }

    // ajoute l'observeur
    public void addObs(BoardObserver obs) {
        listObs.add(obs);
    }

    /*
     * // ajoute l'observeur prom
     * public void addObsProm(PromotionObserver prom) {
     * listObsProm.add(prom);
     * }
     */

    // avertit les observeur de mettre à jour l'échiquier
    public void notifyMov() {
        for (BoardObserver obs : listObs) {
            obs.displayGame();
        }
    }

    // avertit les observeur qu'une pièce a été prise
    public void notifyPieceTaken(Piece piece) {
        for (BoardObserver obs : listObs) {
            obs.displayPieceTaken(piece);
        }
    }

    public void notifyChangeTeam(boolean newTeam) {
        for (BoardObserver obs : listObs) {
            obs.displayTeamToPlay(newTeam);
        }
    }

    // init pour le CheckChecker
    public void setPiece(Piece piece, int posY, int posX) {
        board[posY][posX] = piece;
    }

}
