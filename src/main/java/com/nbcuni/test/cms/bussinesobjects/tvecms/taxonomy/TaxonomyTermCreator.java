package com.nbcuni.test.cms.bussinesobjects.tvecms.taxonomy;

import com.nbcuni.test.cms.pageobjectutils.chiller.contenttype.TaxonomyType;
import com.nbcuni.test.cms.utils.SimpleUtils;

import java.util.ArrayList;
import java.util.List;

public class TaxonomyTermCreator {

    public static TaxonomyTerm getRandomTerm(String prefix) {
        TaxonomyTerm term = new TaxonomyTerm();
        term.setTitle(prefix + SimpleUtils.getRandomString(4));
        term.setDescription(SimpleUtils.getRandomStringWithRandomLength(30));
        term.setWeight(SimpleUtils.getRandomIntInRange(0, 5));
        return term;
    }

    public static List<TaxonomyTerm> getRandomTerms(String prefix, int termsCount) {
        List<TaxonomyTerm> toReturn = new ArrayList<TaxonomyTerm>();
        for (int i = 0; i < termsCount; i++) {
            toReturn.add(getRandomTerm(prefix));
        }
        return toReturn;
    }

    public static TaxonomyTerm getEpisodeTypeTerm(String prefix) {
        return getRandomTerm(prefix).setTaxonomyType(TaxonomyType.EPISODE_TYPE);
    }

}
