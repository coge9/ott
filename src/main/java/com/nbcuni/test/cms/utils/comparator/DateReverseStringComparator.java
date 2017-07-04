package com.nbcuni.test.cms.utils.comparator;

import com.nbcuni.test.cms.utils.logging.TestRuntimeException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;

/**
 * Created by Dzianis_Kulesh on 5/11/2016.
 */
public class DateReverseStringComparator implements Comparator<String> {

    private SimpleDateFormat dateFormat;

    public DateReverseStringComparator() {
        this.dateFormat = new SimpleDateFormat("MM/dd/yyyy - HH:mm");
    }

    public DateReverseStringComparator(String dateFormat) {
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
        return date2.compareTo(date1);
    }
}
