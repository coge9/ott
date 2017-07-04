package com.nbcuni.test.cms.utils.thumbnails.ios.promo;

import com.nbcuni.test.cms.backend.tvecms.MPXLayer;
import com.nbcuni.test.cms.pageobjectutils.tve.ContentType;
import com.nbcuni.test.cms.pageobjectutils.tvecms.concerto.promo.PromoIosSource;
import com.nbcuni.test.cms.utils.ImageUtil;
import com.nbcuni.test.cms.utils.thumbnails.chiller.ThumbnailsCutInterface;

import java.awt.image.BufferedImage;
import java.math.BigDecimal;
import java.util.*;

/**
 * Created by Ivan Karnilau on 08.08.2016.
 */
public enum IosPromoThumbnails implements ThumbnailsCutInterface {

    landscape_widescreen_size1024_x1("1024x576", PromoIosSource.PROMO_MEDIA),
    landscape_widescreen_size640_x2("1280x576", PromoIosSource.PROMO_MEDIA),
    landscape_widescreen_size350_x2("700x394", PromoIosSource.PROMO_MEDIA);

    private static List<ThumbnailsCutInterface> allThumbnails = Arrays.asList(IosPromoThumbnails.values());
    private String requiredAppSize;
    private PromoIosSource source;
    private int width;
    private int height;
    private boolean cutOffHeight;
    private int pixelsToCut;

    IosPromoThumbnails(String requiredAppSize, PromoIosSource source) {
        this.requiredAppSize = requiredAppSize;
        this.source = source;

        //dynamic variable initialization
        String[] mpx = source.getSize().split("x");
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
                height = new BigDecimal(tempHeight).intValue();
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
                width = new BigDecimal(tempWidth).intValue();
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

    public static List<ThumbnailsCutInterface> getTumbnailsBySource(
            PromoIosSource source) {
        List<ThumbnailsCutInterface> toReturn = new ArrayList<>();
        for (ThumbnailsCutInterface thumbnail : getAllThumbnails()) {
            IosPromoThumbnails iosPromoThumbnails = (IosPromoThumbnails) thumbnail;
            if (iosPromoThumbnails.source == source)
                toReturn.add(thumbnail);
        }
        return toReturn;
    }

    public static Map<ThumbnailsCutInterface, BufferedImage> getExpectedIosImages(String brand, String imageMpxID) {
        MPXLayer mpxLayer = new MPXLayer(brand, ContentType.TVE_VIDEO);
        Map<ThumbnailsCutInterface, BufferedImage> toReturn = new HashMap<>();
        for (ThumbnailsCutInterface constant : allThumbnails) {

            toReturn.put(constant, ImageUtil.cutThumbnail(mpxLayer.getImageUrlById(imageMpxID), constant));

        }
        return toReturn;
    }

    @Override
    public String getMpxSourceSize() {
        return source.getSize();
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
        return source.getAssetType();
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
        return this.name().replace("_", ".");
    }

    @Override
    public String getImageNameAtMpx() {
        throw new UnsupportedOperationException("This method is not supported in " + this.getClass().getName());
    }
}



