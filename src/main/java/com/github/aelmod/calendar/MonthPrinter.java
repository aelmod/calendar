package com.github.aelmod.calendar;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.YearMonth;

public class MonthPrinter {

    private YearMonth yearMonth;

    private String pattern = "%5s";

    private int daysInMonth;

    private DayOfWeek monthStartsOf;

    public MonthPrinter(YearMonth yearMonth) {
        this.yearMonth = yearMonth;
        daysInMonth = yearMonth.lengthOfMonth();
        monthStartsOf = yearMonth.atDay(1).getDayOfWeek();
    }

    public void render() {
        printHeader();
        printFirstWeekPadding(monthStartsOf);
        for (int day = 1; day <= daysInMonth; day++) {
            printDay(day);

            if (yearMonth.atDay(day).getDayOfWeek().equals(DayOfWeek.SUNDAY)) {
                printWeekSeparator();
            }
        }
    }

    private void printDay(int day) {
        if (isToday(day)) {
            printToday(day);
        } else if (isWeekend(day)) {
            printWeekend(day);
        } else {
            printRegularDay(day);
        }
    }

    private String[] getWeekDayNames() {
        DayOfWeek[] values = DayOfWeek.values();
        String[] header = new String[DayOfWeek.values().length];
        for (int i = 0; i < values.length; i++) {
            header[i] = values[i].toString();
        }
        return header;
    }

    private boolean isWeekend(int day) {
        return yearMonth.atDay(day).getDayOfWeek().equals(DayOfWeek.SUNDAY) ||
                yearMonth.atDay(day).getDayOfWeek().equals(DayOfWeek.SATURDAY);
    }

    private boolean isToday(int day) {
        return day == LocalDate.now().getDayOfMonth();
    }

    protected void printHeader() {
        String[] weekDayNames = getWeekDayNames();
        for (int i = 0; i < weekDayNames.length; i++) {
            String shortDayName = weekDayNames[i].substring(0, 3);
            if (i > 4) {
                System.out.printf(ConsoleUtils.Color.red(pattern), shortDayName);
            } else {
                System.out.printf(pattern, shortDayName);
            }
        }
        System.out.println();
    }

    protected void printWeekSeparator() {
        System.out.println();
    }

    protected void printRegularDay(int day) {
        System.out.printf(pattern, day);
    }

    protected void printWeekend(int day) {
        System.out.printf(ConsoleUtils.Color.red(pattern), day);
    }

    protected void printToday(int day) {
        System.out.printf(ConsoleUtils.Color.cyan(pattern), day);
    }

    protected void printFirstWeekPadding(DayOfWeek dayOfWeekNumber) {
        System.out.printf("%" + 5 * (dayOfWeekNumber.getValue() - 1) + "s", "");
    }
}
