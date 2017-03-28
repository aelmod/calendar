package com.github.aelmod.calendar;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class App {

    private final int HORIZONTAL_PADDING = 2;

    public void run(Month month) {
        int year = LocalDate.now().getYear();
        LocalDate localDate = LocalDate.of(year, month, 1);
        int dayOfWeek = localDate.getDayOfWeek().getValue();
        boolean leapYear = localDate.isLeapYear();
        int dayOfMonth = LocalDate.now().getDayOfYear() - (month.firstDayOfYear(false) - 1);

        int daysInMonth = 0;
        int tmpCounter = 1;
        String[][] days = new String[5][DayOfWeek.values().length];
        for (int i = 0; i < days.length; i++) {
            for (int j = 0; j < days[i].length; j++) {
                if (tmpCounter < dayOfWeek) {
                    tmpCounter++;
                    continue;
                }
                days[i][j] = "" + (daysInMonth + 1);
                daysInMonth++;
                if (daysInMonth == month.length(leapYear)) break;
            }
        }
        renderGrid(addHeader(toList(days)), dayOfMonth, dayOfWeek);
    }

    private List<String[]> addHeader(List<String[]> days) {
        days.add(0, getHeader());
        return days;
    }

    private List<String[]> toList(String[][] days) {
        return new ArrayList<>(Arrays.asList(days));
    }

    private String[] getHeader() {
        DayOfWeek[] values = DayOfWeek.values();
        String[] header = new String[DayOfWeek.values().length];
        for (int i = 0; i < values.length; i++) {
            header[i] = values[i].toString();
        }
        return header;
    }

    private void renderGrid(List<String[]> days, int dayOfMonth, int dayOfWeek) {
        Grid grid = new Grid(System.out, getMaxDayNameLength() + HORIZONTAL_PADDING, 1);

        grid.setHeaderFormatter((cell, rowNumber, columnNumber) -> {
            if (Objects.equals(cell, DayOfWeek.SUNDAY.toString())) return ConsoleUtils.Color.red(cell);
            if (Objects.equals(cell, DayOfWeek.SATURDAY.toString())) return ConsoleUtils.Color.red(cell);
            return cell;
        });

        grid.setCellFormatter((cell, rowNumber, columnNumber) -> {
            try {
                int day = Integer.parseInt(cell);
                if (((day + dayOfWeek) % 7 == 1) ||
                        ((day + dayOfWeek) % 7 == 0))
                    return ConsoleUtils.Color.red(cell);
            } catch (NumberFormatException ignored) {}
            if (cell.equals(String.valueOf(dayOfMonth))) return ConsoleUtils.Color.yellow(cell);
            return cell;
        });
        grid.render(days);
    }

    private int getMaxDayNameLength() {
        DayOfWeek[] values = DayOfWeek.values();
        int maxLength = 0;
        for (DayOfWeek value : values) {
            int length = value.toString().length();
            if (maxLength < length) maxLength = length;
        }
        return maxLength;
    }
}
