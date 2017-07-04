package com.nbcuni.test.cms.verification.chiller;

import com.nbcuni.test.cms.utils.SoftAssert;
import com.nbcuni.test.cms.utils.jsonparsing.chiller.parser.MediaJson;
import com.nbcuni.test.cms.utils.jsonparsing.chiller.parser.contenttype.season.SeasonJson;
import com.nbcuni.test.cms.verification.Verificator;
import com.nbcuni.test.webdriver.Utilities;

import java.util.List;

/**
 * Created by Ivan_Karnilau on 24-May-16.
 */
public class SeasonVerification extends BaseVerificatorForPublishing implements Verificator<SeasonJson, SeasonJson> {

    @Override
    public boolean verify(SeasonJson expected, SeasonJson actual) {

        SoftAssert softAssert = new SoftAssert();
        Utilities.logInfoMessage("Verify objects started");

        List<String> categoryUuids = actual.getCategories();
        verifyUuids(softAssert, categoryUuids, "category");

        List<String> tagsUuid = actual.getTags();
        verifyUuids(softAssert, tagsUuid, "tag");

        SeasonJson actualClone = (SeasonJson) actual.clone();
        actualClone.setCategories(expected.getCategories());
        actualClone.setTags(expected.getTags());

        List<MediaJson> actualMedia = actualClone.getMedia();
        for (int i = 0; i < actualMedia.size(); i++) {
            MediaJson mediaJson = actualMedia.get(i);
            String actualUuid = mediaJson.getUuid();
            verifyUuid(softAssert, actualUuid, "media uuid");
            expected.getMedia().get(i).setUuid(actualUuid);
        }

        return softAssert.getTempStatus() && actualClone.verifyObject(expected);
    }

    @Override
    public boolean verify(SeasonJson o) {
        throw new UnsupportedOperationException("This type of validation is not supported in current implementation");
    }
}
