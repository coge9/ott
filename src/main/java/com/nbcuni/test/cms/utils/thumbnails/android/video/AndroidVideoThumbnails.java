package com.nbcuni.test.cms.utils.thumbnails.android.video;

import com.nbcuni.test.cms.backend.tvecms.MPXLayer;
import com.nbcuni.test.cms.pageobjectutils.tve.ContentType;
import com.nbcuni.test.cms.utils.ImageUtil;
import com.nbcuni.test.cms.utils.thumbnails.chiller.ThumbnailsCutInterface;

import java.awt.image.BufferedImage;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public enum AndroidVideoThumbnails implements ThumbnailsCutInterface {

    video_landscape_1280_435("2560x1440", "1280x435", "Original Image", "2560x1440__710709.jpg", "613490755612"),
    video_landscape_440_247("2560x1440", "440x247", "Original Image", "2560x1440__710709.jpg", "613490755612"),
    video_landscape_533_300("2560x1440", "533x300", "Original Image", "2560x1440__710709.jpg", "613490755612"),
    video_landscape_670_375("2560x1440", "670x375", "Original Image", "2560x1440__710709.jpg", "613490755612"),
    video_portrait_720_960("2560x1440", "720x960", "Original Image", "2560x1440__710709.jpg", "613490755612"),
    video_portrait_720_406("2560x1440", "720x406", "Original Image", "2560x1440__710709.jpg", "613490755612"),
    video_portrait_646_363("2560x1440", "646x363", "Original Image", "2560x1440__710709.jpg", "613490755612"),
    video_landscape_1920_912("2560x1440", "1920x912", "Original Image", "2560x1440__710709.jpg", "613490755612"),
    video_landscape_323_182("2560x1440", "323x182", "Original Image", "2560x1440__710709.jpg", "613490755612"),
    video_landscape_439_247("2560x1440", "439x247", "Original Image", "2560x1440__710709.jpg", "613490755612"),
    video_landscape_554_312("2560x1440", "554x312", "Original Image", "2560x1440__710709.jpg", "613490755612"),
    video_portrait_1200_1134("2560x1440", "1200x1134", "Original Image", "2560x1440__710709.jpg", "613490755612"),
    video_portrait_390_220("2560x1440", "390x220", "Original Image", "2560x1440__710709.jpg", "613490755612"),
    video_portrait_540_304("2560x1440", "540x304", "Original Image", "2560x1440__710709.jpg", "613490755612"),
    video_landscape_1920_915("2560x1440", "1920x915", "Original Image", "2560x1440__710709.jpg", "613490755612"),
    video_landscape_390_220("2560x1440", "390x220", "Original Image", "2560x1440__710709.jpg", "613490755612"),
    video_landscape_494_278("2560x1440", "494x278", "Original Image", "2560x1440__710709.jpg", "613490755612"),
    video_landscape_702_394("2560x1440", "702x394", "Original Image", "2560x1440__710709.jpg", "613490755612"),
    // 0x0 set because seems that image should not be resized.
    video_source("2560x1440", "0x0", "Original Image", "2560x1440__710709.jpg", "613490755612");

    private static List<ThumbnailsCutInterface> allThumbnails = Arrays.asList(AndroidVideoThumbnails.values());
    private String mpxSourceSize;
    private String requiredAppSize;
    private String assetTypes;
    private String imageNameAtMpx;
    private String imageIdAtMpx;
    private int width;
    private int height;
    private boolean cutOffHeight;
    private int pixelsToCut;

    private AndroidVideoThumbnails(String mpxSourceSize,
                                   String requiredAppSize, String assetTypes, String imageNameAtMpx, String imageIdAtMpx) {
        this.mpxSourceSize = mpxSourceSize;
        this.requiredAppSize = requiredAppSize;
        this.assetTypes = assetTypes;
        this.imageNameAtMpx = imageNameAtMpx;
        this.imageIdAtMpx = imageIdAtMpx;

        //dynamic variable initialization
        String[] mpx = mpxSourceSize.split("x");
        double widthMpx = Double.parseDouble(mpx[0]);
        double heightMpx = Double.parseDouble(mpx[1]);
        String[] app = requiredAppSize.split("x");
        double widthApp = Double.parseDouble(app[0]);
        double heightApp = Double.parseDouble(app[1]);
        if ((widthMpx / widthApp) < (heightMpx / heightApp)) {
            cutOffHeight = true;
            width = (int) widthApp;
            double tempHeight = heightMpx / (widthMpx / widthApp);
            if (Math.abs(heightApp - tempHeight) > 1) {
                height = BigDecimal.valueOf(tempHeight).intValue();
            } else {
                height = (int) heightApp;
            }
            pixelsToCut = (int) (height - heightApp);
            if (pixelsToCut % 2 != 0) {
                pixelsToCut += 1;
                height += 1;
            }
        } else {
            cutOffHeight = false;
            height = (int) heightApp;
            double tempWidth = widthMpx / (heightMpx / heightApp);
            if (Math.abs(widthApp - tempWidth) > 1) {
                width = BigDecimal.valueOf(tempWidth).intValue();
            } else {
                width = (int) widthApp;
            }
            pixelsToCut = (int) (width - widthApp);
            if (pixelsToCut % 2 != 0) {
                pixelsToCut += 1;
                width += 1;
            }
        }

    }

    public static List<ThumbnailsCutInterface> getAllThumbnails() {
        return allThumbnails;
    }

    public static List<ThumbnailsCutInterface> getOriginalThumbnails() {
        return Arrays.asList(video_landscape_440_247, video_landscape_533_300, video_landscape_670_375,
                video_portrait_720_406, video_portrait_646_363, video_landscape_323_182,
                video_landscape_439_247, video_landscape_554_312, video_portrait_390_220,
                video_portrait_540_304, video_landscape_390_220, video_landscape_494_278,
                video_landscape_702_394);
    }

    public static List<ThumbnailsCutInterface> getLandscape1280x435Thumbnails() {
        return Arrays.asList(video_landscape_1280_435);
    }

    public static List<ThumbnailsCutInterface> getPortrait720x960Thumbnails() {
        return Arrays.asList(video_portrait_720_960);
    }

    public static List<ThumbnailsCutInterface> getPortrait1200x1134Thumbnails() {
        return Arrays.asList(video_portrait_1200_1134);
    }

    public static List<ThumbnailsCutInterface> getLandscape1920x912Thumbnails() {
        return Arrays.asList(video_landscape_1920_912);
    }

    public static List<ThumbnailsCutInterface> getLandscape1920x915Thumbnails() {
        return Arrays.asList(video_landscape_1920_915);
    }

    public static Map<ThumbnailsCutInterface, BufferedImage> getExpectedAndroidImage(String brand) {
        MPXLayer mpxLayer = new MPXLayer(brand, ContentType.TVE_VIDEO);
        Map<ThumbnailsCutInterface, BufferedImage> toReturn = new HashMap<>();
        for (ThumbnailsCutInterface constant : allThumbnails) {
            toReturn.put(constant, ImageUtil.cutThumbnail(mpxLayer.getImageUrlByTitle(constant.getImageNameAtMpx()), constant));
        }
        return toReturn;
    }

    @Override
    public String getMpxSourceSize() {
        return mpxSourceSize;
    }

    @Override
    public String getRequiredAppSize() {
        return requiredAppSize;
    }

    @Override
    public int getRequiredAppWidth() {
        return Integer.parseInt(requiredAppSize.split("x")[0]);
    }

    @Override
    public int getRequiredAppHeight() {
        return Integer.parseInt(requiredAppSize.split("x")[1]);
    }

    @Override
    public String getAssetTypes() {
        return assetTypes;
    }

    @Override
    public String getImageNameAtMpx() {
        return imageNameAtMpx;
    }

    public String getImageIdAtMpx() {
        return imageIdAtMpx;
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public void setWidth(int width) {
        this.width = width;
    }

    @Override
    public int getHeight() {
        return height;
    }

    @Override
    public void setHeight(int height) {
        this.height = height;
    }

    @Override
    public boolean isCutOffHeight() {
        return cutOffHeight;
    }

    @Override
    public void setCutOffHeight(boolean cutOffHeight) {
        this.cutOffHeight = cutOffHeight;
    }

    @Override
    public int getPixelsToCut() {
        return pixelsToCut;
    }

    @Override
    public void setPixelsToCut(int pixelsToCut) {
        this.pixelsToCut = pixelsToCut;
    }

    @Override
    public String getImageName() {
        return this.name();
    }

}
