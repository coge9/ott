package com.nbcuni.test.cms.pageobjectutils.tvecms.concerto;

import com.nbcuni.test.cms.utils.logging.TestRuntimeException;

/**
 * Created by Ivan_Karnilau on 19-Jan-16.
 */
public enum ProgramConcertoImageSourceType {

    SOURCE_1600x900("1600x900", "Secondary", "field_ios_1600x900"),
    SOURCE_1965x1108("1965x1108", "Primary", "field_ios_1965x1108"),
    SOURCE_3TILE("2560x1440", "Roku-Small", "field_3_tile_source"),
    SOURCE_1600x900_APPLETV("1600x900", "Secondary", "field_program_appletv_1600x900"),
    SOURCE_1920x1080("1920x1080", "Hero-2", "field_program_appletv_1920x1080"),
    SOURCE_1920x486("1920x486", "Hero", "field_program_appletv_1920x486"),
    SOURCE_1704x440("1704x440", "Featured-4", "field_program_appletv_1704x440"),
    SOURCE_771x292("771x292", "Featured-3", "field_program_appletv_771x292"),
    SOURCE_600x600("600x600", "Logo-Tertiary", "field_program_appletv_600x600");

    private String type;
    private String usage;
    private String machineName;


    private ProgramConcertoImageSourceType(final String type, final String usage, final String machineName) {
        this.type = type;
        this.usage = usage;
        this.machineName = machineName;
    }

    public static String getUsageBySourceType(String imageTitle) {
        for (ProgramConcertoImageSourceType imageIosSourceType : values()) {
            if (imageTitle.contains(SOURCE_1965x1108.getType())
                    && imageTitle.contains(SOURCE_1600x900.getType())) {
                return SOURCE_1965x1108.getUsage();
            } else if (imageTitle.contains(imageIosSourceType.getType())) {
                return imageIosSourceType.getUsage();
            }
        }
        return null;
    }

    /**
     * The method to get enum constant that relates to the particular source size(type)
     *
     * @param type - source type(size itself)
     * @return - enum constant
     */
    public static ProgramConcertoImageSourceType getConcertoImageSourceType(String type) {
        for (ProgramConcertoImageSourceType imageIosSourceType : values()) {
            if (imageIosSourceType.getType().equalsIgnoreCase(type)) {
                return imageIosSourceType;
            }
        }
        throw new TestRuntimeException("Matching of the given source type: " + type + " wasn't found");
    }

    public String getType() {
        return type;
    }

    public String getUsage() {
        return usage;
    }

    public String getMachineName() {
        return machineName;
    }
}

