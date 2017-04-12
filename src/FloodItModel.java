import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;

public class FloodItModel {
    public Color[] colors = new Color[] { Color.RED, Color.BLUE };
    
    public Color activeTileColor;
    public Color[][] tiles;
    public ArrayList<Point> blob;
    
    public FloodItModel(Color[][] board) {
        tiles = board;
        blob = new ArrayList<>();
        blob.add(new Point(0, 0));
    }
    
    public void setActiveColor(Color newColor) {
        activeTileColor = newColor;
        repaintBlob(newColor);
        addToBlob();
    }

    private void repaintBlob(Color newColor) {
        for (Point point : blob) {
            tiles[point.x][point.y] = newColor;
        }
    }

    private void addToBlob() {
        for (int i = 0; i < blob.size(); i++) {
            Point point = blob.get(i);
            Point rightOfPoint = new Point(point.x, point.y + 1);
            if (rightOfPoint.y < tiles[0].length && tiles[rightOfPoint.x][rightOfPoint.y] == activeTileColor) {
                blob.add(rightOfPoint);
            }
        }
    }
    
    public Color colorOfTile(int row, int column) {
        return tiles[row][column];
    }
}
