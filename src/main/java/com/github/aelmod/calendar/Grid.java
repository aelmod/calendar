package com.github.aelmod.calendar;

import java.io.PrintStream;
import java.util.Objects;

public class Grid {

    private PrintStream out;

    private int width;

    private int verticalPadding;

    private String horizontalSeparator = "||";

    private String verticalSeparator = "=";

    private CellProcessor headerFormatter = (s, i, j) -> s;

    private CellProcessor cellFormatter = (s, i, j) -> s;

    public Grid(PrintStream out) {
        this(out, 5, 1);
    }

    public Grid(PrintStream out, int width, int verticalPadding) {
        this.out = out;
        this.width = width;
        this.verticalPadding = verticalPadding;
    }

    public void setHorizontalSeparator(String horizontalSeparator) {
        this.horizontalSeparator = horizontalSeparator;
    }

    public void setVerticalSeparator(String verticalSeparator) {
        this.verticalSeparator = verticalSeparator;
    }

    public void setHeaderFormatter(CellProcessor headerFormatter) {
        this.headerFormatter = headerFormatter;
    }

    public void setCellFormatter(CellProcessor cellFormatter) {
        this.cellFormatter = cellFormatter;
    }

    public void render(String[][] grid) {
        int columnCount = grid[0].length;

        printRowBorder(columnCount);

        for (int i = 0; i < grid.length; i++) {
            printCellPadding(columnCount);

            printRow(grid[i], columnCount, i);

            printCellPadding(columnCount);

            printRowBorder(columnCount);
        }
    }

    private void printRow(String[] strings, int columnCount, int i) {
        for (int j = 0; j < columnCount; j++) {
            printCellContent(strings[j], i, j);
        }
        out.println();
    }

    private void printCellContent(String cellContent, int i, int j) {
        String value = cellContent;
        if (Objects.isNull(value)) value = "";

        int leftPadding = getCellContentPadding(value.length(), false);
        int rightPadding = getCellContentPadding(value.length(), true);

        value = format(value, i, j);

        String formattedCell = String.format("%" + leftPadding + "s%s%" + rightPadding + "s", "", value, "");
        out.print(formattedCell + horizontalSeparator);
    }

    private int getCellContentPadding(int contentLength, boolean roundToCeil) {
        int padding = (width - contentLength) / 2;
        if (roundToCeil) {
            padding += (width - contentLength) % 2;
        }
        return Math.max(padding, 1);
    }

    private String format(String value, int i, int j) {
        if (i == 0) {
            value = headerFormatter.apply(value, i, j);
        } else {
            value = cellFormatter.apply(value, i, j);
        }
        return value;
    }


    private void printCellPadding(int cellCount) {
        for (int l = 0; l < verticalPadding; l++) {
            for (int j = 0; j < cellCount; j++) {
                out.printf("%" + width + "s" + horizontalSeparator, "");
            }
            out.println();
        }
    }

    private void printRowBorder(int cellCount) {
        for (int j = 0; j < cellCount * (width + horizontalSeparator.length()); j++) {
            out.printf(verticalSeparator);
        }
        out.println();
    }

    @FunctionalInterface
    public interface CellProcessor {
        String apply(String cell, int rowNumber, int columnNumber);
    }
}
