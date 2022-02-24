package be.ucll.prog4.ucllchrono.utils;

public class DateTimeUtils {

    public static String formatDuration(long duration) {
        long seconds, minutes, hours;

        // Less then an hour
        if (duration < (60 * 60 * 1000)) {
            minutes = duration / 1000 / 60;
            seconds = duration / 1000 % 60;
            return String.format("%02d:%02d", minutes, seconds);

            // More then an hour
        } else {
            hours = duration / 1000 / 60 / 60;
            minutes = duration / 1000 / 60 % 60;
            seconds = duration / 1000 % 60;
            return String.format("%02d:%02d:%02d", hours, minutes, seconds);
        }
    }
}
