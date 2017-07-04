package com.nbcuni.test.cms.pageobjectutils.entities.mvpd;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public enum SelectLogoType {

    WHITE("White"), BLACK("Black"), COLOR("Color"), NA("N/A"), CUSTOM("Custom");

    private static final Random RANDOM = new Random();
    private static List<SelectLogoType> concreateCollors = Arrays.asList(WHITE, BLACK, COLOR);
    private String name;

    SelectLogoType(final String name) {
        this.name = name;
    }

    public static SelectLogoType getItemByName(String itemName) {
        for (SelectLogoType type : SelectLogoType.values()) {
            if (type.get().equals(itemName)) {
                return type;
            }
        }
        return null;
    }

    public static SelectLogoType getItemFromConcreateCollorsExpectedItem(SelectLogoType expect) {
        SelectLogoType selectedLogoType;
        while (true) {
            selectedLogoType = concreateCollors.get(RANDOM.nextInt(concreateCollors.size()));
            if (!selectedLogoType.equals(expect)) {
                return selectedLogoType;
            }
        }
    }

    public String get() {
        return name;
    }
}
