package com.github.aelmod.calendar;

import java.time.LocalDate;
import java.time.Month;

public class App {

    public static void main(String[] args) {
        CalendarService calendarService = new CalendarService();
        if (args.length != 0) {
            calendarService.show(Month.of(Integer.parseInt(args[0])));
        } else {
            calendarService.show(LocalDate.now().getMonth());
        }
    }
}
