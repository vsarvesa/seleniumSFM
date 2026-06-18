package utils;

import com.github.javafaker.Faker;
import org.apache.commons.lang3.RandomStringUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

public class Utility {

    private static final Faker faker = new Faker();

    /**
     * Generates a random alphabetic string (name) of the specified length.
     * @param length The length of the random name.
     * @return Random alphabetic string.
     */
    public static String generateRandomName(int length) {
        return RandomStringUtils.randomAlphabetic(length);
    }

    /**
     * Generates a random full name using Java Faker.
     * @return A random full name.
     */
    public static String generateFakerFullName() {
        return faker.name().fullName();
    }

    public static String generateFakeFirstName(){
        return faker.name().firstName();
    }

    public static String generateFakerLastName(){
        return faker.name().lastName();
    }

    public static String generateFakerphonenumber(){
        // Guarantees a 10-digit string consisting ONLY of numbers, and guarantees it will never start with a 0.
        return String.valueOf(faker.number().numberBetween(1000000000L, 9999999999L));
    }

    /**
     * Generates a unique value based on the current timestamp.
     * @return A unique string based on time.
     */
    public static String generateUniqueValue() {
        return String.valueOf(System.currentTimeMillis());
    }

    /**
     * Generates a standard Version 4 UUID / GUID.
     * @return A standard GUID string.
     */
    public static String generateGUID() {
        return UUID.randomUUID().toString();
    }

    /**
     * Gets the current date formatted according to the specified pattern.
     * Example format: "yyyy-MM-dd", "dd/MM/yyyy HH:mm:ss"
     * @param format The date time format pattern.
     * @return Formatted current date string.
     */
    public static String getCurrentDate(String format) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(format);
        LocalDateTime now = LocalDateTime.now();
        return dtf.format(now);
    }

    /**
     * Adds the specified number of days to the current date and returns it in the requested format.
     * @param daysToAdd Number of days to add.
     * @param format The date format pattern.
     * @return Formatted future date string.
     */
    public static String addDaysToCurrentDate(int daysToAdd, String format) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(format);
        LocalDate date = LocalDate.now().plusDays(daysToAdd);
        return dtf.format(date);
    }

    /**
     * Subtracts the specified number of days from the current date and returns it in the requested format.
     * @param daysToRemove Number of days to subtract.
     * @param format The date format pattern.
     * @return Formatted past date string.
     */
    public static String subtractDaysFromCurrentDate(int daysToRemove, String format) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(format);
        LocalDate date = LocalDate.now().minusDays(daysToRemove);
        return dtf.format(date);
    }

    /**
     * Adds the specified number of months to the current date and returns it in the requested format.
     * @param monthsToAdd Number of months to add.
     * @param format The date format pattern.
     * @return Formatted future date string.
     */
    public static String addMonthsToCurrentDate(int monthsToAdd, String format) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(format);
        LocalDate date = LocalDate.now().plusMonths(monthsToAdd);
        return dtf.format(date);
    }

    /**
     * Subtracts the specified number of months from the current date and returns it in the requested format.
     * @param monthsToRemove Number of months to subtract.
     * @param format The date format pattern.
     * @return Formatted past date string.
     */
    public static String subtractMonthsFromCurrentDate(int monthsToRemove, String format) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(format);
        LocalDate date = LocalDate.now().minusMonths(monthsToRemove);
        return dtf.format(date);
    }
}
