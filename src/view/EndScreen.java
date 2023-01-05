package view;

import java.awt.*;
import java.net.URL;

import javax.swing.*;

import controller.Initialiser;

public class EndScreen extends JFrame {

    final int WIDTH = 500;
    final int HEIGHT = 200;
    final int WIDTH_IMAGE = 30;
    final int HEIGHT_UNDERMID = 30;
    final int WIDTH_INPUT = 200;
    final int HEIGHT_BUTTON = 60;

    private JLabel winName;
    private JLabel loseName;
    private JLabel winScore;
    private JLabel loseScore;
    private JLabel imageW;
    private JLabel imageL;
    private JLabel lblTitle;

    private Initialiser initialiser;

    public EndScreen(Initialiser gaveInitialiser) {
        initialiser = gaveInitialiser;

        setTitle("EndScreen");
        setSize(new Dimension(WIDTH, HEIGHT));
        setMinimumSize(new Dimension(WIDTH, HEIGHT));
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        setLayout(new BorderLayout());

        // titre
        JPanel pnlTitle = new JPanel();
        pnlTitle.setBackground(Color.gray);
        // ceci doit etre determine programatically : "le gagnant est " + gagnant
        lblTitle = new JLabel("Title", SwingConstants.CENTER);
        lblTitle.setFont(new Font("Verdana", Font.PLAIN, 26));
        lblTitle.setForeground(Color.BLACK);
        pnlTitle.add(lblTitle);

        add(pnlTitle, BorderLayout.NORTH);

        // panel left : joueur gagnant
        JPanel pnlLeftW = new JPanel();
        pnlLeftW.setBackground(Color.DARK_GRAY);
        pnlLeftW.setLayout(new BorderLayout());
        pnlLeftW.setPreferredSize(new Dimension(WIDTH / 2 - 10, HEIGHT_UNDERMID + 10));
        add(pnlLeftW, BorderLayout.WEST);

        winName = new JLabel("winnerName", SwingConstants.CENTER);
        winName.setFont(new Font("Verdana", Font.PLAIN, 22));
        winName.setForeground(Color.WHITE);
        pnlLeftW.add(winName, BorderLayout.NORTH);

        URL iconW = DisplayBoard.class.getResource("/resources/default.png");
        imageW = new JLabel(new ImageIcon(iconW));
        imageW.setPreferredSize(new Dimension(WIDTH_IMAGE, HEIGHT_UNDERMID));
        pnlLeftW.add(imageW, BorderLayout.WEST);

        winScore = new JLabel("winnerScore", SwingConstants.CENTER);
        winScore.setFont(new Font("Verdana", Font.PLAIN, 16));
        winScore.setForeground(Color.WHITE);
        pnlLeftW.add(winScore, BorderLayout.EAST);

        // panel right : joueur perdant
        JPanel pnlRightL = new JPanel();
        pnlRightL.setBackground(Color.DARK_GRAY);
        pnlRightL.setLayout(new BorderLayout());
        pnlRightL.setPreferredSize(new Dimension(WIDTH / 2 - 10, HEIGHT_UNDERMID + 10));
        add(pnlRightL, BorderLayout.EAST);

        loseName = new JLabel("loserName", SwingConstants.CENTER);
        loseName.setFont(new Font("Verdana", Font.PLAIN, 22));
        loseName.setForeground(Color.WHITE);
        pnlRightL.add(loseName, BorderLayout.NORTH);

        URL iconL = DisplayBoard.class.getResource("/resources/default.png");
        imageL = new JLabel(new ImageIcon(iconL));
        imageL.setPreferredSize(new Dimension(WIDTH_IMAGE, HEIGHT_UNDERMID));
        pnlRightL.add(imageL, BorderLayout.WEST);

        loseScore = new JLabel("loserScore", SwingConstants.CENTER);
        loseScore.setFont(new Font("Verdana", Font.PLAIN, 16));
        loseScore.setForeground(Color.WHITE);
        pnlRightL.add(loseScore, BorderLayout.EAST);

        // button recommencer

        JButton btnReplay = new JButton("Jouer à nouveau");
        btnReplay.setFont(new Font("Verdana", Font.PLAIN, 24));
        btnReplay.setBackground(Color.gray);
        btnReplay.setPreferredSize(new Dimension(1, HEIGHT_BUTTON));
        btnReplay.addActionListener(actionEvent -> {
            replayGame();
        });

        add(btnReplay, BorderLayout.SOUTH);

        // visible
        setVisible(true);
        setLocationRelativeTo(null);
    }

    public void fillEndScreen(String nameWin, int scoreWin, String nameLose, int scoreLose, boolean teamWin,
            boolean notPat) {
        if (!notPat) {
            winName.setText("Le gagnant est " + nameWin);
            winScore.setText("Score : " + scoreWin);
            loseName.setText("Le perdant est " + nameLose);
            loseScore.setText("Score : " + scoreLose);
            lblTitle.setText("Echec et Mat !");
            setTitle("Echec et Mat");

            // if white wins
            if (teamWin) {
                imageW.setIcon(new ImageIcon(DisplayBoard.class.getResource("/resources/wpawn.png")));

                imageL.setIcon(new ImageIcon(DisplayBoard.class.getResource("/resources/bpawn.png")));
            }
            // if black wins
            else {
                imageW.setIcon(new ImageIcon(DisplayBoard.class.getResource("/resources/bpawn.png")));

                imageL.setIcon(new ImageIcon(DisplayBoard.class.getResource("/resources/wpawn.png")));
            }
        } else {
            winName.setText("Joueur 1 : " + nameWin);
            winScore.setText("Score : " + scoreWin);
            loseName.setText("Joueur 2 :  " + nameLose);
            loseScore.setText("Score : " + scoreLose);
            lblTitle.setText("Pat !");
            setTitle("Pat");

            imageW.setIcon(new ImageIcon(DisplayBoard.class.getResource("/resources/wpawn.png")));
            imageL.setIcon(new ImageIcon(DisplayBoard.class.getResource("/resources/bpawn.png")));

        }
    }

    private void replayGame() {
        initialiser.LaunchStartMenu();
        dispose();
    }

}
