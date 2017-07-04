package com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.associations;

import com.nbcuni.test.cms.utils.SimpleUtils;

public class PromotionalCreator {

    private static final String PROMOTIONAL_KICKER = "AQA promotional kicker";
    private static final String PROMOTIONAL_TITLE = "AQA promotional title";
    private static final String PROMOTIONAL_DESCRIPTION = "AQA promotional description";

    public static Promotional getRandomPromotional() {
        String postfix = SimpleUtils.getRandomString(4);
        Promotional toReturn = new Promotional();
        toReturn.setPromotionalKicker(PROMOTIONAL_KICKER + postfix);
        toReturn.setPromotionalTitle(PROMOTIONAL_TITLE + postfix);
        toReturn.setPromotionalDescription(PROMOTIONAL_DESCRIPTION + postfix);
        return toReturn;
    }

    public static Promotional getPromotionalForIosProgram() {
        String postfix = SimpleUtils.getRandomString(4);
        Promotional toReturn = new Promotional();
        toReturn.setPromotionalKicker(PROMOTIONAL_KICKER + postfix);
        toReturn.setPromotionalTitle(PROMOTIONAL_TITLE + postfix);
        toReturn.setPromotionalDescription(null);
        return toReturn;
    }

}
