package com.nbcuni.test.cms.utils.thumbnails.ios.program;

import com.nbcuni.test.cms.utils.thumbnails.rokuimages.TileSource;

/**
 * Created by Aliaksei_Klimenka1 on 8/19/2016.
 */
public enum ProgramIos1600x900 implements TileSource {

    USA("747160131715", "747152963953", "1600x900", "iOS"),
    BRAVO("747169859824", "747171907583", "1600x900", "iOS"),
    EONLINE("747168323524", "747167811519", "1600x900", "iOS"),
    SYFY("", "", "1600x900", "iOS"),
    TELEMUNDO("", "", "1600x900", "iOS");

    String testSourceMpxId;
    String secondSourceMpxId;
    String dimensions;
    String assetTypes;

    private ProgramIos1600x900(String testSourceMpxId, String secondSourceMpxId, String dimensions,
                               String assetTypes) {
        this.testSourceMpxId = testSourceMpxId;
        this.secondSourceMpxId = secondSourceMpxId;
        this.dimensions = dimensions;
        this.assetTypes = assetTypes;
    }

    public static String getDimensionForBrand(String brand) {
        return ProgramIos1600x900.getSourcePerBrand(brand).getDimensions();
    }

    public static ProgramIos1600x900 getSourcePerBrand(String brand) {
        for (ProgramIos1600x900 entry : ProgramIos1600x900.values()) {
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
