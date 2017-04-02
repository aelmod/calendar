package com.github.aelmod.calendar;

public class ConsoleUtils {

    public static class Color {

        static final String RESET = "\u001B[0m";

        static final String BLACK = "\u001B[30m";

        static final String RED = "\u001B[31m";

        static final String GREEN = "\u001B[32m";

        static final String YELLOW = "\u001B[33m";

        static final String BLUE = "\u001B[34m";

        static final String PURPLE = "\u001B[35m";

        static final String CYAN = "\u001B[36m";

        static final String WHITE = "\u001B[37m";

        public static String black(String s) {
            return wrap(s, BLACK);
        }

        public static String red(String s) {
            return wrap(s, RED);
        }

        public static String green(String s) {
            return wrap(s, GREEN);
        }

        public static String yellow(String s) {
            return wrap(s, YELLOW);
        }

        public static String blue(String s) {
            return wrap(s, BLUE);
        }

        public static String purple(String s) {
            return wrap(s, PURPLE);
        }

        public static String cyan(String s) {
            return wrap(s, CYAN);
        }

        public static String white(String s) {
            return wrap(s, WHITE);
        }

        private static String wrap(String s, String color) {
            if ("".equals(s)) {
                return s;
            }
            return color + s + RESET;
        }
    }
}
