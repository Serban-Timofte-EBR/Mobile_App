package eu.ase.ro.a1_session.model;

import androidx.room.TypeConverter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateConvertor {
    private static final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");

    @TypeConverter
    public static String fromDate(Date value) {
        if (value == null) {
            return null;
        }
        return format.format(value);
    }

    @TypeConverter
    public static Date toDate(String value) {
        try {
            return format.parse(value);
        } catch (ParseException e) {
            return null;
        }
    }
}
