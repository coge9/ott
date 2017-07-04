package com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.post.info;

import com.nbcuni.test.cms.backend.chiller.block.contenttype.post.BlurbMedia;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.post.factory.PostData;
import com.nbcuni.test.cms.utils.SimpleUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ivan_Karnilau on 22-Apr-16.
 */
public class PostInfoCreator {

    private static final String BLURB_TITLE = "AQA blurb";
    private static final String BLURB_TEXT = "AQA blurb text";

    private static Blurb getRandomBlurb() {
        String postfix = SimpleUtils.getRandomString(4);
        Blurb blurb = new Blurb();
        blurb
                .setTitle(BLURB_TITLE + postfix)
                .setText(BLURB_TEXT + postfix);
        return blurb;
    }

    public static PostInfo getYouTubeBlurb() {
        String postfix = SimpleUtils.getRandomString(4);
        Blurb blurb = new Blurb();
        blurb
                .setTitle(BLURB_TITLE + postfix)
                .setBlurbMedia(BlurbMedia.YOUTUBE);
        PostInfo postInfo = new PostInfo();
        postInfo.addBlurb(blurb);
        return postInfo;
    }

    public static PostInfo getMpxVideoBlurb() {
        String postfix = SimpleUtils.getRandomString(4);
        Blurb blurb = new Blurb();
        blurb
                .setTitle(BLURB_TITLE + postfix)
                .setBlurbMedia(BlurbMedia.MPX_VIDEO);
        PostInfo postInfo = new PostInfo();
        postInfo.addBlurb(blurb);
        return postInfo;
    }

    public static PostInfo getImageBlurb() {
        String postfix = SimpleUtils.getRandomString(4);
        Blurb blurb = new Blurb();
        blurb
                .setTitle(BLURB_TITLE + postfix)
                .setBlurbMedia(BlurbMedia.IMAGE);
        PostInfo postInfo = new PostInfo();
        postInfo.addBlurb(blurb);
        return postInfo;
    }

    public static PostInfo uploadImageBlurb() {
        String postfix = SimpleUtils.getRandomString(4);
        Blurb blurb = new Blurb();
        blurb
                .setTitle(BLURB_TITLE + postfix)
                .setBlurbMedia(BlurbMedia.UPLOAD);
        PostInfo postInfo = new PostInfo();
        postInfo.addBlurb(blurb);
        return postInfo;
    }

    private static List<Blurb> getRandomBlurbs(int blurbCount) {
        List<Blurb> toReturn = new ArrayList<>();
        for (int i = 0; i < blurbCount; i++) {
            toReturn.add(getRandomBlurb());
        }
        return toReturn;
    }

    public static PostInfo getRandomPostInfo(int blurbCount) {
        PostInfo postInfo = new PostInfo();
        postInfo.setBlurbs(getRandomBlurbs(blurbCount));
        return postInfo;
    }

    public static PostInfo getDescriptionsAndBlurbImages() {
        String postfix = SimpleUtils.getRandomString(4);
        Blurb blurb = new Blurb();
        WysiwigDescription longDescription = new WysiwigDescription();
        WysiwigDescription mediumDescription = new WysiwigDescription();
        longDescription.setBlurb(
                new Blurb().setText(String.format(PostData.LONG_DESCRIPTION, postfix)).setBlurbMedia(BlurbMedia.IMAGE)
        );
        mediumDescription.setBlurb(
                new Blurb().setText(String.format(PostData.MEDIUM_DESCRIPTION, postfix)).setBlurbMedia(BlurbMedia.IMAGE)
        );
        blurb
                .setTitle(BLURB_TITLE + postfix)
                .setBlurbMedia(BlurbMedia.IMAGE);
        PostInfo postInfo = new PostInfo();
        postInfo.addBlurb(blurb);
        postInfo.setLongDescription(longDescription);
        postInfo.setMediumDescription(mediumDescription);
        return postInfo;
    }
}
