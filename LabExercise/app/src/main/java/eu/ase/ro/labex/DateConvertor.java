package eu.ase.ro.labex;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateConvertor {
    private static final SimpleDateFormat format = new SimpleDateFormat("yyy-MM-dd");

    public static String fromDate(Date value) {
        if (value == null) {
            return null;
        }
        return format.format(value);
    }

    public static Date toDate(String value) {
        try {
            return format.parse(value);
        } catch (ParseException e) {
            return null;
        }
    }
}
