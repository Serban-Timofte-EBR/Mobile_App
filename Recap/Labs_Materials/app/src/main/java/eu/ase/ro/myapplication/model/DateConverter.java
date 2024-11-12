package eu.ase.ro.myapplication.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateConverter {
    private static SimpleDateFormat format = new SimpleDateFormat("dd.MM.YYYY", Locale.US);

    public static Date toDate(String value) {
        try {
            return format.parse(value);
        } catch (ParseException e) {
            return null;
        }
    }

    public static String fromDate(Date value) {
        if (value==null) {
            return null;
        }
        return format.format(value);
    }
}