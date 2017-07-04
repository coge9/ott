package com.nbcuni.test.cms.pageobjectutils.entities.blocks;

public enum BlocksName {

    NONE("- None -"), HEADER("Header"), REGION_PREFIX("Main Region Prefix"), HIGHLIGHTED("Highlighted"),
    HELP("Help"), CONTENT("Content"), PRIMARY_SIDEBAR("Primary Sidebar"), SECONDARY_SIDEBAR("Secondary Sidebar"),
    REGION_SUFFIX("Main Region Suffix"), FOOTER("Footer"), DISABLED("Disabled");

    private String blockName;

    private BlocksName(final String blockName) {
        this.blockName = blockName;
    }

    public static BlocksName get(final String value) {
        for (final BlocksName name : BlocksName.values()) {
            if (name.getBlock().equals(value)) return name;
        }
        return null;
    }

    public String getBlock() {
        return blockName;
    }
}