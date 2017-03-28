package com.github.aelmod.calendar.grid;

import java.io.PrintStream;
import java.util.List;
import java.util.Objects;

public class Grid {

    private PrintStream out;

    private int width;

    private int verticalPadding;

    private String horizontalSeparator = "||";

    private String verticalSeparator = "=";

    private CellProcessor cellMapper = (s, i, j) -> s;

    private CellProcessor headerMapper = (s, i, j) -> s;

    private CellProcessor headerFormatter = (s, i, j) -> s;

    private CellProcessor cellFormatter = (s, i, j) -> s;

    private String[][] header;

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

    public void setCellMapper(CellProcessor cellMapper) {
        this.cellMapper = cellMapper;
    }

    public void setHeaderMapper(CellProcessor headerMapper) {
        this.headerMapper = headerMapper;
    }

    public void setHeaderFormatter(CellProcessor headerFormatter) {
        this.headerFormatter = headerFormatter;
    }

    public void setCellFormatter(CellProcessor cellFormatter) {
        this.cellFormatter = cellFormatter;
    }

    public void render(List<String[]> grid) {
        int sepLength = (width + horizontalSeparator.length()) * grid.get(0).length;
        setTopHorizontalSeparator(sepLength);
        for (int i = 0; i < grid.size(); i++) {
            int cellCount = grid.get(i).length;
            setTopVerticalSeparator(cellCount);
            for (int j = 0; j < cellCount; j++) {
                String value = grid.get(i)[j];
                if (Objects.isNull(value)) value = "";
                value = setMapper(i, j, value);
                int leftPadding = (width - value.length()) / 2;
                leftPadding = leftPadding > 0 ? leftPadding : 1;
                int rightPadding = (width - value.length()) / 2 + (width - value.length()) % 2;
                rightPadding = rightPadding > 0 ? rightPadding : 1;
                value = setFormatter(i, j, value);
                String formattedCell = String.format("%" + leftPadding + "s%s%" + rightPadding + "s", "", value, "");
                out.print(formattedCell + horizontalSeparator);
            }
            setVerticalPadding(cellCount);
            setHorizontalSeparator(cellCount);
        }
    }

    private String setFormatter(int i, int j, String value) {
        if (i == 0) {
            value = headerFormatter.apply(value, i, j);
        } else {
            value = cellFormatter.apply(value, i, j);
        }
        return value;
    }

    private String setMapper(int i, int j, String value) {
        if (i == 0) {
            value = headerMapper.apply(value, i, j);
        } else {
            value = cellMapper.apply(value, i, j);
        }
        return value;
    }

    private void setTopHorizontalSeparator(int sepLength) {
        for (int i = 0; i < sepLength; i++) {
            out.print(verticalSeparator);
        }
        out.println();
    }

    private void setTopVerticalSeparator(int cellCount) {
        for (int l = 0; l < verticalPadding; l++) {
            for (int j = 0; j < cellCount; j++) {
                out.printf("%" + width + "s" + horizontalSeparator, "");
            }
            out.println();
        }
    }

    private void setHorizontalSeparator(int cellCount) {
        for (int j = 0; j < cellCount * (width + horizontalSeparator.length()); j++) {
            out.printf(verticalSeparator);
        }
        out.println();
    }

    private void setVerticalPadding(int cellCount) {
        for (int l = 0; l < verticalPadding; l++) {
            out.println();
            for (int j = 0; j < cellCount; j++) {
                out.printf("%" + width + "s" + horizontalSeparator, "");
            }
        }
        out.println();
    }

    @FunctionalInterface
    public interface CellProcessor {

        String apply(String cell, int rowNumber, int columnNumber);
    }
}
