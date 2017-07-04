package com.nbcuni.test.cms.pageobjectutils.chiller;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Created by Aleksandra_Lishaeva on 5/23/16.
 */
public enum CustomBrandNames {

    CHILLER("chiller", "chiller");

    private static final List<CustomBrandNames> VALUES = Collections.unmodifiableList(Arrays.asList(values()));
    private static final Random RANDOM = new Random();
    private static final int SIZE = VALUES.size();
    private String brand;
    private String brandID;

    CustomBrandNames(final String brand, final String brandID) {
        this.brand = brand;
        this.brandID = brandID;
    }

    public static String getBrandID(String brandName) {
        for (CustomBrandNames brand : VALUES) {
            if (brand.getBrandName().equalsIgnoreCase(brandName)) {
                return brand.getBrandID();
            }
        }
        return null;
    }

    public static CustomBrandNames getBrandByName(String brandName) {
        for (CustomBrandNames brand : VALUES) {
            if (brand.getBrandName().equalsIgnoreCase(brandName)) {
                return brand;
            }
        }
        return null;
    }

    public static CustomBrandNames randomBrandName() {
        return VALUES.get(RANDOM.nextInt(SIZE));
    }

    public String getBrandName() {
        return brand;
    }

    public String getBrandID() {
        return brandID;
    }

}
