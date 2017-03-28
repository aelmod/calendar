package com.github.aelmod.calendar;

import com.github.aelmod.calendar.grid.Grid;
import com.github.aelmod.calendar.util.ConsoleUtils;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class CalendarService {

    public void show(Month month) {
        int year = LocalDate.now().getYear();
        LocalDate localDate = LocalDate.of(year, month, 1);
        int dayOfWeek = localDate.getDayOfWeek().getValue();
        boolean leapYear = localDate.isLeapYear();
        int dayOfMonth = LocalDate.now().getDayOfYear() - (month.firstDayOfYear(false) - 1);

        String[][] days = getDays(month, dayOfWeek, leapYear);
        renderGrid(dayOfWeek, dayOfMonth, days);
    }

    private String[][] getDays(Month month, int dayOfWeek, boolean leapYear) {
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
        return days;
    }

    private void renderGrid(int dayOfWeek, int dayOfMonth, String[][] days) {
        Grid grid = new Grid(System.out, 11, 1);

        grid.setHeaderFormatter((cell, rowNumber, columnNumber) -> {
            if (Objects.equals(cell, DayOfWeek.SUNDAY.toString())) return ConsoleUtils.Color.red(cell);
            if (Objects.equals(cell, DayOfWeek.SATURDAY.toString())) return ConsoleUtils.Color.red(cell);
            return cell;
        });

        grid.setCellFormatter((cell, rowNumber, columnNumber) -> {
            int currentDay = 0;
            try {
                currentDay = Integer.parseInt(cell);
            } catch (Throwable ignored) {}
            if (((currentDay + dayOfWeek) % 7 == 1) ||
                    ((currentDay + dayOfWeek) % 7 == 0))
                return ConsoleUtils.Color.red(cell);
            if (cell.equals(String.valueOf(dayOfMonth))) return ConsoleUtils.Color.yellow(cell);
            return cell;
        });

        grid.render(setHeader(toList(days)));
    }

    private List<String[]> setHeader(List<String[]> days) {
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
}
