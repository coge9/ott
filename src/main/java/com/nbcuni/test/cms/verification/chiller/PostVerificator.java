package com.nbcuni.test.cms.verification.chiller;

import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.post.info.Blurb;
import com.nbcuni.test.cms.utils.SoftAssert;
import com.nbcuni.test.cms.utils.jsonparsing.chiller.parser.post.PostJson;
import com.nbcuni.test.cms.verification.Verificator;
import com.nbcuni.test.webdriver.Utilities;

/**
 * Created by Ivan_Karnilau on 16-Jun-16.
 */
public class PostVerificator extends BaseVerificatorForPublishing implements Verificator<PostJson, PostJson> {

    @Override
    public boolean verify(PostJson expected, PostJson actual) {

        SoftAssert softAssert = new SoftAssert();
        Utilities.logInfoMessage("Verify objects started");

        softAssert.assertTrue(Math.abs(expected.getDatePublished()
                        - actual.getDatePublished()) < 120,
                "Published dates are differ more than 2 minutes");

        // Remove html tags from Medium Description, Long Description and Blurb
        // Text values
        String tagRegExp = "\\<.*?>";
        actual.setMediumDescription(actual.getMediumDescription().replaceAll(tagRegExp, ""));
        actual.setLongDescription(actual.getLongDescription().replaceAll(tagRegExp, ""));

        for (Blurb blurb : actual.getBlurb()) {
            blurb.setText(blurb.getText().replaceAll(tagRegExp, ""));
        }

        PostJson actualClone = (PostJson) actual.clone();
        actualClone.setDatePublished(expected.getDatePublished());

        return softAssert.getTempStatus() && actualClone.verifyObject(expected);
    }

    @Override
    public boolean verify(PostJson o) {
        throw new UnsupportedOperationException("This type of validation is not supported in current implementation");
    }
}
