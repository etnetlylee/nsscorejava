package com.etnet.coresdk.util;

import java.util.Calendar;

public class DateHelper {
    public static int createTimstampFromGroupTime(int date, String groupTime) {
        int t = Integer.parseInt(groupTime, 10);
        int h = (int) Math.floor(t / 100);
        int m = (int) Math.floor(t % 100);
        Calendar d = Calendar.getInstance();
        d.setTimeInMillis(date);
        int mYear = d.get(Calendar.YEAR);
        int mMonth = d.get(Calendar.MONTH);
        int mDay = d.get(Calendar.DAY_OF_MONTH);
        d.set(Calendar.YEAR, mYear);
        d.set(Calendar.MONTH, mMonth);
        d.set(Calendar.DAY_OF_MONTH, mDay);
        d.set(Calendar.HOUR, h);
        d.set(Calendar.MINUTE, m);
        d.set(Calendar.SECOND, 0);

        return (int) Math.round(d.getTimeInMillis());
    }

    public static boolean isInSameYearWeek(int timestamp1, int timestamp2) {
        Calendar r1 = Calendar.getInstance();
        Calendar r2 = Calendar.getInstance();

        r1.setTimeInMillis(timestamp1);
        r2.setTimeInMillis(timestamp2);

        return r1.get(Calendar.WEEK_OF_YEAR) == r2.get(Calendar.WEEK_OF_YEAR);
    }

    public static boolean isInSameYearMonth(int timestamp1, int timestamp2) {
        Calendar r1 = Calendar.getInstance();
        Calendar r2 = Calendar.getInstance();

        r1.setTimeInMillis(timestamp1);
        r2.setTimeInMillis(timestamp2);

        return r1.get(Calendar.MONTH) == r2.get(Calendar.MONTH) && r1.get(Calendar.YEAR) == r2.get(Calendar.YEAR);
    }

    public static boolean isInSameYear(int timestamp1, int timestamp2) {
        Calendar r1 = Calendar.getInstance();
        Calendar r2 = Calendar.getInstance();

        r1.setTimeInMillis(timestamp1);
        r2.setTimeInMillis(timestamp2);

        return r1.get(Calendar.YEAR) == r2.get(Calendar.YEAR);
    }

    /**
     * Check two timestamps are in same daytime
     * i.e. YYYYMMDD == YYYYMMDD
     */
    static boolean isInSameDay(int timestamp1, int timestamp2) {
        Calendar r1 = Calendar.getInstance();
        Calendar r2 = Calendar.getInstance();

        r1.setTimeInMillis(timestamp1);
        r2.setTimeInMillis(timestamp2);

        return r1.get(Calendar.YEAR) == r2.get(Calendar.YEAR) && r1.get(Calendar.MONTH) == r2.get(Calendar.MONTH) && r1.get(Calendar.DAY_OF_MONTH) == r2.get(Calendar.DAY_OF_MONTH);
    }
}
