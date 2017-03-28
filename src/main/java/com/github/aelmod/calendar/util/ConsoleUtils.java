package com.github.aelmod.calendar.util;

import java.math.BigDecimal;
import java.util.Scanner;

public class ConsoleUtils {

    private static Scanner scanner = new Scanner(System.in);

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


    public static int getIntFromUser() {
        while (!scanner.hasNextInt()) {
            scanner.next();
            System.err.print("Wrong input, please try again: ");
        }
        return scanner.nextInt();
    }

    public static long getLongFromUser() {
        while (!scanner.hasNextLong()) {
            scanner.next();
            System.err.print("Wrong input, please try again: ");
        }
        return scanner.nextLong();
    }

    public static void clearScreen() {
        for (int i = 0; i < 40; i++) {
            System.out.println();
        }
    }

    public static String getLineFromUser() {
        while (!scanner.hasNextLine()) {
            scanner.next();
            System.err.print("Wrong input, please try again: ");
        }

        String res;

        while ((res = scanner.nextLine()).isEmpty()) ;

        return res;
    }

    public static String getWordFromUser() {
        String res;

        while ((res = scanner.next()).isEmpty()) ;

        return res;
    }

    /**
     * @param min Min acceptable value
     * @param max Max acceptable value
     * @return inputted number from user
     */
    public static int getIntFromUser(int min, int max) {
        int inputtedNumber = getIntFromUser();
        while (inputtedNumber < min || inputtedNumber > max) {
            System.err.print("Wrong input, please try again: ");
            inputtedNumber = getIntFromUser();
        }
        return inputtedNumber;
    }

    public static double getDoubleFromUser() {
        while (!scanner.hasNextDouble()) {
            scanner.next();
            System.err.print("Wrong input, please try again: ");
        }
        return scanner.nextDouble();
    }

    public static BigDecimal getBigDecimalFromUser() {
        while (!scanner.hasNextBigDecimal()) {
            scanner.next();
            System.err.print("Wrong input, please try again: ");
        }
        return scanner.nextBigDecimal();
    }
}
