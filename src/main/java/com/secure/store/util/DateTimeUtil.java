package com.secure.store.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateTimeUtil {
    public static final String DATETIME_FORMAT_DB = "yyyy-MMM-dd HH:mm a";
    public static final String DATE_TIME_FORMAT_UI = "dd-MMM-yyyy HH:mm";
    public static final String DATE_FORMAT_UI = "dd-MMM-yyyy";
    public static final String DATE_FORMAT_UI_EDIT = "yyyy-MM-dd";
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

    public static Date formatDate(String strDate, String format) {
        try {
            SimpleDateFormat formatter = new SimpleDateFormat(format);
            return formatter.parse(strDate);
        } catch (ParseException e) {
            e.printStackTrace();
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
        } return null;
    }
}
