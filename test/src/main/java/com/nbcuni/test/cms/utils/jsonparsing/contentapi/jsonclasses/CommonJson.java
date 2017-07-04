package com.nbcuni.test.cms.utils.jsonparsing.contentapi.jsonclasses;

import com.nbcuni.test.cms.utils.logging.TestRuntimeException;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * Created by Aliaksei_Dzmitrenka on 2/20/2017.
 */
public class CommonJson {

    /**
     * @author Aliaksei Dzmitrenka
     * <p/>
     * This class is represent Json with all nodes names and ids
     */

    Map<String, String> entities = new HashMap<>();

    public Map<String, String> getEntity() {
        return entities;
    }

    public CommonJson setEntity(Map<String, String> entities) {
        this.entities = entities;
        return this;
    }

    public String getEntityId(String name) {
        for (Map.Entry<String, String> entry : entities.entrySet()) {
            if (entry.getValue().equals(name)) {
                return entry.getKey();
            }
        }
        throw new TestRuntimeException("There is no exist entry with name [" + name + "]");
    }

    public boolean isEntityExist(String name) {
        for (Map.Entry<String, String> entry : entities.entrySet()) {
            if (entry.getValue().equals(name)) {
                return true;
            }
        }
        return false;
    }

    public String getRandomNode() {
        Random generator = new Random();
        Object[] keys = entities.keySet().toArray();
        return (String) keys[generator.nextInt(keys.length)];
    }

}
