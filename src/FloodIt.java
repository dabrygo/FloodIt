import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

class Game extends JPanel {
    private static final long serialVersionUID = 1L;
    private FloodItModel model;
    private int tileSize = 100;

    Game(FloodItModel model) {
        this.model = model;
    }
    
    @Override
    public void paint(Graphics g) {
        for (int row = 0; row < model.tiles.length; row++) {
            for (int column = 0; column < model.tiles[0].length; column++) {
                g.setColor(model.colorOfTile(row, column));
                g.fillRect(row * tileSize, column * tileSize, tileSize, tileSize);
            }
        }
    }
}

public class FloodIt {
    
    private FloodItModel floodItModel;

    public FloodIt() {
        JFrame frame = new JFrame();
        frame.setLayout(new BorderLayout());

        floodItModel = new FloodItModel(new Color[][] {{ Color.RED, Color.RED, Color.RED },
                                                       { Color.RED, Color.GREEN, Color.RED },
                                                       { Color.RED, Color.RED, Color.RED }});
        Game game = new Game(floodItModel);
        frame.add(game, BorderLayout.CENTER);
        
        JButton blue = new JButton("Blue");
        blue.addActionListener(e -> {
            floodItModel.changeActiveColor(Color.BLUE);
            game.repaint();
        });
        frame.add(blue, BorderLayout.EAST);
        
        frame.setSize(350, 350);
        frame.setTitle("FloodIt");
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    
    public static void main(String[] args) {
        new FloodIt();
    }
}
