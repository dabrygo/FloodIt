import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Random;

public class FloodItModel {
    Color activeTileColor;
    private Color[][] tiles;
    ArrayList<Point> blob;
    private int clicks;
    
    public FloodItModel(int rows, int columns) {
        this(generateRandomBoard(rows, columns));
    }
    
    private static Color[][] generateRandomBoard(int rows, int columns) {
        Color[][] board = new Color[rows][columns];
        for (int row = 0; row < rows; row++) {
            for (int column = 0; column < columns; column++) {
                Random random = new Random();
                int nColors = FloodIt.Colors.values().length;
                int randomColorIndex = random.nextInt(nColors);
                board[row][column] = FloodIt.Colors.values()[randomColorIndex].color;
            }
        }
        return board;
    }

    FloodItModel(Color[][] board, Point startingTile) {
        tiles = board;
        blob = new ArrayList<>();
        blob.add(startingTile);
        clicks = 0;
        activeTileColor = tiles[startingTile.x][startingTile.y];
        updateBlob();
    }
    
    FloodItModel(Color[][] board) {
        this(board, new Point(0, 0));
    }
    
    public void changeActiveColor(Color newColor) {
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
        int[] directions = new int[] {-1, 0, 1};
        for (int dx : directions) {
            for (int dy : directions) {
                if (isDiagonal(dx, dy)) {
                    continue;
                }
                addPointToBlob(new Point(point.x + dx, point.y + dy));
            }
        }
    }

    private boolean isDiagonal(int dx, int dy) {
        return dx == dy;
    }

    private void addPointToBlob(Point point) {
        if (!blob.contains(point) && inBounds(point) && isBlobbable(point.x, point.y)) {
            blob.add(point);
        }
    }

    private boolean inBounds(Point point) {
        return 0 <= point.x && point.x < rows() && 0 <= point.y && point.y < columns();
    }

    private boolean isBlobbable(int x, int y) {
        return colorOfTile(x, y).equals(activeTileColor);
    }

    public int rows() {
        return tiles.length;
    }

    public int columns() {
        return tiles[0].length;
    }

    public Color colorOfTile(int row, int column) {
        return tiles[row][column];
    }
    
    public boolean gameWon() {
        for (int row = 0; row < rows(); row++) {
            for (int column = 0; column < columns(); column++) {
                if (!isBlobbable(row, column)) {
                    return false;
                }
            }
        }
        return true;
    }

    public void incrementClicks() {
        clicks += 1;
    }

    public int getClicks() {
        return clicks;
    }
}
