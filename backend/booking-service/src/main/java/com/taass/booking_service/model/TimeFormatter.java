package com.taass.booking_service.model;

import lombok.Data;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Locale;

@Data
public class TimeFormatter {
    private DayOfWeek day;
    private LocalTime open;
    private LocalTime close;

    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("h:mm a", Locale.ENGLISH);

    public static TimeFormatter fromFormattedString(String input) {
        if (input == null || input.isEmpty()) {
            throw new IllegalArgumentException("Input string cannot be null or empty");
        }

        // Split the input into day and time parts
        String[] parts = input.split(": ", 2); // Use ": " to correctly split day and time
        if (parts.length != 2) {
            throw new IllegalArgumentException("Invalid format: " + input);
        }

        String dayPart = parts[0].trim();
        String timePart = parts[1].trim();

        // Parse the day of the week
        DayOfWeek day = parseDayOfWeek(dayPart);

        // Handle "Closed" case
        if (timePart.equalsIgnoreCase("Closed")) {
            TimeFormatter oh = new TimeFormatter();
            oh.setDay(day);
            oh.setOpen(null);
            oh.setClose(null);
            return oh;
        }

        // Split the time part into opening and closing times
        String[] times = timePart.split(" - ");
        if (times.length != 2) {
            throw new IllegalArgumentException("Invalid time range format: " + timePart);
        }

        try {
            // Parse the opening and closing times
            LocalTime open = LocalTime.parse(times[0].trim(), TIME_FORMATTER);
            LocalTime close = LocalTime.parse(times[1].trim(), TIME_FORMATTER);

            // Create and return the TimeFormatter object
            TimeFormatter oh = new TimeFormatter();
            oh.setDay(day);
            oh.setOpen(open);
            oh.setClose(close);
            return oh;
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Invalid time format in: " + input, e);
        }
    }

    public static String toFormattedString(TimeFormatter input) {
        if (input.getOpen() == null || input.getClose() == null) {
            return input.getDay().toString().charAt(0) + input.getDay().toString().substring(1).toLowerCase() + ": Closed";
        } else {
            // Format time back to "h:mm a" string
            String openStr = input.getOpen().format(getTimeFormatter());
            String closeStr = input.getClose().format(getTimeFormatter());
            return input.getDay().toString().charAt(0) + input.getDay().toString().substring(1).toLowerCase() + ": " + openStr + " - " + closeStr;
        }
    }

    private static DayOfWeek parseDayOfWeek(String dayStr) {
        return switch (dayStr.toLowerCase()) {
            case "monday" -> DayOfWeek.MONDAY;
            case "tuesday" -> DayOfWeek.TUESDAY;
            case "wednesday" -> DayOfWeek.WEDNESDAY;
            case "thursday" -> DayOfWeek.THURSDAY;
            case "friday" -> DayOfWeek.FRIDAY;
            case "saturday" -> DayOfWeek.SATURDAY;
            case "sunday" -> DayOfWeek.SUNDAY;
            default -> throw new IllegalArgumentException("Invalid day of week: " + dayStr);
        };
    }

    public static DateTimeFormatter getTimeFormatter() {
        return TIME_FORMATTER;
    }
}
