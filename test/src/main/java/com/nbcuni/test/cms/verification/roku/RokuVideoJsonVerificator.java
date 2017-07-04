package com.nbcuni.test.cms.verification.roku;

import com.nbcuni.test.cms.utils.RegexUtil;
import com.nbcuni.test.cms.utils.SoftAssert;
import com.nbcuni.test.cms.utils.jsonparsing.tvecms.parser.Image;
import com.nbcuni.test.cms.utils.jsonparsing.tvecms.parser.RokuVideoJson;
import com.nbcuni.test.cms.verification.Verificator;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dzianis_Kulesh on 5/20/2016.
 *
 * Class for Custom validation of RokuVideoJson objects.
 */
public class RokuVideoJsonVerificator implements Verificator<RokuVideoJson, RokuVideoJson> {

    private static String DATE_PATTERN = "\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}Z";


    @Override
    public boolean verify(RokuVideoJson expected, RokuVideoJson actual) {
        SoftAssert softAssert = new SoftAssert();
        List<Image> expImages = getListOfUpdatedImages(expected.getImages());
        List<Image> actImages = getListOfUpdatedImages(actual.getImages());
        softAssert.assertEquals(expImages.size(), actImages.size(), "Expected and actual images has different number of elements");
        if (expImages.size() > actImages.size()) {
            softAssert.assertContainsAll(expImages, actImages, "Images has some errors", "Images are correct");
        } else {
            softAssert.assertContainsAll(actImages, expImages, "Images has some errors", "Images are correct");
        }

        softAssert.assertTrue(RegexUtil.isMatch(actual.getDateModified(), DATE_PATTERN), "Date modified " + actual.getDateModified() + " has wrong format", "Date modified has correct format");
        // do copy to keep original object consistent.
        RokuVideoJson actualClone = (RokuVideoJson) actual.clone();
        actualClone.setDateModified(expected.getDateModified());
        actualClone.setImages(expected.getImages());
        softAssert.assertEquals(expected, actualClone, "RokuVideoJson are not equal");
        return softAssert.getTempStatus();
    }

    @Override
    public boolean verify(RokuVideoJson o) {
        throw new UnsupportedOperationException("This type of validation is not supported in current implementation");
    }


    /**
     * Method do processing of image objects. It take image URL and cut variable part (delete all going after "itok=" param).
     * This method does not change original objects (return new list with copyied and modified objects).
     *
     * @param images - List of images to modify.
     *
     * */
    private List<Image> getListOfUpdatedImages(List<Image> images) {
        List<Image> updated = new ArrayList<Image>();
        for (Image image : images) {
            Image updatedImage = (Image) image.clone();
            String url = updatedImage.getImageUrl();
            if (url.contains("itok=")) {
                updatedImage.setImageUrl(url.substring(0, url.indexOf("itok=")));
            }
            updated.add(updatedImage);
        }
        return updated;
    }

    /**
     * Method do processing of show id. If it has value "false" as string, it return empty value, other way
     * don't modify ID and return as is.
     *
     * @param id - original id of show.
     *
     * */
    private String updateShowId(String id) {
        if (id.equals("false")) {
            return "";
        } else {
            return id;
        }
    }
}
