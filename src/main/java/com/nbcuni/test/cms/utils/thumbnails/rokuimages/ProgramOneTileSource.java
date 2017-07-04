package com.nbcuni.test.cms.utils.thumbnails.rokuimages;

import com.nbcuni.test.cms.utils.logging.TestRuntimeException;

/**
 * Created by Aliaksei_Dzmitrenka on 6/1/2017.
 */
public enum ProgramOneTileSource implements TileSource {
    USA("643885635932", "643889219707", ProgramOneTileSource.REGULAR_SIZE, "n/a"),
    BRAVO("640279619811", "640283715808", ProgramOneTileSource.REGULAR_SIZE, "n/a"),
    EONLINE("620062787743", "625370691571", ProgramOneTileSource.REGULAR_SIZE, "n/a"),
    SYFY("645593155759", "645639235605", ProgramOneTileSource.REGULAR_SIZE, "n/a"),
    TELEMUNDO("", "", ProgramOneTileSource.REGULAR_SIZE, "n/a");


    String testSourceMpxId;
    String secondSourceMpxId;
    String dimensions;
    String assetTypes;

    private static final String REGULAR_SIZE = "1973x918";

    private ProgramOneTileSource(String testSourceMpxId, String secondSourceMpxId, String dimensions,
                                 String assetTypes) {
        this.testSourceMpxId = testSourceMpxId;
        this.secondSourceMpxId = secondSourceMpxId;
        this.dimensions = dimensions;
        this.assetTypes = assetTypes;
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
    public static ProgramOneTileSource getSourcePerBrand(String brand){
                for(ProgramOneTileSource entry : ProgramOneTileSource.values()){
                        if(entry.name().equalsIgnoreCase(brand)){
                                return entry;
                        }
                }
                throw new TestRuntimeException("There is no brand with name [" + brand + "]");
    }

    @Override
    public String[] getAssetTypes() {
        return assetTypes.split(",");
    }

    public String getWidth() {
        return dimensions.split("x")[0];
    }

    public String getHeight() {
        return dimensions.split("x")[1];
    }

}