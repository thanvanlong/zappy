package com.longtv.zappy.utils;

public class StringUtils {

    public static String covertSecondToHMS(long totalSecs) {
        int hours = (int) (totalSecs / 3600);
        int minutes = (int) ((totalSecs % 3600) / 60);
        int seconds = (int) (totalSecs % 60);

        String timeString = String.format("%02d:%02d", minutes, seconds);

        return timeString;
    }

}
