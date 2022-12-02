package view;
import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.text.AttributeSet.ColorAttribute;
import java.awt.image.BufferedImage;


import controller.GameFacade;
import controller.Supervisor;



public class DisplayGame extends JFrame{

    private Supervisor supervisor;
    private GameFacade gameFacade;

    private JPanel panelTitle;
    private JPanel panelMidLeft;
    private JPanel panelMidMid;
    private JPanel panelMidRight;
    private JPanel chessBoard;
    private JButton[][] chessBoardButtons = new JButton[8][8];
    private static final String COLS = "ABCDEFGH";

    public DisplayGame(Supervisor Xsupervisor, GameFacade XgameFacade){
        supervisor = Xsupervisor;
        gameFacade = XgameFacade;
        setTitle("Best chess game");
        setSize(1080, 607);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        

        setLayout(new GridBagLayout());
        GridBagConstraints  gbc = new GridBagConstraints();
        Border eBorder = BorderFactory.createEtchedBorder();


        panelTitle = new JPanel();
        panelTitle.setBorder(BorderFactory.createTitledBorder(eBorder, "up"));
        panelTitle.setBackground(Color.darkGray);
        gbc.gridx = gbc.gridy = 0;
        gbc.gridwidth = gbc.gridheight = 1;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridwidth = 3;
        gbc.insets = new Insets(2, 2, 2, 2);
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.weightx = gbc.weighty = 10;
        add(panelTitle, gbc);


        

        panelMidLeft = new JPanel();
        panelMidLeft.setBorder(BorderFactory.createTitledBorder(eBorder, "left"));
        panelMidLeft.setBackground(Color.lightGray);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = gbc.gridheight = 1;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.anchor = GridBagConstraints.SOUTHWEST;
        gbc.insets = new Insets(2, 2, 2, 2);
        gbc.weightx = 10;
        gbc.weighty = 40;
        add(panelMidLeft, gbc);

        panelMidMid = new JPanel(new GridLayout(0, 9));
        panelMidMid.setBorder(BorderFactory.createTitledBorder(eBorder, "mid"));
        panelMidMid.setBackground(Color.gray);
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.gridwidth = gbc.gridheight = 1;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.anchor = GridBagConstraints.SOUTH;
        gbc.weightx = gbc.weighty = 40;
        add(panelMidMid, gbc);
        
        panelMidRight = new JPanel();
        panelMidRight.setBorder(BorderFactory.createTitledBorder(eBorder, "right"));
        panelMidRight.setBackground(Color.lightGray);
        gbc.gridx = 2;
        gbc.gridy = 1;
        gbc.gridwidth = gbc.gridheight = 1;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.anchor = GridBagConstraints.SOUTHEAST;
        gbc.weightx = 10;
        gbc.weighty = 40;
        add(panelMidRight, gbc);

        //creationBoardBlackWhite();

        setVisible(true);
    }

    public void boardMaj(){

    }

    public void creationBoardBlackWhite(){

        //panelMidMid.add(new JLabel("?"), BorderLayout.LINE_START);
        chessBoard = new JPanel(new GridLayout(10, 10));
        chessBoard.setBorder(new LineBorder(Color.BLACK));
        panelMidMid.add(chessBoard);

        panelMidMid.setBorder(new LineBorder(Color.BLACK));

        // create the chess board squares
        Insets buttonMargin = new Insets(0,0,0,0);

        //Create the chess board
        for (int ii = 0; ii < chessBoardButtons.length; ii++) {
            for (int jj = 0; jj < chessBoardButtons[ii].length; jj++) {
                JButton b = new JButton();
                b.setMargin(buttonMargin);
                // our chess pieces are 64x64 px in size, so we'll
                // 'fill this in' using a transparent icon..
                ImageIcon icon = new ImageIcon(
                        new BufferedImage(64, 64, BufferedImage.TYPE_INT_ARGB));
                b.setIcon(icon);
                if ((jj % 2 == 1 && ii % 2 == 1) || (jj % 2 == 0 && ii % 2 == 0)) {
                    b.setBackground(Color.WHITE);
                } else {
                    b.setBackground(Color.BLACK);
                }
                chessBoardButtons[jj][ii] = b;
            }
        }

        //fill the chess board
        chessBoard.add(new JLabel(""));
        // fill the top row
        for (int ii = 0; ii < 8; ii++) {
            chessBoard.add(
                    new JLabel(COLS.substring(ii, ii + 1),
                    SwingConstants.CENTER));
        }
        // fill the black non-pawn piece row
        for (int ii = 0; ii < 8; ii++) {
            for (int jj = 0; jj < 8; jj++) {
                switch (jj) {
                    case 0:
                        chessBoard.add(new JLabel("" + (ii + 1),
                                SwingConstants.CENTER));
                    default:
                        chessBoard.add(chessBoardButtons[jj][ii]);
                }
            }
        }


    }
}
