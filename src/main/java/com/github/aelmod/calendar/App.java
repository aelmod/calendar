package com.github.aelmod.calendar;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.Objects;

public class App {

    private final int HORIZONTAL_PADDING = 2;

    public void run(YearMonth yearMonth) {
        int daysInMonth = yearMonth.lengthOfMonth();

        String[][] calendarPage = new String[6][DayOfWeek.values().length];
        calendarPage[0] = getHeader();

        int weekStartsOf = yearMonth.atDay(1).getDayOfWeek().getValue() - 1;

        for (int i = weekStartsOf; i < daysInMonth + weekStartsOf; i++) {
            calendarPage[i / 7 + 1][i % 7] = String.valueOf(i - weekStartsOf + 1);
        }

        renderGrid(calendarPage);
    }

    private String[] getHeader() {
        DayOfWeek[] values = DayOfWeek.values();
        String[] header = new String[DayOfWeek.values().length];
        for (int i = 0; i < values.length; i++) {
            header[i] = values[i].toString();
        }
        return header;
    }

    private void renderGrid(String[][] days) {
        Grid grid = new Grid(System.out, getLongestDayTitle() + HORIZONTAL_PADDING, 1);

        grid.setHeaderFormatter((cell, rowNumber, columnNumber) -> {
            if (Objects.equals(cell, DayOfWeek.SUNDAY.toString())) return ConsoleUtils.Color.red(cell);
            if (Objects.equals(cell, DayOfWeek.SATURDAY.toString())) return ConsoleUtils.Color.red(cell);
            return cell;
        });

        final int today = LocalDate.now().getDayOfMonth();

        grid.setCellFormatter((cell, rowNumber, columnNumber) -> {
            if (columnNumber == 5 || columnNumber == 6) return ConsoleUtils.Color.red(cell);
            if (cell.equals(String.valueOf(today))) return ConsoleUtils.Color.yellow(cell);
            return cell;
        });

        grid.render(days);
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
