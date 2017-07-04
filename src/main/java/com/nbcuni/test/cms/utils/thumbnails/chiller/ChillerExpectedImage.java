package com.nbcuni.test.cms.utils.thumbnails.chiller;


import com.nbcuni.test.cms.utils.ImageUtil;

import java.awt.image.BufferedImage;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Aleksandra_Lishaeva on 4/26/16.
 */
public class ChillerExpectedImage {

    private String requiredAppSize;
    private int width;
    private int height;
    private boolean cutOffHeight;
    private int pixelsToCut;

    public Map<ChillerThumbnails, BufferedImage> getExpectedChillerImage(ChillerImageEntity image) {

        Map<ChillerThumbnails, BufferedImage> toReturn = new HashMap<ChillerThumbnails, BufferedImage>();
        for (ChillerThumbnails constant : ChillerThumbnails.getAllTumbnails()) {
            if (!constant.name().equals(ChillerThumbnails.ORIGINAL.name())) {
                setDimensionsForImage(image, constant);
                toReturn.put(constant, ImageUtil.cutThumbnail(image.getSrc(), constant));
            }
        }
        return toReturn;
    }

    private void setDimensionsForImage(ChillerImageEntity image, ChillerThumbnails chillerThumbnails) {
        double widthMpx = image.getWidth();
        double heightMpx = image.getHeight();
        if ((!chillerThumbnails.getUpScaling()) && (image.getHeight() < chillerThumbnails.getHeight())
                && (image.getWidth() < chillerThumbnails.getWidth())) {
            chillerThumbnails.setWidth((int) widthMpx);
            chillerThumbnails.setHeight((int) heightMpx);
            chillerThumbnails.setCutOffHeight(false);
            chillerThumbnails.setPixelsToCut(0);
            return;
        }
        requiredAppSize = chillerThumbnails.getRequiredAppSize();
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
        /*if(!chillerThumbnails.getUpScaling()){
            pixelsToCut = 0;
        }*/
        chillerThumbnails.setWidth(width);
        chillerThumbnails.setHeight(height);
        chillerThumbnails.setCutOffHeight(cutOffHeight);
        chillerThumbnails.setPixelsToCut(pixelsToCut);

    }
}
