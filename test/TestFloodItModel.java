import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
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
        model.changeActiveColor(Colors.GREEN);
        assertEquals(model.activeTileColor.color, model.colorOfTile(0, 0));
    }
    
    @Test
    public void test_tile_gets_added_to_blob() {
        model.changeActiveColor(Colors.BLUE);
        assertTrue(model.blob.contains(new Point(0, 1)));
    }
    
    @Test
    public void test_newly_added_tile_changes_when_active_color_changes() {
        model.changeActiveColor(Colors.BLUE);
        model.changeActiveColor(Colors.GREEN);
        assertEquals(model.colorOfTile(0, 1), Color.GREEN);
    }
    
    @Test
    public void test_change_colors_around_central_square() {
        Color[][] board = new Color[][] {{ Color.RED, Color.RED, Color.RED },
                                         { Color.RED, Color.RED, Color.RED },
                                         { Color.RED, Color.RED, Color.RED }};
        FloodItModel model3By3 = new FloodItModel(board, new Point(1, 1));
        model3By3.activeTileColor = Colors.RED;
        model3By3.addColorsAroundPointToBlob(new Point(1, 1));
        assertEquals(9, model3By3.blob.size());
    }    
    
    @Test
    public void test_do_not_add_diagonals_to_blob() {
        Color[][] board = new Color[][] {{ Color.RED, Color.BLUE, Color.RED },
                                         { Color.BLUE, Color.RED, Color.BLUE },
                                         { Color.RED, Color.BLUE, Color.RED }};
        FloodItModel model3By3 = new FloodItModel(board, new Point(1, 1));
        model3By3.changeActiveColor(Colors.BLUE);
        assertEquals(Color.RED, model3By3.colorOfTile(0, 0));
        assertEquals(Color.RED, model3By3.colorOfTile(2, 0));
        assertEquals(Color.RED, model3By3.colorOfTile(0, 2));
        assertEquals(Color.RED, model3By3.colorOfTile(2, 2));
    }    
    
    @Test
    public void test_win_on_last_click() {
        Color[][] board = new Color[][] {{ Color.RED, Color.BLUE }};
        FloodItModel model2By1 = new FloodItModel(board, new Point(0, 0));
        model2By1.setMaximumNumberOfClicks(1);
        model2By1.changeActiveColor(Colors.BLUE);
        assertFalse(model2By1.gameLost());
        assertEquals(Color.BLUE, model2By1.colorOfTile(0, 0));
        assertEquals(Color.BLUE, model2By1.colorOfTile(0, 1));
        assertTrue(model2By1.gameWon());
    }    
    
    @Test
    public void test_loss_on_last_click() {
        Color[][] board = new Color[][] {{ Color.RED, Color.BLUE }};
        FloodItModel model2By1 = new FloodItModel(board, new Point(0, 0));
        model2By1.setMaximumNumberOfClicks(1);
        model2By1.changeActiveColor(Colors.GREEN);
        assertTrue(model2By1.gameLost());
    }
    
    @Test
    public void test_can_continue_if_not_on_last_click() {
        Color[][] board = new Color[][] {{ Color.RED, Color.BLUE }};
        FloodItModel model2By1 = new FloodItModel(board, new Point(0, 0));
        model2By1.setMaximumNumberOfClicks(2);
        model2By1.changeActiveColor(Colors.GREEN);
        assertFalse(model2By1.gameLost());
        assertFalse(model2By1.gameWon());
    }
    
    @Test
    public void test_win_in_two_clicks_last_click() {
        Color[][] board = new Color[][] {{ Color.RED, Color.BLUE, Color.GREEN }};
        FloodItModel model3By1 = new FloodItModel(board, new Point(0, 0));
        model3By1.setMaximumNumberOfClicks(2);
        model3By1.changeActiveColor(Colors.BLUE);
        model3By1.changeActiveColor(Colors.GREEN);
        assertTrue(model3By1.gameWon());
        assertEquals(2, model3By1.getClicks());
    }
}
