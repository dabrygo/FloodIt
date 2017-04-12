import java.awt.Color;
import java.util.Random;

public class FloodItModel {
    public Color[] colors = new Color[] { Color.RED, Color.BLUE };
    
    public Color activeTileColor;
    public Color[][] tiles;

    public FloodItModel(int rows, int columns, long seed) {
        tiles = new Color[rows][columns];
        Random random = new Random(seed);
        tiles[0][0] = colors[random.nextInt(colors.length)];
        tiles[0][1] = colors[random.nextInt(colors.length)];
        activeTileColor = null;
    }
    
    public void setActiveColor(Color newColor) {
        activeTileColor = newColor;
    }
    
    public Color colorOfTile(int row, int column) {
        return tiles[row][column];
    }
}
