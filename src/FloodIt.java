import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

class Game extends JPanel {
    private static final long serialVersionUID = 1L;
    private FloodItModel model;

    Game(FloodItModel model) {
        this.model = model;
    }

    @Override
    public void paint(Graphics g) {
        int tileWidth = (int) Math.floor(getWidth() / model.rows());
        int tileHeight = (int) Math.floor(getHeight() / model.columns());
        for (int row = 0; row < model.rows(); row++) {
            for (int column = 0; column < model.columns(); column++) {
                g.setColor(model.colorOfTile(row, column));
                g.fillRect(row * tileWidth, column * tileHeight, tileWidth, tileHeight);
            }
        }
    }

    public void setModel(FloodItModel floodItModel) {
        this.model = floodItModel;
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
    private Game game;
    private JFrame frame;

    public FloodIt() {
        frame = new JFrame();
        frame.setLayout(new BorderLayout());

        newGame();
        JMenuBar menuBar = new JMenuBar();
        JMenu gameMenu = new JMenu("Game");
        JMenuItem newGame = new JMenuItem("New Game");
        newGame.addActionListener(e -> newGame());
        gameMenu.add(newGame);
        menuBar.add(gameMenu);
        
        frame.add(menuBar, BorderLayout.NORTH);
        frame.add(game, BorderLayout.CENTER);

        JPanel buttons = new JPanel();
        buttons.setLayout(new GridLayout(2, 3));
        for (Colors color : Colors.values()) {
            JButton colorButton = new JButton();
            colorButton.setBackground(color.color);
            colorButton.addActionListener(e -> {
                floodItModel.changeActiveColor(color.color);
                frame.repaint();
                if (floodItModel.gameWon()) {
                    String message = String.format("You won in %d clicks. Play again?", floodItModel.clicks);
                    String title = "You win! New game?";
                    int n = JOptionPane.showConfirmDialog(frame, message, title, JOptionPane.YES_NO_OPTION);
                    if (n == JOptionPane.YES_OPTION) {
                        newGame();
                    }
                    else if (n == JOptionPane.NO_OPTION) {
                        System.exit(0);
                    }
                }
            });
            buttons.add(colorButton);
        }
        frame.add(buttons, BorderLayout.EAST);

        frame.setSize(600, 800);
        frame.setTitle("FloodIt");
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void newGame() {
        floodItModel = new FloodItModel(14, 14);
        if (game == null) {
            game = new Game(floodItModel);
        }
        else {
            game.setModel(floodItModel);
        }
        frame.repaint();
    }

    public static void main(String[] args) {
        new FloodIt();
    }
}
