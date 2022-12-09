package view;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.text.AttributeSet.ColorAttribute;
import java.awt.image.BufferedImage;

import controller.GameFacade;
import controller.Supervisor;
import model.Board;
import model.Piece;
import model.PreviewObserver;
import model.BoardObserver;

import java.net.URL;
import java.net.http.HttpResponse.BodyHandler;

public class DisplayGame extends JFrame implements PreviewObserver, BoardObserver {

    private Supervisor supervisor;
    private GameFacade gameFacade;
    private Board board;

    private JPanel panelTitle;
    private JPanel panelLow;
    private JPanel panelLowLeft;
    private JPanel panelLowMid;
    private JPanel panelLowRight;
    private JButton[][] chessBoardButtons = new JButton[8][8];
    private static final String COLS = "ABCDEFGH";

    public DisplayGame(Supervisor Xsupervisor, GameFacade XgameFacade, Board xBoard) {
        supervisor = Xsupervisor;
        gameFacade = XgameFacade;
        board = xBoard;
        setTitle("Best chess game");
        setSize(1080, 800);
        setMinimumSize(new Dimension(1080, 607));
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

        panelTitle = new JPanel();
        panelTitle.setBackground(Color.darkGray);
        panelTitle.setSize(new Dimension(1080, 200));
        panelTitle.setMinimumSize(new Dimension(1080, 100));
        add(panelTitle);

        panelLow = new JPanel();
        panelLow.setLayout(new BoxLayout(panelLow, BoxLayout.X_AXIS));
        panelLow.setSize(new Dimension(1080, 507));
        panelLow.setMinimumSize(new Dimension(1080, 507));
        add(panelLow);

        panelLowLeft = new JPanel();
        panelLowLeft.setBackground(Color.lightGray);
        panelLowLeft.setSize(new Dimension(180, 507));
        panelLowLeft.setMinimumSize(new Dimension(180, 507));
        panelLow.add(panelLowLeft);

        panelLowMid = new JPanel(new GridLayout(0, 10));
        panelLowMid.setBackground(Color.gray);
        panelLowMid.setSize(new Dimension(720, 507));
        panelLowMid.setMinimumSize(new Dimension(720, 507));
        panelLow.add(panelLowMid);

        panelLowRight = new JPanel();
        panelLowRight.setBackground(Color.lightGray);
        panelLowRight.setSize(new Dimension(720, 507));
        panelLowRight.setMinimumSize(new Dimension(720, 507));
        panelLow.add(panelLowRight);

        creationBoardBlackWhite();
        setVisible(true);
    }

    public void creationBoardBlackWhite() {

        // panelLowMid.add(new JLabel("?"), BorderLayout.LINE_START);

        panelLowMid.setBorder(new LineBorder(Color.BLACK));

        // create the chess board squares
        Insets buttonMargin = new Insets(0, 0, 0, 0);

        // Create the chess board
        for (int i = 0; i < chessBoardButtons.length; i++) {
            for (int j = 0; j < chessBoardButtons[i].length; j++) {
                JButton b = new JButton();
                b.setMargin(buttonMargin);
                // our chess pieces are 64x64 px in size, so we'll
                // 'fill this in' using a transparent icon..
                ImageIcon icon = new ImageIcon(
                        new BufferedImage(64, 64, BufferedImage.TYPE_INT_ARGB));
                // ImageIcon icon = new ImageIcon("/resources/bbishop.png");
                b.setIcon(icon);
                if ((j % 2 == 1 && i % 2 == 1) || (j % 2 == 0 && i % 2 == 0)) {
                    b.setBackground(Color.WHITE);
                } else {
                    b.setBackground(Color.DARK_GRAY);
                }

                b.addActionListener(actionEvent -> {
                    clickedOnSomeCase(b);
                });

                chessBoardButtons[j][i] = b;
            }
        }

        // fill the chess board
        panelLowMid.add(new JLabel(""));
        // fill the top row
        for (int i = 0; i < 8; i++) {
            panelLowMid.add(
                    new JLabel(COLS.substring(i, i + 1),
                            SwingConstants.CENTER));
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

    public void resetChessBoardColor() {
        // go around each jButton and put the basic color again
        for (int i = 0; i < chessBoardButtons.length; i++) {
            for (int j = 0; j < chessBoardButtons[i].length; j++) {
                if ((j % 2 == 1 && i % 2 == 1) || (j % 2 == 0 && i % 2 == 0)) {
                    chessBoardButtons[i][j].setBackground(Color.WHITE);
                } else {
                    chessBoardButtons[i][j].setBackground(Color.DARK_GRAY);
                }
            }
        }
    }

    public void clickedOnSomeCase(JButton jButton) {
        for (int i = 0; i < chessBoardButtons.length; i++) {
            for (int j = 0; j < chessBoardButtons[i].length; j++) {
                if (chessBoardButtons[i][j] == jButton) {
                    gameFacade.clickedOnSomeCase(i, j);
                }
            }
        }
    }

    public void displayPreviewAtk(boolean[][] caseAtk) {
        // TODO Auto-generated method stub

    }

    public void displayPreviewMvt(boolean[][] caseAtk) {
        // TODO Auto-generated method stub

    }

    public void erasePreviews() {
        // TODO Auto-generated method stub

    }

    public void displayGame() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Piece newPiece = board.getPiece(i, j);
                if (newPiece != null) {
                    URL temp = Main.class.getResource("/resources/" + newPiece.getImageLink() + ".png");
                    chessBoardButtons[i][j].setIcon(new ImageIcon(temp));
                }

            }
        }
    }

    public void displayPieceTaken(Piece piece) {

    }

    public void displayPromotion(int posX, int posY) {
        // TODO Auto-generated method stub

    }

}
