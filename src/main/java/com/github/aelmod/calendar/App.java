package com.github.aelmod.calendar;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.YearMonth;

public class App {

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
            header[i] = values[i].toString().substring(0, 3);
        }
        return header;
    }

    private void renderGrid(String[][] calendarGrid) {
        Grid grid = new Grid(System.out, getLongestDayTitle());

        grid.setHeaderFormatter((cell, rowNumber, columnNumber) -> {
            if (isWeekEnd(columnNumber)) return ConsoleUtils.Color.red(cell);
            return cell;
        });

        grid.setCellFormatter((cell, rowNumber, columnNumber) -> {
            if (isToday(cell)) return ConsoleUtils.Color.yellow(cell);
            if (isWeekEnd(columnNumber)) return ConsoleUtils.Color.red(cell);
            return cell;
        });

        grid.render(calendarGrid);
    }

    private int getLongestDayTitle() {
        DayOfWeek[] values = DayOfWeek.values();
        int maxLength = 0;
        for (DayOfWeek value : values) {
            maxLength = Math.max(value.toString().substring(0, 5).length(), maxLength);
        }
        return maxLength;
    }

    private boolean isWeekEnd(int day) {
        return day > 0 && ((DayOfWeek.of(day + 1) == DayOfWeek.SATURDAY) || DayOfWeek.of(day + 1) == DayOfWeek.SUNDAY);
    }

    private boolean isToday(String day) {
        final int today = LocalDate.now().getDayOfMonth();
        return day.equals(String.valueOf(today));
    }
}
