package com.nbcuni.test.cms.pageobjectutils.tve;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public enum BrandNames {

    NBCNEWS("nbcnews", "nbcnews"),
    TELEMUNDO("telemundo", "telemundo"),
    EONLINE("eonline", "e"),
    CNBC("cnbc", "cnbc"),
    ESQUIRE("esquire", "style"),
    NBCUNIVERSO("nbcUniverso", "mun2");

    private static final List<BrandNames> VALUES = Collections.unmodifiableList(Arrays.asList(values()));
    private static final Random RANDOM = new Random();
    private static final int SIZE = VALUES.size();
    private String brand;
    private String brandID;

    BrandNames(final String brand, final String brandID) {
        this.brand = brand;
        this.brandID = brandID;
    }

    public static String getBrandID(String brandName) {
        for (BrandNames brand : VALUES) {
            if (brand.getBrandName().equalsIgnoreCase(brandName)) {
                return brand.getBrandID();
            }
        }
        return null;
    }

    public static BrandNames randomBrandName() {
        return VALUES.get(RANDOM.nextInt(SIZE));
    }

    public String getBrandName() {
        return brand;
    }

    public String getBrandID() {
        return brandID;
    }

}
