package com.nbcuni.test.cms.pageobjectutils.mvpd;

import com.nbcuni.test.cms.utils.logging.TestRuntimeException;

import java.util.*;

public enum BrandsMvpdAdmin {
    BRAVO("bravo", "bravo"),
    CNBC("cnbc", "cnbc"),
    CSN_BAY_AREA("CSN Bay Area", "CSNBayArea"),
    CSN_CALIFORNIA("CSN California", "CSNCalifornia"),
    CSN_CHICAGO("CSN Chicago", "CSNChicago"),
    CSN_MID_ATLANTIC("CSN Mid-Atlantic", "CSNMidAtlantic"),
    CSN_NEW_ENGLAND("CSN New England", "CSNNewEngland"),
    CSN_PHILADELPHIA("CSN Philadelphia", "CSNPhiladelphia"),
    EONLINE("e", "e"),
    ESQUIRE("Esquire", "style"),
    G4("g4", "g4"),
    GOLF("golf", "golf"),
    MSNBC("msnbc", "msnbc"),
    MUN2("mun2", "mun2"),
    NBCENTERTAINMENT("nbcentertainment", "nbcentertainment"),
    NBCNEWS("nbcnews", "nbcnews"),
    NBCSPORTS("nbcsports", "nbcsports"),
    OXYGEN("oxygen", "oxygen"),
    SPROUT("sprout", "sprout"),
    SYFY("syfy", "syfy"),
    TCN("TCN", "TCN"),
    TELEMUNDO("telemundo", "telemundo"),
    USA("usa", "usa");

    private static final List<BrandsMvpdAdmin> VALUES = Collections.unmodifiableList(Arrays.asList(values()));
    private static final Random RANDOM = new Random();
    private static final int SIZE = VALUES.size();
    private String brand;
    private String requestorId;

    BrandsMvpdAdmin(final String brand, final String requestorId) {
        this.brand = brand;
        this.requestorId = requestorId;
    }

    public static BrandsMvpdAdmin randomBrandName() {
        return VALUES.get(RANDOM.nextInt(SIZE));
    }

    public static BrandsMvpdAdmin randomBrandNameFromPopular() {
        int i = 0;
        while (i < 15) {
            BrandsMvpdAdmin brand = VALUES.get(RANDOM.nextInt(SIZE));
            if (brand.equals(CSN_BAY_AREA) || brand.equals(CSN_CALIFORNIA) || brand.equals(CSN_CHICAGO) || brand.equals(CSN_MID_ATLANTIC) || brand.equals(CSN_NEW_ENGLAND) || brand.equals(CSN_PHILADELPHIA)) {
                continue;
            } else {
                return brand;
            }
        }
        throw new TestRuntimeException("Unable to selecet rendom brand");
    }

    public static BrandsMvpdAdmin getBrandById(String requestorId) {
        for (BrandsMvpdAdmin brand : BrandsMvpdAdmin.VALUES) {
            if (brand.getRequestorId().equals(requestorId)) {
                return brand;
            }
        }
        return null;
    }

    public static BrandsMvpdAdmin getBrandByName(String name) {
        for (BrandsMvpdAdmin brand : BrandsMvpdAdmin.VALUES) {
            if (brand.getName().equals(name)) {
                return brand;
            }
        }
        return null;
    }

    public static List<BrandsMvpdAdmin> getListOfBrandsWithPlayerFeed() {
        List<BrandsMvpdAdmin> brands = new ArrayList<BrandsMvpdAdmin>();
        brands.add(BrandsMvpdAdmin.BRAVO);
        brands.add(BrandsMvpdAdmin.CNBC);
        brands.add(BrandsMvpdAdmin.EONLINE);
        brands.add(BrandsMvpdAdmin.G4);
        brands.add(BrandsMvpdAdmin.GOLF);
        brands.add(BrandsMvpdAdmin.MSNBC);
        brands.add(BrandsMvpdAdmin.MUN2);
        brands.add(BrandsMvpdAdmin.NBCENTERTAINMENT);
        brands.add(BrandsMvpdAdmin.NBCNEWS);
        brands.add(BrandsMvpdAdmin.NBCSPORTS);
        brands.add(BrandsMvpdAdmin.OXYGEN);
        brands.add(BrandsMvpdAdmin.SPROUT);
        brands.add(BrandsMvpdAdmin.ESQUIRE);
        brands.add(BrandsMvpdAdmin.SYFY);
        brands.add(BrandsMvpdAdmin.TELEMUNDO);
        brands.add(BrandsMvpdAdmin.USA);
        return brands;
    }

    public String getName() {
        return brand;
    }

    public String getRequestorId() {
        return requestorId;
    }
}
