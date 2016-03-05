import DomainObjects.Canvas;
import GeneratePixels.GenerateRandomPixels;

import java.awt.*;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;

public class Window extends JPanel {

    private BufferedImage image;
    private Canvas canvas;
    private int pixelSize;

    public Window(int width, int height, int pixelSize) {
        this.pixelSize = pixelSize;
        image = new BufferedImage(width*pixelSize, height*pixelSize, BufferedImage.TYPE_INT_ARGB);
        canvas = Canvas.create(width, height, new GenerateRandomPixels());

        canvas.paint(image, pixelSize);
        repaint();
    }

    public Dimension getPreferredSize() {
        return new Dimension(image.getWidth(), image.getHeight());
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.drawImage(image, null, null);
    }

    public void mutate() {
        canvas.mutate();
        canvas.paint(image, pixelSize);
        repaint();
    }
}