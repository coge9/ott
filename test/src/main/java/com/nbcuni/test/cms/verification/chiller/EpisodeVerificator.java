package com.nbcuni.test.cms.verification.chiller;

import com.nbcuni.test.cms.utils.SoftAssert;
import com.nbcuni.test.cms.utils.jsonparsing.chiller.parser.contenttype.episode.EpisodeJson;
import com.nbcuni.test.cms.verification.Verificator;
import com.nbcuni.test.webdriver.Utilities;

import java.util.List;

/**
 * Created by Ivan_Karnilau on 16-Jun-16.
 */
public class EpisodeVerificator extends BaseVerificatorForPublishing implements Verificator<EpisodeJson, EpisodeJson> {

    @Override
    public boolean verify(EpisodeJson expected, EpisodeJson actual) {

        SoftAssert softAssert = new SoftAssert();
        Utilities.logInfoMessage("Verify objects started");

        List<String> categoryUuids = actual.getCategories();
        verifyUuids(softAssert, categoryUuids, "category");

        List<String> tagsUuid = actual.getTags();
        verifyUuids(softAssert, tagsUuid, "tag");
        softAssert.assertTrue(Math.abs(expected.getSupplementaryAiring()
                        - actual.getSupplementaryAiring()) < 18000000,
                "Supplementary Airing dates are differ more than 5 hours");


        softAssert.assertTrue(Math.abs(expected.getDatePublished() - actual.getDatePublished()) < 60000,
                "The actual published time :" + actual.getDatePublished() + " does not matched with expected: " + expected.getDatePublished()
                , "The actual published time is matched with expected");

        EpisodeJson actualClone = (EpisodeJson) actual.clone();
        actualClone.setSupplementaryAiring(expected.getSupplementaryAiring());
        actualClone.setCategories(expected.getCategories());
        actualClone.setTags(expected.getTags());

        return softAssert.getTempStatus() && actualClone.verifyObject(expected);
    }

    @Override
    public boolean verify(EpisodeJson o) {
        throw new UnsupportedOperationException("This type of validation is not supported in current implementation");
    }
}
