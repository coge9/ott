package com.nbcuni.test.cms.verification.tvecms.concertoapi;

import com.nbcuni.test.cms.utils.SoftAssert;
import com.nbcuni.test.cms.utils.jsonparsing.chiller.parser.images.ImagesJson;
import com.nbcuni.test.cms.verification.Verificator;
import com.nbcuni.test.cms.verification.chiller.BaseVerificatorForPublishing;
import com.nbcuni.test.webdriver.Utilities;

import java.util.List;

/**
 * Created by Ivan_Karnilau on 3/27/2017.
 */
public class ImageListConcertoVerificator extends BaseVerificatorForPublishing implements Verificator<List<ImagesJson>, List<ImagesJson>> {
    @Override
    public boolean verify(List<ImagesJson> expected, List<ImagesJson> actual) {
        SoftAssert softAssert = new SoftAssert();
        Utilities.logInfoMessage("Verify image json list started");

        softAssert.assertEquals(expected.size(), actual.size(), "Size is not equals for Image jsons",
                "Size equals for Image jsons");

        for (ImagesJson expectedImageJson : expected) {
            boolean isFound = false;
            for (ImagesJson actualImageJson : actual) {
                if (expectedImageJson.getUuid().equals(actualImageJson.getUuid())) {
                    softAssert.assertEquals(expectedImageJson, actualImageJson, "Image json are not equals",
                            "Image json are equals", new ImageConcertoVerificator());
                    isFound = true;
                }
            }
            softAssert.assertTrue(isFound, "Image with UUID: " + expectedImageJson.getUuid() + " and URL: " +
                    expectedImageJson.getHref() + " was not found in actual JSON");
        }

        for (ImagesJson actualImageJson : actual) {
            boolean isFound = false;
            for (ImagesJson expectedImageJson : expected) {
                if (actualImageJson.getUuid().equals(expectedImageJson.getUuid())) {
                    softAssert.assertEquals(expectedImageJson, actualImageJson, "Image json are not equals",
                            "Image json are equals", new ImageConcertoVerificator());
                    isFound = true;
                }
            }
            softAssert.assertTrue(isFound, "Image with UUID: " + actualImageJson.getUuid() + " and URL: " +
                    actualImageJson.getHref() + " was not found in expected JSON");
        }
        return softAssert.getTempStatus();
    }

    @Override
    public boolean verify(List<ImagesJson> o) {
        throw new UnsupportedOperationException("This type of validation is not supported in current implementation");
    }
}
