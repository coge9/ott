package com.nbcuni.test.cms.pageobjectutils.chiller.collection;

import com.nbcuni.test.cms.utils.logging.TestRuntimeException;

import java.util.Random;

/**
 * Created by Ivan_Karnilau on 6/20/2017.
 */
public enum TileType {

    ONE_TILE("1 tile"),
    THREE_TILE("3 tile");

    private String name;

    TileType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static TileType getTileTypeByName(String name) {
        for (TileType tileType : values()) {
            if (tileType.getName().equals(name)) {
                return tileType;
            }
        }
        throw new TestRuntimeException("Tile Type with name '" + name + "' is not exist");
    }

    public static TileType getRandom() {
        return values()[new Random().nextInt(values().length)];
    }
}
