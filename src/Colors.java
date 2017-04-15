import java.awt.Color;

public enum Colors {
    YELLOW(Color.YELLOW), 
    BLUE(Color.BLUE), 
    GREEN(Color.GREEN), 
    PINK(Color.PINK), 
    RED(Color.RED), 
    ORANGE(Color.ORANGE);

    Color color;

    Colors(Color color) {
        this.color = color;
    }
};