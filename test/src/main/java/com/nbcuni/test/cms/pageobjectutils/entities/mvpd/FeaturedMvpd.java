package com.nbcuni.test.cms.pageobjectutils.entities.mvpd;

public class FeaturedMvpd {
    Instance instance;
    String id;
    String weight;
    Platform platform;

    public FeaturedMvpd(String id, String weight, Platform platform,
                        Instance instance) {
        super();
        this.instance = instance;
        this.id = id;
        this.weight = weight;
        this.platform = platform;
    }

    public Platform getPlatform() {
        return platform;
    }

    public void setPlatform(Platform platform) {
        this.platform = platform;
    }

    public Instance getInstance() {
        return instance;
    }

    public void setInstance(Instance instance) {
        this.instance = instance;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

}
