import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

class Game extends JPanel {
    private static final long serialVersionUID = 1L;
    private FloodItModel model;

    Game(FloodItModel model) {
        this.model = model;
    }
    
    @Override
    public void paint(Graphics g) {
        int tileWidth = (int) Math.floor(getWidth() / model.tiles.length);
        int tileHeight = (int) Math.floor(getHeight() / model.tiles[0].length);
        for (int row = 0; row < model.tiles.length; row++) {
            for (int column = 0; column < model.tiles[0].length; column++) {
                g.setColor(model.colorOfTile(row, column));
                g.fillRect(row * tileWidth, column * tileHeight, tileWidth, tileHeight);
            }
        }
    }
}

public class FloodIt {
    
    enum Colors { 
        YELLOW(Color.YELLOW), 
        BLUE(Color.BLUE), 
        GREEN(Color.GREEN), 
        PINK(Color.PINK), 
        RED(Color.RED), 
        ORANGE(Color.ORANGE);
        
        Color color;

        Colors(Color color) {
            this.color = color;
        }
   };
    
    private FloodItModel floodItModel;

    public FloodIt() {
        JFrame frame = new JFrame();
        frame.setLayout(new BorderLayout());

        floodItModel = new FloodItModel(100, 100);
        Game game = new Game(floodItModel);
        frame.add(game, BorderLayout.CENTER);
        
        JPanel buttons = new JPanel();
        buttons.setLayout(new GridLayout(2, 3));
        for (Colors color : Colors.values()) {
            JButton colorButton = new JButton(color.name());
            colorButton.addActionListener(e -> {
                floodItModel.changeActiveColor(color.color);
                frame.repaint();
            });
            buttons.add(colorButton);
        }
        frame.add(buttons, BorderLayout.EAST);
        
        frame.setSize(600, 800);
        frame.setTitle("FloodIt");
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    
    public static void main(String[] args) {
        new FloodIt();
    }
}
