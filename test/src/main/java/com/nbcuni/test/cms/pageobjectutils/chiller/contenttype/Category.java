package com.nbcuni.test.cms.pageobjectutils.chiller.contenttype;

import java.util.*;

public enum Category {

    BACKSTAGE("Backstage"),
    EPISODE_RECAP("Episode Recap"),
    FUN_FACTS("Fun Facts"),
    RED_CARPET("Red Carpet");

    private static final List<Category> VALUES = Collections.unmodifiableList(Arrays.asList(values()));
    private static final int SIZE = VALUES.size();
    private static final Random RANDOM = new Random();

    private static final Map<String, Category> lookup = new HashMap<String, Category>();

    static {
        for (Category cat : Category.values()) {
            lookup.put(cat.getCategory(), cat);
        }
    }

    private String category;

    private String uuid;

    Category(String category) {
        this.category = category;
    }

    public static Category randomValue() {
        return VALUES.get(RANDOM.nextInt(SIZE));
    }

    public static Category get(String cat) {
        return lookup.get(cat);
    }

    public String getCategory() {
        return category;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

}
