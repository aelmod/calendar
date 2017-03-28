package com.github.aelmod.calendar;

import java.time.LocalDate;
import java.time.Month;

public class Main {

    public static void main(String[] args) {
        App app = new App();
        if (args.length != 0) {
            app.run(Month.of(Integer.parseInt(args[0])));
        } else {
            app.run(LocalDate.now().getMonth());
        }
    }
}
