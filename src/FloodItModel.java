import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;

public class FloodItModel {
    public Color[] colors = new Color[] { Color.RED, Color.BLUE };
    
    public Color activeTileColor;
    public Color[][] tiles;
    public ArrayList<Point> blob;
    
    public FloodItModel(Color[][] board, Point startingTile) {
        tiles = board;
        blob = new ArrayList<>();
        blob.add(startingTile);
    }
    
    public FloodItModel(Color[][] board) {
        this(board, new Point(0, 0));
    }
    
    public void setActiveColor(Color newColor) {
        activeTileColor = newColor;
        repaintBlob(newColor);
        updateBlob();
    }

    private void repaintBlob(Color newColor) {
        for (Point point : blob) {
            tiles[point.x][point.y] = newColor;
        }
    }

    private void updateBlob() {
        for (int i = 0; i < blob.size(); i++) {
            Point point = blob.get(i);
            addColorsAroundPointToBlob(point);
        }
    }

    void addColorsAroundPointToBlob(Point point) {
        addPointToBlob(new Point(point.x, point.y + 1));
        addPointToBlob(new Point(point.x, point.y - 1));
        addPointToBlob(new Point(point.x - 1, point.y));
        addPointToBlob(new Point(point.x + 1, point.y));
        addPointToBlob(new Point(point.x + 1, point.y + 1));
        addPointToBlob(new Point(point.x + 1, point.y - 1));
        addPointToBlob(new Point(point.x - 1, point.y + 1));
        addPointToBlob(new Point(point.x - 1, point.y - 1));
    }

    private void addPointToBlob(Point point) {
        if (!blob.contains(point) && inBounds(point) && tiles[point.x][point.y] == activeTileColor) {
            blob.add(point);
        }
    }
    
    private boolean inBounds(Point point) {
        return 0 <= point.x && point.x < tiles.length && 0 <= point.y && point.y < tiles[0].length;
    }

    public Color colorOfTile(int row, int column) {
        return tiles[row][column];
    }
}
