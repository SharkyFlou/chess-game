package view;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;

import java.awt.image.BufferedImage;

import controller.GameFacade;
import model.Board;
import model.Piece;
import model.PreviewObserver;
import model.BoardObserver;

import java.net.URL;

public class DisplayBoard extends JFrame implements PreviewObserver, BoardObserver {

    private GameFacade gameFacade;
    private Board board;

    private JPanel panelTitle;
    private JPanel panelLow;
    private JPanel panelLowLeft;
    private JPanel panelLowMid;
    private JPanel panelLowRight;
    private JButton[][] chessBoardButtons = new JButton[8][8];
    private static final String COLS = "ABCDEFGH";

    public DisplayBoard(GameFacade XgameFacade, Board xBoard) {
        gameFacade = XgameFacade;
        board = xBoard;
        setTitle("Awfull chessgame");
        setSize(980, 800);
        setMinimumSize(new Dimension(980, 607));
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

        //ajout panel du haut
        panelTitle = new JPanel();
        panelTitle.setBackground(Color.darkGray);
        panelTitle.setSize(new Dimension(1080, 100));
        panelTitle.setMinimumSize(new Dimension(1080, 100));
        add(panelTitle);

        //ajout panel du bas contenant les trois sous panel donc l'échequier
        panelLow = new JPanel();
        panelLow.setLayout(new BoxLayout(panelLow, BoxLayout.X_AXIS));
        panelLow.setSize(new Dimension(1080, 507));
        panelLow.setMinimumSize(new Dimension(1080, 507));
        add(panelLow);

        //panel qui contiendra les pièces conquises par le joueur blanc
        panelLowLeft = new JPanel();
        panelLowLeft.setBackground(Color.lightGray);
        panelLowLeft.setSize(new Dimension(180, 507));
        panelLowLeft.setMinimumSize(new Dimension(180, 507));
        panelLow.add(panelLowLeft);

        //panel qui contiendra l'échequier
        panelLowMid = new JPanel(new GridLayout(0, 10));
        panelLowMid.setBackground(Color.gray);
        panelLowMid.setSize(new Dimension(620, 507));
        panelLowMid.setMinimumSize(new Dimension(720, 507));
        panelLow.add(panelLowMid);

        //panel qui contiendra les pièces conquises par le joueur noir
        panelLowRight = new JPanel();
        panelLowRight.setBackground(Color.lightGray);
        panelLowRight.setSize(new Dimension(720, 507));
        panelLowRight.setMinimumSize(new Dimension(720, 507));
        panelLow.add(panelLowRight);


        //Ajout JLabel du titre
        JLabel title = new JLabel();
        title.setText("Awfull chessgame");
        title.setFont(new Font("Verdana", Font.PLAIN, 24));
        title.setForeground(Color.WHITE);
        panelTitle.add(title);


        creationBoardBlackWhite(); //creation des cases
        setVisible(true);
    }

    public void creationBoardBlackWhite() {

        //ajout de border entre les cases, ne fonctionne pas pour l'instant
        panelLowMid.setBorder(new EmptyBorder(5, 5, 5, 5));

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
                if ((j % 2 == 1 && i % 2 == 1) || (j % 2 == 0 && i % 2 == 0)) {
                    chessBoardButtons[i][j].setBackground(Color.WHITE);
                    chessBoardButtons[i][j].setOpaque(true);
                    chessBoardButtons[i][j].setBorderPainted(false);

                } else {
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
                    URL temp = Main.class.getResource("/resources/" + newPiece.getImageLink() + ".png");
                    chessBoardButtons[i][j].setIcon(new ImageIcon(temp));
                } else {
                    ImageIcon icon = new ImageIcon(
                            new BufferedImage(64, 64, BufferedImage.TYPE_INT_ARGB));
                    chessBoardButtons[i][j].setIcon(icon);
                }

            }
        }
    }

    //affichera les pieces prises
    public void displayPieceTaken(Piece piece) {

    }

    //affichera les promotions lorsqu'un pion atteint le bout de l'échiquier
    public void displayPromotion(int posY, int posX) {
        

    }

}
