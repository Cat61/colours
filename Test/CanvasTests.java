import DomainObjects.Canvas;
import GeneratePixels.GenerateRandomPixels;
import GeneratePixels.IGeneratePixels;
import org.junit.Test;

import java.awt.*;
import java.awt.image.BufferedImage;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.mockito.Mockito.*;

public class CanvasTests {
    @Test
    public void create_WhenWidthIs1AndHeightIs2_ShouldGenerate2Cells(){
        int width = 1;
        int height = 2;
        IGeneratePixels generatePixelsMock = mock(IGeneratePixels.class);

        Canvas canvas = Canvas.create(width, height, generatePixelsMock);

        verify(generatePixelsMock, times(2)).createPixel(anyInt());
    }

    @Test
    public void create_WhenWidthIs3AndHeightIs3_ShouldGenerate9Cells(){
        int width = 3;
        int height = 3;
        IGeneratePixels generatePixelsMock = mock(IGeneratePixels.class);

        Canvas canvas = Canvas.create(width, height, generatePixelsMock);

        verify(generatePixelsMock, times(9)).createPixel(anyInt());
    }

    @Test
    public void get_ShouldGetCorrectPixel(){
        int width = 2;
        int height = 3;
        Color pixelZero = new Color(0);
        Color pixelOne = new Color(1);
        Color pixelTwo = new Color(2);
        IGeneratePixels generatePixelsMock = mock(IGeneratePixels.class);
        when(generatePixelsMock.createPixel(0)).thenReturn(pixelZero);
        when(generatePixelsMock.createPixel(1)).thenReturn(pixelOne);
        when(generatePixelsMock.createPixel(2)).thenReturn(pixelTwo);

        Canvas canvas = Canvas.create(width, height, generatePixelsMock);

        assertEquals(pixelZero.getRGB(), canvas.get(0, 0));
        assertEquals(pixelOne.getRGB(), canvas.get(0, 1));
        assertEquals(pixelTwo.getRGB(), canvas.get(0, 2));

        assertEquals(pixelZero.getRGB(), canvas.get(1, 0));
        assertEquals(pixelOne.getRGB(), canvas.get(1, 1));
        assertEquals(pixelTwo.getRGB(), canvas.get(1, 2));
    }

    @Test
    public void paint_ShouldAssignCorrectColours(){
        int width = 1;
        int height = 3;
        IGeneratePixels generatePixels = mock(IGeneratePixels.class);
        when(generatePixels.createPixel(0)).thenReturn(Color.black);
        when(generatePixels.createPixel(1)).thenReturn(Color.blue);
        when(generatePixels.createPixel(2)).thenReturn(Color.white);
        BufferedImage bufferedImage = new BufferedImage(width,height, BufferedImage.TYPE_INT_ARGB);

        Canvas canvas = Canvas.create(width, height, generatePixels);
        canvas.paint(bufferedImage, 1);

        assertEquals(Color.black.getRGB(), bufferedImage.getRGB(0,0));
        assertEquals(Color.blue.getRGB(), bufferedImage.getRGB(0,1));
        assertEquals(Color.white.getRGB(), bufferedImage.getRGB(0,2));
    }

    @Test
    public void paint_WhenPixelSizeIs2_ShouldAssignCorrectColours(){
        int pixelSize = 2;
        int width = 1;
        int height = 2;
        IGeneratePixels generatePixels = mock(IGeneratePixels.class);
        Color colorOne = new Color(1);
        Color colorTwo = new Color(2);
        when(generatePixels.createPixel(anyInt())).thenReturn(
                colorOne,
                colorTwo);
        BufferedImage bufferedImage = new BufferedImage(
                width*pixelSize,
                height*pixelSize,
                BufferedImage.TYPE_INT_ARGB);

        Canvas canvas = Canvas.create(width, height, generatePixels);
        canvas.paint(bufferedImage, pixelSize);

        assertEquals(colorOne.getRGB(), bufferedImage.getRGB(0,0));
        assertEquals(colorOne.getRGB(), bufferedImage.getRGB(0,1));
        assertEquals(colorOne.getRGB(), bufferedImage.getRGB(1,0));
        assertEquals(colorOne.getRGB(), bufferedImage.getRGB(1,1));

        assertEquals(colorTwo.getRGB(), bufferedImage.getRGB(0,2));
        assertEquals(colorTwo.getRGB(), bufferedImage.getRGB(0,3));
        assertEquals(colorTwo.getRGB(), bufferedImage.getRGB(1,2));
        assertEquals(colorTwo.getRGB(), bufferedImage.getRGB(1,3));
    }

    @Test
    public void mutate_ShouldNotMutateTheSameForColumn1AndColumn2(){
        int width = 2;
        int height = 1;

        Canvas canvas = Canvas.create(width, height, new GenerateRandomPixels());

        int originalCellOne = canvas.get(0,0);
        int originalCellTwo = canvas.get(1,0);
        canvas.mutate();
        int mutatedCellOne = canvas.get(0,0);
        int mutatedCellTwo = canvas.get(1,0);

        int diffOne = originalCellOne - mutatedCellOne;
        int diffTwo = originalCellTwo - mutatedCellTwo;

        assertNotEquals(diffOne, diffTwo);
    }
}
