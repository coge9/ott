package com.nbcuni.test.cms.tests.backend.tvecms;

import com.nbcuni.test.cms.pageobjectutils.chiller.CustomBrandNames;
import com.nbcuni.test.cms.pageobjectutils.tvecms.brands.RokuBrandNames;
import org.testng.annotations.DataProvider;

import java.util.List;

public class StaticBrandsProvider {

    private static final String brand = System.getProperty("brand");

    //  Used to return all brands except chiller
    @DataProvider(name = "brandDataProvider")
    public static Object[][] brandDataProvider() {
        Object[][] brandNamesArray = new Object[0][];
        if (brand == null || brand.equalsIgnoreCase("all")) {
            RokuBrandNames[] brands = RokuBrandNames.values();
            brandNamesArray = new Object[brands.length][1];
            for (int i = 0; i < brands.length; i++) {
                brandNamesArray[i][0] = brands[i].getBrandName();
            }
        } else {
            RokuBrandNames singleBrand = RokuBrandNames.getBrandByName(brand);
            if (singleBrand != null) {
                brandNamesArray = new Object[1][1];
                brandNamesArray[0][0] = brand;
            }
        }
        return brandNamesArray;
    }

    //  Used to return only chiller brand
    @DataProvider(name = "brandChillerDataProvider")
    public static Object[][] chillerDataProvider() {
        Object[][] brandNamesArray = new Object[0][];
        if (brand == null || brand.equalsIgnoreCase("all")) {
            CustomBrandNames[] brands = CustomBrandNames.values();
            brandNamesArray = new Object[brands.length][1];
            for (int i = 0; i < brands.length; i++) {
                brandNamesArray[i][0] = brands[i].getBrandName();
            }
        } else {
            CustomBrandNames singleBrand = CustomBrandNames.getBrandByName(brand);
            if (singleBrand != null) {
                brandNamesArray = new Object[1][1];
                brandNamesArray[0][0] = brand;
            }
        }
        return brandNamesArray;
    }

    //   Used to return brands which has roku platform
    @DataProvider(name = "brandHasRokuDataProvider")
    public static Object[][] brandDataProviderHeaders() {
        Object[][] brandNamesArray = new Object[0][];
        if (brand == null || brand.equalsIgnoreCase("all")) {
            List<RokuBrandNames> brands = RokuBrandNames.getBrandsWithRokuPlatform();
            brandNamesArray = new Object[brands.size()][1];
            int i = 0;
            for (RokuBrandNames brand : brands) {
                brandNamesArray[i++][0] = brand.getBrandName();
            }
        } else {
            RokuBrandNames singleBrand = RokuBrandNames.getBrandByName(brand);
            if (singleBrand != null && singleBrand.isHasRoku()) {
                brandNamesArray = new Object[1][1];
                brandNamesArray[0][0] = brand;
            }
        }
        return brandNamesArray;
    }

    //   Used to return brands which has android platform
    @DataProvider(name = "brandHasAndroidDataProvider")
    public static Object[][] brandDataProviderAndroid() {
        Object[][] brandNamesArray = new Object[0][];
        if (brand == null || brand.equalsIgnoreCase("all")) {
            List<RokuBrandNames> brands = RokuBrandNames.getBrandsWithAndroidPlatform();
            brandNamesArray = new Object[brands.size()][1];
            int i = 0;
            for (RokuBrandNames brand : brands) {
                brandNamesArray[i++][0] = brand.getBrandName();
            }
        } else {
            RokuBrandNames singleBrand = RokuBrandNames.getBrandByName(brand);
            if (singleBrand != null && singleBrand.isHasAndroid()) {
                brandNamesArray = new Object[1][1];
                brandNamesArray[0][0] = brand;
            }
        }
        return brandNamesArray;
    }

    //  Used to return brands which has IOS platform
    @DataProvider(name = "brandHasIOSDataProvider")
    public static Object[][] brandHasConcertoDataProvider() {
        Object[][] brandNamesArray = new Object[0][];
        if (brand == null || brand.equalsIgnoreCase("all")) {
            List<RokuBrandNames> brands = RokuBrandNames.getBrandsWithIosPlatform();
            brandNamesArray = new Object[brands.size()][1];
            int i = 0;
            for (RokuBrandNames brand : brands) {
                brandNamesArray[i++][0] = brand.getBrandName();
            }
        } else {
            RokuBrandNames singleBrand = RokuBrandNames.getBrandByName(brand);
            if (singleBrand != null && singleBrand.isHasConcerto()) {
                brandNamesArray = new Object[1][1];
                brandNamesArray[0][0] = brand;
            }
        }
        return brandNamesArray;
    }

    //  Used to return brands which has Concerto API instance including Chiller
    @DataProvider(name = "brandHasConcertoAPIDataProvider")
    public static Object[][] brandHasConcertoAPIDataProvider() {
        Object[][] brandNamesArray = new Object[0][];
        if (brand == null || brand.equalsIgnoreCase("all")) {
            Object[][] rokuBrands = StaticBrandsProvider.brandHasConcertoDataProvider();
            CustomBrandNames[] customBrandNames = CustomBrandNames.values();
            brandNamesArray = new Object[rokuBrands.length + customBrandNames.length][1];
            for (int i = 0; i < rokuBrands.length; i++) {
                brandNamesArray[i][0] = rokuBrands[i];
            }
            for (int i = 0; i < customBrandNames.length; i++) {
                brandNamesArray[i + rokuBrands.length][0] = customBrandNames[i].getBrandName();
            }
        } else {
            RokuBrandNames singleBrand = RokuBrandNames.getBrandByName(brand);
            if (singleBrand != null && singleBrand.isHasConcerto()) {
                brandNamesArray = new Object[1][1];
                brandNamesArray[0][0] = brand;
            }
            CustomBrandNames сcustomSingleBrand = CustomBrandNames.getBrandByName(brand);
            if (сcustomSingleBrand != null) {
                brandNamesArray = new Object[1][1];
                brandNamesArray[0][0] = brand;
            }
        }
        return brandNamesArray;
    }
}
