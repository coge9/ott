package com.nbcuni.test.cms.verification.chiller;

import com.nbcuni.test.cms.utils.SoftAssert;
import com.nbcuni.test.cms.utils.jsonparsing.chiller.parser.images.ImageStyleJson;
import com.nbcuni.test.cms.utils.jsonparsing.chiller.parser.images.ImagesJson;
import com.nbcuni.test.cms.verification.Verificator;
import com.nbcuni.test.webdriver.Utilities;

import java.util.ArrayList;
import java.util.List;

public class ImageVerificator extends BaseVerificatorForPublishing implements Verificator<ImagesJson, ImagesJson> {

    @Override
    public boolean verify(ImagesJson expected, ImagesJson actual) {
        SoftAssert softAssert = new SoftAssert();
        Utilities.logWarningMessage("Verify objects started");
        if (!expected.getCategories().isEmpty()) {
            verifyUuids(softAssert, actual.getCategories(), "categories");
        }
        if (!expected.getTags().isEmpty()) {
            verifyUuids(softAssert, actual.getTags(), "tags");
        }
        List<ImageStyleJson> actualToBeModified = new ArrayList<>();
        for (ImageStyleJson imageStyle : actual.getImageStyles()) {
            String href = imageStyle.getHref();
            imageStyle.setHref(href);
            actualToBeModified.add(imageStyle);
        }
        List<ImageStyleJson> expectedToBeModified = new ArrayList<>();
        for (ImageStyleJson imageStyle : expected.getImageStyles()) {
            String href = imageStyle.getHref();
            imageStyle.setHref(href);
            expectedToBeModified.add(imageStyle);
        }

        softAssert.assertReflectEquals(expectedToBeModified, actualToBeModified, "The image Styles are not matched", "The image Styles are matched");
        ImagesJson clone = (ImagesJson) actual.clone();
        clone.setTags(expected.getTags());
        clone.setRevision(expected.getRevision());
        clone.setCategories(expected.getCategories());
        clone.setImageStyles(expected.getImageStyles());
        softAssert.assertReflectEquals(expected, clone, "The object doesn't equals", "The object equals");
        return softAssert.getTempStatus();
    }

    @Override
    public boolean verify(ImagesJson o) {
        throw new UnsupportedOperationException("This type of validation is not supported in current implementation");
    }
}
