import static org.junit.Assert.*;

import java.awt.Color;

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
    public void test_default_active_color() {
        assertNull(model.activeTileColor);
    }

    @Test
    public void test_change_active_color() {
        model.setActiveColor(Color.RED);
        assertEquals(model.activeTileColor, Color.RED);
    }
    
    @Test
    public void test_tiles_are_different_colors() {
        assertNotEquals(model.colorOfTile(0, 0), model.colorOfTile(0, 1));
    }
    
    @Test
    public void test_color_of_first_tile() {
        assertEquals(model.colorOfTile(0, 0), Color.RED);
    }
}
