package eu.ase.ro.damapp.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateConv {
    private static final SimpleDateFormat format = new SimpleDateFormat("dd-MM-YYYY");

    public static Date convert_string_to_date(String dateS) throws ParseException {
        return format.parse(dateS);
    }

    public static String convert_date_to_string(Date dateD) {
        return format.format(dateD);
    }
}
