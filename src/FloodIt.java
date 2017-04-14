import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
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

    public enum Colors {
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
    private JFrame frame;
    private JMenuBar menuBar;
    private JPanel palette;
    private Game game;
    private JLabel clicksLabel;
    private JPanel sidePanel;

    public FloodIt() {
        frame = new JFrame();
        frame.setLayout(new BorderLayout());

        newGame();
        makeMenuBar();
        makePalette();
        makeSidePanel();
        
        frame.add(menuBar, BorderLayout.NORTH);
        frame.add(sidePanel, BorderLayout.EAST);
        frame.add(game, BorderLayout.CENTER);

        frame.setSize(600, 800);
        frame.setTitle("FloodIt");
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void makeMenuBar() {
        menuBar = new JMenuBar();
        JMenu gameMenu = new JMenu("Game");
        JMenuItem newGame = new JMenuItem("New Game");
        newGame.addActionListener(e -> newGame());
        gameMenu.add(newGame);
        menuBar.add(gameMenu);
    }

    private void makePalette() {
        palette = new JPanel();
        GridLayout gridLayout = new GridLayout(2, 3);
        gridLayout.setHgap(10);
        gridLayout.setVgap(10);
        palette.setLayout(gridLayout);
        for (Colors color : Colors.values()) {
            palette.add(buttonFor(color));
        }
    }

    private void makeSidePanel() {
        sidePanel = new JPanel();
        sidePanel.setLayout(new BoxLayout(sidePanel, BoxLayout.PAGE_AXIS));
        sidePanel.add(palette);
        clicksLabel = new JLabel();
        clicksLabel.setFont(new Font("Arial", Font.PLAIN, 32));
        updateClicksLabel();
        sidePanel.add(clicksLabel);
    }

    private JButton buttonFor(Colors color) {
        JButton colorButton = new JButton();
        colorButton.setBackground(color.color);
        colorButton.addActionListener(e -> {
            floodItModel.incrementClicks();
            updateClicksLabel();
            floodItModel.changeActiveColor(color.color);
            frame.repaint();
            if (floodItModel.gameWon()) {
                String message = String.format("You won in %d clicks. Play again?", floodItModel.getClicks());
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
        return colorButton;
    }

    private void updateClicksLabel() {
        clicksLabel.setText(String.format("Clicks: %d", floodItModel.getClicks()));
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
