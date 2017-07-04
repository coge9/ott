package com.nbcuni.test.cms.utils;

import com.nbcuni.test.cms.pageobjectutils.entities.mvpd.Alignment;
import com.nbcuni.test.cms.utils.thumbnails.chiller.ThumbnailsCutInterface;
import com.nbcuni.test.webdriver.Utilities;
import org.imgscalr.Scalr;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class ImageUtil {

    private static final Double coeff = 0.15;
    private static final String rokuHeaderImageBase = "homepage_header.png";

    public ImageUtil() {
        ImageIO.setUseCache(false);
    }

    public static boolean compareScreenshotWithFiles(File file,
                                                     File[] standard, double diff) {
        Double percentage = 0.0;
        for (File standardfile : standard) {
            try {
                percentage = compareScreenshots(file, standardfile);
            } catch (IOException e) {
                Utilities.logSevereMessage("Error during images comporation" + Utilities.convertStackTraceToString(e));
            }
            if (percentage >= diff) {
                return true;
            }
        }
        return false;
    }

    public static String getImageResolution(File imageFile) {
        BufferedImage image = null;
        String resolution = "";
        try {
            image = ImageIO.read(imageFile);
            Integer columns = image.getWidth();
            Integer rows = image.getHeight();
            resolution = columns + "x" + rows;
            image.flush();
        } catch (IOException e) {
            Utilities.logSevereMessage("Error during getting image resolution");

        }
        return resolution;
    }

    public static String getImageResolution(String url) {
        BufferedImage image = null;
        String resolution = "";
        try {
            image = ImageIO.read(new URL(url));
            Integer columns = image.getWidth();
            Integer rows = image.getHeight();
            resolution = columns + "x" + rows;
            image.flush();
        } catch (IOException e) {
            Utilities.logSevereMessage("Error during getting image resolution");

        }
        return resolution;
    }

    public static double compareScreenshots(String path1, String path2)
            throws IOException {
        File file1 = new File(path1);
        File file2 = new File(path2);
        return compareScreenshots(file1, file2);
    }

    public static boolean compareScreenshots(String path1, String path2,
                                             double diff) throws IOException {
        return compareScreenshots(path1, path2) > diff;
    }

    public static boolean compareScreenshots(File file1, File file2, double diff) {
        Utilities.logInfoMessage("Comparing screenshots");
        double fileSimilarity;
        try {
            fileSimilarity = compareScreenshots(file1, file2);
            Utilities.logInfoMessage("Equality is " + fileSimilarity + " %");
        } catch (Exception e) {
            throw new RuntimeException("unable to compare files "
                    + e.getMessage());
        }
        if (fileSimilarity < diff) {
            String link1 = DropBoxUtil.uploadFile(file1,
                    System.currentTimeMillis() + ".jpg");
            String link2 = DropBoxUtil.uploadFile(file2,
                    System.currentTimeMillis() + ".jpg");
            Utilities.logInfoMessage("Expected Result:" + "<br><a target='_blank' href='"
                    + link1 + "'> <img height='" + 200 + "' weight='" + 300
                    + "'src='" + link1 + "'/> </a>");

            Utilities.logInfoMessage("Actual result:" + "<br><a target='_blank' href='"
                    + link2 + "'> <img height='" + 200 + "' weight='" + 300
                    + "'src='" + link2 + "'/> </a>");
        }
        return fileSimilarity >= diff;
    }

    public static boolean compareListUrlsAndListFiles(List<String> urls, List<File> files, double diff) {
        if (urls.size() != files.size()) {
            return false;
        }
        List<File> newFiles = new ArrayList<>();
        long timestamp = System.currentTimeMillis();
        for (int i = 0; i < urls.size(); i++) {
            newFiles.add(new File(Config.getInstance().getPathToTempFiles()
                    + File.separator + "result" + i + "_" + timestamp + ".png"));
            ImageUtil.loadImage(urls.get(i), newFiles.get(i));
            newFiles.get(i).deleteOnExit();
        }
        return compareScreenshotsLists(files, newFiles, diff);
    }

    public static boolean compareScreenshotsLists(List<File> files1, List<File> files2, double diff) {
        if (files1.size() != files2.size()) {
            return false;
        }
        boolean status = true;
        for (int i = 0; i < files1.size(); i++) {
            status = status && compareScreenshots(files1.get(i), files2.get(i), diff);
        }
        return status;
    }

    public static boolean compareUrlAndUrl(String url1, String url2,
                                           double diff) {
        File fileFromUrl = new File(Config.getInstance()
                .getPathToTempFiles()
                + System.currentTimeMillis()
                + ".jpg");
        loadImage(url1, fileFromUrl);
        return compareScreenshotAndUrl(fileFromUrl, url2, diff);
    }

    public static boolean compareScreenshotAndUrl(File file, String url,
                                                  double diff) {
        {
            if (url.contains("?")) {
                url = url + "&";
            } else {
                url = url + "?";
            }
            url = url + "timestamp=" + System.currentTimeMillis();
            File fileFromUrl = new File(Config.getInstance()
                    .getPathToTempFiles() + System.currentTimeMillis() + ".jpg");
            loadImage(url, fileFromUrl);
            double fileSimilarity = 0.0;
            try {
                fileSimilarity = compareScreenshots(file, fileFromUrl);
                Utilities.logInfoMessage("Equality is " + fileSimilarity + " %");
            } catch (Exception e) {
                throw new RuntimeException("unable to compare files by url: " + url
                        + e.getMessage());
            }
            fileFromUrl.deleteOnExit();
            if (fileSimilarity < diff) {
                String link1 = DropBoxUtil.uploadFile(file,
                        System.currentTimeMillis() + ".jpg");
                String link2 = DropBoxUtil.uploadFile(fileFromUrl,
                        System.currentTimeMillis() + ".jpg");

                Utilities.logInfoMessage("Expected Result:" + "<br><a target='_blank' href='"
                        + link1 + "'> <img height='" + 200 + "' weight='" + 300
                        + "'src='" + link1 + "'/> </a>");

                Utilities.logInfoMessage("Actual result:" + "<br><a target='_blank' href='"
                        + link2 + "'> <img height='" + 200 + "' weight='" + 300
                        + "'src='" + link2 + "'/> </a>");
            }
            return fileSimilarity >= diff;
        }
    }

    public static boolean compareScreenshotAndUrl(String filePath, String url,
                                                  double diff) {
        File fileFromPath = new File(filePath);
        return compareScreenshotAndUrl(fileFromPath, url, diff);
    }

    public static boolean compareImageAndUrl(BufferedImage image, String url,
                                             double diff) {
        File fileFromImage = new File(Config.getInstance().getPathToTempFiles()
                + System.currentTimeMillis() + ".jpg");
        loadImage(image, fileFromImage);
        return compareScreenshotAndUrl(fileFromImage, url, diff);
    }


    public static double compareImages(BufferedImage image1, BufferedImage image2)
            throws IOException {
        Utilities.logInfoMessage(image1.getWidth() + "x" + image1.getHeight() + " : "
                + image2.getWidth() + "x" + image2.getHeight());
        Integer wrongNumb = 0;
        Integer columns = image1.getWidth();
        Integer rows = image1.getHeight();

        if (image1.getWidth() != image2.getWidth() || image1.getHeight() != image2.getHeight()) {
            return 0;
        }

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < columns; col++) {
                int rgb = image1.getRGB(col, row);
                int rgb2 = image2.getRGB(col, row);

                if (!comparePixels(rgb, rgb2)) {
                    wrongNumb++;
                }
            }
        }
        double pixels = (columns * rows);
        double d = Double.valueOf((wrongNumb / (pixels)) * 100);
        return 100 - d;
    }


    public static double compareScreenshots(File file1, File file2)
            throws IOException {
        BufferedImage image1 = ImageIO.read(file1);
        BufferedImage image2 = ImageIO.read(file2);
        return compareImages(image1, image2);
    }

    public static double compareScreenshotsPartially(File file1, File file2,
                                                     int x1, int y1, int x2, int y2) throws IOException {
        BufferedImage image1 = ImageIO.read(file1);
        BufferedImage image2 = ImageIO.read(file2);

        BufferedImage image1crop = image1.getSubimage(x1, y1, x2 - x1, y2 - y1);
        BufferedImage image2crop = image2.getSubimage(x1, y1, x2 - x1, y2 - y1);
        ImageIO.write(image1crop, "png", file1);
        ImageIO.write(image2crop, "png", file2);
        Integer wrongNumb = 0;
        Integer columns = x2 - x1;
        Integer rows = y2 - y1;
        for (int row = y1; row < y2; row++) {
            for (int col = x1; col < x2; col++) {
                int rgb = image1.getRGB(col, row);
                int rgb2 = image2.getRGB(col, row);
                if (!comparePixels(rgb, rgb2)) {
                    wrongNumb++;
                }
            }
        }
        double pixels = (columns * rows);
        double d = Double.valueOf((wrongNumb / (pixels)) * 100);
        image1.flush();
        image2.flush();
        image1crop.flush();
        image2crop.flush();
        return 100 - d;
    }

    public static double compareScreenshotsPartially(File file1, File file2,
                                                     int x1, int y1) throws IOException {
        BufferedImage image1 = ImageIO.read(file1);
        int x2 = image1.getWidth();
        int y2 = image1.getHeight();
        image1.flush();
        return compareScreenshotsPartially(file1, file2, x1, y1, x2, y2);
    }

    public static boolean compareScreenshotsPartially(File file1, File file2,
                                                      int x1, int y1, double diff) {
        double fileSimilarity;
        try {
            fileSimilarity = compareScreenshotsPartially(file1, file2, x1, y1);
        } catch (Exception e) {
            throw new RuntimeException("unable to compare files");
        }
        return fileSimilarity > diff;
    }

    public static boolean compareScreenshotsPartially(File file1, File file2,
                                                      int x1, int y1, int x2, int y2, double diff) {
        double fileSimilarity;
        try {
            fileSimilarity = compareScreenshotsPartially(file1, file2, x1, y1,
                    x2, y2);
        } catch (Exception e) {
            throw new RuntimeException("unable to compare files");
        }
        return fileSimilarity > diff;
    }

    public static File createRandomImage(int width, int height, String... fileExtension) {
        String ext = "png";
        if (fileExtension.length > 0) {
            ext = fileExtension[0];
        }
        String path = Config.getInstance().getPathToTempFiles() + File.separator + System.currentTimeMillis() + "." + ext;
        File resultFile = new File(path);
        createRandomImage(width, height, resultFile);
        return resultFile;
    }

    public static void createRandomImage(int width, int height, File resultFile) {

        BufferedImage img = new BufferedImage(width, height,
                BufferedImage.TYPE_INT_ARGB);

        // create random image pixel by pixel
        for (int y = 0; y < height; y++) {
            int a = (int) (Math.random() * 256); // alpha
            int r = (int) (Math.random() * 256); // red
            int g = (int) (Math.random() * 256); // green
            int b = (int) (Math.random() * 256); // blue
            for (int x = 0; x < width; x++) {
                int p = (a << 24) | (r << 16) | (g << 8) | b; // pixel
                img.setRGB(x, y, p);
            }
        }

        // write image
        try {
            ImageIO.write(img, "png", resultFile);
            img.flush();
        } catch (IOException e) {
            System.out.println("Error: " + e);
        }
    }

    public static List<File> createRandomImages(int width, int height, int count) {
        List<File> toReturn = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            String fileName = "TEMP_FILE" + System.currentTimeMillis() + ".png";
            String resultPath = Config.getInstance().getPathToTempFiles() + fileName;
            File file = new File(resultPath);
            ImageUtil.createRandomImage(width, height, file);
            toReturn.add(file);
        }
        return toReturn;
    }

    public static void createRokuHeaderImage(File logoImage, File resultFile,
                                             String brand) {
        try {
            String filePath = Config.getInstance()
                    .getPathToRokuHeaderGenerationImages(brand)
                    + rokuHeaderImageBase;
            File baseImage = new File(filePath);
            BufferedImage baseImg = ImageIO.read(baseImage);
            BufferedImage logoImg = ImageIO.read(logoImage);
            int targetLogoHeight = (int) Math.round(logoImg.getHeight() * 0.65);
            int targetLogoWidth = (int) Math.round(logoImg.getWidth() * 0.65);
            logoImg = Scalr.resize(logoImg, Scalr.Method.QUALITY,
                    Scalr.Mode.FIT_EXACT, targetLogoWidth, targetLogoHeight,
                    Scalr.OP_ANTIALIAS);
            int canvasWidth = baseImg.getWidth();
            int canvasHeight = baseImg.getHeight();

            BufferedImage im = new BufferedImage(canvasWidth, canvasHeight,
                    BufferedImage.TYPE_INT_ARGB);

            im.getGraphics().drawImage(baseImg, 0, 0, null);
            im.getGraphics().drawImage(logoImg, 1120, 50, null);
            ImageIO.write(im, "png", resultFile);
            baseImg.flush();
            logoImg.flush();
            im.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void stitchImages(File image1, File image2, File separator,
                                    int padding, Alignment verticalAlignment,
                                    Alignment horizontalAllignment, File resultFile) {
        try {
            BufferedImage img1 = ImageIO.read(image1);
            BufferedImage img2 = ImageIO.read(image2);
            BufferedImage imgSeparator = ImageIO.read(separator);
            int height1 = img1.getHeight();
            int height2 = img2.getHeight();
            int width1 = img1.getWidth();
            int width2 = img2.getWidth();
            int separatorWidth = imgSeparator.getWidth();
            int canvasWidth = width1 + width2 + imgSeparator.getWidth()
                    + padding * 2;
            int canvasHeight = max(height1, height2, separatorWidth);
            BufferedImage im = new BufferedImage(canvasWidth, canvasHeight,
                    BufferedImage.TYPE_INT_ARGB);
            if (horizontalAllignment.equals(Alignment.JUSTIFIED)) {
                switch (verticalAlignment) {
                    case TOP:
                        stitchVerticalTop(im, img1, img2, imgSeparator, padding);
                        break;
                    case BOTTOM:
                        stitchVerticalBottom(im, img1, img2, imgSeparator, padding);
                        break;
                    default:
                        stitchVerticalJustified(im, img1, img2, imgSeparator,
                                padding);
                        break;
                }
            }
            ImageIO.write(im, "png", resultFile);
            img1.flush();
            img2.flush();
            imgSeparator.flush();
            im.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static BufferedImage stitchVerticalJustified(BufferedImage im,
                                                         BufferedImage img1, BufferedImage img2, BufferedImage imgSeparator,
                                                         int padding) {
        int height1 = img1.getHeight();
        int height2 = img2.getHeight();
        int width1 = img1.getWidth();
        int separatorWidth = imgSeparator.getWidth();
        int canvasHeight = max(height1, height2, separatorWidth);
        im.getGraphics().drawImage(img1, 0, (canvasHeight - height1) / 2, null);
        im.getGraphics().drawImage(imgSeparator, width1 + padding,
                (canvasHeight - imgSeparator.getHeight()) / 2, null);
        im.getGraphics().drawImage(img2,
                width1 + padding + padding + imgSeparator.getWidth(),
                (canvasHeight - height2) / 2, null);
        return im;
    }

    private static BufferedImage stitchVerticalTop(BufferedImage im,
                                                   BufferedImage img1, BufferedImage img2, BufferedImage imgSeparator,
                                                   int padding) {
        int width1 = img1.getWidth();
        im.getGraphics().drawImage(img1, 0, 0, null);
        im.getGraphics().drawImage(imgSeparator, width1 + padding, 0, null);
        im.getGraphics().drawImage(img2,
                width1 + padding + padding + imgSeparator.getWidth(), 0, null);
        return im;
    }

    private static BufferedImage stitchVerticalBottom(BufferedImage im,
                                                      BufferedImage img1, BufferedImage img2, BufferedImage imgSeparator,
                                                      int padding) {
        int height1 = img1.getHeight();
        int height2 = img2.getHeight();
        int width1 = img1.getWidth();
        int separatorWidth = imgSeparator.getWidth();
        int separatorHeight = imgSeparator.getHeight();
        int canvasHeight = max(height1, height2, separatorWidth);
        im.getGraphics().drawImage(img1, 0, canvasHeight - height1, null);
        im.getGraphics().drawImage(imgSeparator, width1 + padding,
                canvasHeight - separatorHeight, null);
        im.getGraphics().drawImage(img2,
                width1 + padding + padding + imgSeparator.getWidth(),
                canvasHeight - height2, null);
        return im;
    }

    public static Integer[] getAverageRgbComponets(String imageName)
            throws IOException {
        File file = new File(imageName);
        BufferedImage image = ImageIO.read(file);
        Integer columns = image.getWidth();
        Integer rows = image.getHeight();
        int r = 0;
        int g = 0;
        int b = 0;
        Integer[] components;
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < columns; col++) {
                int rgb = image.getRGB(col, row);
                components = getSingleRGBcomponets(rgb);
                r += components[0];
                g += components[1];
                b += components[2];
            }
        }
        Integer[] values = new Integer[3];
        values[0] = Integer.valueOf(r / (rows * columns));
        values[1] = Integer.valueOf(g / (rows * columns));
        values[2] = Integer.valueOf(b / (rows * columns));
        return values;
    }

    public static Integer[] getSingleRGBcomponets(int rgb) {
        int red = (rgb >> 16) & 0xFF;
        int green = (rgb >> 8) & 0xFF;
        int blue = rgb & 0xFF;
        Integer[] values = new Integer[3];
        values[0] = red;
        values[1] = green;
        values[2] = blue;
        return values;
    }

    private static int max(int... values) {
        if (values == null) {
            return -1;
        }
        int max = values[0];
        for (int value : values) {
            if (value > max) {
                max = value;
            }
        }
        return max;
    }

    private static boolean comparePixels(final int rgb1, final int rgb2) {

        int red1 = (rgb1 >> 16) & 0xFF;
        int green1 = (rgb1 >> 8) & 0xFF;
        int blue1 = rgb1 & 0xFF;

        int red2 = (rgb2 >> 16) & 0xFF;
        int green2 = (rgb2 >> 8) & 0xFF;
        int blue2 = rgb2 & 0xFF;

        if (compareTwoValues(red1, red2) && compareTwoValues(green1, green2)
                && compareTwoValues(blue1, blue2)) {
            return true;
        } else {
            return false;
        }

    }

    private static Boolean compareTwoValues(int val1, int val2) {
        if (val1 == val2) {
            return true;
        }
        int k = Math.min(val1, val2);
        int f = (Math.max(val1, val2));
        double div = Double.valueOf(f - k) / Double.valueOf(f);
        return (div < coeff);
    }

    public static void loadImage(String url, String filePath) {
        String path = Config.getInstance().getPathToTempFiles() + File.separator + System.currentTimeMillis() + ".png";
        File resultFile = new File(path);
        loadImage(url, resultFile);
    }

    public static void loadImage(String url, File file) {
        try {
            BufferedImage img = ImageIO.read(new URL(url));
            if (!file.exists()) {
                file.createNewFile();
            }
            ImageIO.write(img, "png", file);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static File loadImage(String url) {
        String path = Config.getInstance().getPathToTempFiles() + File.separator + System.currentTimeMillis() + ".png";
        File resultFile = new File(path);
        loadImage(url, resultFile);
        return resultFile;
    }

    public static BufferedImage getImageFromFile(File imageFile) {
        BufferedImage image = null;
        try {
            image = ImageIO.read(imageFile);
        } catch (IOException e) {
            Utilities.logSevereMessage("Error during getting image resolution");

        }
        return image;
    }

    public static BufferedImage getImageFromUrl(String url) {
        BufferedImage img = null;
        try {
            img = ImageIO.read(new URL(url));
        } catch (Exception e) {
            Utilities.logSevereMessage("Error during getting image from URL " + url + " " + Utilities.convertStackTraceToString(e));
        }
        return img;
    }

    public static BufferedImage getImageFromInputStream(InputStream inputStream) {
        BufferedImage img = null;
        try {
            img = ImageIO.read(inputStream);
        } catch (Exception e) {
            Utilities.logSevereMessage("Error during getting image from input stream " + Utilities.convertStackTraceToString(e));
        }
        return img;
    }

    public static void loadImage(BufferedImage img, File file) {
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
            ImageIO.write(img, "jpg", file);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Resizes an image to a absolute width and height (the image may not be
     * proportional)
     *
     * @param inputImage
     *            picture that need resize
     * @param scaledWidth
     *            absolute width in pixels
     * @param scaledHeight
     *            absolute height in pixels
     *
     *  @return Resized image as BufferedImage object
     */
    public static BufferedImage resize(BufferedImage inputImage,
                                       int scaledWidth, int scaledHeight) {
        BufferedImage outputImage = inputImage.getSubimage(0, 0, inputImage.getWidth(), inputImage.getHeight());
        outputImage = Scalr.resize(outputImage, Scalr.Method.ULTRA_QUALITY,
                Scalr.Mode.FIT_EXACT, scaledWidth, scaledHeight,
                Scalr.OP_ANTIALIAS);

        /*BufferedImage outputImage = new BufferedImage(scaledWidth,
                scaledHeight, inputImage.getType());
        Graphics2D g2d = outputImage.createGraphics();
        g2d.drawImage(inputImage, 0, 0, scaledWidth, scaledHeight, null);
        g2d.dispose();*/
        return outputImage;
    }

    public static BufferedImage resize(String imageUrl, int scaledWidth,
                                       int scaledHeight) throws IOException {
        URL url = new URL(imageUrl);
        return resize(ImageIO.read(url), scaledWidth, scaledHeight);
    }

    public static BufferedImage resize(URL url, int scaledWidth,
                                       int scaledHeight) throws IOException {
        return resize(ImageIO.read(url), scaledWidth, scaledHeight);
    }

    /**
     * Resizes an image by a percentage of original size (proportional).
     *
     * @param inputImage
     *            picture that need resize
     * @param percent
     *            a double number specifies percentage of the output image over
     *            the input image.
     *
     * @return resized image as BufferedImage object
     */
    public static BufferedImage resize(BufferedImage inputImage, double percent) {
        int scaledWidth = (int) (inputImage.getWidth() * percent);
        int scaledHeight = (int) (inputImage.getHeight() * percent);
        return resize(inputImage, scaledWidth, scaledHeight);
    }

    public static BufferedImage resize(String imageUrl, double percent)
            throws IOException {
        URL url = new URL(imageUrl);
        return resize(ImageIO.read(url), percent);
    }

    public static BufferedImage resize(URL url, double percent)
            throws IOException {
        return resize(ImageIO.read(url), percent);
    }

    public static BufferedImage cutThumbnail(String imageUrl,
                                             ThumbnailsCutInterface thumbnailsProperties) {
        BufferedImage toReturn = null;
        BufferedImage resizedImage = null;
        BufferedImage image = null;
        try {
            image = ImageIO.read(new URL(imageUrl));
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (thumbnailsProperties.getPixelsToCut() == 0) {
            toReturn = resize(image, thumbnailsProperties.getWidth(),
                    thumbnailsProperties.getHeight());
        } else {
            if (thumbnailsProperties.isCutOffHeight()) {
                resizedImage = resize(image, thumbnailsProperties.getWidth(),
                        thumbnailsProperties.getHeight());
                // thumbnailsProperties.getPixelsToCut() / 2 because we need to
                // cut off a half count of pixels from bottom and half from head
                toReturn = resizedImage.getSubimage(0,
                        (thumbnailsProperties.getPixelsToCut() / 2),
                        thumbnailsProperties.getRequiredAppWidth(),
                        thumbnailsProperties.getRequiredAppHeight());
            } else {
                // thumbnailsProperties.getPixelsToCut() / 2 because we need to
                // cut off a half count of pixels from bottom and half from head
                resizedImage = resize(image, thumbnailsProperties.getWidth(),
                        thumbnailsProperties.getHeight());
                toReturn = resizedImage.getSubimage(
                        (thumbnailsProperties.getPixelsToCut() / 2), 0,
                        thumbnailsProperties.getRequiredAppWidth(),
                        thumbnailsProperties.getRequiredAppHeight());
            }
        }
        return toReturn;
    }

    public static int getWidth(String imageUrl) {
        return getImageFromUrl(imageUrl).getWidth();
    }

    public static int getHeight(String imageUrl) {
        return getImageFromUrl(imageUrl).getHeight();
    }


}
