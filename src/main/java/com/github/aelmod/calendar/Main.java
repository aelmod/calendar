package com.github.aelmod.calendar;

import java.time.YearMonth;

public class Main {

    public static void main(String[] args) {

        YearMonth yearAndMonth;
        if (args.length != 0) {
            yearAndMonth = YearMonth.of(Integer.parseInt(args[0]), Integer.parseInt(args[1]));
        } else {
            yearAndMonth = YearMonth.now();
        }

        MonthPrinter monthPrinter = new MonthPrinter(yearAndMonth);
        monthPrinter.render();
    }
}
