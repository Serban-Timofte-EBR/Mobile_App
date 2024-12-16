package eu.ase.ro.a3_event.model;

import androidx.room.TypeConverter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateConverter {
    private static final SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");

    @TypeConverter
    public static Date toDate(String value) {
        try {
            return format.parse(value);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    @TypeConverter
    public static String fromDate(Date value) {
        if (value == null) {
            return null;
        }
        return format.format(value);
    }
}
