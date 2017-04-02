package com.github.aelmod.calendar;

import java.io.PrintStream;
import java.util.Objects;

public class Grid {

    private PrintStream out;

    private int width;

    private CellProcessor headerFormatter = (s, i, j) -> s;

    private CellProcessor cellFormatter = (s, i, j) -> s;

    public Grid(PrintStream out, int width) {
        this.out = out;
        this.width = width;
    }

    public void setHeaderFormatter(CellProcessor headerFormatter) {
        this.headerFormatter = headerFormatter;
    }

    public void setCellFormatter(CellProcessor cellFormatter) {
        this.cellFormatter = cellFormatter;
    }

    public void render(String[][] grid) {
        int columnCount = grid[0].length;

        for (int i = 0; i < grid.length; i++) {
            printRow(grid[i], columnCount, i);
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

        int leftPadding = (int) Math.floor(getCellContentPadding(value.length()));
        int rightPadding = (int) Math.ceil(getCellContentPadding(value.length()));

        value = format(value, i, j);

        String formattedCell = String.format("%" + leftPadding + "s%s%" + rightPadding + "s", "", value, "");
        out.print(formattedCell);
    }

    private double getCellContentPadding(int contentLength) {
        double padding = (width - contentLength) / 2d;
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

    @FunctionalInterface
    public interface CellProcessor {
        String apply(String cell, int rowNumber, int columnNumber);
    }
}
