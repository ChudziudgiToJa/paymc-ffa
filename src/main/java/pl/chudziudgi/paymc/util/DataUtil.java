package pl.chudziudgi.paymc.util;

public final class DataUtil {

    public static String durationToString(long time) {
        time -= System.currentTimeMillis();

        final StringBuilder stringBuilder = new StringBuilder();
        final long days = time / 86400000L;
        final long hours = (time / 3600000L) % 24L;
        final long minutes = (time / 60000L) % 60L;
        final long seconds = (time / 1000L) % 60L;
        final long milliseconds = time % 1000L;

        if (days > 0) stringBuilder.append(days).append("d");

        if (hours > 0) stringBuilder.append(hours).append("h");

        if (minutes > 0) stringBuilder.append(minutes).append("min");

        if (seconds > 0) stringBuilder.append(seconds).append("s");

        if (time > 0 && seconds == 0) stringBuilder.append(milliseconds).append("ms");

        return stringBuilder.toString();
    }
}
