package com.nbcuni.test.cms.utils.thumbnails.rokuimages;

import com.nbcuni.test.cms.utils.logging.TestRuntimeException;

public enum ProgramOneTileShowPageSource implements TileSource {

        USA("643885635932", "643889219707", ProgramOneTileShowPageSource.REGULAR_SIZE, "n/a"),
        BRAVO("640279619811", "640283715808", ProgramOneTileShowPageSource.REGULAR_SIZE, "n/a"),
        EONLINE("620062787743", "625370691571", ProgramOneTileShowPageSource.REGULAR_SIZE, "n/a"),
        SYFY("645593155759", "645639235605", ProgramOneTileShowPageSource.REGULAR_SIZE, "n/a"),
        TELEMUNDO("", "", ProgramOneTileShowPageSource.REGULAR_SIZE, "n/a");

        String testSourceMpxId;
        String secondSourceMpxId;
        String dimensions;
        String assetTypes;

        private static final String REGULAR_SIZE = "1973x918";

        private ProgramOneTileShowPageSource(String testSourceMpxId, String secondSourceMpxId, String dimensions,
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

        @Override
        public String[] getAssetTypes() {
                return assetTypes.split(",");
        }


        public static ProgramOneTileShowPageSource getSourcePerBrand(String brand) {
                for (ProgramOneTileShowPageSource entry : ProgramOneTileShowPageSource.values()) {
                        if (entry.name().equalsIgnoreCase(brand)) {
                                return entry;
                        }
                }
                throw new TestRuntimeException("There is no brand with name [" + brand + "]");
        }

        public String getWidth() {
                return dimensions.split("x")[0];
        }

        public String getHeight() {
                return dimensions.split("x")[1];
        }

}
