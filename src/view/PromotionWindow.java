package view;

import controller.Supervisor;

import model.Queen;
import model.Rook;
import model.Bishop;
import model.Knight;

import java.net.URL;
import javax.swing.*;
import javax.swing.JOptionPane;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PromotionWindow extends JFrame implements ActionListener {
    private Supervisor supervisor;

    private JPanel panelTitle;
    private JPanel panelButtons;
    private JButton[] possiblePromotion;

    final int HEIGHT = 500;
    final int WIDTH = 500;
    final int TITLEHEIGHT = 100;

    // private boolean promotionTime = false;

    public PromotionWindow(Supervisor sup) {
        supervisor = sup;
        possiblePromotion = new JButton[4];

        setTitle("Promotion");
        setSize(new Dimension(WIDTH, HEIGHT));
        setMinimumSize(new Dimension(WIDTH, HEIGHT));
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        setLayout(new BorderLayout());

        // ajoute panel du haut
        panelTitle = new JPanel();
        panelTitle.setBackground(Color.darkGray);
        panelTitle.setSize(new Dimension(WIDTH, TITLEHEIGHT));
        panelTitle.setMinimumSize(new Dimension(WIDTH, TITLEHEIGHT));
        panelTitle.setLayout(new GridLayout(1, 0));
        add(panelTitle, BorderLayout.NORTH);

        // ajoute panel du bas
        panelButtons = new JPanel();
        panelButtons.setBackground(Color.lightGray);
        panelButtons.setSize(new Dimension(WIDTH, HEIGHT - TITLEHEIGHT));
        panelButtons.setMinimumSize(new Dimension(WIDTH, HEIGHT - TITLEHEIGHT));
        panelButtons.setLayout(new FlowLayout());
        add(panelButtons, BorderLayout.CENTER);

        // ajoute titre
        JLabel title = new JLabel("Promotion", SwingConstants.CENTER);
        title.setFont(new Font("Verdana", Font.PLAIN, 32));
        title.setForeground(Color.WHITE);
        panelTitle.add(title, BorderLayout.NORTH);

        // ajoute buttons pour choisir la promotion
        // Board board = supervisor.getBoard();
        // JButton buttonQueen = new JButton();
        // buttonQueen.setSize(100, 100);
        // // buttonQueen.setMinimumSize(100, 100);
        // buttonQueen.setIcon(null);
        // panelButtons.add(buttonQueen);

        createButtons();
        setVisible(true);
    }

    private void createButtons() {
        for (int i = 0; i < 4; i++) {
            possiblePromotion[i] = new JButton();
            possiblePromotion[i].setName("one");
            ImageIcon icon = new ImageIcon(
                    new BufferedImage(100, 100, BufferedImage.TYPE_INT_ARGB));
            possiblePromotion[i].setIcon(icon);
            panelButtons.add(possiblePromotion[i]);
        }
    }

    // affichera les promotions lorsqu'un pion atteint le bout de l'échiquier
    public void displayPromotion(int posY, int posX, boolean team) {
        // int i = 0;
        supervisor.hasClicked = false;
        supervisor.pieceProm = "";
        // queen
        Queen queen = new Queen(team);
        URL iconQ = PromotionWindow.class.getResource("/resources/" + queen.getImageLink() + ".png");
        possiblePromotion[0].setIcon(new ImageIcon(iconQ));
        possiblePromotion[0].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                supervisor.hasClicked = true;
                supervisor.pieceProm = queen.getChessName();
                supervisor.sendPromotion(posY, posX, team);
                JOptionPane.showMessageDialog(null, supervisor.pieceProm);
                setVisible(false);
            }
        });

        // rook
        Rook rook = new Rook(team);
        URL iconR = PromotionWindow.class.getResource("/resources/" + rook.getImageLink() + ".png");
        possiblePromotion[1].setIcon(new ImageIcon(iconR));
        possiblePromotion[1].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                supervisor.hasClicked = true;
                supervisor.pieceProm = rook.getChessName();
                supervisor.sendPromotion(posY, posX, team);
                JOptionPane.showMessageDialog(null, supervisor.pieceProm);
                setVisible(false);
            }
        });

        // bishop
        Bishop bishop = new Bishop(team);
        URL iconB = PromotionWindow.class.getResource("/resources/" + bishop.getImageLink() + ".png");
        possiblePromotion[2].setIcon(new ImageIcon(iconB));
        possiblePromotion[2].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                supervisor.hasClicked = true;
                supervisor.pieceProm = bishop.getChessName();
                supervisor.sendPromotion(posY, posX, team);
                JOptionPane.showMessageDialog(null, supervisor.pieceProm);
                setVisible(false);
            }
        });

        // knight
        Knight knight = new Knight(team);
        URL iconK = PromotionWindow.class.getResource("/resources/" + knight.getImageLink() + ".png");
        possiblePromotion[3].setIcon(new ImageIcon(iconK));
        possiblePromotion[3].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                supervisor.hasClicked = true;
                supervisor.pieceProm = knight.getChessName();
                supervisor.sendPromotion(posY, posX, team);
                JOptionPane.showMessageDialog(null, supervisor.pieceProm);
                setVisible(false);
            }
        });

    }

    @Override
    public void actionPerformed(ActionEvent e) {
    }
}
