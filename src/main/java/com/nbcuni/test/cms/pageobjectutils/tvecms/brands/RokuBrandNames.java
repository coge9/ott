package com.nbcuni.test.cms.pageobjectutils.tvecms.brands;

import com.nbcuni.test.cms.pageobjectutils.tvecms.CmsPlatforms;

import java.util.*;

public enum RokuBrandNames {

    USA("usa", "usa", new UsaPlatformMatcher()),
    BRAVO("bravo", "bravo", new BravoPlatformMatcher()),
    EONLINE("eonline", "eonline", new EonlinePlatformMatcher()),
    TELEMUNDO("telemundo", "telemundo", new TelemundoPlatformMatcher()),
    SYFY("syfy", "syfy", new SyfyPlatformMatcher()),
    OXYGEN("oxygen", "oxygen", new OxygenPlatformMatcher()),
    NBCUNIVERSO("nbcuniverso", "nbcuniverso", new NbcUniversoPlatformMatcher()),
    SPROUT("sprout", "sprout", new SproutPlatformMatcher()),
    CNBC("cnbc", "cnbc", new CnbcPlatformMatcher()),
    MSNBC("msnbc", "msnbc", new MsnbcPlatformMatcher()),
    ESQUIRE("esquire", "style", new EsquirePlatformMatcher());

    private static final List<RokuBrandNames> VALUES = Collections.unmodifiableList(Arrays.asList(values()));
    private static final Random RANDOM = new Random();
    private static final int SIZE = VALUES.size();
    final private String brand;
    final private String brandID;
    final private PlatformMatcher platformMatcher;

    RokuBrandNames(final String brand, final String brandID, final PlatformMatcher platformMatcher) {
        this.brand = brand;
        this.brandID = brandID;
        this.platformMatcher = platformMatcher;
    }

    public static String getBrandID(String brandName) {
        for (RokuBrandNames brand : VALUES) {
            if (brand.getBrandName().equalsIgnoreCase(brandName)) {
                return brand.getBrandID();
            }
        }
        return null;
    }

    public static RokuBrandNames getBrandByName(String brandName) {
        for (RokuBrandNames brand : VALUES) {
            if (brand.getBrandName().equalsIgnoreCase(brandName)) {
                return brand;
            }
        }
        return null;
    }

    public static RokuBrandNames randomBrandName() {
        return VALUES.get(RANDOM.nextInt(SIZE));
    }

    public static List<RokuBrandNames> getBrandsWithIosPlatform() {
        List<RokuBrandNames> toReturn = new LinkedList<RokuBrandNames>();
        for (RokuBrandNames brand : VALUES) {
            if (brand.isHasConcerto()) {
                toReturn.add(brand);
            }
        }
        return toReturn;
    }

    public static List<RokuBrandNames> getBrandsWithRokuPlatform() {
        List<RokuBrandNames> toReturn = new LinkedList<RokuBrandNames>();
        for (RokuBrandNames brand : VALUES) {
            if (brand.isHasRoku()) {
                toReturn.add(brand);
            }
        }
        return toReturn;
    }

    public static List<RokuBrandNames> getBrandsWithAndroidPlatform() {
        List<RokuBrandNames> toReturn = new LinkedList<RokuBrandNames>();
        for (RokuBrandNames brand : VALUES) {
            if (brand.isHasAndroid()) {
                toReturn.add(brand);
            }
        }
        return toReturn;
    }

    public String getBrandName() {
        return brand;
    }

    public String getBrandID() {
        return brandID;
    }

    public PlatformMatcher getPlatformMatcher() {
        return platformMatcher;
    }

    public boolean isHasRoku() {
        return platformMatcher.getPlatforms().contains(CmsPlatforms.ROKU);
    }

    public boolean isHasAndroid() {
        return platformMatcher.getPlatforms().contains(CmsPlatforms.ANDROID);
    }

    public boolean isHasConcerto() {
        for (CmsPlatforms platform : CmsPlatforms.getConcertoPlatforms()) {
            if (platformMatcher.getPlatforms().contains(platform)) {
                return true;
            }
        }
        return false;
    }

    public boolean isHasSerial() {
        for (CmsPlatforms platform : CmsPlatforms.getSerialPlatforms()) {
            if (platformMatcher.getPlatforms().contains(platform)) {
                return true;
            }
        }
        return false;
    }

}
