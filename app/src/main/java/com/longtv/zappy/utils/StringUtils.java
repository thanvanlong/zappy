package com.longtv.zappy.utils;

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

}
