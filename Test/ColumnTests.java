import DomainObjects.Column;
import GeneratePixels.GenerateRandomPixels;
import GeneratePixels.IGeneratePixels;
import org.junit.Test;
import org.junit.Assert;

import java.awt.*;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class ColumnTests {
    @Test
    public void constructor_ShouldGenerateCorrectNumberOfCells() {
        int numberOfCells = 3;
        IGeneratePixels generatePixelsMock = mock(IGeneratePixels.class);

        Column column = new Column(numberOfCells, generatePixelsMock);

        verify(generatePixelsMock, times(3)).createPixel(anyInt());
    }

    @Test(expected=IndexOutOfBoundsException.class)
    public void getCell_WhenCellNotExists_ThrowException() {
        int numberOfCells = 2;
        IGeneratePixels generatePixelsMock = mock(IGeneratePixels.class);

        Column column = new Column(numberOfCells, generatePixelsMock);

        column.getCell(numberOfCells + 1);
    }

    @Test
    public void mutate_AllCellsShouldHaveTheSameDifference(){
        int numberOfCells = 2;

        Column originalColumn = new Column(numberOfCells, new GenerateRandomPixels());
        int originalCellOne = originalColumn.getCell(0).getRGB();
        int originalCellTwo = originalColumn.getCell(1).getRGB();

        Column mutatedColumn = originalColumn.mutate();
        int mutatedCellOne = mutatedColumn.getCell(0).getRGB();
        int mutatedCellTwo = mutatedColumn.getCell(1).getRGB();

        int diffOne = originalCellOne - mutatedCellOne;
        int diffTwo = originalCellTwo - mutatedCellTwo;

        assertNotEquals(0, diffOne);
        assertEquals(diffOne, diffTwo);
    }

    @Test
    public void best_ReturnColumnWithLeastDifferencesInCells(){
        int numberOfCells = 2;

        IGeneratePixels generatePixelsOne = mock(IGeneratePixels.class);
        when(generatePixelsOne.createPixel(0)).thenReturn(new Color(10, 100, 0));
        when(generatePixelsOne.createPixel(1)).thenReturn(new Color(0, 80, 14));
        Column columnOne = new Column(numberOfCells, generatePixelsOne);

        IGeneratePixels generatePixelsTwo = mock(IGeneratePixels.class);
        when(generatePixelsTwo.createPixel(0)).thenReturn(new Color(0, 0, 0));
        when(generatePixelsTwo.createPixel(1)).thenReturn(new Color(200, 200, 200));
        Column columnTwo = new Column(numberOfCells, generatePixelsTwo);

        Column best = Column.best(columnOne, columnTwo);
        assertEquals(best, columnOne);

        best = Column.best(columnTwo, columnOne);
        assertEquals(best, columnOne);
    }

    @Test
    public void getRank_WhenCellsEqual_ShouldReturnZero() {
        int numberOfCells = 2;

        IGeneratePixels generatePixels = mock(IGeneratePixels.class);
        when(generatePixels.createPixel(0)).thenReturn(new Color(0));
        when(generatePixels.createPixel(1)).thenReturn(new Color(0));
        Column column = new Column(numberOfCells, generatePixels);

        assertThat(Column.getRank(column), is(0.0));
    }

    @Test
    public void getRank_WhenOneColumnIsWhiteAndOtherIsBlack_ShouldReturnGreaterThanOne() {
        int numberOfCells = 2;

        IGeneratePixels generatePixels = mock(IGeneratePixels.class);
        when(generatePixels.createPixel(0)).thenReturn(Color.WHITE);
        when(generatePixels.createPixel(1)).thenReturn(Color.BLACK);
        Column column = new Column(numberOfCells, generatePixels);

        assertTrue(Column.getRank(column) > 1);
    }
}