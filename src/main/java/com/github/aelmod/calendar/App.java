package com.github.aelmod.calendar;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.YearMonth;

public class App {

    private final int HORIZONTAL_PADDING = 2;

    public void run(YearMonth yearMonth) {
        String[][] calendarGrid = makeCalendarGrid(yearMonth);

        renderGrid(calendarGrid);
    }

    private String[][] makeCalendarGrid(YearMonth yearMonth) {
        int daysInMonth = yearMonth.lengthOfMonth();
        int weekStartsOf = yearMonth.atDay(1).getDayOfWeek().getValue() - 1;
        int rowCount = (int) Math.ceil((weekStartsOf + daysInMonth) / 7.0) + 1;
        String[][] calendarGrid = new String[rowCount][7];
        calendarGrid[0] = getHeaderRow();
        for (int i = weekStartsOf; i < daysInMonth + weekStartsOf; i++) {
            calendarGrid[i / 7 + 1][i % 7] = String.valueOf(i - weekStartsOf + 1);
        }
        return calendarGrid;
    }

    private String[] getHeaderRow() {
        DayOfWeek[] values = DayOfWeek.values();
        String[] header = new String[DayOfWeek.values().length];
        for (int i = 0; i < values.length; i++) {
            header[i] = values[i].toString();
        }
        return header;
    }

    private void renderGrid(String[][] calendarGrid) {
        Grid grid = new Grid(System.out, getLongestDayTitle() + HORIZONTAL_PADDING, 1);

        grid.setHeaderFormatter((cell, rowNumber, columnNumber) -> {
            if (columnNumber == 5 || columnNumber == 6) return ConsoleUtils.Color.red(cell);
            return cell;
        });

        final int today = LocalDate.now().getDayOfMonth();

        grid.setCellFormatter((cell, rowNumber, columnNumber) -> {
            if (columnNumber == 5 || columnNumber == 6) return ConsoleUtils.Color.red(cell);
            if (cell.equals(String.valueOf(today))) return ConsoleUtils.Color.yellow(cell);
            return cell;
        });

        grid.render(calendarGrid);
    }

    private int getLongestDayTitle() {
        DayOfWeek[] values = DayOfWeek.values();
        int maxLength = 0;
        for (DayOfWeek value : values) {
            maxLength = Math.max(value.toString().length(), maxLength);
        }
        return maxLength;
    }
}
