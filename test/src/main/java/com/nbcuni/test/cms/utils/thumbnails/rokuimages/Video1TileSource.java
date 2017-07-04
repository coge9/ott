package com.nbcuni.test.cms.utils.thumbnails.rokuimages;

public enum Video1TileSource implements TileSource {

    USA("643951683928", "643947587877", "n/a", "Original Image"),
    BRAVO("642064963572", "642062915926", "n/a", "Original Image"),
    EONLINE("623657027779", "626140739679", "n/a", "Original Image"),
    SYFY("", "", "n/a", "Original Image"),
    TELEMUNDO("", "", "n/a", "Original Image");

    String testSourceMpxId;
    String secondSourceMpxId;
    String dimensions;
    String assetTypes;


    private Video1TileSource(String testSourceMpxId, String secondSourceMpxId, String dimensions,
                             String assetTypes) {
        this.testSourceMpxId = testSourceMpxId;
        this.secondSourceMpxId = secondSourceMpxId;
        this.dimensions = dimensions;
        this.assetTypes = assetTypes;
    }

    public static Video1TileSource getSourcePerBrand(String brand) {
        for (Video1TileSource entry : Video1TileSource.values()) {
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
