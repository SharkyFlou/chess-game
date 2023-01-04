package view;

import java.awt.*;
import java.net.URL;

import javax.swing.*;

import controller.Initialiser;


public class StartScreen extends JFrame{
    
    final int WIDTH = 500;
    final int HEIGHT = 200;
    final int WIDTH_IMAGE = 30;
    final int HEIGHT_UNDERMID = 30;
    final int WIDTH_INPUT = 200;
    final int HEIGHT_BUTTON = 60;

    private JTextField whiteName;
    private JTextField blackName;


    private Initialiser initialiser;


    public StartScreen(Initialiser gaveInitialiser){
        initialiser=gaveInitialiser;

        setTitle("Awful chessgame start menu");
        setSize(new Dimension(WIDTH, HEIGHT));
        setMinimumSize(new Dimension(WIDTH, HEIGHT));
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setBackground(Color.RED);

        setLayout(new BorderLayout());

        //titre
        JPanel pnlTitle = new JPanel();
        pnlTitle.setBackground(Color.gray);


        JLabel lblTitle = new JLabel("Bienvenu dans Awful Chessgame !", SwingConstants.CENTER);
        lblTitle.setFont(new Font("Verdana", Font.PLAIN, 24));
        lblTitle.setForeground(Color.BLACK);
        pnlTitle.add(lblTitle);
       
        add(pnlTitle,BorderLayout.NORTH);

        //pnl gauche
        JPanel pnlLeft = new JPanel();
        pnlLeft.setBackground(Color.LIGHT_GRAY);
        pnlLeft.setLayout(new BorderLayout());
        pnlLeft.setPreferredSize(new Dimension(WIDTH/2-8,HEIGHT_UNDERMID+10));
        add(pnlLeft,BorderLayout.WEST);

        JLabel lblWhite = new JLabel("Nom du joueur blanc");
        lblWhite.setFont(new Font("Verdana", Font.PLAIN, 16));
        lblWhite.setForeground(Color.BLACK);
        pnlLeft.add(lblWhite,BorderLayout.NORTH);

        
        URL iconW = DisplayBoard.class.getResource("/resources/wpawn.png");
        JLabel imageWhite = new JLabel(new ImageIcon(iconW));
        imageWhite.setPreferredSize(new Dimension(WIDTH_IMAGE,HEIGHT_UNDERMID));
        pnlLeft.add(imageWhite,BorderLayout.WEST);

        whiteName = new JTextField("Blanc");
        whiteName.setFont(new Font("Verdana", Font.PLAIN, 16));
        whiteName.setPreferredSize(new Dimension(WIDTH_INPUT,HEIGHT_UNDERMID));
        pnlLeft.add(whiteName,BorderLayout.EAST);

        //panel droit
        JPanel pnlRight = new JPanel();
        pnlRight.setBackground(Color.DARK_GRAY);
        pnlRight.setLayout(new BorderLayout());
        pnlRight.setPreferredSize(new Dimension(WIDTH/2-8,HEIGHT_UNDERMID+10));
        add(pnlRight,BorderLayout.EAST);


        JLabel lblBlack = new JLabel("Nom du joueur noir");
        lblBlack.setFont(new Font("Verdana", Font.PLAIN, 16));
        lblBlack.setForeground(Color.WHITE);
        pnlRight.add(lblBlack,BorderLayout.NORTH);

        URL iconB = DisplayBoard.class.getResource("/resources/bpawn.png");
        JLabel imageBlack = new JLabel(new ImageIcon(iconB));
        imageBlack.setPreferredSize(new Dimension(WIDTH_IMAGE,HEIGHT_UNDERMID));
        pnlRight.add(imageBlack,BorderLayout.WEST);

        blackName = new JTextField("Noir");
        blackName.setFont(new Font("Verdana", Font.PLAIN, 16));
        blackName.setPreferredSize(new Dimension(WIDTH_INPUT,HEIGHT_UNDERMID));
        pnlRight.add(blackName,BorderLayout.EAST);


        //bouton du bas
        JButton btnPlay = new JButton("JOUER");
        btnPlay.setFont(new Font("Verdana", Font.PLAIN, 24));
        btnPlay.setBackground(Color.gray);
        btnPlay.setPreferredSize(new Dimension(1, HEIGHT_BUTTON));
        btnPlay.addActionListener(actionEvent -> {
            beginGame();
        });
        add(btnPlay,BorderLayout.SOUTH);


        setBackground(Color.RED);
        setVisible(true);
    }

    public void beginGame(){
        System.out.println("blanc :"+whiteName.getText()+"\nnoir : "+blackName.getText());
        initialiser.LaunchGame(whiteName.getText(), blackName.getText());
        dispose();
    }


}
