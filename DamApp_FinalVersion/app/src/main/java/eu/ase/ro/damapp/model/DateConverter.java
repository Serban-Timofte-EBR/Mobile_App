package eu.ase.ro.damapp.model;

import androidx.room.TypeConverter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateConverter {

    private static final SimpleDateFormat FORMATTER = new SimpleDateFormat("dd-MM-yyyy", Locale.US);

    @TypeConverter
    public static Date toDate(String value) {
        try {
            return FORMATTER.parse(value);
        } catch (ParseException e) {
            return null;
        }
    }

    @TypeConverter
    public static String fromDate(Date date) {
        if (date == null) {
            return null;
        }
        return FORMATTER.format(date);
    }
}
