package com.nbcuni.test.cms.utils.thumbnails.android.program;

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

public enum AndroidProgramThumbnails implements ThumbnailsCutInterface {

    program_landscape_1280_435("2560x1440", "1280x435", "Android", "2560x1440.jpg", "643889731991"),
    program_landscape_590_331("2560x1440", "590x331", "Android", "2560x1440.jpg", "643889731991"),
    program_landscape_535_300("2560x1440", "535x300", "Android", "2560x1440.jpg", "643889731991"),
    program_landscape_640_360("2560x1440", "640x360", "Android", "2560x1440.jpg", "643889731991"),
    program_portrait_720_960("1440x2560", "720x960", "Android", "1440x2560.jpg", "608996931583"),
    program_portrait_720_406("2560x1440", "720x406", "Android", "2560x1440.jpg", "643889731991"),
    program_landscape_1270_1200("1200x1200", "1270x1200", "Android", "1200x1200.jpg", "608996419796"),
    program_landscape_554_312("2560x1440", "554x312", "Android", "2560x1440.jpg", "643889731991"),
    program_landscape_439_247("2560x1440", "439x247", "Android", "2560x1440.jpg", "643889731991"),
    program_landscape_436_519("1440x2560", "436x519", "Android", "1440x2560.jpg", "608996931583"),
    program_portrait_1200_1134("1200x1200", "1200x1134", "Android", "1200x1200.jpg", "608996419796"),
    program_portrait_540_304("2560x1440", "540x304", "Android", "2560x1440.jpg", "643889731991"),
    program_landscape_1284_1080("2088x1576", "1284x1080", "n/a", "2088x1576.jpg", "613435459668"),
    program_landscape_494_278("2560x1440", "494x278", "n/a", "2560x1440.jpg", "643889731991"),
    program_landscape_390_220("2560x1440", "390x220", "n/a", "2560x1440.jpg", "643889731991"),
    program_landscape_390_462("680x1176", "390x462", "n/a", "680x1176.jpg", "608996419797"),
    program_landscape_702_394("2560x1440", "702x394", "n/a", "2560x1440.jpg", "643889731991");

    private static String programLogoName = "program_logo_1";
    private static List<AndroidProgramThumbnails> allThumbnails = Arrays.asList(AndroidProgramThumbnails.values());
    private String mpxSourceSize;
    private String requiredAppSize;
    private String assetTypes;
    private String imageNameAtMpx;
    private String imageIdAtMpx;
    private int width;
    private int height;
    private boolean cutOffHeight;
    private int pixelsToCut;

    private AndroidProgramThumbnails(String mpxSourceSize,
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

    public static List<AndroidProgramThumbnails> getAllTumbnails() {
        return allThumbnails;
    }

    public static String getProgramLogoName() {
        return programLogoName;
    }

    public static HashMap<AndroidProgramThumbnails, BufferedImage> getExpectedAndroidImage(String brand) {
        MPXLayer mpxLayer = new MPXLayer(brand, ContentType.TVE_PROGRAM);
        HashMap<AndroidProgramThumbnails, BufferedImage> toReturn = new HashMap<AndroidProgramThumbnails, BufferedImage>();
        for (AndroidProgramThumbnails constant : allThumbnails) {
            toReturn.put(constant, ImageUtil.cutThumbnail(mpxLayer.getMpxThumbnailById(constant.getImageIdAtMpx()).getUrl(), constant));
        }
        return toReturn;
    }

    public static List<ThumbnailsCutInterface> getThumbnailsByMpxSourceSize(
            String sourceSize) {
        List<ThumbnailsCutInterface> toReturn = new ArrayList<>();
        for (AndroidProgramThumbnails thumbnail : getAllTumbnails()) {
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
