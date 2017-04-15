import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;

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

    private FloodItModel floodItModel;
    private JFrame frame;
    private JMenuBar menuBar;
    private JPanel palette;
    private Game game;
    private JLabel clicksLabel;
    private JPanel sidePanel;
    private JButton[] colorButtons;

    public FloodIt() {
        frame = new JFrame();
        frame.setLayout(new BorderLayout());

        newGame();
        makeMenuBar();
        makeSidePanel();
        toggleActiveColorClickable(false);
        
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

    private void makeSidePanel() {
        sidePanel = new JPanel();
        sidePanel.setLayout(new GridBagLayout());
        
        makePalette();
        makeClicksLabel();

        GridBagConstraints gc = new GridBagConstraints();
        gc.gridx = 0;
        gc.gridy = 0;
        gc.weighty = 0.25;
        gc.fill = GridBagConstraints.HORIZONTAL;
        gc.anchor = GridBagConstraints.LINE_START;
        sidePanel.add(palette, gc);

        gc.gridx = 0;
        gc.gridy = 1;
        gc.weighty = 0.75;
        gc.fill = GridBagConstraints.NONE;
        gc.anchor = GridBagConstraints.CENTER;
        sidePanel.add(clicksLabel, gc);
    }

    private void makePalette() {
        palette = new JPanel();
        GridLayout gridLayout = new GridLayout(2, 3);
        gridLayout.setHgap(10);
        gridLayout.setVgap(10);
        palette.setLayout(gridLayout);
        colorButtons = new JButton[Colors.values().length];
        for (int i = 0; i < Colors.values().length; i++) {
            Colors color = Colors.values()[i];
            JButton colorButton = buttonFor(color);
            colorButtons[i] = colorButton;
            palette.add(colorButton);
        }
    }

    private void makeClicksLabel() {
        clicksLabel = new JLabel();
        clicksLabel.setFont(new Font("Arial", Font.PLAIN, 32));
        updateClicksLabel();
    }

    private JButton buttonFor(Colors color) {
        JButton colorButton = new JButton();
        colorButton.setPreferredSize(new Dimension(50, 50));
        colorButton.setBackground(color.color);
        colorButton.addActionListener(e -> {
            toggleActiveColorClickable(true);
            updateClicksLabel();
            floodItModel.changeActiveColor(color);
            toggleActiveColorClickable(false);
            frame.repaint();
            if (floodItModel.gameFinished()) {
                String message = String.format("You did not solve in %d clicks. Play again?", floodItModel.getMaximumNumberOfClicks());
                String title = "You lose. New game?";
                if (floodItModel.gameWon()) {
                    message = String.format("You won in %d clicks. Play again?", floodItModel.getClicks());
                    title = "You win! New game?";
                }
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

    private void toggleActiveColorClickable(boolean visible) {
        String colorName = floodItModel.activeTileColor.name();
        int colorIndex = Colors.valueOf(colorName).ordinal();
        colorButtons[colorIndex].setVisible(visible);
    }

    private void updateClicksLabel() {
        int clicks = floodItModel.getClicks();
        int maximumNumberOfClicks = floodItModel.getMaximumNumberOfClicks();
        String clickText = String.format("Clicks: %d / %d", clicks, maximumNumberOfClicks);
        clicksLabel.setText(clickText);
    }

    private void newGame() {
        if (colorButtons != null) {
            for (JButton button : colorButtons) {
                button.setVisible(true);
            }
        }
        floodItModel = new FloodItModel(14, 14);
        if (game == null) {
            game = new Game(floodItModel);
        }
        else {
            game.setModel(floodItModel);
            toggleActiveColorClickable(false);
            updateClicksLabel();
        }
        frame.repaint();
    }

    public static void main(String[] args) {
        new FloodIt();
    }
}
