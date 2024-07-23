package com.secure.store.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateTimeUtil {
    public static final String DATETIME_FORMAT_DB = "yyyy-MMM-dd HH:mm a";
    public static final String DATE_TIME_SS_FORMAT_UI = "yyyy-MM-dd HH:mm:ss";
    public static final String DATE_TIME_FORMAT_UI = "dd-MMM-yyyy HH:mm";
    public static Date currentDateTime() {
        try {
            Date date = new Date();
            SimpleDateFormat format = new SimpleDateFormat(DATETIME_FORMAT_DB);
            format.format(date);
            return date;
        }catch(Exception exception) {
            exception.printStackTrace();
        } return null;
    }

    public static String formatDate(Date date, String format) {
        try {
            if (date != null) {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
                return simpleDateFormat.format(date);
            }
        } catch(Exception exception) {
            exception.printStackTrace();
        } return "";
    }
}
