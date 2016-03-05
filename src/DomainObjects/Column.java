package DomainObjects;

import org.apache.commons.math3.stat.descriptive.SummaryStatistics;

public class Column {
    private final Cell[] cells;

    public Column(int size, GeneratePixels.IGeneratePixels generatePixels){
        cells = new Cell[size];

        for (int cellIndex = 0; cellIndex < cells.length; cellIndex++){
            cells[cellIndex] = new Cell(generatePixels.createPixel(cellIndex));
        }
    }

    private Column(Cell[] cells) {
        this.cells = cells;
    }

    public Cell getCell(int index) {
        return cells[index];
    }

    public Column mutate() {
        Column column = new Column(this.cells);
        int redDiff = createDiff();
        int greenDiff = createDiff();
        int blueDiff = createDiff();

        for (Cell cell : column.cells){
            cell.mutate(redDiff, greenDiff, blueDiff);
        }

        return column;
    }

    public static Column best(Column one, Column two) {
        double oneValue = Column.getRank(one);
        double twoValue = Column.getRank(two);

        return oneValue < twoValue ? one : two;
    }

    public static double getRank(Column column) {
        SummaryStatistics stats = new SummaryStatistics();
        for (Cell cell : column.cells){
            stats.addValue(cell.getRGB());
        }
        return stats.getStandardDeviation();
    }

    private int createDiff() {
        double diff = Math.random() * 2.0 - 1.0;
        diff *= 10.0;
        return (int)diff;
    }
}
