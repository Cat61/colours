package GeneratePixels;

import java.awt.*;

public class GenerateRandomPixels implements IGeneratePixels {
    @Override
    public Color createPixel(int index) {
        return new Color(randomColor(), randomColor(), randomColor());
    }

    private int randomColor() {
        return (int)(Math.random() * 255);
    }
}
