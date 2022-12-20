package view;

import java.awt.*;
import javax.swing.*;

import java.awt.image.BufferedImage;

import controller.GameFacade;
import model.Board;
import model.Piece;
import model.PreviewObserver;
import model.BoardObserver;
import model.LockedObserver;

import java.net.URL;

public class DisplayBoard extends JFrame implements PreviewObserver, BoardObserver, LockedObserver {

    private GameFacade gameFacade;
    private Board board;

    private JPanel panelTitle;
    private JPanel panelLowLeft;
    private JPanel panelLowMid;
    private JPanel panelLowRight;
    private JPanel pnlWhitePieces;
    private JPanel pnlBlkPieces;
    private JButton[][] chessBoardButtons;
    private static final String COLS = "ABCDEFGH";
    private JLabel subtitle;
    private JLabel[] takenBlkPieces;
    private int nbrBlkPieceTaken;
    private JLabel[] takenWhtPieces;
    private int nbrWhtPieceTaken;

    final int WIDTH = 950;
    final int HEIGHT = 650;
    final int TITLEHEIGHT = 100;
    final int CHESSWIDTH = 600;
    final int SIDESWIDTH = (WIDTH - CHESSWIDTH) /2;
    final int LOWHEIGHT = HEIGHT-TITLEHEIGHT;
    final int SCOREHEIGHT = 60;
    final int PIECETAKENHEIGHT = LOWHEIGHT- SCOREHEIGHT;

    //pas beau mais plus simple et plus opti :
    private boolean[][] lockedPieces = new boolean[8][8];
    private int[] coordsKingHigh = new int[2];

    public DisplayBoard(GameFacade XgameFacade, Board xBoard, LabelScore lblWht, LabelScore lblBlk) {
        chessBoardButtons = new JButton[8][8];
        takenBlkPieces = new JLabel[15];
        takenWhtPieces = new JLabel[15];

        gameFacade = XgameFacade;
        board = xBoard;
        setTitle("Awful chessgame");
        setSize(new Dimension(WIDTH, HEIGHT));
        setMinimumSize(new Dimension(WIDTH, HEIGHT));
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        setLayout(new BorderLayout());

        //ajout panel du haut
        panelTitle = new JPanel();
        panelTitle.setBackground(Color.darkGray);
        panelTitle.setSize(new Dimension(WIDTH, TITLEHEIGHT));
        panelTitle.setMinimumSize(new Dimension(WIDTH, TITLEHEIGHT));
        panelTitle.setLayout(new GridLayout(2, 0));
        add(panelTitle,BorderLayout.NORTH);


        //panel qui contiendra les pièces conquises par le joueur blanc
        panelLowLeft = new JPanel();
        panelLowLeft.setBackground(Color.lightGray);
        panelLowLeft.setLayout(new BoxLayout(panelLowLeft, BoxLayout.Y_AXIS));
        panelLowLeft.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(panelLowLeft, BorderLayout.WEST);

        //panel qui contiendra l'échequier
        panelLowMid = new JPanel(new GridLayout(0, 10));
        panelLowMid.setBackground(Color.gray);
        panelLowMid.setSize(new Dimension(CHESSWIDTH, LOWHEIGHT+1000));
        panelLowMid.setMinimumSize(new Dimension(CHESSWIDTH, LOWHEIGHT+1000));
        add(panelLowMid, BorderLayout.CENTER);

        //panel qui contiendra les pièces conquises par le joueur noir
        panelLowRight = new JPanel();
        panelLowRight.setBackground(Color.lightGray);
        panelLowRight.setLayout(new BoxLayout(panelLowRight, BoxLayout.Y_AXIS));
        panelLowRight.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(panelLowRight, BorderLayout.EAST);


        //Ajout JLabel du titre
        JLabel title = new JLabel("Awful chessgame",SwingConstants.CENTER);
        title.setFont(new Font("Verdana", Font.PLAIN, 24));
        title.setForeground(Color.WHITE);
        panelTitle.add(title, BorderLayout.NORTH);

        //Ajout JLabel tour
        subtitle = new JLabel("Tour des blancs",SwingConstants.CENTER);
        subtitle.setFont(new Font("Verdana", Font.PLAIN, 20));
        subtitle.setForeground(Color.WHITE);
        panelTitle.add(subtitle, BorderLayout.SOUTH);

        //ajout compteur points des blancs
        lblWht.setSize(new Dimension(SIDESWIDTH, SCOREHEIGHT));
        lblWht.setMinimumSize(new Dimension(SIDESWIDTH, SCOREHEIGHT));
        panelLowLeft.add(lblWht);
        lblWht.changeScore(0, true);

        //ajout compteur points des noirs
        lblBlk.setSize(new Dimension(SIDESWIDTH, SCOREHEIGHT));
        lblBlk.setMinimumSize(new Dimension(SIDESWIDTH, SCOREHEIGHT));
        panelLowRight.add(lblBlk);
        lblBlk.changeScore(0, false);

        //ajout panel piece noires
        pnlBlkPieces= new JPanel();
        pnlBlkPieces.setLayout(new GridLayout(0,2));
        pnlBlkPieces.setSize(new Dimension(SIDESWIDTH, PIECETAKENHEIGHT));
        pnlBlkPieces.setMinimumSize(new Dimension(SIDESWIDTH, PIECETAKENHEIGHT));
        panelLowLeft.add(pnlBlkPieces);
        nbrBlkPieceTaken=0;

        //ajout panel piece blanches
        pnlWhitePieces= new JPanel();
        pnlWhitePieces.setLayout(new GridLayout(0,2));
        pnlWhitePieces.setSize(new Dimension(SIDESWIDTH, PIECETAKENHEIGHT));
        pnlWhitePieces.setMinimumSize(new Dimension(SIDESWIDTH, PIECETAKENHEIGHT));
        panelLowRight.add(pnlWhitePieces);
        nbrWhtPieceTaken=0;

        creationBoardBlackWhite(); //creation des cases
        createSidesBoards();
        setVisible(true);
    }

    public void creationBoardBlackWhite() {
        //ajout de border entre les cases, ne fonctionne pas pour l'instant
        //panelLowMid.setBorder(new EmptyBorder(5, 5, 5, 5));

        // creation des bouttons
       

        // creer le chess board
        for (int i = 0; i < chessBoardButtons.length; i++) {
            for (int j = 0; j < chessBoardButtons[i].length; j++) {
                JButton bt = new JButton();
                
                
                if ((j % 2 == 1 && i % 2 == 1) || (j % 2 == 0 && i % 2 == 0)) { //blanc
                    bt.setBackground(Color.WHITE);
                    bt.setOpaque(true);
                    bt.setBorderPainted(false);
                } else {
                    bt.setBackground(Color.DARK_GRAY); //gris foncé
                    bt.setOpaque(true);
                    bt.setBorderPainted(false);
                }

                bt.addActionListener(actionEvent -> {
                    clickedOnSomeCase(bt);
                });

                chessBoardButtons[j][i] = bt;
            }
        }

        // fill the chess board
        panelLowMid.add(new JLabel(""));
        // fill the top row
        for (int i = 0; i < 8; i++) {
            final JLabel label = new JLabel(COLS.substring(i, i + 1),SwingConstants.CENTER);
            panelLowMid.add(label);
        }

        panelLowMid.add(new JLabel(""));

        // fill the black non-pawn piece row
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 10; j++) {
                switch (j) {
                    case 0:
                        panelLowMid.add(new JLabel("" + (8 - i),
                                SwingConstants.CENTER));
                        break;
                    case 9:
                        panelLowMid.add(new JLabel("" + (8 - i),
                                SwingConstants.CENTER));
                        break;
                    default:
                        panelLowMid.add(chessBoardButtons[i][j - 1]);
                        break;

                }
            }
        }

        panelLowMid.add(new JLabel(""));
        for (int i = 0; i < 8; i++) {
            panelLowMid.add(
                    new JLabel(COLS.substring(i, i + 1),
                            SwingConstants.CENTER));
        }
    }

    public void createSidesBoards(){
        for (int i = 0; i < 15; i++) {
            takenWhtPieces[i] = new JLabel("",SwingConstants.CENTER);
            ImageIcon icon = new ImageIcon(
                            new BufferedImage(32, 32, BufferedImage.TYPE_INT_ARGB));
            takenWhtPieces[i].setIcon(icon);
            pnlWhitePieces.add(takenWhtPieces[i]);
        }

        for (int i = 0; i < 15; i++) {
            takenBlkPieces[i] = new JLabel("",SwingConstants.CENTER);
            ImageIcon icon = new ImageIcon(
                            new BufferedImage(32, 32, BufferedImage.TYPE_INT_ARGB));
            takenBlkPieces[i].setIcon(icon);
            pnlBlkPieces.add(takenBlkPieces[i]);
        }
    }

    //redirige vers facade, avec le position X et Y de la case cliquée
    public void clickedOnSomeCase(JButton jButton) {
        //parcours les cases pour savoir la quelle a été cliquée
        for (int i = 0; i < chessBoardButtons.length; i++) {
            for (int j = 0; j < chessBoardButtons[i].length; j++) {
                if (chessBoardButtons[i][j] == jButton) {
                    gameFacade.clickedOnSomeCase(i, j);
                }
            }
        }
    }

    //affichage du preview attaque
    public void displayPreviewAtk(boolean[][] caseAtk) { 
        for (int i = 0; i < chessBoardButtons.length; i++) {
            for (int j = 0; j < chessBoardButtons[i].length; j++) {
                if (caseAtk[i][j]) {
                    if ((j % 2 == 1 && i % 2 == 1) || (j % 2 == 0 && i % 2 == 0)) {
                        chessBoardButtons[i][j].setBackground(Color.decode("#B30000")); //rouge clair
                        chessBoardButtons[i][j].setOpaque(true);
                        chessBoardButtons[i][j].setBorderPainted(false);
                    } else {
                        chessBoardButtons[i][j].setBackground(Color.decode("#9B0000")); //rouge plus foncé
                        chessBoardButtons[i][j].setOpaque(true);
                        chessBoardButtons[i][j].setBorderPainted(false);
    
                    }

                }
            }
        }
    }

    //affichage du preview mouvement
    public void displayPreviewMvt(boolean[][] caseMvt) { 
        for (int i = 0; i < chessBoardButtons.length; i++) {
            for (int j = 0; j < chessBoardButtons[i].length; j++) {
                if (caseMvt[i][j]) {
                    if ((j % 2 == 1 && i % 2 == 1) || (j % 2 == 0 && i % 2 == 0)) {
                        chessBoardButtons[i][j].setBackground(Color.decode("#009B18")); //vert clair
                        chessBoardButtons[i][j].setOpaque(true);
                        chessBoardButtons[i][j].setBorderPainted(false);
    
                    } else {
                        chessBoardButtons[i][j].setBackground(Color.decode("#008A1A")); //vert plus foncé
                        chessBoardButtons[i][j].setOpaque(true);
                        chessBoardButtons[i][j].setBorderPainted(false);
    
                    }
                }
            }
        }

    }

    //vide les preview, remet les couleurs de l'échiquier à defaut
    public void erasePreviews() { 
        for (int i = 0; i < chessBoardButtons.length; i++) {
            for (int j = 0; j < chessBoardButtons[i].length; j++) {
                if(lockedPieces[i][j] || (i==coordsKingHigh[0] && j==coordsKingHigh[1])){
                    continue;
                }
                if (((j % 2 == 1 && i % 2 == 1) || (j % 2 == 0 && i % 2 == 0))) {
                    chessBoardButtons[i][j].setBackground(Color.WHITE);
                    chessBoardButtons[i][j].setOpaque(true);
                    chessBoardButtons[i][j].setBorderPainted(false);

                } else{
                    chessBoardButtons[i][j].setBackground(Color.DARK_GRAY);
                    chessBoardButtons[i][j].setOpaque(true);
                    chessBoardButtons[i][j].setBorderPainted(false);

                }
            }
        }
    }

    //parcours le board pour re-afficher les pièces
    public void displayGame() { 
        System.out.println("Reaffichage des piece");
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Piece newPiece = board.getPiece(i, j);
                if (newPiece != null) {
                    URL temp = DisplayBoard.class.getResource("/resources/" + newPiece.getImageLink() + ".png");
                    chessBoardButtons[i][j].setIcon(new ImageIcon(temp));
                } else {
                    ImageIcon icon = new ImageIcon(
                            new BufferedImage(32, 32, BufferedImage.TYPE_INT_ARGB));
                    chessBoardButtons[i][j].setIcon(icon);
                }

            }
        }
    }

    //affichera les pieces prises
    public void displayPieceTaken(Piece piece) {
        boolean team = piece.getTeam();
        URL icon = DisplayBoard.class.getResource("/resources/" + piece.getImageLink() + ".png");
        if(team){
            takenWhtPieces[nbrWhtPieceTaken].setIcon(new ImageIcon(icon));
            nbrWhtPieceTaken++;
        }
        else{
            takenBlkPieces[nbrBlkPieceTaken].setIcon(new ImageIcon(icon));
            nbrBlkPieceTaken++;
        }
    }

    //affichera les promotions lorsqu'un pion atteint le bout de l'échiquier
    public void displayPromotion(int posY, int posX) {
        

    }

    public void displayTeamToPlay(boolean team){
        subtitle.setText("Tour des "+(team ? "blancs" : "noirs"));
    }

    
    public void displayLocked(boolean[][] casesLocked) {
        lockedPieces=casesLocked;
        for (int i = 0; i < chessBoardButtons.length; i++) {
            for (int j = 0; j < chessBoardButtons[i].length; j++) {
                if (casesLocked[i][j]) {
                    if ((j % 2 == 1 && i % 2 == 1) || (j % 2 == 0 && i % 2 == 0)) {
                        chessBoardButtons[i][j].setBackground(Color.decode("#9C4100")); //orange
                        chessBoardButtons[i][j].setOpaque(true);
                        chessBoardButtons[i][j].setBorderPainted(false);
    
                    } else {
                        chessBoardButtons[i][j].setBackground(Color.decode("#1853700")); //orange foncé
                        chessBoardButtons[i][j].setOpaque(true);
                        chessBoardButtons[i][j].setBorderPainted(false);
                    }
                }
            }
        }
    }

    
    public void displayKing(int posY, int posX) {
        coordsKingHigh[0]=posY;
        coordsKingHigh[1]=posX;
        chessBoardButtons[posY][posX].setBackground(Color.decode("#A600FF")); //rose
        chessBoardButtons[posY][posX].setOpaque(true);
        chessBoardButtons[posY][posX].setBorderPainted(false);
    }

    
    public void erasePreviewsLock() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                lockedPieces[i][j] = false;
            }
        }
        coordsKingHigh[0]=-1;
        coordsKingHigh[1]=-1;
        erasePreviews();
    }

}
