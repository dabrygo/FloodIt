import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.awt.Color;
import java.awt.Point;

import org.junit.Before;
import org.junit.Test;

public class TestFloodItModel {

    private FloodItModel model;

    @Before
    public void setUp() throws Exception {
        Color[][] board = new Color[][] { { Color.RED, Color.BLUE }, 
                                          { Color.GREEN, Color.YELLOW }};
        model = new FloodItModel(board);
    }
    
    @Test
    public void test_change_color_of_first_tile() {
        model.changeActiveColor(Color.GREEN);
        assertEquals(model.activeTileColor, model.colorOfTile(0, 0));
    }
    
    @Test
    public void test_tile_gets_added_to_blob() {
        model.changeActiveColor(Color.BLUE);
        assertTrue(model.blob.contains(new Point(0, 1)));
    }
    
    @Test
    public void test_newly_added_tile_changes_when_active_color_changes() {
        model.changeActiveColor(Color.BLUE);
        model.changeActiveColor(Color.GREEN);
        assertEquals(model.colorOfTile(0, 1), Color.GREEN);
    }
    
    @Test
    public void test_change_colors_around_central_square() {
        Color[][] board = new Color[][] {{ Color.RED, Color.RED, Color.RED },
                                         { Color.RED, Color.RED, Color.RED },
                                         { Color.RED, Color.RED, Color.RED }};
        FloodItModel model3By3 = new FloodItModel(board, new Point(1, 1));
        model3By3.activeTileColor = Color.RED;
        model3By3.addColorsAroundPointToBlob(new Point(1, 1));
        assertEquals(9, model3By3.blob.size());
    }    
    
    @Test
    public void test_do_not_add_diagonals_to_blob() {
        Color[][] board = new Color[][] {{ Color.RED, Color.BLUE, Color.RED },
                                         { Color.BLUE, Color.RED, Color.BLUE },
                                         { Color.RED, Color.BLUE, Color.RED }};
        FloodItModel model3By3 = new FloodItModel(board, new Point(1, 1));
        model3By3.changeActiveColor(Color.BLUE);
        assertEquals(Color.RED, model3By3.colorOfTile(0, 0));
        assertEquals(Color.RED, model3By3.colorOfTile(2, 0));
        assertEquals(Color.RED, model3By3.colorOfTile(0, 2));
        assertEquals(Color.RED, model3By3.colorOfTile(2, 2));
    }
}
