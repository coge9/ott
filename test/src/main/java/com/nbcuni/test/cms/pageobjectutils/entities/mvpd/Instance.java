package com.nbcuni.test.cms.pageobjectutils.entities.mvpd;

import com.nbcuni.test.cms.elements.DdlSelectable;
import com.nbcuni.test.webdriver.Utilities;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public enum Instance implements DdlSelectable {
    PROD, STAGE, QA, DEV, LOAD;

    private static final List<Instance> VALUES = Collections
            .unmodifiableList(Arrays.asList(values()));
    private static final int SIZE = VALUES.size();
    private static final Random RANDOM = new Random();

    public static Instance randomInstance() {
        return VALUES.get(RANDOM.nextInt(SIZE));
    }

    public static Instance randomInstanceExcept(Instance instance) {
        Instance selectedInstance;
        if (SIZE <= 1) {
            return null;
        }
        while (true) {
            selectedInstance = randomInstance();
            if (!instance.equals(selectedInstance)) {
                return selectedInstance;
            }
        }
    }

    public static Instance getInstanceByName(String name) {
        for (Instance instance : VALUES) {
            if (instance.get().equals(name)) {
                return instance;
            }
        }
        Utilities.logSevereMessageThenFail("No instance found by name '" + name + "'.");
        return null;
    }

    public String get() {
        return this.name().toLowerCase();
    }

    @Override
    public String getValueToSelect() {
        return get();
    }

    @Override
    public DdlSelectable getItemByText(String text) {
        return Instance.valueOf(text.toUpperCase());
    }
}