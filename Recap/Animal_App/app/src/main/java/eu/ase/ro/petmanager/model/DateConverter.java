package eu.ase.ro.petmanager.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateConverter {
    private static SimpleDateFormat format = new SimpleDateFormat("dd.MM.YYYY");

    public static String fromDate(Date value) {
        if(value == null) {
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
