package com.github.aelmod.calendar;

import java.time.YearMonth;

public class Main {

    public static void main(String[] args) {
        App app = new App();

        YearMonth yearAndMonth;
        if (args.length != 0) {
            yearAndMonth = YearMonth.of(Integer.parseInt(args[0]), Integer.parseInt(args[1]));
        } else {
            yearAndMonth = YearMonth.now();
        }

        app.run(yearAndMonth);
    }
}
