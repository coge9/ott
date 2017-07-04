package com.nbcuni.test.cms.utils.thumbnails.rokuimages;

public enum Program3TileSource implements TileSource {

    USA("643891779620", "643889731991", "2560x1440", "Thumbnail3"),
    BRAVO("642008131977", "642008132002", "2560x1440", "Thumbnail3"),
    EONLINE("620117571728", "620065347919", "2560x1440", "Thumbnail3"),
    SYFY("645600323609", "645604931515", "2560x1440", "Thumbnail3"),
    TELEMUNDO("", "", "2560x1440", "Thumbnail3");

    String testSourceMpxId;
    String secondSourceMpxId;
    String dimensions;
    String assetTypes;


    private Program3TileSource(String testSourceMpxId, String secondSourceMpxId, String dimensions,
                               String assetTypes) {
        this.testSourceMpxId = testSourceMpxId;
        this.secondSourceMpxId = secondSourceMpxId;
        this.dimensions = dimensions;
        this.assetTypes = assetTypes;
    }

    public static Program3TileSource getSourcePerBrand(String brand) {
        for (Program3TileSource entry : Program3TileSource.values()) {
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
