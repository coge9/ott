package com.nbcuni.test.cms.utils.thumbnails.ios.program;

import com.nbcuni.test.cms.utils.thumbnails.rokuimages.TileSource;

/**
 * Created by Aliaksei_Klimenka1 on 8/19/2016.
 */
public enum ProgramIos1965x1108 implements TileSource {

    USA("747152963955", "747158595856", "1965x1108", "iOS"),
    BRAVO("747169347838", "747171907587", "1965x1108", "iOS"),
    EONLINE("747168323522", "747166787781", "1965x1108", "iOS"),
    SYFY("", "", "1965x1108", "iOS"),
    TELEMUNDO("", "", "1965x1108", "iOS");

    String testSourceMpxId;
    String secondSourceMpxId;
    String dimensions;
    String assetTypes;

    private ProgramIos1965x1108(String testSourceMpxId, String secondSourceMpxId, String dimensions,
                                String assetTypes) {
        this.testSourceMpxId = testSourceMpxId;
        this.secondSourceMpxId = secondSourceMpxId;
        this.dimensions = dimensions;
        this.assetTypes = assetTypes;
    }

    public static String getDimensionForBrand(String brand) {
        return ProgramIos1965x1108.getSourcePerBrand(brand).getDimensions();
    }

    public static ProgramIos1965x1108 getSourcePerBrand(String brand) {
        for (ProgramIos1965x1108 entry : ProgramIos1965x1108.values()) {
            if (entry.name().equalsIgnoreCase(brand)) {
                return entry;
            }
        }
        throw new RuntimeException("There is no brand with name [" + brand + "]");
    }

    @Override
    public String getTestSourceMpxId() {
        return testSourceMpxId;
    }

    @Override
    public String getSecondSourceMpxId() {
        return secondSourceMpxId;
    }

    @Override
    public String getDimensions() {
        return dimensions;
    }

    @Override
    public String[] getAssetTypes() {
        return assetTypes.split(",");
    }
}
