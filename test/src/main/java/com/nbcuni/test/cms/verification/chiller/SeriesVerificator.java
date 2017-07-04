package com.nbcuni.test.cms.verification.chiller;

import com.nbcuni.test.cms.utils.RegexUtil;
import com.nbcuni.test.cms.utils.SoftAssert;
import com.nbcuni.test.cms.utils.jsonparsing.chiller.parser.MediaJson;
import com.nbcuni.test.cms.utils.jsonparsing.chiller.parser.contenttype.series.SeriesJson;
import com.nbcuni.test.cms.utils.logging.TestRuntimeException;
import com.nbcuni.test.cms.verification.Verificator;
import com.nbcuni.test.webdriver.Utilities;

import java.util.Collections;
import java.util.List;

/**
 * Created by Dzianis_Kulesh on 5/20/2016.
 */
public class SeriesVerificator extends BaseVerificatorForPublishing implements Verificator<SeriesJson, SeriesJson> {

    @Override
    public boolean verify(SeriesJson expected, SeriesJson actual) {
        //The soft assertions is used to verify pattern for list of elements and do not use custom flag
        SoftAssert softAssert = new SoftAssert();
        Utilities.logInfoMessage("Verify objects started");

        List<String> categoryUuids = actual.getCategories();
        verifyUuids(softAssert, categoryUuids, "category");

        List<String> tagsUuid = actual.getTags();
        verifyUuids(softAssert, tagsUuid, "tag");


        softAssert.assertTrue(Math.abs(expected.getDatePublished() - actual.getDatePublished()) < 60000,
                "The actual published time :" + actual.getDatePublished() + " does not matched with expected: " + expected.getDatePublished()
                , "The actual published time is matched with expected");



        SeriesJson actualClone = (SeriesJson) actual.clone();
        actualClone.setCategories(expected.getCategories());
        actualClone.setTags(expected.getTags());
        actualClone.setDatePublished(expected.getDatePublished());

        expected.setSortTitle(createSortTitle(actual.getSortTitle()));
        if (expected.getMedia() != null && actual.getMedia() != null) {
            mediaSorting(expected.getMedia());
            mediaSorting(actual.getMedia());
        }
        return softAssert.getTempStatus() && actualClone.verifyObject(expected);
    }

    @Override
    public boolean verify(SeriesJson o) {
        throw new UnsupportedOperationException("This type of validation is not supported in current implementation");
    }

    public String createSortTitle(String sortTitle) {
        String pattern = "^(?i)((an |the |a |episode [one|two|three|four|five|six|seven|eight|nine|ten" +
                "|eleven|twelve|thirteen|fourteen|fifteen|sixteen|seventeen|eighteen|nineteen|twenty|thirty" +
                "|forty|fifty|sixty|seventy|eighty|ninety]) .*)";
        if (!RegexUtil.isMatch(sortTitle.trim(), pattern)) {
            return sortTitle;
        }
        throw new TestRuntimeException("Unable to create Sort Title");
    }

    public void mediaSorting(List<MediaJson> medias) {
        Collections.sort(medias, (o1, o2) -> {
            if (!o1.getUuid().equals(o2.getUuid())) {
                return o1.getUuid().compareTo(o2.getUuid());
            } else {
                return o1.getUsage().compareTo(o2.getUsage());
            }
        });
    }
}
