package com.cjl.hibernate.demo;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {

	// The date formatter
    // - dd:   day in month (number)
    // - MM:   month in year (number)
    // - yyyy: year
    //
    // See this link for details: https://docs.oracle.com/javase/tutorial/i18n/format/simpleDateFormat.html
    //
    //
    private static SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
    
    // Read a date string and parse/convert to a date
    public static Date parseDate(String dateStr) throws ParseException {
        Date date = formatter.parse(dateStr);
        
        return date;        
    }
    
    // Read a date and format/convert to a string
    public static String formatDate(Date date) {
        
        String result = null;
        
        if (date != null) {
            result = formatter.format(date);
        }
        
        return result;
    }
    
}
