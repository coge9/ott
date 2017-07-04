package com.nbcuni.test.cms.utils.comparator;

import org.apache.commons.lang3.ObjectUtils;

import java.util.Comparator;

/**
 * Created by Ivan_Karnilau on 5/23/2017.
 */
public class LongComparator implements Comparator<Long> {
    @Override
    public int compare(Long o1, Long o2) {
        return ObjectUtils.compare(o1, o2);
    }
}
