package stocksageservice.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class EpochTimeDateConversion {

    private EpochTimeDateConversion() { }

    public static long getCurrentEpochTime() {
        return System.currentTimeMillis();
    }

    public static String convertEpochToDateTime(long epochTime) {
        Date date = new Date(epochTime);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return format.format(date);
    }

    public static String convertEpochToDate(long epochTime) {
        Date date = new Date(epochTime);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(date);
    }
}
