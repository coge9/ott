package com.nbcuni.test.cms.utils.comparator;

import com.nbcuni.test.cms.utils.logging.TestRuntimeException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;

/**
 * Current class is designed to compare dates which are represented by String.
 * It can take {@link SimpleDateFormat} as a constructor parameter
 * <p>
 * Created by Dzianis_Kulesh on 5/11/2016.
 */
public class DateStringComparator implements Comparator<String> {

    private SimpleDateFormat dateFormat;

    public DateStringComparator() {
        this.dateFormat = new SimpleDateFormat("MM/dd/yyyy - HH:mm");
    }

    public DateStringComparator(String dateFormat) {
        this.dateFormat = new SimpleDateFormat(dateFormat);
    }

    @Override
    public int compare(String s1, String s2) {
        Date date1 = null;
        Date date2 = null;
        try {
            date1 = dateFormat.parse(s1);
            date2 = dateFormat.parse(s2);
        } catch (ParseException e) {
            new TestRuntimeException("Error during date parsing");
        }
        return date1.compareTo(date2);
    }
}
