package com.nbcuni.test.cms.utils.comparator;

import java.text.Normalizer;
import java.util.Comparator;

/**
 * Created by Dzianis_Kulesh on 5/11/2016.
 */
public class StringIgnoreCaseComparator implements Comparator<String> {

    @Override
    public int compare(String s1, String s2) {
        return Normalizer.normalize(s1, Normalizer.Form.NFD).compareToIgnoreCase(Normalizer.normalize(s2, Normalizer.Form.NFD));
    }
}
