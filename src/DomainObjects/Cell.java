package DomainObjects;

import java.awt.*;

public class Cell {

    private Color color;

    public Cell(Color color) {
        this.color = color;
    }

    public int getRGB() {
        return color.getRGB();
    }

    public void mutate(int redDiff, int greenDiff, int blueDiff) {
        int red = updateColourComponent(color.getRed(), redDiff);
        int green = updateColourComponent(color.getGreen(), greenDiff);
        int blue = updateColourComponent(color.getBlue(), blueDiff);
        color = new Color(red, green, blue);
    }

    private int updateColourComponent(int original, int diff) {
        int newValue = original + diff;
        if(newValue < 0){
            return 0;
        }

        if(newValue > 255){
            return 255;
        }

        return newValue;
    }
}
