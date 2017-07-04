package com.nbcuni.test.cms.utils.thumbnails.appletv.program;

import com.nbcuni.test.cms.backend.tvecms.MPXLayer;
import com.nbcuni.test.cms.pageobjectutils.tve.ContentType;
import com.nbcuni.test.cms.utils.ImageUtil;
import com.nbcuni.test.cms.utils.thumbnails.chiller.ThumbnailsCutInterface;

import java.awt.image.BufferedImage;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public enum AppleTVProgramThumbnails implements ThumbnailsCutInterface {

    landscape_127x71_x1("1600x900", "127x71", "Apple TV", "", ""),
    landscape_127x71_x1_5("1600x900", "190x110", "Apple TV", "", ""),
    landscape_255x143_x1("1600x900", "255x143", "Apple TV", "", ""),
    landscape_255x143_x1_5("1600x900", "382x215", "Apple TV", "", ""),
    square_600x600_x1("600x600", "600x600", "Apple TV", "", ""),
    landscape_771x292_x1("771x292", "771x292", "Apple TV", "", ""),
    landscape_1920x486_x1("1920x486", "1920x486", "Apple TV", "", ""),
    landscape_540x304_x1("1600x900", "540x304", "Apple TV", "", ""),
    landscape_540x304_x1_5("1600x900", "720x405", "Apple TV", "", ""),
    landscape_1704x440_x1("1704x440", "1704x440", "Apple TV", "", ""),
    landscape_1920x1080_x1("1920x1080", "1920x1080", "Apple TV", "", "");

    private static List<AppleTVProgramThumbnails> allThumbnails = Arrays.asList(AppleTVProgramThumbnails.values());
    private String mpxSourceSize;
    private String requiredAppSize;
    private String assetTypes;
    private String imageNameAtMpx;
    private String imageIdAtMpx;
    private int width;
    private int height;
    private boolean cutOffHeight;
    private int pixelsToCut;

    private AppleTVProgramThumbnails(String mpxSourceSize,
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

    public static List<AppleTVProgramThumbnails> getAllThumbnails() {
        return allThumbnails;
    }

    public static HashMap<AppleTVProgramThumbnails, BufferedImage> getExpectedIosImages(String brand, String sourceSize, String imageMpxID) {
        MPXLayer mpxLayer = new MPXLayer(brand, ContentType.TVE_PROGRAM);
        HashMap<AppleTVProgramThumbnails, BufferedImage> toReturn = new HashMap<AppleTVProgramThumbnails, BufferedImage>();
        for (AppleTVProgramThumbnails constant : allThumbnails) {
            if (constant.getMpxSourceSize().contains(sourceSize)) {
                toReturn.put(constant, ImageUtil.cutThumbnail(mpxLayer.getImageUrlById(imageMpxID), constant));
            }
        }
        return toReturn;
    }

    public static List<ThumbnailsCutInterface> getTumbnailsByMpxSourceSize(String sourceSize) {
        List<ThumbnailsCutInterface> toReturn = new ArrayList<>();
        for (AppleTVProgramThumbnails thumbnail : getAllThumbnails()) {
            if (thumbnail.getMpxSourceSize().equals(sourceSize))
                toReturn.add(thumbnail);
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

    public int getRequiredWidth() {
        return Integer.parseInt(requiredAppSize.substring(requiredAppSize.indexOf("x")));
    }

    @Override
    public int getHeight() {
        return height;
    }

    @Override
    public void setHeight(int height) {
        this.height = height;
    }

    public int getRequiredHeight() {
        return Integer.parseInt(requiredAppSize.substring(0, requiredAppSize.indexOf("x") - 1));
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



