package eu.ase.ro.triviachimieorganica.models;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateConverter {
    private static final SimpleDateFormat formater = new SimpleDateFormat("dd.MM.YYYY");

    public static String fromDate(Date value) {
        if (value == null) {
            return null;
        }

        return formater.format(value);
    }

    public static Date toDate(String value) {
        try {
            return formater.parse(value);
        } catch (ParseException e) {
            return null;
        }
    }
}
