package DomainObjects;

import GeneratePixels.IGeneratePixels;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Canvas {

    private int width;
    private int height;
    private Column[] columns;

    private Canvas(int width, int height, IGeneratePixels generatePixels) {
        this.width = width;
        this.height = height;
        columns = new Column[width];
        for (int index = 0; index < columns.length; index++){
            columns[index] = new Column(height, generatePixels);
        }
    }

    public static Canvas create(int width, int height, IGeneratePixels generatePixels) {
        return new Canvas(width, height, generatePixels);
    }

    public int get(int x, int y) {
        return columns[x].getCell(y).getRGB();
    }

    public void paint(BufferedImage image, int pixelSize) {
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                paintPixel(
                        image,
                        get(x, y),
                        x*pixelSize,
                        x*pixelSize + pixelSize,
                        y*pixelSize,
                        y*pixelSize + pixelSize);
            }
        }
    }

    private void paintPixel(BufferedImage image,
                            int colour,
                            int xStart,
                            int xEnd,
                            int yStart,
                            int yEnd) {
        for (int i = xStart; i < xEnd; i++)
        {
            for (int j = yStart; j < yEnd; j++)
            {
                image.setRGB(i, j, colour);
            }
        }
    }

    public void mutate() {
        for (int i = 0; i < columns.length; i++) {
            Column column = columns[i];
            columns[i] = Column.best(column, column.mutate());
        }
    }
}
