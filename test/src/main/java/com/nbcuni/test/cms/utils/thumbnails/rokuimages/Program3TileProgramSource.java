package com.nbcuni.test.cms.utils.thumbnails.rokuimages;

public enum Program3TileProgramSource implements TileSource {

    USA("643889731991", "643889731991", "2560x1440", "Thumbnail2"),
    BRAVO("642008131979", "642008132002", "2560x1440", "Thumbnail2"),
    EONLINE("620117059621", "620065347919", "2560x1440", "Thumbnail2"),
    SYFY("645596227934", "645604931515", "2560x1440", "Thumbnail2"),
    TELEMUNDO("", "", "2560x1440", "Thumbnail2");

    String testSourceMpxId;
    String secondSourceMpxId;
    String dimensions;
    String assetTypes;


    private Program3TileProgramSource(String testSourceMpxId, String secondSourceMpxId, String dimensions,
                                      String assetTypes) {
        this.testSourceMpxId = testSourceMpxId;
        this.secondSourceMpxId = secondSourceMpxId;
        this.dimensions = dimensions;
        this.assetTypes = assetTypes;
    }

    public static Program3TileProgramSource getSourcePerBrand(String brand) {
        for (Program3TileProgramSource entry : Program3TileProgramSource.values()) {
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
