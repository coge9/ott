package com.nbcuni.test.cms.verification.tvecms.concertoapi;

import com.nbcuni.test.cms.utils.SoftAssert;
import com.nbcuni.test.cms.utils.jsonparsing.chiller.parser.images.ImageStyleJson;
import com.nbcuni.test.cms.utils.jsonparsing.chiller.parser.images.ImagesJson;
import com.nbcuni.test.cms.verification.Verificator;
import com.nbcuni.test.cms.verification.chiller.BaseVerificatorForPublishing;
import com.nbcuni.test.webdriver.Utilities;

/**
 * Created by Ivan_Karnilau on 3/27/2017.
 */
public class ImageConcertoVerificator extends BaseVerificatorForPublishing implements Verificator<ImagesJson, ImagesJson> {
    @Override
    public boolean verify(ImagesJson expected, ImagesJson actual) {
        SoftAssert softAssert = new SoftAssert();
        Utilities.logInfoMessage("Verify image json started");
        for (ImageStyleJson imageStyle : actual.getImageStyles()) {
            String href = imageStyle.getHref();
            imageStyle.setHref(href);
        }

        softAssert.assertReflectEquals(expected.getImageStyles(), actual.getImageStyles(), "The image Styles are not matched",
                "The image Styles are matched");
        ImagesJson clone = (ImagesJson) actual.clone();
        clone.setImageStyles(expected.getImageStyles());
        return softAssert.getTempStatus() && clone.verifyObject(expected);
    }

    @Override
    public boolean verify(ImagesJson o) {
        throw new UnsupportedOperationException("This type of validation is not supported in current implementation");
    }
}
