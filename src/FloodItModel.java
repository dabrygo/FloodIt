import java.awt.Color;

public class FloodItModel {
    public Color[] colors = new Color[] { Color.RED, Color.BLUE };
    
    public Color activeTileColor;
    public Color[][] tiles;
    
    public FloodItModel(Color[][] board) {
        tiles = board;
    }
    
    public void setActiveColor(Color newColor) {
        activeTileColor = newColor;
    }
    
    public Color colorOfTile(int row, int column) {
        return tiles[row][column];
    }
}
