import static org.junit.Assert.*;

import java.awt.Color;

import org.junit.Before;
import org.junit.Test;

public class TestFloodItModel {

    private FloodItModel model;

    @Before
    public void setUp() throws Exception {
        model = new FloodItModel(1, 2, 1);
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
        assertNotEquals(model.tiles[0][0], model.tiles[0][1]);
    }
    
    @Test
    public void test_color_of_first_tile() {
        assertEquals(model.colorOfTile(0, 1), Color.RED);
    }
}
