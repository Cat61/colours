import DomainObjects.Cell;
import org.junit.Test;

import java.awt.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

public class CellTests {
    @Test
    public void getRGB_ShouldReturnRGB(){
        int red = 1;
        int green = 2;
        int blue = 3;
        Color color = new Color(red, green, blue);

        Cell cell = new Cell(color);

        assertEquals(color.getRGB(), cell.getRGB());
    }

    @Test
    public void mutate_ShouldMakeAColorChange(){
        Color color = new Color(1, 2, 3);
        int diff = 1;

        Cell cell = new Cell(color);
        int redOriginal = new Color(cell.getRGB()).getRed();
        cell.mutate(diff, 0, 0);
        int redMutated = new Color(cell.getRGB()).getRed();

        int difference = Math.abs(redOriginal - redMutated);
        assertEquals(1, difference);
    }
}
