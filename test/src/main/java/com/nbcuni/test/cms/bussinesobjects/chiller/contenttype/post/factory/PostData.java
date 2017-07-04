package com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.post.factory;

import com.nbcuni.test.cms.utils.DateUtil;

/**
 * Created by Ivan on 06.04.2016.
 */
public class PostData {
    public static final String MEDIUM_DESCRIPTION = "AQA Post medium description%s";
    public static final String LONG_DESCRIPTION = "AQA Post long description%s";
    static final String TITLE = "AQA Post%s";
    static final String BYLINE = "AQA Post byline%s";
    static final String DATE_LINE = DateUtil.getCurrentDate("MM/dd/yyyy").toString();
    static final String SHORT_DESCRIPTION = "AQA Post short description%s";
}
