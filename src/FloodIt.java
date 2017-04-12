import javax.swing.JFrame;

public class FloodIt {
    
    public FloodIt() {
        JFrame frame = new JFrame();
        frame.setTitle("FloodIt");
        frame.setSize(300, 400);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    
    public static void main(String[] args) {
        new FloodIt();
    }
}
