package com.longtv.zappy.utils;

import android.util.Log;

import com.longtv.zappy.network.dto.Author;
import com.longtv.zappy.network.dto.ContentType;

import java.util.List;

public class StringUtils {

    public static String covertSecondToHMS(long totalSecs) {
        int hours = (int) (totalSecs / 3600);
        int minutes = (int) ((totalSecs % 3600) / 60);
        int seconds = (int) (totalSecs % 60);

        String timeString = String.format("%02d:%02d:%02d", hours, minutes, seconds);

        return timeString;
    }

    //convert milliseconds to hh:mm
    public static String convertMillisecondToHMS(long millisecond) {
        Log.e("anth", "convertMillisecondToHMS: " + millisecond);
        long seconds = millisecond / 1000;
        long minutes = seconds / 60;
        long hours = minutes / 60;
        return String.format("%02dh, %02dmin", hours, minutes % 60);
    }

    public static String getCategory(List<ContentType> contentTypes) {
        StringBuilder rs = new StringBuilder();
        for (ContentType c :
                contentTypes) {
            rs.append(c.getName()).append(",");
        }
        return rs.toString();
    }

    public static String getAuthors(List<Author> authors) {
        StringBuilder rs = new StringBuilder();
        for (Author a :
                authors) {
            rs.append(a.getName()).append(",");
        }

        return rs.toString();
    }

    public static String getContentTypesId(List<ContentType> contentTypes) {
        StringBuilder rs = new StringBuilder();
        for (ContentType a :
                contentTypes) {
            rs.append(a.getId()).append(",");
        }
        rs.deleteCharAt(rs.length() - 1);


        return rs.toString();
    }

}
