import javax.swing.*;

public class Program {
    public static void main(String[] args) {
        int width = 50;
        int height = 20;
        int pixelSize = 20;
        JFrame frame = new JFrame("Fun");

        Window window = new Window(width, height, pixelSize);

        frame.add(window);
        frame.pack();
        frame.setVisible(true);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        while (true){
            window.mutate();
            try {
                Thread.sleep(100);
            } catch(InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
        }
    }
}

