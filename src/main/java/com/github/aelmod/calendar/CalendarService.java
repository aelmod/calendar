package com.github.aelmod.calendar;

import com.github.aelmod.calendar.grid.Grid;
import com.github.aelmod.calendar.util.ConsoleUtils;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.util.*;

public class CalendarService {

    public void show(Month month) {
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

        Grid grid = new Grid(System.out, 11,1);
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

        List<String[]> calendarValues = setHeader(days);
        grid.render(calendarValues);
    }

    private List<String[]> setHeader(String[][] days) {
        List<String[]> strings = new ArrayList<>(Arrays.asList(days));
        strings.add(0, getHeader());
        return strings;
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
