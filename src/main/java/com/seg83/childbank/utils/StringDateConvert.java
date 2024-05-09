package com.seg83.childbank.utils;

import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * The StringDateConvert class provides methods for converting between String and Date objects.
 */
@Component
public class StringDateConvert {

    /**
     * Converts a String representation of a date to a Date object.
     *
     * @param dateString The String representation of the date.
     * @return A Date object parsed from the input String.
     */
    public Date StringToDate(String dateString) {
        String pattern = "yyyy-MM-dd HH:mm:ss";
        SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);

        try {
            return dateFormat.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Converts a Date object to its String representation.
     *
     * @param date The Date object to be converted.
     * @return A String representation of the input Date object.
     */
    public String DateToString(Date date) {
        String pattern = "yyyy-MM-dd HH:mm:ss";
        SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
        return dateFormat.format(date);
    }
}
