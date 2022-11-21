import javax.swing.*;
import java.awt.*;

public class Chessboard extends JFrame {

    private Image[][] allTeamSprites = new Image[2][6];
    private JButton[][] allSquares = new JButton[8][8];

    //faut faire un panel qui ajoute ABCDEFGHI, 12345678, pieces mangees des deux cotes

    public Chessboard(){
        super("Game Chessboard");
        setSize(800,800);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(8,8));





    }


}
